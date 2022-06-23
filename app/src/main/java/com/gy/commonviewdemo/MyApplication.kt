package com.gy.commonviewdemo

import android.app.Application
import android.util.Log

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.i("cccccccccc","MyApplication")
    }
}