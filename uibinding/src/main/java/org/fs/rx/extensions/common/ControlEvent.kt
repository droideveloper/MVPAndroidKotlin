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
package org.fs.rx.extensions.common

import io.reactivex.rxjava3.android.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer

open class ControlEvent<T>constructor(source: Observable<T>): Observable<T>() {

  protected val events: Observable<T> = source.subscribeOn(AndroidSchedulers.mainThread())

  override fun subscribeActual(observer: Observer<in T>) = events.subscribe(observer)

  open fun asObservable(): Observable<T> = events
  open fun asControlEvent(): ControlEvent<T> = this
}