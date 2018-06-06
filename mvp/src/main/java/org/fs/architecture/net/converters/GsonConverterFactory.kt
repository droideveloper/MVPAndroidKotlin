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
package org.fs.architecture.net.converters

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class GsonConverterFactory(private val gson: Gson = Gson()): Converter.Factory() {

  companion object {
    @JvmStatic fun create(): GsonConverterFactory = GsonConverterFactory()
    @JvmStatic fun create(gson: Gson): GsonConverterFactory = GsonConverterFactory(gson)
  }

  override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *> {
    type?.let {
      val typeAdapter = typeAdapter(type)
      return GsonResponseBodyConverter(typeAdapter)
    }
    throw IllegalArgumentException("We can not recognize type for (#responseBodyConverter) $type")
  }

  override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out Annotation>?, methodAnnotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody> {
    type?.let {
      val typeAdapter = typeAdapter(type)
      return GsonRequestBodyConverter(typeAdapter)
    }

    throw IllegalArgumentException("We can not recognize type for (#requestBodyConverter) $type")
  }

  private fun typeAdapter(type: Type): TypeAdapter<*> {
    return gson.getAdapter(TypeToken.get(type))
  }
}