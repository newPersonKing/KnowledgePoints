package com.gy.commonviewdemo.ext

import android.app.Activity
import android.app.PendingIntent


// 校验打开这个Activity 的是哪个应用打开的
// 主要是处理通过Intent-Filter 打开Activity 的情况
fun Activity.checkOpenPackage(){

   val componentName =  callingActivity
    if(componentName == null){
        finish()
    }else if(packageName !== componentName.packageName){
        finish()
    }
}
