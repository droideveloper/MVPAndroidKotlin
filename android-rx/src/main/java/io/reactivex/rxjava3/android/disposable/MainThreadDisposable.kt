/*
 *  Copyright (C) 2020 Fatih, MVI Android Kotlin.
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

package io.reactivex.rxjava3.android.disposable

import io.reactivex.rxjava3.android.AndroidSchedulers
import io.reactivex.rxjava3.android.verifyMainThread
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.atomic.AtomicBoolean

abstract class MainThreadDisposable: Disposable {

  private var unsubscribe = AtomicBoolean(false)

  override fun isDisposed(): Boolean = unsubscribe.get()

  override fun dispose() {
    if (unsubscribe.compareAndSet(false, true)) {
      when {
        verifyMainThread() -> onDispose()
        else -> AndroidSchedulers.mainThread().scheduleDirect(::onDispose)
      }
    }
  }

  protected abstract fun onDispose()
}