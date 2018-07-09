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

package org.fs.rx.extensions.util

import android.os.Looper
import android.util.Log
import io.reactivex.Observer

fun <T> Observer<T>.checkMainThread(): Boolean = (Looper.myLooper() == Looper.getMainLooper()).also {
  if (!it) {
    Log.e("uibinding lib", "current thread is not ui thread, ensure it is on UI thread")
  }
}

val String.Companion.EMPTY get() = ""
