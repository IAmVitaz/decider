package com.vitaz.decider.Controller

import android.app.Application
import com.vitaz.decider.Utilities.SharedPrefs

class App : Application() {

    companion object {
        lateinit var sharedPreferences: SharedPrefs
    }

    override fun onCreate() {
        sharedPreferences = SharedPrefs(applicationContext)
        super.onCreate()
    }
}