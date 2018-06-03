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
package org.fs.architecture.net

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.Scheduler
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import java.lang.reflect.Type

class RxJava2CallAdapter<R>(val type: Type, val scheduler: Scheduler?,
    val async: Boolean, val result: Boolean, val body: Boolean,
    val flowable: Boolean, val single: Boolean, val maybe: Boolean, val completable: Boolean): CallAdapter<R, Any> {

  override fun responseType(): Type = type

  override fun adapt(call: Call<R>?): Any {
    val responseObservable: Observable<Response<R>>
    if (async) {
      responseObservable = CallEnqueuObservable(call)
    } else {
      responseObservable = CallExecuteObservable(call)
    }

    var observable: Observable<*>
    if (result) {
      observable = ResultObservable(responseObservable)
    } else if (body) {
      observable = BodyObservable(responseObservable)
    } else {
      observable = responseObservable
    }

    if (scheduler != null) {
      observable = observable.subscribeOn(scheduler)
    }

    if (flowable) {
      return observable.toFlowable(BackpressureStrategy.LATEST)
    }
    if (single) {
      return observable.singleOrError()
    }
    if (maybe) {
      return observable.singleElement()
    }
    if (completable) {
      return observable.ignoreElements()
    }
    return observable
  }
}