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
package org.fs.rx.extensions.design.util

import com.google.android.material.tabs.TabLayout
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiConsumer
import org.fs.rx.extensions.common.UIBindingObserver
import org.fs.rx.extensions.design.observable.TabLayoutTabSelectedObservable

fun TabLayout.selects(): Observable<TabLayout.Tab> = TabLayoutTabSelectedObservable(this)
fun TabLayout.reselects(): Observable<TabLayout.Tab> = TabLayoutTabSelectedObservable(this, true)
fun TabLayout.select(): UIBindingObserver<TabLayout, Int> = UIBindingObserver(this, BiConsumer { view, index ->
  (0..view.tabCount).filter { it == index }
    .map { view.getTabAt(it) }
    .forEach { it?.select()  }
})
