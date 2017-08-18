/*
 * MVP Android Kotlin Copyright (C) 2017 Fatih, Brokoli Labs.
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

package org.fs.mvp.util

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.fs.mvp.common.ViewType
import java.io.PrintWriter
import java.io.StringWriter

// Rx
fun <T: Observable<T>> T.async(): Observable<T> = this.compose {
  it.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
}

fun <T: Observable<T>> T.async(view: ViewType): Observable<T> = async()
    .doOnSubscribe { if (view.isAvailable()) view.showProgress() }
    .doFinally { if (view.isAvailable()) view.hideProgress() }

// String
val String.Companion.EMPTY get() = ""
val String.Companion.WHITE_SPACE get() = " "
val String.Companion.NEW_LINE get() = "\n"
val String.Companion.INDENT get() = "\t"

// Logger
fun <T: Any> T.isLogEnabled(): Boolean = false
fun <T: Any> T.getClassTag(): String = this.javaClass.simpleName
fun <T: Any> T.log(message: String) = log(Log.DEBUG, message)
fun <T: Any> T.log(error : Throwable) {
  val str = StringWriter()
  val ptr = PrintWriter(str)
  error.printStackTrace(ptr)
  log(Log.ERROR, str.toString())
}
fun <T: Any> T.log(level: Int, message: String) {
  if (isLogEnabled()) {
    Log.println(level, getClassTag(), message)
  }
}

