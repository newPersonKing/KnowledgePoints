package com.gy.commonviewdemo.cusview.progress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.ext.drawCenterText

class ArcProgress @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0
    private var innerStrokeWith = 0f
    private var innerStrokeColor = Color.WHITE
    private var outStrokeWidth = 0f
    private var outStrokeColor = Color.WHITE
    private var angle = 300
    private var mPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.ArcProgress)
        innerStrokeWith = typeArray.getDimension(R.styleable.ArcProgress_inner_stroke_width,0f)
        innerStrokeColor = typeArray.getColor(R.styleable.ArcProgress_inner_stroke_color,Color.WHITE)
        outStrokeWidth = typeArray.getDimension(R.styleable.ArcProgress_out_stroke_with,0f)
        outStrokeColor= typeArray.getColor(R.styleable.ArcProgress_out_stroke_color,Color.WHITE)
        angle = typeArray.getInt(R.styleable.ArcProgress_arc_angle,300)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(mWidth == 0|| mHeight == 0)return
        var startAngle = 90 - (360 - angle)/2
        mPaint.color = outStrokeColor
        mPaint.strokeWidth = outStrokeWidth
        canvas.drawArc(outStrokeWidth,outStrokeWidth,mWidth.toFloat()-outStrokeWidth,mHeight.toFloat()-outStrokeWidth,startAngle.toFloat(),-angle.toFloat(),false,mPaint)

        mPaint.color = Color.WHITE
        mPaint.strokeWidth = outStrokeWidth
        mPaint.strokeWidth = innerStrokeWith + 3
        canvas.drawArc(outStrokeWidth,outStrokeWidth,mWidth.toFloat()-outStrokeWidth,mHeight.toFloat()-outStrokeWidth,startAngle.toFloat(),-angle.toFloat(),false,mPaint)


        mPaint.strokeWidth  = innerStrokeWith
        mPaint.color = innerStrokeColor
        val innerOffset = outStrokeWidth
        canvas.drawArc(innerOffset,innerOffset,mWidth.toFloat()-innerOffset,mHeight.toFloat()-innerOffset,startAngle.toFloat(),-angle.toFloat(),false,mPaint)
    }
}