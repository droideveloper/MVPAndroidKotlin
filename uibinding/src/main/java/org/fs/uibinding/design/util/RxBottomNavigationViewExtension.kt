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
package org.fs.uibinding.design.util

import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import io.reactivex.Observable
import io.reactivex.functions.BiConsumer
import org.fs.uibinding.common.ControlPorperty
import org.fs.uibinding.common.UIBindingObserver
import org.fs.uibinding.design.observable.BottomNavigationViewItemSelectedObservable
import org.fs.uibinding.util.detaches

fun BottomNavigationView.selected(): UIBindingObserver<BottomNavigationView, Int> = UIBindingObserver(this, BiConsumer { view, id -> view.selectedItemId = id })

fun BottomNavigationView.selectedChanges(predicate: (MenuItem) -> Boolean = { _ -> true }): Observable<MenuItem> = BottomNavigationViewItemSelectedObservable(this, predicate).takeUntil(detaches())

fun BottomNavigationView.selectedPropert(predicate: (MenuItem) -> Boolean = { _ -> true }): ControlPorperty<Int> = ControlPorperty(selectedChanges(predicate).map { it.itemId }, selected())