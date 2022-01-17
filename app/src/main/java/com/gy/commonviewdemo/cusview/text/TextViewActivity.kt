package com.gy.commonviewdemo.cusview.text

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_textview.*


class TextViewActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textview)
        // 匹配高亮
        resolveHighLight()
        // 计算textView 的行数
        resolveLineCount()
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

}