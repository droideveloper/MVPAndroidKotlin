/*
 * MVP Android Kotlin Copyright (C) 2017 Fatih.
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
package org.fs.rx.extensions.util

import android.widget.TextView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiConsumer
import org.fs.rx.extensions.common.ControlProperty
import org.fs.rx.extensions.common.UIBindingObserver
import org.fs.rx.extensions.observable.TextViewTextChangedObservable

fun TextView.textChanges(): Observable<CharSequence> = TextViewTextChangedObservable(this)

fun TextView.text(): UIBindingObserver<TextView, CharSequence> = UIBindingObserver(this, BiConsumer { view, text -> view.text = text })

fun TextView.textProperty(): ControlProperty<CharSequence> = ControlProperty(textChanges(), text())
