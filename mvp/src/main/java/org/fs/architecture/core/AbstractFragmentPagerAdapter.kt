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


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.fs.architecture.common.PropertyChangedListener
import org.fs.architecture.util.ObservableList

abstract class AbstractFragmentPagerAdapter<D>(
  protected val dataSet: ObservableList<D>,
  fragmentManager: FragmentManager,
  state: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT): FragmentPagerAdapter(fragmentManager, state), PropertyChangedListener {

  open fun register() {
    dataSet.register(this)
  }

  open fun unregister() {
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

  open fun itemAt(position: Int): D = dataSet[position]
  override fun getCount(): Int = dataSet.size
  open fun viewType(position: Int): Int = 0

  protected abstract fun onBindFragment(position: Int, item: D): Fragment
}