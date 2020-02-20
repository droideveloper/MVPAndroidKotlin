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
import io.reactivex.rxjava3.android.disposable.MainThreadDisposable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import org.fs.rx.extensions.model.LayoutState
import org.fs.rx.extensions.util.checkMainThread

class ViewLayoutStateObservable(private val view: View): Observable<LayoutState>() {

  override fun subscribeActual(observer: Observer<in LayoutState>) {
    if (observer.checkMainThread()) {
      val listener = Listener(view, observer)
      observer.onSubscribe(listener)
      view.addOnLayoutChangeListener(listener)
    }
  }

  class Listener(private val view: View, private val observer: Observer<in LayoutState>): MainThreadDisposable(), View.OnLayoutChangeListener {

    override fun onDispose() {
      view.removeOnLayoutChangeListener(this)
    }

    override fun onLayoutChange(view: View?, l: Int, t: Int, r: Int, b: Int, pl: Int, pt: Int, pr: Int, pb: Int) {
      if (!isDisposed) {
        observer.onNext(LayoutState(this.view, l, t, r, b))
      }
    }
  }
}