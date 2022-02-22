package com.gy.commonviewdemo.cusview.text.rich_text.span_watcher

import android.text.Selection
import android.text.SpanWatcher
import android.text.Spannable
import java.lang.Math.abs
import kotlin.reflect.KClass


class SelectionSpanWatcher<T : Any>(val clazz: KClass<T>) : SpanWatcher {

    override fun onSpanAdded(text: Spannable?, what: Any?, start: Int, end: Int) {
    }

    override fun onSpanRemoved(text: Spannable?, what: Any?, start: Int, end: Int) {
    }

    private var lastSelectionStart : Int = 0
    private var lastSelectionEnd : Int = 0
    override fun onSpanChanged(
        text: Spannable,
        what: Any?,
        ostart: Int,
        oend: Int,
        nstart: Int,
        nend: Int
    ) {
        //光标从左 进入span
        if(what == Selection.SELECTION_START && lastSelectionStart != nstart){
            lastSelectionStart = nstart
            text.getSpans(nstart,nend,clazz.java).firstOrNull()?.also {
                val spanStart = text.getSpanStart(it)
                val spanEnd = text.getSpanEnd(it)
                val index = if(abs(spanEnd - lastSelectionStart) > abs(spanStart - lastSelectionStart)) spanStart else spanEnd
                // TODO 这里为什么要设置 Selection.getSelectionStart(text)
                Selection.setSelection(text,index,Selection.getSelectionStart(text))
            }
        }

        // 光标从右进入span
        if(what == Selection.SELECTION_END && lastSelectionEnd != nend){
            lastSelectionEnd = nend
            text.getSpans(nstart,nend,clazz.java).firstOrNull()?.also {
                val spanStart = text.getSpanStart(it)
                val spanEnd = text.getSpanEnd(it)
                val index = if(abs(spanEnd - lastSelectionStart) > abs(spanStart - lastSelectionStart)) spanStart else spanEnd
                // TODO 这里为什么要设置 Selection.getSelectionStart(text)
                Selection.setSelection(text,Selection.getSelectionStart(text),index)
            }
        }
    }

}