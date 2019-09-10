package com.jadsalhani.proximiedatafeed.common.companions

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    val PREFS_FILENAME = "com.jadsalhani.proximiedatafeed.prefs"

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
}
