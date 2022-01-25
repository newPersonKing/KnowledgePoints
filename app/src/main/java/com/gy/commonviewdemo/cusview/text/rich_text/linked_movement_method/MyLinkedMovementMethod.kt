package com.gy.commonviewdemo.cusview.text.rich_text.linked_movement_method

import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView

object MyLinkedMovementMethod : LinkMovementMethod() {
    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        // 如果能正确匹配到ClickSpan 这里会返回true 否则是false
        val isConsume = super.onTouchEvent(widget, buffer, event)
        if (!isConsume && event.action == MotionEvent.ACTION_UP) {
            val parent = widget.parent
            if (parent is ViewGroup) {
                parent.performClick()
            }
        }
        return isConsume
    }
}