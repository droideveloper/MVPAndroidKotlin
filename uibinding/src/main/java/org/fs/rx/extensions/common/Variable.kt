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

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

class Variable<T>(value: T): Disposable  {

  private var disposed = false

  var value: T = value
    set(value) {
      if (field != value) {
        field = value
        subject.onNext(value)
      }
    }

  private val subject = BehaviorSubject.createDefault(value)

  fun asObservable(): Observable<T> = subject

  override fun isDisposed(): Boolean = disposed

  override fun dispose() {
    if (!disposed) {
      subject.onComplete()
      disposed = true
    }
  }
}