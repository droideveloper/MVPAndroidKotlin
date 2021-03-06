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
package org.fs.rx.extensions.v4.util

import androidx.viewpager.widget.ViewPager
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiConsumer
import org.fs.rx.extensions.common.ControlProperty
import org.fs.rx.extensions.common.UIBindingObserver
import org.fs.rx.extensions.model.ViewPagerScroll
import org.fs.rx.extensions.v4.observable.ViewPagerPageChangedObservable
import org.fs.rx.extensions.v4.observable.ViewPagerScrollEventObservable
import org.fs.rx.extensions.v4.observable.ViewPagerScrollStateObservable

fun ViewPager.pageChanges(): Observable<Int> = ViewPagerPageChangedObservable(this)

fun ViewPager.scrolls(): Observable<ViewPagerScroll> = ViewPagerScrollEventObservable(this)

fun ViewPager.scrollStateChanges(): Observable<Int> = ViewPagerScrollStateObservable(this)

fun ViewPager.page(smoothScroll: Boolean = true): UIBindingObserver<ViewPager, Int> = UIBindingObserver(this, BiConsumer { view, page -> view.setCurrentItem(page, smoothScroll) })
fun ViewPager.offscreenPageLimit(): UIBindingObserver<ViewPager, Int> = UIBindingObserver(this, BiConsumer { view, offscreenPageLimit -> view.offscreenPageLimit = offscreenPageLimit })


fun ViewPager.pageProperty(): ControlProperty<Int> = ControlProperty(pageChanges(), page())