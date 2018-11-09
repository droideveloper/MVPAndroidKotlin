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

abstract class AbstractPresenter<V: ViewType>(val view: V) {

  open fun onResume() {}
  open fun onPause() {}
  open fun onStart() {}
  open fun onStop() {}
  open fun onCreate() {}
  open fun onDestroy() {}
  open fun onBackPressed() {}
  open fun restoreState(restore: Bundle?) {}
  open fun storeState(store: Bundle?) {}
  open fun activityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
  open fun requestPermissionsResult(requestCode: Int, permissions: Array<out String>, grants: IntArray) {}
  open fun onOptionsItemSelected(item: MenuItem): Boolean = false
}