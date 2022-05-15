package com.gy.commonviewdemo.systemapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.DemoData
import com.gy.commonviewdemo.MainAdapter
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_system_api.*

class SystemApiActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_api)

        val adapter = MainAdapter(listOf(
            DemoData("hasOverlappingRendering 叠加绘制", HasOverlappingRenderingActivity::class.java,this),
            DemoData("TimerTask", TimerTaskActivity::class.java,this),
            DemoData("ValueAnimation", ValueAnimationActivity::class.java,this),
        ))

        rv_system_api.layoutManager = LinearLayoutManager(this)
        rv_system_api.adapter = adapter
    }

}