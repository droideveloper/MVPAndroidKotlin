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
package org.fs.uibinding.v4.util

import android.support.v4.widget.DrawerLayout
import io.reactivex.Observable
import io.reactivex.functions.BiConsumer
import org.fs.uibinding.common.ControlProperty
import org.fs.uibinding.common.UIBindingObserver
import org.fs.uibinding.util.detaches
import org.fs.uibinding.v4.observable.DrawerLayoutOpenOrClosedObservable
import org.fs.uibinding.v4.observable.DrawerLayoutSlideOffsetObservable
import org.fs.uibinding.v4.observable.DrawerLayoutStateChangedObservable

fun DrawerLayout.opensOrCloses(): Observable<Boolean> = DrawerLayoutOpenOrClosedObservable(this).takeUntil(detaches())

fun DrawerLayout.slideOfsetChanges(): Observable<Float> = DrawerLayoutSlideOffsetObservable(this).takeUntil(detaches())

fun DrawerLayout.stateChanges(): Observable<Int> = DrawerLayoutStateChangedObservable(this).takeUntil(detaches())

fun DrawerLayout.openOrClose(gravity: Int): UIBindingObserver<DrawerLayout, Boolean> = UIBindingObserver(this, BiConsumer { view, open ->
  if (view.isDrawerOpen(gravity)) {
    view.closeDrawer(gravity)
  } else {
    view.openDrawer(gravity)
  }
})

fun DrawerLayout.openOrCloseProperty(gravity: Int): ControlProperty<Boolean> = ControlProperty(opensOrCloses(), openOrClose(gravity))