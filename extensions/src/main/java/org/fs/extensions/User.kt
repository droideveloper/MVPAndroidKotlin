/*
 * Kotlin Extensions Android Copyright (C) 2018 Fatih.
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
package org.fs.extensions

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import org.fs.architecture.core.AbstractEntity

@Entity
open class User: AbstractEntity() {

  @PrimaryKey(autoGenerate = true) var userId: Int = Int.MIN_VALUE
    private set(value) { field = value }

  @ColumnInfo var name: String? = null
  @ColumnInfo var surname: String? = null

  override fun readParcel(input: Parcel?) {
    userId = input?.readInt() ?: Int.MIN_VALUE
    val hasName = input?.readInt() == 1
    if (hasName) {
      name = input?.readString()
    }
    val hasSurname = input?.readInt() == 1
    if (hasSurname) {
      surname = input?.readString()
    }
  }

  override fun writeParcel(out: Parcel?, flags: Int) {
    out?.writeInt(userId)
    val name = name
    out?.writeInt(if(name != null) 1 else 0)
    if (name != null) {
      out?.writeString(name)
    }
    val surname = surname
    out?.writeInt(if(surname != null) 1 else 0)
    if (surname != null) {
      out?.writeString(surname)
    }
  }

  companion object {
    @JvmField
    val CREATOR = object : Parcelable.Creator<User> {

      override fun createFromParcel(source: Parcel?): User {
        val obj = User()
        obj.readParcel(source)
        return obj
      }

      override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)

    }
  }
}