package com.gy.commonviewdemo.cusview.text.scroll_textview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ScrollTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {


//    private val array = IntArray(2)
//    private var rectOnScreen = Rect()
//    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        super.onLayout(changed, l, t, r, b)
//        if(changed){
//            getLocationOnScreen(array)
//            rectOnScreen.set(array[0], array[1], array[0] + r - l, array[1] + b - t)
//        }
//    }
//
//    /**
//     * 用于单词高亮时，使高亮单词可见 竖屏使用
//     */
//    fun makeItVisible(rect: Rect){
//        if(rectOnScreen.isEmpty) return
//        if(rect.top != rectOnScreen.top){
//            var dy = 0
//            if(rect.top < rectOnScreen.top || (rect.bottom + rect.bottom - rect.top) >= rectOnScreen.bottom){
//                dy = rect.top - rectOnScreen.top
//            }
//            if(dy != 0) {
////                fling(dy)
////                smoothScrollByDuration(0, dy, 1000)
//                scrollBy(0,dy)
//            }
//        }
//    }
}