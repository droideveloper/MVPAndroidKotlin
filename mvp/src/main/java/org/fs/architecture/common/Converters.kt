/*
 * MVP Android Kotlin Copyright (C) 2018 Fatih.
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

import androidx.room.TypeConverter
import java.util.*


sealed class Converters {

  companion object {

    @JvmStatic @TypeConverter fun convertToString(charSequence: CharSequence?): String? = when(charSequence) {
      null -> null
      else -> charSequence.toString()
    }

    @JvmStatic @TypeConverter fun converToCharSequence(string: String?): CharSequence? = string

    @JvmStatic @TypeConverter fun convertToDate(timeStamp: Long?): Date? = when(timeStamp){
      null -> null
      else -> Date(timeStamp)
    }

    @JvmStatic @TypeConverter fun convertToLong(date: Date?): Long? = when(date) {
      null -> null
      else -> date.time
    }
  }
}