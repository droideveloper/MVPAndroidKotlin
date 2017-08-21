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
package org.fs.uibinding.util

import android.view.View
import android.widget.AdapterView
import io.reactivex.Observable
import io.reactivex.functions.BiConsumer
import org.fs.uibinding.common.ControlProperty
import org.fs.uibinding.common.UIBindingObserver
import org.fs.uibinding.observable.AdapterViewItemClickObservable
import org.fs.uibinding.observable.AdapterViewItemLongClickObservable
import org.fs.uibinding.observable.AdapterViewItemSelectedObservable

fun AdapterView<*>.itemSelected(): Observable<Int> = AdapterViewItemSelectedObservable(this).takeUntil(detaches())

fun AdapterView<*>.select(): UIBindingObserver<AdapterView<*>, Int> = UIBindingObserver(this, BiConsumer { view, position ->  view.setSelection(position) })

fun AdapterView<*>.selectProperty(): ControlProperty<Int> = ControlProperty(itemSelected(), select())

fun AdapterView<*>.itemLongClicks(predicate: (View) -> Boolean = { _ -> true }): Observable<Int> = AdapterViewItemLongClickObservable(this, predicate).takeUntil(detaches())

fun AdapterView<*>.itemClicks(): Observable<Int> = AdapterViewItemClickObservable(this).takeUntil(detaches())