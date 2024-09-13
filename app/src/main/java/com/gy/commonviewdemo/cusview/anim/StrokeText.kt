package com.gy.commonviewdemo.cusview.anim

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

class StrokeText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    private var borderText : AppCompatTextView? = null

    init {
        borderText = AppCompatTextView(context,attrs)
        borderText?.paint?.apply {
            strokeWidth = 4f
            style = Paint.Style.STROKE
        }
        borderText?.also {
            gravity = this.gravity
            it.setTextColor(Color.RED)
        }
    }


    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        super.setLayoutParams(params)
        borderText?.layoutParams = params
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        borderText?.text = text
        borderText?.measure(widthMeasureSpec, heightMeasureSpec);
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        borderText?.layout(left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        // 直接绘制描边 效果并不是特别的完美
        // 应该先绘制描边 再绘制正文

//        paint.apply {
//            style = Paint.Style.STROKE
//            strokeWidth = 4f
//        }
        super.onDraw(canvas)
        borderText?.draw(canvas)
    }
}