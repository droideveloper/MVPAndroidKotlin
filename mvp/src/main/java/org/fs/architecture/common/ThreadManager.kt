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
package org.fs.architecture.common

import android.os.Handler
import android.os.Looper

class ThreadManager private constructor() {

  companion object {

    private var instance: ThreadManager? = null

    @JvmStatic fun shared(): ThreadManager = instance ?: synchronized(this) {
      instance ?: ThreadManager().also { instance = it }
    }

    private const val DELAY_MS = 300L

    @JvmStatic fun runOnUiThread(task: Runnable) = shared().post(task)
    @JvmStatic fun runOnUiThreadDelayed(task: Runnable, delay: Long = DELAY_MS) = shared().postDelayed(task, delay)
    @JvmStatic fun clearAll() = shared().removeAll()
    @JvmStatic fun clear(task: Runnable) = shared().remove(task)
  }

  private val handler by lazy { Handler(Looper.getMainLooper()) }

  fun post(task: Runnable) = handler.post(task)

  fun postDelayed(task: Runnable, delay: Long = DELAY_MS) = handler.postDelayed(task, delay)

  fun removeAll() = handler.removeCallbacksAndMessages(null)

  fun remove(task: Runnable) = handler.removeCallbacks(task)
}