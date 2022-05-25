package com.gy.commonviewdemo.ext

import android.app.Activity
import android.graphics.Canvas
import android.graphics.Paint
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

fun drawCenterText(text:String,textPint:Paint,rect: Rect,canvas: Canvas){

    val textWith = textPint.measureText(text)
    val startX = rect.centerX() - textWith/2

    val fontMetrics = textPint.fontMetrics
    val distance = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
    val startY = rect.centerY() + distance
    canvas.drawText(text,startX,startY,textPint)
}