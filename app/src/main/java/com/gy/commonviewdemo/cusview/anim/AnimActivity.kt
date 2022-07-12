package com.gy.commonviewdemo.cusview.anim

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_anim.*

class AnimActivity : AppCompatActivity() {

    val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        animation_text.text = "x100"
        anim_frame.setText("x100")
        animText()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim)
        handler.postDelayed(runnable,1000)
    }

    private fun animText(){
        handler.postDelayed(runnable,1000)
    }
}