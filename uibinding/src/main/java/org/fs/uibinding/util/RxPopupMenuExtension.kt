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
package org.fs.uibinding.util

import android.view.MenuItem
import android.widget.PopupMenu
import io.reactivex.Observable
import org.fs.uibinding.observable.PopupMenuDismissObservable
import org.fs.uibinding.observable.PopupMenuItemClickObservable

fun PopupMenu.dismisses(): Observable<PopupMenu> = PopupMenuDismissObservable(this)

fun PopupMenu.itemClicks(predicate: (MenuItem) -> Boolean): Observable<MenuItem> = PopupMenuItemClickObservable(this, predicate)