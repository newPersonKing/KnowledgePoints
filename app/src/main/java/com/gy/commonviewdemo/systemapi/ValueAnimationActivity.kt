package com.gy.commonviewdemo.systemapi

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_value_animation.*

class ValueAnimationActivity : AppCompatActivity(){
    var vibrator : Vibrator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value_animation)

        if(vibrator == null){
            vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        val  valueAnim = ValueAnimator.ofInt(0,100).apply {
            duration = 20000
            addUpdateListener {
                btn_anim_value.text = "${it.animatedValue as Int}"
            }
        }
        valueAnim.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
              Log.i("ValueAnimationActivity","onAnimationStart")
                vibrator?.vibrate(20000)
            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.i("ValueAnimationActivity","onAnimationEnd")
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.i("ValueAnimationActivity","onAnimationCancel")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                Log.i("ValueAnimationActivity","onAnimationRepeat")
            }

        })

        btn_anim_start.setOnClickListener {
            valueAnim.start()
        }

        btn_anim_cancel.setOnClickListener {
            valueAnim.cancel()
        }

        btn_anim_end.setOnClickListener {
            valueAnim.end()
        }

    }

}
