/*
 * UIBinding Android Kotlin Copyright (C) 2017 Fatih.
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
package org.fs.rx.extensions.v7.util

import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import io.reactivex.Observable
import io.reactivex.functions.BiConsumer
import org.fs.rx.extensions.common.UIBindingObserver
import org.fs.rx.extensions.v7.observable.ToolbarMenuItemClickObservable
import org.fs.rx.extensions.v7.observable.ToolbarNavigationClickObservable

fun Toolbar.navigationClicks(): Observable<View> = ToolbarNavigationClickObservable(this)
fun Toolbar.menuItemClicks(predicate: (MenuItem) -> Boolean = { _ -> true }): Observable<MenuItem> = ToolbarMenuItemClickObservable(this, predicate)
// title setter
fun Toolbar.titleCharSequence(): UIBindingObserver<Toolbar, CharSequence> = UIBindingObserver(this, BiConsumer { view, title -> view.title = title })
fun Toolbar.titleStringRes(): UIBindingObserver<Toolbar, Int> = UIBindingObserver(this, BiConsumer { view, titleRes -> view.setTitle(titleRes) })
// sub title setter
fun Toolbar.subTitleCharSequence(): UIBindingObserver<Toolbar, CharSequence> = UIBindingObserver(this, BiConsumer { view, subTitle -> view.subtitle = subTitle })
fun Toolbar.subtitleStringRes(): UIBindingObserver<Toolbar, Int> = UIBindingObserver(this, BiConsumer { view, subTitleRes -> view.setSubtitle(subTitleRes) })
// logo setter
fun Toolbar.logoDrawableRes(): UIBindingObserver<Toolbar, Int> = UIBindingObserver(this, BiConsumer { view, logoRes -> view.setLogo(logoRes) })