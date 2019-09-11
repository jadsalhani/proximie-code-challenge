package com.jadsalhani.proximiedatafeed.application

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    val PREFS_FILENAME = "com.jadsalhani.proximiedatafeed.application.getPrefs"

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
}
