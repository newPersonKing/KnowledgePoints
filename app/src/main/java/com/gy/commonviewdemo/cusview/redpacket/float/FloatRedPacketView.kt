package com.gy.commonviewdemo.cusview.redpacket.float

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.widget.FrameLayout
import androidx.core.animation.addListener
import kotlinx.android.synthetic.main.red_rain.view.*
import java.lang.Exception
import kotlin.math.sqrt

class FloatRedPacketView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0
    private var baseDistance = 2400
    private var baseDuration = 5 * 1000
    private var durationAnimator : ValueAnimator? = null
    private var onlyChild : View? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if(childCount != 1) throw Exception("FloatRedPacketView only can has one child")
        val child = getChildAt(0)
        onlyChild = child
        val childWidth = child.measuredWidth
        val childHeight = child.measuredHeight

        child.layout(mWidth/2 - childWidth /2,mHeight/2 - childHeight/2,mWidth/2 +  childWidth /2,mHeight/2 + childHeight/2)
    }


    fun createAnimation(startX:Int,startY:Int,endX:Int,endY:Int){
        durationAnimator = ValueAnimator.ofFloat(0f,1f)
        val realDistance = ((endX - startX) * (endX - startX) + (endY - startY) * (endY - startY)).toDouble()
        durationAnimator?.duration = (sqrt(realDistance) / baseDistance * baseDuration).toLong()
        durationAnimator?.addUpdateListener {
            val value = it.animatedValue as Float
            val child = onlyChild?:return@addUpdateListener
            child.x = startX +(endX - startX) * value
            child.y = startY + (endY - startY) * value
            if((child.x <= 0
                        || child.x + child.width >= mWidth
                        || child.y <= 0
                        || child.y + child.height >= mHeight) && value > 0) {
                durationAnimator?.cancel()
                val currentX = child.x
                val currentY = child.y
                val vDirection = when {
                    currentY <=0 -> {
                        2
                    }
                    currentY + child.height >= mHeight -> {
                        1
                    }
                    currentY < startY -> {
                        1
                    }
                    else -> {
                        2
                    }
                }
                val hDirection = when {
                    currentX <= 0 -> {
                        2
                    }
                    currentX + child.width >= mWidth -> {
                        1
                    }
                    currentX < startX -> {
                        1
                    }
                    else -> {
                        2
                    }
                }

                checkNextEndPoint(child.x.toInt(),child.y.toInt(),vDirection,hDirection)
            }
        }
        durationAnimator?.start()
    }

    // vDirection 1 向上 2 向下 hDirection 1 left 2 right
    private fun checkNextEndPoint(startX:Int,startY: Int,vDirection:Int,hDirection:Int){
        val endX = if(hDirection == 1){
            0
        }else{
            mWidth
        }
        val diff = (400 - Math.random() * 100).toInt()
        val endY = if(vDirection == 1){
            startY - diff
        }else {
            startY + diff
        }
        createAnimation(startX,startY,endX,endY)
    }
}