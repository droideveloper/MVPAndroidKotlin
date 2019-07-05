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

import com.google.android.material.textfield.TextInputLayout
import io.reactivex.functions.BiConsumer
import org.fs.rx.extensions.common.UIBindingObserver

fun TextInputLayout.counterEnabled(): UIBindingObserver<TextInputLayout, Boolean> = UIBindingObserver(this, BiConsumer { view, enabled -> view.isCounterEnabled = enabled })
fun TextInputLayout.counterMax(): UIBindingObserver<TextInputLayout, Int> = UIBindingObserver(this, BiConsumer { view, counter -> view.counterMaxLength = counter })
fun TextInputLayout.hintEnabled(): UIBindingObserver<TextInputLayout, Boolean> = UIBindingObserver(this, BiConsumer { view, enabled -> view.isHintEnabled = enabled })
fun TextInputLayout.errorEnabled(): UIBindingObserver<TextInputLayout, Boolean> = UIBindingObserver(this, BiConsumer { view, enabled -> view.isErrorEnabled = enabled })
fun TextInputLayout.hint(): UIBindingObserver<TextInputLayout, CharSequence> = UIBindingObserver(this, BiConsumer { view, hint -> view.hint = hint })
fun TextInputLayout.error(): UIBindingObserver<TextInputLayout, CharSequence> = UIBindingObserver(this, BiConsumer { view, error -> view.error = error })

