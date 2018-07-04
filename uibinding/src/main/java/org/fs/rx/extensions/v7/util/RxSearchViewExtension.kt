/*
 * UIBinding Android Kotlin Copyright (C) 2017 Fatih..
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

import android.support.v7.widget.SearchView
import android.view.View
import io.reactivex.Observable
import io.reactivex.functions.BiConsumer
import org.fs.rx.extensions.common.UIBindingObserver
import org.fs.rx.extensions.util.EMPTY
import org.fs.rx.extensions.v7.observable.SearchViewQueryTextChangedObservable

private val fn: (view: View, stringRes: Int) -> CharSequence = { view, stringRes ->
  if (stringRes != 0) view.resources.getString(stringRes) else String.EMPTY // load text or return empty for safe failure
}

fun SearchView.queryChanges(predicate: (CharSequence) -> Boolean = {_ -> true}): Observable<CharSequence> = SearchViewQueryTextChangedObservable(this, predicate)

fun SearchView.hintCharSequence(): UIBindingObserver<SearchView, CharSequence> = UIBindingObserver(this, BiConsumer { view, hintCharSequence -> view.queryHint = hintCharSequence })
fun SearchView.hintStringRes(strConverter: (view: View, stringRes: Int) -> CharSequence = fn): UIBindingObserver<SearchView, Int> = UIBindingObserver(this, BiConsumer { view, stringRes -> view.queryHint = strConverter(view, stringRes) })

fun SearchView.queryCharSequence(shouldSubmit: Boolean = false): UIBindingObserver<SearchView, CharSequence> = UIBindingObserver(this, BiConsumer { view, query -> view.setQuery(query, shouldSubmit) })
fun SearchView.queryStringRes(strConverter: (view: View, stringRes: Int) -> CharSequence = fn, shouldSubmit: Boolean = false): UIBindingObserver<SearchView, Int> = UIBindingObserver(this, BiConsumer { view, stringRes -> view.setQuery(strConverter(view, stringRes), shouldSubmit) })