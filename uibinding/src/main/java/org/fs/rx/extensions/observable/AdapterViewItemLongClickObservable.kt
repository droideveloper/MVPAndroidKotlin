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

import android.view.View
import android.widget.AdapterView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.rx.extensions.util.checkMainThread

class AdapterViewItemLongClickObservable(private val view: AdapterView<*>, private val predicate: (View) -> Boolean): Observable<Int>() {

  override fun subscribeActual(observer: Observer<in Int>?) {
    if (observer != null) {
      if (!observer.checkMainThread()) { return }

      val listener = Listener(view, observer, predicate)
      observer.onSubscribe(listener)
      view.onItemLongClickListener = listener
    }
  }

  class Listener(private val view: AdapterView<*>, private val observer: Observer<in Int>, private val predicate: (View) -> Boolean): MainThreadDisposable(), AdapterView.OnItemLongClickListener {

    override fun onDispose() {
      view.onItemLongClickListener = null
    }

    override fun onItemLongClick(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
      if (!isDisposed) {
        observer.onNext(position)
        if (view != null) {
          return predicate(view)
        }
      }
      return false
    }
  }
}