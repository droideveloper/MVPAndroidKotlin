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
package org.fs.uibinding.v4.util

import android.support.v4.view.ViewPager
import io.reactivex.Observable
import io.reactivex.functions.BiConsumer
import org.fs.uibinding.common.ControlPorperty
import org.fs.uibinding.common.UIBindingObserver
import org.fs.uibinding.model.ViewPagerScroll
import org.fs.uibinding.util.detaches
import org.fs.uibinding.v4.observable.ViewPagerPageChangedObservable
import org.fs.uibinding.v4.observable.ViewPagerScrollEventObservable
import org.fs.uibinding.v4.observable.ViewPagerScrollStateObservable

fun ViewPager.pageChanges(): Observable<Int> = ViewPagerPageChangedObservable(this).takeUntil(detaches())

fun ViewPager.scrolls(): Observable<ViewPagerScroll> = ViewPagerScrollEventObservable(this).takeUntil(detaches())

fun ViewPager.scrollStateChanges(): Observable<Int> = ViewPagerScrollStateObservable(this).takeUntil(detaches())

fun ViewPager.page(): UIBindingObserver<ViewPager, Int> = UIBindingObserver(this, BiConsumer { view, page -> view.setCurrentItem(page, true) })

fun ViewPager.pageProperty(): ControlPorperty<Int> = ControlPorperty(pageChanges(), page())