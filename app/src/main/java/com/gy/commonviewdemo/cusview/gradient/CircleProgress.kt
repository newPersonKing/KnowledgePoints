package com.gy.commonviewdemo.cusview.gradient

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class CircleProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)  {

    private var mWidth = 0
    private var mHeight = 0
    private var mStrokePaint = Paint()
    private var mFillPaint = Paint()

    init {

        mStrokePaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 15f
            color = Color.WHITE
        }

        mFillPaint.apply {
            style = Paint.Style.FILL
            color = Color.parseColor("#E1FFEC")
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.drawCircle(mWidth/2f,mHeight/2f,200f/2f,mFillPaint)

        canvas?.drawCircle(mWidth/2f,mHeight/2f,300f/2f,mStrokePaint)
    }

}