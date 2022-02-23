package com.gy.commonviewdemo.cusview.text

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.DemoData
import com.gy.commonviewdemo.MainAdapter
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.text.rich_text.*
import kotlinx.android.synthetic.main.activity_span_main.*

class SpanEnterActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_span_main)

        val adapter = MainAdapter(listOf(
            DemoData("官方常用实现", RichTextActivity::class.java,this),
            DemoData("ImageSpan", ImageSpanActivity::class.java,this),
            DemoData("ClickSpan", ClickSpanActivity::class.java,this),
            DemoData("replaceSpan", ReplacementSpanActivity::class.java,this),
            DemoData("SpanWatcher", SpanWatcherActivity::class.java,this),
            DemoData("替换Span", ReplaceSpanActivity::class.java,this),
        ))

        rv_span_main.layoutManager = LinearLayoutManager(this)
        rv_span_main.adapter = adapter
    }

}