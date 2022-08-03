package com.tc2r1.android.nudennie_white_boilerplate

import android.app.Application

import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Timber Debugging.
        Timber.plant(Timber.DebugTree())
    }
}