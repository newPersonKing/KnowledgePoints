package com.gy.commonviewdemo.cusview.gradient

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class EdgesView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0
    private var startColor = 0
    private var endColor = 0
    private var isColorChange = false
    private var strokeWidth = 10
    val paint = Paint().apply {
        color = Color.GREEN
    }

    private val borderPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = 20f
    }

    var shader : LinearGradient? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(mWidth == 0 || mHeight == 0) return

        val path = Path().apply {
            moveTo(0F,mHeight/2f)
            lineTo(mHeight/2f,0f)
            lineTo(mWidth - mHeight/2f,0f)
            lineTo(mWidth*1f,mHeight/2f)
            lineTo(mWidth - mHeight/2f,mHeight*1F)
            lineTo(mHeight/2f,mHeight*1f)
            lineTo(0f,mHeight/2f)
        }

        paint.clearShadowLayer()
        paint.shader  = if(shader == null || isColorChange) createShader()  else shader
        canvas?.drawPath(path,paint)

        val borderPath = Path().apply {
            moveTo(0f,mHeight/2f)
            lineTo(mHeight/2f,strokeWidth/2f)
            lineTo(mWidth - mHeight/2f,strokeWidth/2f)
            lineTo(mWidth*1f,mHeight/2f)
            lineTo(mWidth - mHeight/2f,mHeight*1F - strokeWidth/2f)
            lineTo(mHeight/2f,mHeight*1f -strokeWidth/2f)
            lineTo(0f,mHeight/2f)
        }

        canvas?.drawPath(borderPath,borderPaint)
    }

    private fun createShader():LinearGradient?{
        isColorChange =false
        if(startColor == 0 || endColor == 0)return null
        return LinearGradient(
            0F,
            mHeight /2f,
            mWidth*1f,
            mHeight /2f,
            intArrayOf(Color.parseColor("#4EB1F8"), Color.parseColor("#4EF8BB")),
            null,
            Shader.TileMode.CLAMP
        )
    }


    fun setColors(startColor:Int,endColor:Int){
        isColorChange = true
        this.startColor = startColor
        this.endColor = endColor
        invalidate()
    }

}