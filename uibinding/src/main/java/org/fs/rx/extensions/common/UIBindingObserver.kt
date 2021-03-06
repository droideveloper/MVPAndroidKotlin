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

import android.os.Looper
import io.reactivex.rxjava3.android.AndroidSchedulers
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins

open class UIBindingObserver<T, V>(private val view: T, private val binding: BiConsumer<T, V>): DisposableObserver<V>() {

  override fun onNext(value: V) {
    if (Looper.myLooper() != Looper.getMainLooper()) {
      AndroidSchedulers.mainThread().scheduleDirect {
        onNext(value)
      }
    } else {
      try {
        binding.accept(view, value)
      } catch (error: Throwable) {
        if (!isDisposed) {
          dispose()
        }
        onError(error)
      }
    }
  }

  override fun onComplete() { /*no-opt*/ }
  override fun onError(e: Throwable) = RxJavaPlugins.onError(e)
}