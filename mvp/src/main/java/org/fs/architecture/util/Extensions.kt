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

package org.fs.architecture.util

import android.os.Build
import android.text.TextUtils
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.fs.architecture.common.ViewType
import java.io.File
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

fun <T: Maybe<T>> T.async(): Maybe<T> = this.compose {
  it.subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())
}

fun <T: Maybe<T>> T.async(view: ViewType): Maybe<T> = async()
  .doOnSubscribe { if (view.isAvailable()) view.showProgress() }
  .doFinally { if (view.isAvailable()) view.hideProgress() }

fun <T: Single<T>> T.async(): Single<T> = this.compose {
  it.subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())
}

fun <T: Single<T>> T.async(view: ViewType): Single<T> = async()
  .doOnSubscribe { if (view.isAvailable()) view.showProgress() }
  .doFinally { if (view.isAvailable()) view.hideProgress() }

fun <T: Flowable<T>> T.async(): Flowable<T> = this.compose {
  it.subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())
}

fun <T: Flowable<T>> T.async(view: ViewType): Flowable<T> = async()
  .doOnSubscribe { if (view.isAvailable()) view.showProgress() }
  .doFinally { if (view.isAvailable()) view.hideProgress() }

// String
val String.Companion.EMPTY get() = ""
val String.Companion.WHITE_SPACE get() = " "
val String.Companion.NEW_LINE get() = "\n"
val String.Companion.INDENT get() = "\t"

// Logger
inline fun <reified T: Any> T.isLogEnabled(): Boolean = false
inline fun <reified T: Any> T.getClassTag(): String = this.javaClass.simpleName
inline fun <reified T: Any> T.log(message: String) = log(Log.DEBUG, message)
inline fun <reified T: Any> T.log(error : Throwable) {
  val str = StringWriter()
  val ptr = PrintWriter(str)
  error.printStackTrace(ptr)
  log(Log.ERROR, str.toString())
}
inline fun <reified T: Any> T.log(level: Int, message: String) {
  if (isLogEnabled()) {
    Log.println(level, getClassTag(), message)
  }
}

// Objects
fun <T: Any> T?.isNullOrEmpty(): Boolean {
  if (this != null) {
    if (this is String) {
      return TextUtils.isEmpty(this)
    }
    if (this is Collection<*>) {
      return this.isEmpty()
    }
    if (this is File) {
      return !this.exists()
    }
  }
  return true
}
fun <T: Any> T?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()

fun isApiAvailable(requiredSdkVersion: Int): Boolean = Build.VERSION.SDK_INT >= requiredSdkVersion

fun <T: Any> T?.checkNotNull(errorString: String = "$this is null") { if (isNullOrEmpty()) throw RuntimeException(errorString) }
fun Boolean.throwIfConditionFails(errorString: String = "$this failed since it won't meet true") = { if (!this) throw RuntimeException(errorString) }