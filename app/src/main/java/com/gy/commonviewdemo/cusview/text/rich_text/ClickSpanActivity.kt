package com.gy.commonviewdemo.cusview.text.rich_text

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.text.rich_text.linked_movement_method.ExLinkMovementMethod
import com.gy.commonviewdemo.cusview.text.rich_text.linked_movement_method.MyLinkedMovementMethod
import com.gy.commonviewdemo.cusview.text.rich_text.span.ExClickableSpan
import kotlinx.android.synthetic.main.activity_click_span.*

class ClickSpanActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_click_span)
        clickSpanCommon()

        exClickableSpan()

        exMutiClickableSpan()

        doubleClickResolveOne()

        doubleClickResolveTwo()

        doubleClickResolveBest()

        clickSelectSentence()
    }


    private fun clickSpanCommon(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月，是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读，起点中文网，新丽传媒等业界品牌。")
        textString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@ClickSpanActivity, "clickSpanCommon", Toast.LENGTH_SHORT).show()
            }
        }, 4, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_click_common_span.highlightColor = Color.LTGRAY
        tv_click_common_span.text = textString
        tv_click_common_span.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun exClickableSpan(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月，是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读，起点中文网，新丽传媒等业界品牌。")
        textString.setSpan(ExClickableSpan{
            Toast.makeText(this@ClickSpanActivity, "exClickableSpan==$it", Toast.LENGTH_SHORT).show()
        }, 4, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        //对于默认选中的高亮效果，可以通过设置highlightColor来解决，这是TextView的默认逻辑，要去掉高亮，只需要将highlightColor设置为透明即可。
        tv_ex_click_span.highlightColor = Color.TRANSPARENT
        tv_ex_click_span.text = textString
        tv_ex_click_span.movementMethod = LinkMovementMethod.getInstance()
    }

    //我们给TextView设置点击事件，同时给TextView中的两个不同的Span设置点击事件，这时候再点击，
    // 就会发现ClickableSpan的一个新的问题，那就是在点击ClickableSpan的时候，TextView如果设置了点击事件，
    // 则也会响应，也就是说，点击ClickableSpan区域，会触发两次点击事件。
    private fun exMutiClickableSpan(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月，是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读，起点中文网，新丽传媒等业界品牌。")
        textString.setSpan(ExClickableSpan {
            Toast.makeText(this@ClickSpanActivity, "ClickableSpan1 $it", Toast.LENGTH_SHORT).show()
        }, 4, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textString.setSpan(ExClickableSpan {
            Toast.makeText(this@ClickSpanActivity, "ClickableSpan2 $it", Toast.LENGTH_SHORT).show()
        }, 11, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_ex_muit_click_span.setOnClickListener {
            Toast.makeText(this@ClickSpanActivity, "Text", Toast.LENGTH_SHORT).show()
        }
        //对于默认选中的高亮效果，可以通过设置highlightColor来解决，这是TextView的默认逻辑，要去掉高亮，只需要将highlightColor设置为透明即可。
        tv_ex_muit_click_span.highlightColor = Color.TRANSPARENT
        tv_ex_muit_click_span.text = textString
        tv_ex_muit_click_span.movementMethod = LinkMovementMethod.getInstance()
    }

    // 点击事件触发多次 解决办法1
    //这种方式一般会在TextView外面套一层用于处理点击事件的Container，例如FrameLayout，将整体的点击事件设置在FrameLayout中，
    // ClickableSpan的事件依然在ClickableSpan中设置，同时，修改movementMethod。
    private fun doubleClickResolveOne(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月，是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读，起点中文网，新丽传媒等业界品牌。")
        textString.setSpan(ExClickableSpan {
            Toast.makeText(this@ClickSpanActivity, "ClickableSpan1 $it", Toast.LENGTH_SHORT).show()
        }, 4, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textString.setSpan(ExClickableSpan {
            Toast.makeText(this@ClickSpanActivity, "ClickableSpan2 $it", Toast.LENGTH_SHORT).show()
        }, 11, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        container.setOnClickListener {
            Toast.makeText(this@ClickSpanActivity, "Text", Toast.LENGTH_SHORT).show()
        }
        tv_double_click_span_resolve_one.highlightColor = Color.TRANSPARENT
        tv_double_click_span_resolve_one.text = textString
        tv_double_click_span_resolve_one.movementMethod = MyLinkedMovementMethod
    }

    // 点击事件触发多次 解决办法2
    // 这种修改的关键点，实际上就是最后的返回值，由return super.onTouchEvent(widget, buffer, event);改为了return false，从而打断传递链。
    private fun doubleClickResolveTwo(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月，是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读，起点中文网，新丽传媒等业界品牌。")
        textString.setSpan(ExClickableSpan {
            Toast.makeText(this@ClickSpanActivity, "ClickableSpan1 $it", Toast.LENGTH_SHORT).show()
        }, 4, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textString.setSpan(ExClickableSpan {
            Toast.makeText(this@ClickSpanActivity, "ClickableSpan2 $it", Toast.LENGTH_SHORT).show()
        }, 11, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_double_click_span_resolve_two.setOnClickListener {
            Toast.makeText(this@ClickSpanActivity, "Text", Toast.LENGTH_SHORT).show()
        }
        tv_double_click_span_resolve_two.setOnTouchListener { _, event ->
            ExLinkMovementMethod.onTouchEvent(
                tv_double_click_span_resolve_two,
                Spannable.Factory.getInstance().newSpannable(tv_double_click_span_resolve_two.text), event
            )
        }
        tv_double_click_span_resolve_two.highlightColor = Color.TRANSPARENT
        tv_double_click_span_resolve_two.text = textString
    }

    // 只需要在TextView的click事件上增加一层判断即可。因为当点击ClickableSpan时，
    // TextView的selectionStart和selectionEnd会改变，这时候就不用处理TextView的点击事件了，经过这层过滤，就实现了TextView和ClickableSpan的互斥点击。
    private fun doubleClickResolveBest(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月，是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读，起点中文网，新丽传媒等业界品牌。")
        textString.setSpan(ExClickableSpan {
            Toast.makeText(this@ClickSpanActivity, "ClickableSpan1 $it", Toast.LENGTH_SHORT).show()
        }, 4, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textString.setSpan(ExClickableSpan {
            Toast.makeText(this@ClickSpanActivity, "ClickableSpan2 $it", Toast.LENGTH_SHORT).show()
        }, 11, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_double_click_span_resolve_best.setOnClickListener {
            Toast.makeText(this@ClickSpanActivity, "Text", Toast.LENGTH_SHORT).show()
        }
        tv_double_click_span_resolve_best.setOnClickListener {
            if (tv_double_click_span_resolve_best.selectionStart == -1 && tv_double_click_span_resolve_best.selectionEnd == -1) {
                Toast.makeText(this@ClickSpanActivity, "Text", Toast.LENGTH_SHORT).show()
            }
        }

        tv_double_click_span_resolve_best.highlightColor = Color.TRANSPARENT
        tv_double_click_span_resolve_best.text = textString
        tv_double_click_span_resolve_best.movementMethod = LinkMovementMethod.getInstance()
    }

    // 在ScrollerView 等等滑动view 中highlightColor 不生效
    private fun clickSelectSentence(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月，是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读，起点中文网，新丽传媒等业界品牌。")
        val indices = getIndices(textString.toString(), '，')

        var start = 0
        var end: Int
        for (i in 0..indices.size) {
            val clickSpan: ClickableSpan = getClickableSpan()
            end = if (i < indices.size) indices[i] else textString.length
            textString.setSpan(
                clickSpan, start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            start = end + 1
        }
        tv_click_select_sentence.highlightColor = Color.CYAN
        tv_click_select_sentence.text = textString
        tv_click_select_sentence.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun getClickableSpan(): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                val tv = widget as TextView
                tv.text.subSequence(
                    tv.selectionStart,
                    tv.selectionEnd
                ).toString()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }
    }

    private fun getIndices(text: String, char: Char): MutableList<Int> {
        var pos = text.indexOf(char, 0)
        val indices: MutableList<Int> = mutableListOf()
        while (pos != -1) {
            indices.add(pos)
            pos = text.indexOf(char, pos + 1)
        }
        return indices
    }

}