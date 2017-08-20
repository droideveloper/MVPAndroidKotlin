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
package org.fs.uibinding.v4.observable

import android.support.v4.view.ViewPager
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.uibinding.model.ViewPagerScroll
import org.fs.uibinding.util.checkMainThread

class ViewPagerScrollEventObservable(private val view: ViewPager): Observable<ViewPagerScroll>() {

  override fun subscribeActual(observer: Observer<in ViewPagerScroll>?) {
    if (observer != null) {
      if (!observer.checkMainThread()) { return }

      val listener = Listener(view, observer)
      observer.onSubscribe(listener)
      view.addOnPageChangeListener(listener)
    }
  }

  class Listener(private val view: ViewPager, private val observer: Observer<in ViewPagerScroll>): MainThreadDisposable(), ViewPager.OnPageChangeListener {

    override fun onDispose() {
      view.removeOnPageChangeListener(this)
    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
      if (!isDisposed) {
        observer.onNext(ViewPagerScroll(position, positionOffset, positionOffsetPixels))
      }
    }
  }
}