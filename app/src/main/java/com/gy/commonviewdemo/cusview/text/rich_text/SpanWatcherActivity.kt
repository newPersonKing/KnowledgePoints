package com.gy.commonviewdemo.cusview.text.rich_text

import android.os.Bundle
import android.text.NoCopySpan
import android.text.Selection
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.text.rich_text.span_watcher.ExEditableFactory
import com.gy.commonviewdemo.cusview.text.rich_text.span_watcher.SelectionSpanWatcher
import kotlinx.android.synthetic.main.activity_span_watcher.*

class SpanWatcherActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_span_watcher)

        val span = UnderlineSpan()
        val spannableString = SpannableString("测试下划线内容123测试下划线测试下划线测试下划线测试下划线测试下划线测试下划线测试下划线测试下划线测试下划线")
        spannableString.setSpan(span, 5, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        et_span_watcher.setText(spannableString)

        val watchers = ArrayList<NoCopySpan>()
        watchers.add(SelectionSpanWatcher(UnderlineSpan::class))
        et_span_watcher.setEditableFactory(ExEditableFactory(watchers))

        et_span_watcher.setOnKeyListener { v, keyCode, event ->

            if(keyCode === KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN){
               val text = et_span_watcher.text
                val selectionStart = Selection.getSelectionStart(text)
                val selectionEnd = Selection.getSelectionEnd(text)
                if(selectionEnd != selectionStart) return@setOnKeyListener false
                val underlineSpans = text.getSpans(selectionStart,selectionEnd,UnderlineSpan::class.java)
                underlineSpans.firstOrNull()?.also {
                    val spanStart = text.getSpanStart(it)
                    val spanEnd = text.getSpanEnd(it)
                    text.replace(spanStart,spanEnd,"")
                    return@setOnKeyListener true
                }
            }

            return@setOnKeyListener false
        }
    }


}