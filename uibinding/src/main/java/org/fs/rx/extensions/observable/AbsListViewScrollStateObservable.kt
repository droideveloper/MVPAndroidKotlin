/*
 * UIBinding Android Kotlin Copyright (C) 2017 Fatih.
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
package org.fs.rx.extensions.observable

import android.widget.AbsListView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.rx.extensions.util.checkMainThread

class AbsListViewScrollStateObservable(private val view: AbsListView): Observable<Int>() {

  override fun subscribeActual(observer: Observer<in Int>) {
    if (observer.checkMainThread()) {
      val listener = Listener(view, observer)
      observer.onSubscribe(listener)
      view.setOnScrollListener(listener)
    }
  }

  class Listener(private val view: AbsListView, private val observer: Observer<in Int>): MainThreadDisposable(), AbsListView.OnScrollListener {

    override fun onDispose() {
      view.setOnScrollListener(null)
    }

    override fun onScroll(absView: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {}

    override fun onScrollStateChanged(absView: AbsListView?, scrollState: Int) {
      if (!isDisposed) {
        observer.onNext(scrollState)
      }
    }
  }
}