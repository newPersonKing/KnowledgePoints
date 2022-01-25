package com.gy.commonviewdemo.cusview.text.rich_text

import android.text.Editable
import android.widget.TextView
import java.util.*

// Android7.0系统以上getSpans方法会返回乱序的排列数组，这跟它的内部实现有关，所以，在使用getSpans时，如果希望获取顺序的排列数组，那就需要对返回结果进行手动排序，代码如下所示。
inline fun <reified T> getOrderedSpans(textView: TextView): Array<T> {
    val editable: Editable = textView.editableText
    val spans: Array<T> = editable.getSpans(0, textView.toString().length, T::class.java)
    Arrays.sort(spans) { o1, o2 -> editable.getSpanStart(o1) - editable.getSpanStart(o2) }
    return spans
}

