package com.gy.commonviewdemo.cusview.text.rich_text.span

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.style.ReplacementSpan
import android.util.Log

class FrameSpan : ReplacementSpan() {
    private val paint: Paint = Paint()
    private var width = 0F
    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        width = paint.measureText(text, start, end)
        return width.toInt()
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        canvas.drawRect(x, top.toFloat(), x + width, bottom.toFloat(), this.paint)
        canvas.drawText(text.toString(), start, end, x, y.toFloat(), paint)
    }

    init {
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLUE
        paint.isAntiAlias = true
    }
}