package com.jadsalhani.proximiedatafeed

import android.app.Application
import com.jadsalhani.proximiedatafeed.common.companions.Prefs

val prefs: Prefs by lazy {
    App.prefs!!
}

class App : Application() {
    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}
