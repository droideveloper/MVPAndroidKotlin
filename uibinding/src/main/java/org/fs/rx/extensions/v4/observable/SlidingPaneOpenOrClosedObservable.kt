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
package org.fs.rx.extensions.v4.observable

import android.view.View
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.rx.extensions.util.checkMainThread

class SlidingPaneOpenOrClosedObservable(private val view: SlidingPaneLayout): Observable<Boolean>() {

  override fun subscribeActual(observer: Observer<in Boolean>) {
    if (observer.checkMainThread()) {
      val listener = Listener(view, observer)
      observer.onSubscribe(listener)
      view.setPanelSlideListener(listener)
    }
  }

  class Listener(private val view: SlidingPaneLayout, private val observer: Observer<in Boolean>): MainThreadDisposable(), SlidingPaneLayout.PanelSlideListener {

    override fun onDispose() {
      view.setPanelSlideListener(null)
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {}

    override fun onPanelClosed(panel: View) {
      if (!isDisposed) {
        observer.onNext(false)
      }
    }

    override fun onPanelOpened(panel: View) {
      if (!isDisposed) {
        observer.onNext(true)
      }
    }
  }
}