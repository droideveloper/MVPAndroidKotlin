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

import android.app.AlarmManager
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.hardware.SensorManager
import android.location.LocationManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.os.Vibrator
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

inline val Context.inputMethodManager: InputMethodManager
  get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

inline val Context.windowManager: WindowManager
  get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

inline val Context.alarmManager: AlarmManager
  get() = getSystemService(Context.ALARM_SERVICE) as AlarmManager

inline val Context.sensorManager: SensorManager
  get() = getSystemService(Context.SENSOR_SERVICE) as SensorManager

inline val Context.downloadManager: DownloadManager
  get() = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

inline val Context.connectivityManager: ConnectivityManager
  get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

inline val Context.notificationManager: NotificationManager
  get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

inline val Context.audioManager: AudioManager
  get() = getSystemService(Context.AUDIO_SERVICE) as AudioManager

inline val Context.locationManager: LocationManager
  get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager

inline val Context.vibrator: Vibrator
  get() = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

inline val Context.layoutInflater: LayoutInflater
  get() = LayoutInflater.from(this)

inline val Context.displayMetrics: DisplayMetrics
  get() = resources.displayMetrics

inline val Context.widthPixels: Int
  get() = displayMetrics.widthPixels

inline val Context.hightPixels: Int
  get() = displayMetrics.heightPixels

fun Context.valueFor(value: Float, typed: Int = TypedValue.COMPLEX_UNIT_DIP): Float
    = TypedValue.applyDimension(typed, value, displayMetrics)

fun Context.colorFor(@ColorRes colorRes: Int): Int
    = ResourcesCompat.getColor(resources, colorRes, theme)

fun Context.drawableFor(@DrawableRes drawableRes: Int): Drawable?
    = ResourcesCompat.getDrawable(resources, drawableRes, theme)

fun Context.vectorDrawableFor(@DrawableRes drawableRes: Int): VectorDrawableCompat?
    = VectorDrawableCompat.create(resources, drawableRes, theme)