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

import androidx.viewpager.widget.ViewPager
import io.reactivex.rxjava3.android.disposable.MainThreadDisposable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import org.fs.rx.extensions.model.ViewPagerScroll
import org.fs.rx.extensions.util.checkMainThread

class ViewPagerScrollEventObservable(private val view: ViewPager): Observable<ViewPagerScroll>() {

  override fun subscribeActual(observer: Observer<in ViewPagerScroll>) {
    if (observer.checkMainThread()) {
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