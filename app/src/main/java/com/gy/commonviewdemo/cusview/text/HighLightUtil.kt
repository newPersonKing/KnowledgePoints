package com.gy.commonviewdemo.cusview.text

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.util.Log
import java.util.regex.Matcher
import java.util.regex.Pattern


object HighLightUtil {

    /**
     *  关键字高亮显示
     *  text  原文
     *  keyWord 需要高亮显示的关键字
     *  isCut 是否需要做分词高亮展示
     *  isCut = true  关键字里的每一个字，只要有都会高亮
     *  isCut = false（默认） 只有整词才会高亮
     **/
    fun stringToHighLight(text: String, keyWord: String, isCut: Boolean = false): SpannableStringBuilder {
        val spannable = SpannableStringBuilder(text)
        try {
            var keyword: MutableList<String> = ArrayList()
            if (isCut) {
                for (i in keyWord.indices) {
                    keyword.add(keyWord.substring(i, i + 1))
                }
            } else {
                keyword = arrayListOf(keyWord)
            }
            var span: CharacterStyle?
            var wordReg: String
            for (i in keyword.indices) {
                var key = ""
                if (keyword[i].contains("*") || keyword[i].contains("(") || keyword[i].contains(")")) {
                    val chars = keyword[i].toCharArray()
                    for (k in chars.indices) {
                        key = if (chars[k] == '*' || chars[k] == '(' || chars[k] == ')') {
                            key + "\\" + chars[k].toString()
                        } else {
                            key + chars[k].toString()
                        }
                    }
                    keyword[i] = key
                }

//                (?i) 表示所在位置右侧的表达式开启忽略大小写模式
//                (?s) 表示所在位置右侧的表达式开启单行模式
//                (?m) 表示所在位置右侧的表示式开启指定多行模式
//                (?is) 更改句点字符 (.) 的含义，以使它与每个字符（而不是除 \n 之外的所有字符）匹配
//                (?im) 更改 ^ 和 $ 的含义，以使它们分别与任何行的开头和结尾匹配,而不只是与整个字符串的开头和结尾匹配
                wordReg = "(?m)" + keyword[i]
                val pattern: Pattern = Pattern.compile(wordReg)
                val matcher: Matcher = pattern.matcher(text)
                while (matcher.find()) {
                    span = ForegroundColorSpan(Color.parseColor("#4599F7"))
                    spannable.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_MARK_MARK)
                }
            }
        } catch (e: Exception) {
            Log.i("error","error===${e.toString()}")
        }
        return spannable
    }

}