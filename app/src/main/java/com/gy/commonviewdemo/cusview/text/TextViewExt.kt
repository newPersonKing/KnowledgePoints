package com.gy.commonviewdemo.cusview.text

import android.graphics.Rect
import android.os.Build
import android.text.*
import android.util.Log
import android.widget.TextView


/*gy 根据内容 和字体 获取在textView中可以显示几行 可能会有偏差*/
fun TextView.getLineCountByFontSize(content: String, width: Int = 0):Int{
    val textPaint = TextPaint()
    textPaint.textSize = textSize
    val dm = context.resources.displayMetrics
    val viewWidth = if(width>0) width else dm.widthPixels
    val staticLayout  = StaticLayout.Builder.obtain(
        content,
        0,
        content.length,
        textPaint,
        viewWidth
    ).build()
    val lineCount =  staticLayout.lineCount;
    return lineCount
}


fun String.htmlContentToSpannableStringBuilder() : SpannableStringBuilder {

    /*开始添加一对标签 作用1 保证刚开始就替换handler,自定义的handler先处理 2 保证不自动拼接html body 方便处理未处理的<>*/
    var newContent = "<ivyDad></ivyDad>$this"
    /*处理空格错误导致 英文换行错误*/
    newContent = newContent.replace("&nbsp;", "&#32;")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(
            newContent, Html.FROM_HTML_MODE_LEGACY, null, WrapperContentHandler(
                HtmlTagHandler()
            )
        ) as SpannableStringBuilder
    } else {
        return Html.fromHtml(newContent, null, WrapperContentHandler(HtmlTagHandler())) as SpannableStringBuilder
    }
}


/**
 * 获取 SimpleClickSpan 的位置
 * 参考：https://stackoverflow.com/questions/11905486/how-get-coordinate-of-a-clickablespan-inside-a-textview
 *
 * @param start 计算区域的开始字符下标
 * @param end 计算区域结束字符下标
 */
fun TextView.getRect(start: Int, end: Int) : Rect{

    val desRect = Rect()

    val startOffsetOfClickedText = start.toDouble()
    val endOffsetOfClickedText = end.toDouble()
    // 获取该字符的左边位置
    var startXCoordinatesOfClickedText = layout.getPrimaryHorizontal(
        startOffsetOfClickedText.toInt()
    ).toDouble()
    var endXCoordinatesOfClickedText = layout.getPrimaryHorizontal(
        endOffsetOfClickedText.toInt()
    ).toDouble()


    // 获取该字符所在的行数
    val currentLineStartOffset = layout.getLineForOffset(startOffsetOfClickedText.toInt())
    val currentLineEndOffset = layout.getLineForOffset(endOffsetOfClickedText.toInt())
    // 判断是否是多行
    val keywordIsInMultiLine = currentLineStartOffset != currentLineEndOffset


    // 获取view 在屏幕上的位置
    val parentTextViewLocation = intArrayOf(0, 0)
    getLocationOnScreen(parentTextViewLocation)

    // parentTextViewTopAndBottomOffset 计算的是 textview 第一行文本 在屏幕中的位置
    val parentTextViewTopAndBottomOffset = (parentTextViewLocation[1] -
            scrollY +
            compoundPaddingTop).toDouble()


    // 处理多行
    if (keywordIsInMultiLine) {
//        val screenHeight: Int = 100 // TODO 修改为获取屏幕的高度
//        val dyTop = parentTextViewRect.top
//        val dyBottom = screenHeight - parentTextViewRect.bottom
//        val onTop = dyTop > dyBottom
//        if (onTop) {
//            endXCoordinatesOfClickedText =
//                layout.getLineRight(currentLineStartOffset).toDouble()
//        } else {
        layout.getLineBounds(currentLineEndOffset, desRect)
        desRect.top += parentTextViewTopAndBottomOffset.toInt()
        desRect.bottom += parentTextViewTopAndBottomOffset.toInt()
        startXCoordinatesOfClickedText =
            layout.getLineLeft(currentLineEndOffset).toDouble()
//        }
    }else {
        //获取当前行的区域
        layout.getLineBounds(currentLineStartOffset, desRect)
        // parentTextViewRect.top 当前行所占区域的顶部 相对于第一行文本顶部
        // parentTextViewRect.bottom 当前行所占区域的底部 相对于第一行文本
        // 这里 加 parentTextViewTopAndBottomOffset 计算的是 当前行在屏幕中的位置
        desRect.top += parentTextViewTopAndBottomOffset.toInt()
        desRect.bottom += parentTextViewTopAndBottomOffset.toInt()
    }

    // textview 在屏幕中的位置 + 当前文字左边的距离 - 偏移量 计算结果是 当前文字在屏幕上的位置
    desRect.left += (parentTextViewLocation[0] +
            startXCoordinatesOfClickedText +
            compoundPaddingLeft -
            scrollX).toInt()
    desRect.right = (desRect.left +
            endXCoordinatesOfClickedText -
            startXCoordinatesOfClickedText).toInt()

    return desRect
}