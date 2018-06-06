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
package org.fs.rx.extensions.design.observable

import android.support.design.widget.Snackbar
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.rx.extensions.util.checkMainThread

class SnackbarDismissObservable(private val view: Snackbar): Observable<Int>() {

  override fun subscribeActual(observer: Observer<in Int>?) {
    observer?.let {
      if (!it.checkMainThread()) { return }
      val listener = Listener(view, it)
      it.onSubscribe(listener)
    }
  }

  class Listener(private val view: Snackbar, private val observer: Observer<in Int>): MainThreadDisposable() {

    private val callback = object: Snackbar.Callback() {
      override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
        if (!isDisposed) {
          observer.onNext(event)
        }
      }
    }

    init {
      view.addCallback(callback)
    }

    override fun onDispose() {
      view.removeCallback(callback)
    }
  }
}