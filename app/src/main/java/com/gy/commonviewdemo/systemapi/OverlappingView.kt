package com.gy.commonviewdemo.systemapi

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class OverlappingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

    override fun onDraw(canvas: Canvas) {
        paint.color = Color.BLUE
        canvas.drawRect(0F, 0F, width.toFloat(), height / 2F + height / 4F, paint)
        paint.color = Color.GREEN
        canvas.drawRect(0F, height / 2F - height / 4F, width.toFloat(), height.toFloat(), paint)
    }

    // 修改这个属性 可以看见不同的效果
    override fun hasOverlappingRendering(): Boolean {
        return false
    }
}