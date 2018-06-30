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

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.fs.architecture.common.PropertyChangedListener
import org.fs.architecture.util.ObservableList

abstract class AbstractRecyclerViewAdapter<D, VH: AbstractRecyclerViewHolder<D>>(protected val dataSet: ObservableList<D>): RecyclerView.Adapter<VH>(), PropertyChangedListener {

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    dataSet.register(this)
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    dataSet.unregister(this)
  }

  override fun onViewAttachedToWindow(viewHolder: VH) {
    super.onViewAttachedToWindow(viewHolder)
    viewHolder.attached()
  }

  override fun onViewDetachedFromWindow(viewHolder: VH) {
    super.onViewDetachedFromWindow(viewHolder)
    viewHolder.detached()
  }

  override fun notifyItemsRemoved(index: Int, size: Int) {
    if (size == 1) {
      notifyItemRemoved(index)
    } else {
      notifyItemRangeRemoved(index, size)
    }
  }

  override fun notifyItemsInserted(index: Int, size: Int) {
    if (size == 1) {
      notifyItemInserted(index)
    } else {
      notifyItemRangeInserted(index, size)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = throw IllegalArgumentException("implement onCreateViewHolder so that you can have it")

  override fun onBindViewHolder(viewHolder: VH, position: Int) {
    val entity = itemAt(position)
    viewHolder.onBindView(entity)
  }

  override fun notifyItemsChanged(index: Int, size: Int) {
    if (size == 1) {
      notifyItemChanged(index)
    } else {
      notifyItemRangeChanged(index, size)
    }
  }

  open fun itemAt(position: Int): D = dataSet[position]
  override fun getItemId(position: Int): Long = position.toLong()
  override fun getItemViewType(position: Int): Int = 0
  override fun getItemCount(): Int = dataSet.size
}