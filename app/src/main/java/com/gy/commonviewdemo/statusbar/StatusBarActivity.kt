package com.gy.commonviewdemo.statusbar

import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.gy.commonviewdemo.R
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_status_bar.*

class StatusBarActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_bar)


        btn_set_color.setOnClickListener {
            // 修改顶部状态栏的颜色
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.color_3cc864))
        }
        // 文字颜色是黑色
        setLightModel.setOnClickListener {
            StatusBarUtil.setLightMode(this)
        }

        //文字颜色是白色
        setDarkModel.setOnClickListener {
            StatusBarUtil.setDarkMode(this)
        }
        setTransparent.setOnClickListener {
            StatusBarUtil.setTransparent(this)
        }

        setTranslucent.setOnClickListener {
            StatusBarUtil.setTranslucent(this)
        }

        setTransparentForImageView.setOnClickListener {
            StatusBarUtil.setTransparentForImageView(this, setTransparentForImageView)
        }
    }


}