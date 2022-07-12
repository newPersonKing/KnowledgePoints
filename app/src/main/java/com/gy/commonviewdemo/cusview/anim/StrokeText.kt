package com.gy.commonviewdemo.cusview.anim

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class StrokeText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {


    override fun onDraw(canvas: Canvas?) {
        paint.apply {
            strokeWidth = 5f
            style = Paint.Style.STROKE
        }
        super.onDraw(canvas)
    }
}