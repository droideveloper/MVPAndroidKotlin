/*
 * UIBinding Android Kotlin Copyright (C) 2017 Fatih, Brokoli Labs.
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
package org.fs.uibinding.observable

import android.view.MotionEvent
import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.uibinding.util.checkMainThread

class ViewTouchObservable(private val view: View, private val callback: (MotionEvent?) -> Boolean): Observable<MotionEvent>() {

  override fun subscribeActual(observer: Observer<in MotionEvent>?) {
    if (observer != null) {
      if (!observer.checkMainThread()) { return }

      val listener = Listener(view, observer, callback)
      observer.onSubscribe(listener)
      view.setOnTouchListener(listener)
    }
  }

  class Listener(private val view: View, private val observer: Observer<in MotionEvent>, private val callback: (MotionEvent?) -> Boolean): MainThreadDisposable(), View.OnTouchListener {

    override fun onDispose() {
      view.setOnTouchListener(null)
    }

    override fun onTouch(view: View?, event: MotionEvent): Boolean {
      if (!isDisposed) {
        observer.onNext(event)
        try {
          return callback(event)
        } catch (error: Throwable) {
          if (!isDisposed) {
            observer.onError(error)
            dispose()
          }
        }
      }
      return false // always false if we do not send anything
    }
  }
}