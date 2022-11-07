package com.gy.commonviewdemo.cusview.threeD

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.gy.commonviewdemo.R
import java.lang.Exception

class Rotate3DView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var mWidth = 0
    private var mHeight = 0
    private var animation:Rotate3dAnimation? = null
    private var circleCount = 10 //转多少圈停止
    private var duration =  5 //时长 s

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.Rotate3DView)
        circleCount = typeArray.getInt(R.styleable.Rotate3DView_circle_count,10)
        duration = typeArray.getInt(R.styleable.Rotate3DView_rotate_duration,5)
        typeArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        resetAnimation()
    }

    private fun resetAnimation(){
        animation = Rotate3dAnimation(0f,circleCount * 360f,mWidth/2f,mHeight/2f,10f,false)
        animation?.duration = duration * 1000L
        animation?.fillAfter = true
        animation?.interpolator = LinearOutSlowInInterpolator()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if(childCount != 2){
            throw Exception("必须指定两个child")
        }
    }


    fun startAnimation(){
        clearAnimation()
        startAnimation(animation)
        animation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                startEndSwitchAnim()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
    }

    private fun startEndSwitchAnim(){
        if(childCount!=2)return
        val alphaAnimation = ObjectAnimator.ofFloat(1f,0f)
        alphaAnimation.duration = 500
        alphaAnimation.addUpdateListener {
            val firstChild = getChildAt(0)
            firstChild.alpha = it.animatedValue as Float
            val secondChild = getChildAt(1)
            secondChild.visibility = View.VISIBLE
            secondChild.alpha = 1 - it.animatedValue as Float
        }
        alphaAnimation.start()
    }

}