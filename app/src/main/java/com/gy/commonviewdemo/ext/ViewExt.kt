package com.gy.commonviewdemo.ext

import android.app.Activity
import android.graphics.Rect
import android.view.View

fun View.isVisibleInWindow(activity:Activity):Boolean{
    val screenWidth = activity.resources.displayMetrics.widthPixels
    val screenHeight = activity.resources.displayMetrics.heightPixels
    val location = intArrayOf(0, 0)

    // 调用这个方法的时机要注意 不确定 就view.post
    getLocationInWindow(location)

    val rect = Rect(0,0,screenWidth,screenHeight)

    return getLocalVisibleRect(rect)
}