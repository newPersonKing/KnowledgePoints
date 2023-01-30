package com.gy.commonviewdemo.globaltouch

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_global_touch.*

class GlobalTouchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_global_touch)

        btn_common.setOnClickListener {
            GlobalTouchDialog().show(supportFragmentManager,"12312")
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        GlobalTouchUtils.onTouch()
        return super.dispatchTouchEvent(ev)
    }
}