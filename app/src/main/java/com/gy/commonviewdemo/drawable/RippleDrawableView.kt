package com.gy.commonviewdemo.drawable

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class RippleDrawableView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0

//    init {
//        createDrawable()
//    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        createDrawable()
    }

    private fun createDrawable(){

        val stateList = arrayOf(
            intArrayOf(R.attr.state_pressed), intArrayOf(R.attr.state_focused), intArrayOf(
                R.attr.state_activated
            ), intArrayOf()
        )

        //深蓝

        //深蓝
        val normalColor: Int = Color.GREEN
        //玫瑰红
        val pressedColor: Int = Color.BLUE
        val stateColorList = intArrayOf(
            pressedColor,
            pressedColor,
            pressedColor,
            pressedColor
        )
        val colorStateList = ColorStateList(stateList, stateColorList)

        val roundRectShape = RoundRectShape(floatArrayOf(5f,5f,5f,5f,5f,5f,5f,5f,), null, null)
        val maskDrawable = ShapeDrawable()
        maskDrawable.setBounds(0,0,width/2,height/2)
        maskDrawable.shape = roundRectShape
        maskDrawable.paint.style = Paint.Style.FILL
        // 拿到paint 可以设置渐变色
        maskDrawable.paint.color = normalColor

        val contentDrawable = ShapeDrawable()
        contentDrawable.shape = roundRectShape
        contentDrawable.paint.style = Paint.Style.FILL

        contentDrawable.paint.color = pressedColor

        //contentDrawable实际是默认初始化时展示的；maskDrawable 控制了rippleDrawable的范围

        //contentDrawable实际是默认初始化时展示的；maskDrawable 控制了rippleDrawable的范围
        val rippleDrawable = RippleDrawable(colorStateList, contentDrawable, maskDrawable)

        background = rippleDrawable
    }
}