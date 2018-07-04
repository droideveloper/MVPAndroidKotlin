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
package org.fs.rx.extensions.util

import android.widget.AbsListView
import io.reactivex.Observable
import org.fs.rx.extensions.model.AbsListViewScrollEventState
import org.fs.rx.extensions.observable.AbsListViewScrollEventStateObservable
import org.fs.rx.extensions.observable.AbsListViewScrollStateObservable

fun AbsListView.scrollEvents(): Observable<AbsListViewScrollEventState> = AbsListViewScrollEventStateObservable(this)

fun AbsListView.scrollStateChanges(): Observable<Int> = AbsListViewScrollStateObservable(this)