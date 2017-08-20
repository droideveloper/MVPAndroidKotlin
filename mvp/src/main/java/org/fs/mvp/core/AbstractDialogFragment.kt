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

import android.app.DialogFragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MenuItem
import android.view.View
import org.fs.mvp.common.PresenterType
import javax.inject.Inject

abstract class AbstractDialogFragment<P: PresenterType> : DialogFragment() {

  @Inject lateinit var presenter: P

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
    presenter.storeState(outState)
  }

  override fun onResume() {
    super.onResume()
    presenter.onResume()
  }

  override fun onPause() {
    super.onPause()
    presenter.onPause()
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
  }

  override fun onStop() {
    super.onStop()
    presenter.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    presenter.requestPermissionsResult(requestCode, permissions, grantResults)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    presenter.activityResult(requestCode, resultCode, data)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item != null) {
      return presenter.onOptionsItemSelected(item)
    }
    return super.onOptionsItemSelected(item)
  }

  fun isAvailable(): Boolean = isAdded && activity != null

  fun getStringRes(stringRes: Int): String? = getString(stringRes)

  fun view(): View? = view

  fun finish() { throw RuntimeException("you should call on #dismiss()") }

  override fun dismiss() {
    super.dismiss()
  }

  override fun show(manager: FragmentManager?, tag: String?) {
    val transaction = manager?.beginTransaction()
    show(transaction, tag)
  }

  override fun show(transaction: FragmentTransaction?, tag: String?): Int {
    return transaction?.add(this, tag)!!.commit()
  }
}