/*
 * Kotlin Extensions Android Copyright (C) 2018 Fatih.
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

package org.fs.extensions

import android.support.annotation.StringRes
import android.util.Property
import android.view.View
import android.view.ViewGroup

inline val View.lpHeight: Int
  get() = layoutParams.height

inline val View.lpWidth: Int
  get() = layoutParams.width

inline val View.marginLayoutParams: ViewGroup.MarginLayoutParams?
  get() = layoutParams as? ViewGroup.MarginLayoutParams

/**
 * will return topMargin value for LayoutParams yet it might return -1 if layout can not have margin properties
 */
inline val View.marginTop: Int
  get() = marginLayoutParams?.topMargin ?: -1

inline val View.marginBottom: Int
  get() = marginLayoutParams?.bottomMargin ?: -1

inline val View.marginStart: Int get() = marginLeft
inline val View.marginLeft: Int
  get() = marginLayoutParams?.leftMargin ?: -1

inline val View.marginEnd: Int get() = marginRight
inline val View.marginRight: Int
  get() = marginLayoutParams?.rightMargin ?: -1

fun View.stringFor(@StringRes stringRes: Int): CharSequence = resources.getString(stringRes)

fun View.resizeTo(width: Int = 0, height: Int = 0) {
  val lp = layoutParams
  lp?.let {
    lp.height = height
    lp.width = width
    layoutParams = lp
  }
}

fun View.widthTo(width: Int) = resizeTo(width, lpHeight)
fun View.heightTo(height: Int) = resizeTo(lpWidth, height)



class ViewWidthProperty: Property<View, Int>(Int::class.java, ViewWidthProperty::class.java.simpleName) {
  override fun get(view: View): Int = view.lpWidth
  override fun set(view: View, width: Int) = view.widthTo(width)
}

class ViewHeightProperty: Property<View, Int>(Int::class.java, ViewHeightProperty::class.java.simpleName) {
  override fun get(view: View): Int = view.lpHeight
  override fun set(view: View, height: Int) = view.heightTo(height)
}