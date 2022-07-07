package com.gy.commonviewdemo.cusview.gradient

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.gy.commonviewdemo.R

class ThreeGradientCornerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {


    private var mWidth = 0
    private var mHeight = 0
    private var borderWidth = 10
    private var oneColor = Color.TRANSPARENT
    private var secondColor = Color.TRANSPARENT
    private var progressColor = Color.TRANSPARENT
    private var progress = 0.8f
    private var mPaint = Paint().apply {
        style = Paint.Style.FILL
    }

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.ThreeGradientCornerView)
        oneColor = typeArray.getColor(R.styleable.ThreeGradientCornerView_one_color,Color.TRANSPARENT)
        secondColor = typeArray.getColor(R.styleable.ThreeGradientCornerView_second_color,Color.TRANSPARENT)
        progressColor = typeArray.getColor(R.styleable.ThreeGradientCornerView_progress_color,Color.TRANSPARENT)
        borderWidth = typeArray.getDimension(R.styleable.ThreeGradientCornerView_bg_border_color,10f).toInt()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        mPaint.color = oneColor
        canvas?.drawRoundRect(0f,0f,mWidth*1f,mHeight*1f,mHeight/2f,mHeight/2f,mPaint)
        mPaint.color = secondColor
        canvas?.drawRoundRect(0f + borderWidth,0f+borderWidth,mWidth*1f - borderWidth,mHeight*1f - borderWidth,mHeight/2f - borderWidth,mHeight/2f- borderWidth,mPaint)

        canvas?.save()
        canvas?.clipPath(Path().apply {
            addRect(0f + 2 * borderWidth,0f+2 * borderWidth,(mWidth*1f -2 * borderWidth) * progress,mHeight*1f - 2 * borderWidth,
                Path.Direction.CW)
        })
        mPaint.color = progressColor
        canvas?.drawRoundRect(0f + 2 * borderWidth,0f+ 2 * borderWidth,mWidth*1f - 2 * borderWidth,mHeight*1f - 2 * borderWidth,mHeight/2f - borderWidth,mHeight/2f- borderWidth,mPaint)
        canvas?.restore()

        super.onDraw(canvas)
    }

    fun setPercent(progress:Float){
        this.progress = progress
    }
}