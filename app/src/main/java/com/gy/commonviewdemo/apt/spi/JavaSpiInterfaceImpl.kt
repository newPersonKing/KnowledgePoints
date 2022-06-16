package com.gy.commonviewdemo.apt.spi

import android.util.Log

class JavaSpiInterfaceImpl : SpiInterface {
    override fun run() {
        Log.i("SpiInterface","running in JavaSpiInterfaceImpl")
    }
}