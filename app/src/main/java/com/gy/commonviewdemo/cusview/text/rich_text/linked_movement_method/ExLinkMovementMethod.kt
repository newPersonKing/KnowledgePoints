package com.gy.commonviewdemo.cusview.text.rich_text.linked_movement_method

import android.graphics.Rect
import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.widget.TextView

// 去掉了movementMethod的调用，直接托管onTouchEvent
// 整体的点击事件和ClickableSpan的点击事件可以同时设置
object  ExLinkMovementMethod : LinkMovementMethod() {
    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        val action = event.action
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            var x = event.x.toInt()
            var y = event.y.toInt()
            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop
            x += widget.scrollX
            y += widget.scrollY
            val layout = widget.layout
            val line = layout.getLineForVertical(y)
            var off = layout.getOffsetForHorizontal(line, x.toFloat())
            var xLeft = layout.getPrimaryHorizontal(off)
            if (xLeft < x) {
                if (off < widget.length() - 1) {
                    off += 1
                }
            } else {
                if (off > 0) {
                    off -= 1
                }
            }
            val links = buffer.getSpans(off, off, ClickableSpan::class.java)
            if (links.isNotEmpty()) {
                val span = links[0]
                val spanStartOffset = buffer.getSpanStart(span)
                val spanEndOffset = buffer.getSpanEnd(span)
                xLeft = layout.getPrimaryHorizontal(spanStartOffset)
                val bound = Rect()
                val offsetOfLine = layout.getLineForOffset(off)
                layout.getLineBounds(offsetOfLine, bound)
                if (y < bound.top || y > bound.bottom) {
                    return false
                }
                val xRight = layout.getPrimaryHorizontal(spanEndOffset - 1)
                if (xRight > xLeft && (x > xRight || x < xLeft)) {
                    return false
                }
                if (action == MotionEvent.ACTION_UP) {
                    links[0].onClick(widget)
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(
                        buffer,
                        buffer.getSpanStart(links[0]),
                        buffer.getSpanEnd(links[0])
                    )
                }
                return true
            } else {
                Selection.removeSelection(buffer)
            }
        }
        return false
    }
}