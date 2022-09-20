package com.gy.commonviewdemo

import android.util.Log

object LogUtil {

    fun i(msg:String){
        Log.i("common",msg)
    }

    fun e(msg:String){
        Log.e("common",msg)
    }

    fun e(tag:String,msg:String){
        Log.e("common_$tag",msg)
    }
}