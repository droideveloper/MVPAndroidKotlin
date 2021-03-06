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

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import io.reactivex.rxjava3.android.disposable.MainThreadDisposable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import org.fs.rx.extensions.util.checkMainThread

class ToolbarMenuItemClickObservable(private val view: Toolbar, private val predicate: (MenuItem) -> Boolean): Observable<MenuItem>() {

  override fun subscribeActual(observer: Observer<in MenuItem>) {
    if (observer.checkMainThread()) {
      val listener = Listener(view, observer, predicate)
      observer.onSubscribe(listener)
      view.setOnMenuItemClickListener(listener)
    }
  }

  class Listener(private val view: Toolbar, private val observer: Observer<in MenuItem>, private val predicate: (MenuItem) -> Boolean): MainThreadDisposable(), Toolbar.OnMenuItemClickListener {

    override fun onDispose() {
      view.setOnMenuItemClickListener(null)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
      if (!isDisposed) {
        if (item != null) {
          observer.onNext(item)
          return predicate(item)
        }
      }
      return false
    }
  }
}