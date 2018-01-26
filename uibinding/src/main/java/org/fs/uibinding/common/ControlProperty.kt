/*
 * UIBinding Android Kotlin Copyright (C) 2017 Fatih, Brokoli Labs.
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
package org.fs.uibinding.common

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper
import java.util.concurrent.atomic.AtomicReference


class ControlProperty<T>(source: Observable<T>, private val sink: Observer<T>): Observable<T>(), Observer<T>, Disposable {

  private val s = AtomicReference<Disposable>()
  private val source = source.subscribeOn(AndroidSchedulers.mainThread())

  override fun subscribeActual(observer: Observer<in T>) = source.subscribe(observer)

  override fun onSubscribe(d: Disposable) { DisposableHelper.setOnce(s, d) }
  override fun onError(e: Throwable) = throw RuntimeException(e)
  override fun onNext(value: T) = sink.onNext(value)
  override fun onComplete() = sink.onComplete()

  override fun isDisposed(): Boolean = s.get() == DisposableHelper.DISPOSED
  override fun dispose() { DisposableHelper.dispose(s) }

  fun changed(): ControlEvent<T> = ControlEvent(source.skip(1))
  fun asObservable(): Observable<T> = source
  fun asControlProperty(): ControlProperty<T> = this
}