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

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiConsumer
import org.fs.rx.extensions.common.ControlProperty
import org.fs.rx.extensions.common.UIBindingObserver
import org.fs.rx.extensions.v4.observable.SwipeRefreshRefreshingObservable

fun SwipeRefreshLayout.refreshes(): Observable<Boolean> = SwipeRefreshRefreshingObservable(this)

fun SwipeRefreshLayout.refresh(): UIBindingObserver<SwipeRefreshLayout, Boolean> = UIBindingObserver(this, BiConsumer { view, refreshing -> view.isRefreshing = refreshing })

fun SwipeRefreshLayout.refreshProperty(): ControlProperty<Boolean> = ControlProperty(refreshes(), refresh())