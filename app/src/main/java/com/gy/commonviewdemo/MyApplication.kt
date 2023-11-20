package com.gy.commonviewdemo

import android.app.Activity
import android.app.Application
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import com.gy.commonviewdemo.wifi.WifiUtil
import com.ycbjie.webviewlib.X5WebUtils

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.i("cccccccccc","MyApplication")
        WifiUtil.init(application = this)
        WifiUtil.openWifi()
        X5WebUtils.init(this)
    }
}