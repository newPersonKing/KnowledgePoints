package com.gy.commonviewdemo.cusview.text.rich_text.span

import android.graphics.Color
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

class ExClickableSpan(val onSpanClick: (String) -> Unit) : ClickableSpan() {

    override fun onClick(widget: View) {
        val tv = widget as TextView
        val s = tv.text as Spanned
        val start = s.getSpanStart(this)
        val end = s.getSpanEnd(this)
        onSpanClick(s.subSequence(start, end).toString())
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = Color.parseColor("#5790DF")
        ds.isUnderlineText = false
    }
}