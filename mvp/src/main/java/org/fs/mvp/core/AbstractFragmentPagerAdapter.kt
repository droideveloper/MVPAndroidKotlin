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

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.fs.mvp.common.PropertyChangedListener
import org.fs.mvp.util.ObservableList

abstract class AbstractFragmentPagerAdapter<D>(protected val dataSet: ObservableList<D>, fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager), PropertyChangedListener {

  fun register() {
    dataSet.register(this)
  }

  fun unregister() {
    dataSet.unregister(this)
  }

  override fun notifyItemsRemoved(index: Int, size: Int) {
    notifyDataSetChanged()
  }

  override fun notifyItemsChanged(index: Int, size: Int) {
    notifyDataSetChanged()
  }

  override fun notifyItemsInserted(index: Int, size: Int) {
    notifyDataSetChanged()
  }

  override fun getItem(position: Int): Fragment = onBindFragment(position, itemAt(position))

  fun itemAt(position: Int): D = dataSet[position]
  override fun getCount(): Int = dataSet.size

  protected abstract fun onBindFragment(position: Int, item: D): Fragment
}