package com.gy.commonviewdemo.cusview.text.rich_text.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan

class BlockSpaceSpan(private val mHeight: Int) : ReplacementSpan() {
    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        if (fm != null) {
            fm.top = -mHeight - paint.getFontMetricsInt(fm)
            fm.ascent = fm.top
            fm.bottom = 0
            fm.descent = fm.bottom
        }
        return 0
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {}
}