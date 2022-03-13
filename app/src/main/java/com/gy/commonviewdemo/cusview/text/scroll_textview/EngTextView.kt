package com.gy.commonviewdemo.cusview.text.scroll_textview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.widget.OverScroller
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView


class EngTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    val textView = ScrollTextView(context)

    init {
        // 是子布局充满
        isFillViewport = true

        textView.setLineSpacing(14F, 1F)  // 设置行间距
        addView(textView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        textView.text = "这是一段神话故事。这是一段神话故事。这是一段神话故事。这是一段神话故事。这是一段神话故事。这是一段神话故事。这是一段神话故事。这是一段神话故事。这是一段神话故事。这是一段神话故事。这是一段神话故事。这是一段神话故事。这是一段神话故事。这是一段神话故事。"
    }

    private val array = IntArray(2)
    private var rectOnScreen = Rect()
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if(changed){
            getLocationOnScreen(array)
            rectOnScreen.set(array[0], array[1], array[0] + r - l, array[1] + b - t)
        }
    }


    /**
     * 用于单词高亮时，使高亮单词可见 竖屏使用
     */
    fun makeItVisible(rect: Rect){
        if(rectOnScreen.isEmpty) return
        if(rect.top != rectOnScreen.top){
            var dy = 0
            if(rect.top < rectOnScreen.top || (rect.bottom + rect.bottom - rect.top) >= rectOnScreen.bottom){
                dy = rect.top - rectOnScreen.top
            }
            if(dy != 0) {
                fling(dy)
                smoothScrollBy(0, dy)
            }
        }
    }

    /**
     * 用于单词高亮时，使高亮单词可见 竖屏使用
     */
    fun makeItVisible2(rect: Rect){
        if(rectOnScreen.isEmpty) return
        if(rect.top != rectOnScreen.top){
            var dy = 0
            if(rect.top < rectOnScreen.top || (rect.bottom + rect.bottom - rect.top) >= rectOnScreen.bottom){
                dy = rect.top - rectOnScreen.top
            }
            if(dy != 0) {
                fling(dy)
                smoothScrollByDuration(0, dy, 2000)
            }
        }
    }

    private fun smoothScrollByDuration(dx :Int,dy :Int, customDuration :Int){
        if(childCount == 0){
            return
        }

        try {
            val fieldScroller = this.javaClass.superclass.getDeclaredField("mScroller")
            fieldScroller.isAccessible = true
            val mScroller = fieldScroller.get(this) as OverScroller

            val fieldLastScroll = this.javaClass.superclass.getDeclaredField("mLastScroll")
            fieldLastScroll.isAccessible = true
            var mLastScroll = fieldLastScroll.get(this) as Long

            val duration = AnimationUtils.currentAnimationTimeMillis() - mLastScroll
            if(duration > 100){
                val height = height - paddingBottom - paddingTop
                val bottom = getChildAt(0).height
                val maxY = 0.coerceAtLeast(bottom - height)
                val scrollY = scrollY
                val newDy = 0.coerceAtLeast((scrollY + dy).coerceAtMost(maxY)) - scrollY
                mScroller.startScroll(scrollX, scrollY, 0, newDy, customDuration)
                ViewCompat.postInvalidateOnAnimation(this)
            } else {
                if(!mScroller.isFinished){
                    mScroller.abortAnimation()
                }
                scrollBy(dx, dy)
            }
            mLastScroll = AnimationUtils.currentAnimationTimeMillis()
        }catch (e :Exception){
            smoothScrollBy(dx, dy)
        }
    }

}