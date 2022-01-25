package com.gy.commonviewdemo.cusview.text.rich_text.span

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan

class RoundBackgroundColorSpan(private val bgColor: Int, private val textColor: Int, private val radius: Int, private val textString: String) : ReplacementSpan() {

    private var spanWidth = 0

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return ((paint.measureText(textString, 0, textString.length) + 2 * radius).toInt()).also { spanWidth = it }
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val originalColor = paint.color
        paint.color = bgColor
        paint.isAntiAlias = true
        val rectF = RectF(x, y + paint.ascent(), x + spanWidth, y + paint.descent())
        canvas.drawRoundRect(rectF, radius.toFloat(), radius.toFloat(), paint)
        paint.color = textColor
        canvas.drawText(textString, x + radius, y.toFloat(), paint)
        paint.color = originalColor
    }
}