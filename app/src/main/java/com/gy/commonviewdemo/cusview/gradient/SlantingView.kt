package com.gy.commonviewdemo.cusview.gradient

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class SlantingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    private var mWidth = 0
    private var mHeight = 0
    private val paint = Paint().apply {
        style = Paint.Style.FILL
    }

    private var color = Color.WHITE

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(mWidth == 0 || mHeight == 0)return

        val path = Path().apply {
            moveTo(0f,0f)
            lineTo(mWidth * 1f,0f)
            lineTo(mWidth * 0.8f,mHeight * 1f)
            lineTo(mWidth * 0.2f,mHeight * 1f)
            lineTo(0f,0f)
        }
        paint.color = color
        canvas?.drawPath(path,paint)
    }

    fun setColor(color:Int){
        this.color = color
        invalidate()
    }
}