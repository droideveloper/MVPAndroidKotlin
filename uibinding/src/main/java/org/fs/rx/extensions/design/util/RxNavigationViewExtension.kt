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
package org.fs.rx.extensions.design.util

import android.support.design.widget.NavigationView
import android.view.MenuItem
import io.reactivex.Observable
import io.reactivex.functions.BiConsumer
import org.fs.rx.extensions.common.ControlProperty
import org.fs.rx.extensions.common.UIBindingObserver
import org.fs.rx.extensions.design.observable.NavigationViewItemSelectedObservable

fun NavigationView.checked(): UIBindingObserver<NavigationView, Int> = UIBindingObserver(this, BiConsumer { view, id -> view.setCheckedItem(id) })

fun NavigationView.checkedChanges(predicate: (MenuItem) -> Boolean = { _ -> true }): Observable<MenuItem> = NavigationViewItemSelectedObservable(this, predicate)

fun NavigationView.checkedProperty(predicate: (MenuItem) -> Boolean = { _ -> true }): ControlProperty<Int> = ControlProperty(checkedChanges(predicate).map { it.itemId }, checked())