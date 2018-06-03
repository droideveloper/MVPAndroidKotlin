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
package org.fs.rx.extensions.v7.observable

import android.support.v7.widget.Toolbar
import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.rx.extensions.util.checkMainThread

class ToolbarNavigationClickObservable(private val view: Toolbar): Observable<View>() {

  override fun subscribeActual(observer: Observer<in View>?) {
    if (observer != null) {
      if (!observer.checkMainThread()) { return }

      val listener = Listener(view, observer)
      observer.onSubscribe(listener)
      view.setNavigationOnClickListener(listener)
    }
  }

  class Listener(private val view: Toolbar, private val observer: Observer<in View>): MainThreadDisposable(), View.OnClickListener {

    override fun onDispose() {
      view.setNavigationOnClickListener(null)
    }

    override fun onClick(view: View?) {
      if (!isDisposed) {
        observer.onNext(this.view)
      }
    }
  }
}