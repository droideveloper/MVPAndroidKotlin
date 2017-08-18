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

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem
import android.view.View
import org.fs.mvp.common.PresenterType
import java.io.PrintWriter
import java.io.StringWriter
import javax.inject.Inject

abstract class AbstractFragment<P: PresenterType>: Fragment() {

  @Inject var presenter: P? = null

  fun showProgress() {
    throw RuntimeException("you should implement show progress")
  }

  fun hideProgress() {
    throw RuntimeException("you should implement hide progress")
  }

  fun showError(msg: String) {
    val view = view()
    if (view != null) {
      Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
          .show()
    }
  }

  fun showError(msg: String, action: String, callback: View.OnClickListener?) {
    val view = view()
    if (view != null) {
      Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
          .setAction(action) { v: View ->
            callback?.onClick(v)
          }.show()
    }
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    presenter?.storeState(outState)
  }

  override fun onResume() {
    super.onResume()
    presenter?.onResume()
  }

  override fun onPause() {
    super.onPause()
    presenter?.onPause()
  }

  override fun onStart() {
    super.onStart()
    presenter?.onStart()
  }

  override fun onStop() {
    super.onStop()
    presenter?.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter?.onDestroy()
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    presenter?.requestPermissionsResult(requestCode, permissions, grantResults)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    presenter?.activityResult(requestCode, resultCode, data)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item != null) {
      val handle = presenter?.onOptionsItemSelected(item)
      if (handle != null) {
        return handle
      }
    }
    return super.onOptionsItemSelected(item)
  }

  fun isAvailable(): Boolean = isAdded && activity != null

  fun getStringRes(stringRes: Int): String? = getString(stringRes)

  fun view(): View? = view

  fun finish() { throw RuntimeException("you should call on #dismiss()") }

  protected abstract fun isLogEnabled() : Boolean
  protected abstract fun getClassTag() : String

  protected fun log(msg: String) {
    log(Log.DEBUG, msg)
  }

  protected fun log(lv: Int, msg: String) {
    if (isLogEnabled()) {
      Log.println(lv, getClassTag(), msg)
    }
  }

  protected fun log(error : Throwable) {
    val str = StringWriter()
    val ptr = PrintWriter(str)
    error.printStackTrace(ptr)
    log(Log.ERROR, str.toString())
  }
}