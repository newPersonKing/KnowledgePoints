package com.gy.commonviewdemo.cusview.text.rich_text

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_replace_span.*


class ReplaceSpanActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_replace_span)
        val textString = "阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月,阅文集团"
        tv_span.text = textString

        addSpan.setOnClickListener {
            tv_span.text =  textString.addSpan("尾部追加", listOf<Any>(
                BackgroundColorSpan(Color.RED),
                ForegroundColorSpan(Color.YELLOW)
            ))
        }

        insertSpan.setOnClickListener {
            tv_span.text =  textString.addSpan(3,"中间插入", listOf<Any>(
                BackgroundColorSpan(Color.RED),
                ForegroundColorSpan(Color.YELLOW)
            ))
        }

        replaceSpan.setOnClickListener {
            tv_span.text =  textString.replaceSpan("阅文集团"){

                return@replaceSpan BackgroundColorSpan(Color.RED)
            }
        }

        replaceLastSpan.setOnClickListener {
            tv_span.text =  textString.replaceSpanLast("阅文集团"){

                return@replaceSpanLast listOf(BackgroundColorSpan(Color.RED),
                    ForegroundColorSpan(Color.YELLOW))
            }
        }
    }

}