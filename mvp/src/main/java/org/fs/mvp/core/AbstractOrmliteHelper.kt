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

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import org.fs.mvp.util.log
import java.sql.SQLException

abstract class AbstractOrmliteHelper(context: Context, dbName: String, dbVersion: Int): OrmLiteSqliteOpenHelper(context, dbName, null, dbVersion) {

  override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
    try {
      createTables(connectionSource)
    } catch (error: SQLException) {
      log(error)
    }
  }

  override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
    try {
      dropTables(connectionSource)
      onCreate(database, connectionSource)
    } catch (error: SQLException) {
      log(error)
    }
  }

  @Throws(SQLException::class)
  protected abstract fun createTables(connectionSource: ConnectionSource?)

  @Throws(SQLException::class)
  protected abstract fun dropTables(connectionSource: ConnectionSource?)
}