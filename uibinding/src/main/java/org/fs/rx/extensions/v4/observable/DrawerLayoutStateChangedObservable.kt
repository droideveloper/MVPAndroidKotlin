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
import androidx.drawerlayout.widget.DrawerLayout
import io.reactivex.rxjava3.android.disposable.MainThreadDisposable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import org.fs.rx.extensions.util.checkMainThread

class DrawerLayoutStateChangedObservable(private val view: DrawerLayout): Observable<Int>() {

  override fun subscribeActual(observer: Observer<in Int>) {
    if (observer.checkMainThread()) {
      val listener = Listener(view, observer)
      observer.onSubscribe(listener)
      view.addDrawerListener(listener)
    }
  }

  class Listener(private val view: DrawerLayout, private val observer: Observer<in Int>) : MainThreadDisposable(), DrawerLayout.DrawerListener {

    override fun onDispose() {
      view.removeDrawerListener(this)
    }

    override fun onDrawerClosed(drawerView: View) {}
    override fun onDrawerOpened(drawerView: View) {}
    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

    override fun onDrawerStateChanged(newState: Int) {
      if (!isDisposed) {
        observer.onNext(newState)
      }
    }
  }
}