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

import android.support.v7.widget.SearchView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.rx.extensions.util.checkMainThread

class SearchViewQueryTextChangedObservable(private val view: SearchView, private val predicate: (CharSequence) -> Boolean): Observable<CharSequence>() {

  override fun subscribeActual(observer: Observer<in CharSequence>?) {
    if (observer != null) {
      if (!observer.checkMainThread()) { return }

      val listener = Listener(view, observer, predicate)
      observer.onSubscribe(listener)
      view.setOnQueryTextListener(listener)
    }
  }

  class Listener(private val view: SearchView, private val observer: Observer<in CharSequence>, private val predicate: (CharSequence) -> Boolean): MainThreadDisposable(), SearchView.OnQueryTextListener {
    override fun onDispose() {
     view.setOnQueryTextListener(null)
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
      if (!isDisposed) {
        if (newText != null) {
          observer.onNext(newText)
          return predicate(newText)
        }
      }
      return false
    }
  }
}