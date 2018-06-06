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
package org.fs.architecture.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import org.fs.architecture.common.PresenterType
import javax.inject.Inject

abstract class AbstractActivity<P: PresenterType>: AppCompatActivity(), HasSupportFragmentInjector {

  @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
  @Inject lateinit var presenter: P

  override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)

    presenter.restoreState(savedInstanceState ?: intent.extras)
    presenter.onCreate()
  }

  open fun showProgress() {
    throw RuntimeException("you should implement show progress")
  }

  open fun hideProgress() {
    throw RuntimeException("you should implement hide progress")
  }

  open fun showError(msg: String) {
    val view = view()
    if (view != null) {
      Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        .show()
    }
  }

  open fun showError(msg: String, action: String, callback: View.OnClickListener?) {
    val view = view()
    if (view != null) {
      Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        .setAction(action) { v: View ->
          callback?.onClick(v)
        }.show()
    }
  }

  open fun isAvailable(): Boolean = !isFinishing

  open fun getContext(): Context = this

  open fun getStringRes(stringRes: Int): String? = getString(stringRes)

  open fun view(): View? = findViewById(android.R.id.content)

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

  override fun onBackPressed() {
    presenter.onBackPressed()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item != null) {
      return presenter.onOptionsItemSelected(item)
    }
    return super.onOptionsItemSelected(item)
  }
}