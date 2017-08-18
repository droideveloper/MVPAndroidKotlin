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

import android.view.MotionEvent
import android.view.View
import io.reactivex.Observable
import org.fs.uibinding.model.ViewAttachState
import org.fs.uibinding.observable.ViewAttachStateObservable
import org.fs.uibinding.observable.ViewClickObservable
import org.fs.uibinding.observable.ViewTouchObservable

// click observer
fun View.clicks(): Observable<View> = ViewClickObservable(this)
// touch observer
fun View.touches(callback: (MotionEvent?) -> Boolean): Observable<MotionEvent> = ViewTouchObservable(this, callback)
// attach observer
fun View.attaches(): Observable<ViewAttachState> = ViewAttachStateObservable(this).filter { it == ViewAttachState.ATTACHED }
// detach observer
fun View.detaches(): Observable<ViewAttachState> = ViewAttachStateObservable(this).filter { it == ViewAttachState.DETACHED }
