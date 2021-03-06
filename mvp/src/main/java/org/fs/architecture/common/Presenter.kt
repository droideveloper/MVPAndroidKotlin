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
package org.fs.architecture.common

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem

interface Presenter {

  fun onResume()
  fun onPause()
  fun onStart()
  fun onStop()
  fun onCreate()
  fun onDestroy()
  fun onBackPressed()
  fun restoreState(restore: Bundle?)
  fun storeState(store: Bundle?)
  fun activityResult(requestCode: Int, resultCode: Int, data: Intent?)
  fun requestPermissionsResult(requestCode: Int, permissions: Array<out String>, grants: IntArray)
  fun onOptionsItemSelected(item: MenuItem): Boolean
}