/*
 * Playz Android Kotlin Copyright (C) 2018 Fatih.
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
package org.fs.rx.extensions.design.observable

import com.google.android.material.tabs.TabLayout
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.rx.extensions.util.checkMainThread

/**
 * Will observe only tab layout item selection in the manner
 * that will allow user the new selected tab will be showed in observer
 */
class TabLayoutTabSelectedObservable(private val view: TabLayout, private val reselectAllowed: Boolean = false): Observable<TabLayout.Tab>() {

  override fun subscribeActual(observer: Observer<in TabLayout.Tab>) {
    if (observer.checkMainThread()) {
      val listener = Listener(view, observer, reselectAllowed)
      observer.onSubscribe(listener)
      view.addOnTabSelectedListener(listener)
    }
  }

  /**
   * Listener only allowed for TabLayout.OnTabSelectedListener implementation for observing selected tab
   */
  class Listener(private val view: TabLayout, private val observer: Observer<in TabLayout.Tab>, private val reselectAllowed: Boolean): MainThreadDisposable(), TabLayout.OnTabSelectedListener {

    override fun onDispose() {
      view.removeOnTabSelectedListener(this)
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
      if (!isDisposed && reselectAllowed) {
        observer.onNext(tab)
      }
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {} // no opt

    override fun onTabSelected(tab: TabLayout.Tab) {
      if (!isDisposed) {
        observer.onNext(tab)
      }
    }
  }
}