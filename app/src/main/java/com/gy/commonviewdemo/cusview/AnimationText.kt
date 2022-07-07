package com.gy.commonviewdemo.cusview

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.appcompat.widget.AppCompatTextView

class AnimationText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        pivotX = 0f
        pivotY = 1f
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        // TODO startAnimation
        createAnimation()
    }

    private fun createAnimation(){
        clearAnimation()
        val scaleAnimation = ScaleAnimation(0.3f,1f,0.3f,1f,
            Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,1f)
        scaleAnimation.duration  = 200
        startAnimation(scaleAnimation)
    }
}