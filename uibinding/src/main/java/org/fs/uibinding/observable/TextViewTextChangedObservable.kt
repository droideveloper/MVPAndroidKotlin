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

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.uibinding.util.checkMainThread

class TextViewTextChangedObservable(private val view: TextView): Observable<CharSequence>() {

  override fun subscribeActual(observer: Observer<in CharSequence>?) {
    if (observer != null) {
      if (!observer.checkMainThread()) { return }

      val listener = Listener(view, observer)
      observer.onSubscribe(listener)
      view.addTextChangedListener(listener)
    }
  }

  class Listener(private val view: TextView, private val observer: Observer<in CharSequence>): MainThreadDisposable(), TextWatcher {

    override fun onDispose() {
      view.removeTextChangedListener(this)
    }

    override fun afterTextChanged(all: Editable?) {}
    override fun beforeTextChanged(ch: CharSequence?, start: Int, end: Int, count: Int) {}

    override fun onTextChanged(text: CharSequence, start: Int, end: Int, count: Int) {
      if (!isDisposed) {
        observer.onNext(text)
      }
    }
  }
}