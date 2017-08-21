/*
 * UIBinding Android Kotlin Copyright (C) 2017 Fatih, Brokoli Labs.
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
package org.fs.uibinding.v7.util

import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import io.reactivex.Observable
import org.fs.uibinding.util.detaches
import org.fs.uibinding.v7.observable.ToolbarMenuItemClickObservable
import org.fs.uibinding.v7.observable.ToolbarNavigationClickObservable

fun Toolbar.navigationClicks(): Observable<View> = ToolbarNavigationClickObservable(this).takeUntil(detaches())

fun Toolbar.menuItemClicks(predicate: (MenuItem) -> Boolean = { _ -> true }): Observable<MenuItem> = ToolbarMenuItemClickObservable(this, predicate).takeUntil(detaches())