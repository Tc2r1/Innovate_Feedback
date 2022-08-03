package com.tc2r1.android.nudennie_white_boilerplate

import android.app.Application
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree());
        }
    }

    companion object{
        var isPositive: Boolean = false
        var numOfStars: Int = 1

    }
}