package com.gy.commonviewdemo.cusview.text

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.text.no_padding_textview.NoPaddingTextViewActivity
import com.gy.commonviewdemo.cusview.text.scroll_textview.ScrollTextViewActivity
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

        btn_scroll_textview.setOnClickListener {
            val intent = Intent(this,ScrollTextViewActivity::class.java)
            startActivity(intent)
        }

        btn_no_padding_textview.setOnClickListener {
            val intent = Intent(this,NoPaddingTextViewActivity::class.java)
            startActivity(intent)
        }

        math_view_1.setShaderColors(Color.parseColor("#ffFF4081"))
        math_view_1.setText("2H","2","",10f)
            .appendMarkText("+")
            .appendText("O","2","",10f)
            .appendMarkText("=")
            .appendText("2H","2","",10f)
            .appendText("O","","",10f);

        math_view_2.setShaderColors(Color.parseColor("#ffff9922"));
        math_view_2.setText("2","","2",10f)
            .appendMarkText("+")
            .appendText("5","","-1",10f)
            .appendMarkText("=")
            .appendText("4.2","","",10f);

        math_view_3.setShaderColors(Color.parseColor("#ffFFEAC4"));
        math_view_3.setText("H","2","0",10f)
            .appendMarkText("+")
            .appendText("Cu","","+2",10f)
            .appendText("O","","-2",10f)
            .appendMarkText("==")
            .appendText("Cu","","0",10f)
            .appendText("H","2","+1",10f)
            .appendText("O","","-2",10f);

        math_view_4.setShaderColors(Color.RED)
        math_view_4.setText("985","","GB",10f)
            .appendMarkText("+")
            .appendText("211","","MB",10f);

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