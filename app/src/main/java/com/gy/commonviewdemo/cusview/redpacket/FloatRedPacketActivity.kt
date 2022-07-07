package com.gy.commonviewdemo.cusview.redpacket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_float_red_packet.*

// 漂浮红包
class FloatRedPacketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_red_packet)
        float_packet.post {
            float_packet.createAnimation(10,10,float_packet.width,float_packet.height)
        }
    }
}