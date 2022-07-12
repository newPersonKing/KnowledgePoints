package com.gy.commonviewdemo.cusview.anim

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import android.widget.TextView

class AnimFramelayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    fun setText(text: CharSequence) {

        if(childCount != 2) return
        val bottomTv = getChildAt(0)
        val topTv = getChildAt(1)
        if(bottomTv is TextView) bottomTv.text = text
        if(topTv is TextView) topTv.text = text
        createAnimation()
    }

    private fun createAnimation(){
        clearAnimation()
        val scaleAnimation = ScaleAnimation(0.3f,1f,0.3f,1f,
            Animation.RELATIVE_TO_SELF,0f, Animation.RELATIVE_TO_SELF,1f)
        scaleAnimation.duration  = 1000
        startAnimation(scaleAnimation)
    }
}