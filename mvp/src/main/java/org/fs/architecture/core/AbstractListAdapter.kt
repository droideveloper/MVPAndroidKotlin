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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import org.fs.architecture.common.PropertyChangedListener
import org.fs.architecture.util.ObservableList

abstract class AbstractListAdapter<D: AbstractEntity, VH: AbstractViewHolder<D>>(protected val dataSet: ObservableList<D>) : BaseAdapter(), PropertyChangedListener {

  private var factory: LayoutInflater? = null

  fun register() {
    dataSet.register(this)
  }

  fun unregister() {
    dataSet.unregister(this)
  }

  override fun getItemViewType(position: Int): Int = 0
  override fun getViewTypeCount(): Int = 1
  override fun getCount(): Int = dataSet.size
  override fun getItem(position: Int): Any = dataSet[position]
  protected fun itemAt(position: Int): D = dataSet[position]
  override fun getItemId(position: Int): Long = position.toLong()

  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    if (convertView == null) {
      val viewType = getItemViewType(position)
      val viewHolder = createViewHolder(parent, viewType)
      viewHolder.applyTag()
      onBindViewHolder(viewHolder, position)
      return viewHolder.view
    }

    val tag = convertView.tag
    val viewHolder: VH? = tag as VH?
    if (viewHolder != null) {
      viewHolder.applyTag()
      onBindViewHolder(viewHolder, position)
      return viewHolder.view
    } else {
      throw RuntimeException("you should at least have an item here")
    }
  }

  override fun notifyItemsChanged(index: Int, size: Int) {
    notifyDataSetChanged()
  }

  override fun notifyItemsInserted(index: Int, size: Int) {
    notifyDataSetChanged()
  }

  override fun notifyItemsRemoved(index: Int, size: Int) {
    notifyDataSetChanged()
  }

  protected abstract fun createViewHolder(parent: ViewGroup?, viewType: Int): VH

  protected fun onBindViewHolder(viewHolder: VH, position: Int) {
    val item = itemAt(position)
    viewHolder.onBindView(item)
  }

  protected fun factory(parent: ViewGroup?): LayoutInflater? {
    if (factory == null) {
      if (parent != null) {
        factory = LayoutInflater.from(parent.context)
      }
    }
    return factory
  }
}