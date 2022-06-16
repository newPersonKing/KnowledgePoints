package com.gy.commonviewdemo.apt.spi

import android.util.Log

class SpiInterfaceImpl : SpiInterface {
    override fun run() {
        Log.i("SpiInterface","running in SpiInterfaceImpl")
    }
}