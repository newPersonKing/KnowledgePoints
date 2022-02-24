package com.gy.commonviewdemo.cusview.text

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.text.ticker.TickerUtils
import kotlinx.android.synthetic.main.activity_textview.*
import java.util.*
import kotlin.concurrent.schedule


class TextViewActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textview)
        // 匹配高亮
        resolveHighLight()
        // 计算textView 的行数
        resolveLineCount()

        animNumTextView()

        autoFitTextView()

        expandTextView()

        tickerTextView()

    }

    private fun resolveHighLight(){
        val builder = HighLightUtil.stringToHighLight("测试文本测试文本测试文本测试文本测试文本测试文本测试文本测试文本","测试",false)
        high_light_tv.text = builder
    }

    private fun resolveLineCount(){
        tv_line_count.textSize = 20f
        val content = "测试行数测试行数测试行数测试行数测试行数测试行数测试行数测试行数测试行数测试行数测试行数测试行数测试行数测试行数"
        tv_line_count.text = content
        val count = tv_line_count.getLineCountByFontSize(content)
        Log.i("ccccccccccc","count===$count")
    }

    private fun animNumTextView(){
        anim_tv.setNumberString("1000","10000")
    }

    private fun autoFitTextView(){
        var content = "自适应宽度的 textView自适应宽度的 textView自适应宽度的 textView自适应宽度的 textView"
        auto_fit_tv.text = content
        auto_fit_tv.postDelayed({
            content+="自适应宽度的 textView自适应宽度的 textView自适应宽度的 textView自适应宽度的 textView"
            auto_fit_tv.text = content
        },1000)
    }

    private fun expandTextView(){
        tv_expandable.postDelayed({
            tv_expandable.text = "这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView这是可以收缩的textView"
        },2000)

    }

    private fun tickerTextView(){
        ticker_tv.setCharacterLists(TickerUtils.provideNumberList())
        ticker_tv.animationDuration = 3000
        ticker_tv.setText("这是",true)
        ticker_tv.postDelayed({
            ticker_tv.setText("不是我的",true)
        },2000)
    }

}