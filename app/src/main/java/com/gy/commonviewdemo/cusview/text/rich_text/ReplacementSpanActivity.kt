package com.gy.commonviewdemo.cusview.text.rich_text

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.text.rich_text.span.RoundBackgroundColorSpan
import kotlinx.android.synthetic.main.activity_replacement_span.*

//需要注意的是，在混合使用多个Span时，由于ReplacementSpan会改变Span的宽度，
// 所以一般需要最先设置ReplacementSpan，再设置其它Span，避免由于调整尺寸后导致坐标的变化，出现例如ClickableSpan点击错位的问题。
class ReplacementSpanActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_replacement_span)

        roundBackground()
    }

    private fun roundBackground(){
        val textString = SpannableStringBuilder(" ")
        textString.append("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月。是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读、起点中文网、新丽传媒等业界品牌。")
        textString.setSpan(RoundBackgroundColorSpan(Color.RED, Color.WHITE, 8, "彩彩彩"), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_round_background_span.text = textString
    }

}