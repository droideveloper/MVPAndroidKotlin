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

import android.widget.SeekBar
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.rx.extensions.model.SeekState
import org.fs.rx.extensions.util.checkMainThread

class SeekBarProgressChangedObservable(private val view: SeekBar): Observable<SeekState>() {

  override fun subscribeActual(observer: Observer<in SeekState>) {
    if (observer.checkMainThread()) {
      val listener = Listener(view, observer)
      observer.onSubscribe(listener)
      view.setOnSeekBarChangeListener(listener)
    }
  }

  class Listener(private val view: SeekBar, private val observer: Observer<in SeekState>): MainThreadDisposable(), SeekBar.OnSeekBarChangeListener {

    override fun onDispose() {
      view.setOnSeekBarChangeListener(null)
    }

    override fun onProgressChanged(view: SeekBar?, progress: Int, byUser: Boolean) {
      if (!isDisposed) {
        observer.onNext(SeekState(this.view, progress, byUser))
      }
    }

    override fun onStartTrackingTouch(view: SeekBar?) {}
    override fun onStopTrackingTouch(view: SeekBar?) {}
  }
}