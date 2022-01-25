package com.gy.commonviewdemo.cusview.text.rich_text.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.style.LeadingMarginSpan
import android.util.Log

class TextRoundSpan(private val lines: Int, private val margin: Int) :
    LeadingMarginSpan.LeadingMarginSpan2 {

    override fun getLeadingMargin(first: Boolean): Int {
        return if (first) {
            margin
        } else {
            0
        }
    }

    override fun drawLeadingMargin(c: Canvas?, p: Paint?, x: Int, dir: Int, top: Int, baseline: Int, bottom: Int, text: CharSequence?, start: Int, end: Int, first: Boolean, layout: Layout?) {
        Log.i("TextRoundSpan","text====$text")
    }

    override fun getLeadingMarginLineCount(): Int = lines
}