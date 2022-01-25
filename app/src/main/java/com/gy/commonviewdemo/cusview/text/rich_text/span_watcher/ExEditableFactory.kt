package com.gy.commonviewdemo.cusview.text.rich_text.span_watcher

import android.text.Editable
import android.text.NoCopySpan
import android.text.SpannableStringBuilder
import android.text.Spanned

class ExEditableFactory(private val spans: List<NoCopySpan>) : Editable.Factory() {
    override fun newEditable(source: CharSequence): Editable {
        val spannableStringBuilder = SpannableStringBuilder(source)
        for (span in spans) {
            spannableStringBuilder.setSpan(span, 0, source.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE or Spanned.SPAN_PRIORITY)
        }
        return spannableStringBuilder
    }
}