/*
 * UIBinding Android Kotlin Copyright (C) 2018 Fatih.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fs.rx.extensions.v7.observable

import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.fs.rx.extensions.util.checkMainThread
import java.util.concurrent.atomic.AtomicBoolean

class RecyclerViewOnScrollObservable(private val view: RecyclerView, private val visibleThreshold: Int): Observable<Boolean>() {

  companion object {
    @JvmStatic val DEFAULT_VISIBLE_THRESHOLD = 5
  }

  override fun subscribeActual(observer: Observer<in Boolean>) {
    if (observer.checkMainThread()) {
      val listener = Listener(view, observer, visibleThreshold)
      observer.onSubscribe(listener)
      view.addOnScrollListener(listener)
    }
  }

  class Listener(private val view: RecyclerView, private val observer: Observer<in Boolean>, private val visibleThreshold: Int): RecyclerView.OnScrollListener(), Disposable {

    private var firstVisibleItem: Int? = null
    private var visibleItemCount:Int? = null
    private var totalItemCount:Int? = null
    private var previousTotal: Int? = null
    private var loading: Boolean? = null

    private var target: Int? = null
    private var visibleItems: Int? = null

    private val unsubscribed = AtomicBoolean()

    override fun isDisposed(): Boolean {
      return unsubscribed.get()
    }

    init {
      firstVisibleItem = 0
      visibleItemCount = 0
      totalItemCount = 0
      previousTotal = 0
      loading = false
      target = 0
      visibleItems = 0
    }

    override fun dispose() {
      if (unsubscribed.compareAndSet(false, true)) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
          onDispose()
        } else {
          AndroidSchedulers.mainThread().scheduleDirect { this.onDispose() }
        }
      }
    }

    fun onDispose() {
      view.removeOnScrollListener(this)
    }

    private val layoutManager: RecyclerView.LayoutManager? = view.layoutManager

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
      if (dy <= 0) return

      visibleItemCount = view.childCount
      totalItemCount = layoutManager?.itemCount
      firstVisibleItem = firstVisibleItemPosition()

      if (loading == true) {
        if (totalItemCount != previousTotal) {
          loading = false
          previousTotal = totalItemCount
        }
      }

      if ((loading == false) && (totalItemCount ?: 0) - (visibleItemCount ?: 0) <= (firstVisibleItem ?: 0) + visibleThreshold) {
        if (!isDisposed) {
          observer.onNext(true)
        }
        loading = true
      }
    }

    private fun firstVisibleItemPosition(): Int = when (layoutManager) {
      is LinearLayoutManager -> layoutManager.findFirstVisibleItemPosition()
      is StaggeredGridLayoutManager -> {
        val array = layoutManager.findFirstVisibleItemPositions(null)
        if (array.isEmpty()) 0 else array.first()
      }
      else -> 0
    }
  }
}