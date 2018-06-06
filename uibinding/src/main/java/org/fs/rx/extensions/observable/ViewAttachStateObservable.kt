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
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.rx.extensions.model.ViewAttachState
import org.fs.rx.extensions.util.checkMainThread

class ViewAttachStateObservable(private val view: View): Observable<ViewAttachState>() {

  override fun subscribeActual(observer: Observer<in ViewAttachState>?) {
    observer?.let {
      if (!it.checkMainThread()) { return }
      val listener = Listener(view, it)
      it.onSubscribe(listener)
      view.addOnAttachStateChangeListener(listener)
    }
  }

  class Listener(private val view: View, private val observer: Observer<in ViewAttachState>): MainThreadDisposable(), View.OnAttachStateChangeListener {

    override fun onDispose() {
      view.removeOnAttachStateChangeListener(this)
    }

    override fun onViewDetachedFromWindow(view: View?) {
      if (!isDisposed) {
        observer.onNext(ViewAttachState.DETACHED)
      }
    }

    override fun onViewAttachedToWindow(view: View?) {
      if (!isDisposed) {
        observer.onNext(ViewAttachState.ATTACHED)
      }
    }
  }
}