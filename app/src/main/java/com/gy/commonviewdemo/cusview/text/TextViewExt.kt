package com.gy.commonviewdemo.cusview.text

import android.content.Context
import android.os.Build
import android.text.*
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.TextView


/*gy 根据内容 和字体 获取在textView中可以显示几行 可能会有偏差*/
fun TextView.getLineCountByFontSize(content:String, width:Int = 0):Int{
    val textPaint = TextPaint()
    textPaint.textSize = textSize
    val dm = context.resources.displayMetrics
    val viewWidth = if(width>0) width else dm.widthPixels
    val staticLayout  = StaticLayout.Builder.obtain(content,0,content.length,textPaint,viewWidth).build()
    val lineCount =  staticLayout.lineCount;
    return lineCount
}


fun String.htmlContentToSpannableStringBuilder() : SpannableStringBuilder {

    /*开始添加一对标签 作用1 保证刚开始就替换handler,自定义的handler先处理 2 保证不自动拼接html body 方便处理未处理的<>*/
    var newContent = "<ivyDad></ivyDad>$this"
    /*处理空格错误导致 英文换行错误*/
    newContent = newContent.replace("&nbsp;","&#32;")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(newContent, Html.FROM_HTML_MODE_LEGACY,null,WrapperContentHandler(HtmlTagHandler())) as SpannableStringBuilder
    } else {
        return Html.fromHtml(newContent,null,WrapperContentHandler(HtmlTagHandler())) as SpannableStringBuilder
    }
}