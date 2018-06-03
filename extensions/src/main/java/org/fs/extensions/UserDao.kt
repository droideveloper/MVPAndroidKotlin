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

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface UserDao {

  @Query("SELECT * FROM user")
  fun queryForAll(): List<User>

  @Query("SELECT * FROM user WHERE userId = :userId")
  fun queryFor(userId: Int): User

  @Query("SELECT COUNT(*) FROM user")
  fun countOf()

  @Insert
  fun create(user: User): Long

  @Insert
  fun create(users: List<User>): List<Long>

  @Delete
  fun delete(user: User): Int
}