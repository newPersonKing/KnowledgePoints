package com.gy.commonviewdemo.sensor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.DemoData
import com.gy.commonviewdemo.MainAdapter
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.viewpager.ViewPagerActivity
import com.gy.commonviewdemo.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class SensorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainAdapter(listOf(
            DemoData("SersonProximity 脸部距离屏幕距离", SensorTypeProximity::class.java,this),
        ))

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapter
    }
}