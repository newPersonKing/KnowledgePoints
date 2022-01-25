package com.gy.commonviewdemo.cusview.text.rich_text.span

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan

class MarginImageSpan @JvmOverloads constructor(d: Drawable, verticalAlignment: Int, private val marginLeft: Int, private val marginRight: Int, private val imageWidth: Int = 0) : ImageSpan(d, verticalAlignment) {

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return if (marginLeft != 0 || marginRight != 0) {
            super.getSize(paint, text, start, end, fm)
            imageWidth + marginLeft + marginRight
        } else {
            super.getSize(paint, text, start, end, fm)
        }
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        canvas.save()
        super.draw(canvas, text, start, end, x + marginLeft, top, y, bottom, paint)
        canvas.restore()
    }
}