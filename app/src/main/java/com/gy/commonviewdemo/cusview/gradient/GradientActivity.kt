package com.gy.commonviewdemo.cusview.gradient

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_gradient.*

// 记录一些渐变效果的UI
class GradientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient)

        edgesView.setColors(Color.RED,Color.YELLOW)
        slantView.setColor(Color.YELLOW)
    }

}