package com.gy.commonviewdemo.cusview.gradient

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.gy.commonviewdemo.R

// 单边斜
class SingleSlant @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0
    private var percent = 0.1f
    private var bgColor = Color.TRANSPARENT
    private var paint = Paint().apply {
        style = Paint.Style.FILL
    }

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.SingleSlant)
        percent = typeArray.getFloat(R.styleable.SingleSlant_slant_percent,0f)
        bgColor = typeArray.getColor(R.styleable.SingleSlant_single_slant_bg_color,Color.TRANSPARENT)
        typeArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val path = Path()
        path.moveTo(mWidth * percent,0f)
        path.lineTo(mWidth * 1f,0f)
        path.lineTo(mWidth * 1f,mHeight * 1f)
        path.lineTo(0f,mHeight * 1f)
        path.lineTo(mWidth * percent,0f)
        paint.color = bgColor
        canvas?.drawPath(path,paint)
    }
}