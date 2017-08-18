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
package org.fs.mvp.core

import android.os.Parcel
import android.os.Parcelable

abstract class AbstractEntity constructor(): Parcelable {

  constructor(input: Parcel): this() {
    readParcel(input)
  }

  protected abstract fun readParcel(input: Parcel)
  protected abstract fun writeParcel(out: Parcel?, flags: Int)

  override fun writeToParcel(dest: Parcel?, flags: Int) {
    writeParcel(dest, flags)
  }
}