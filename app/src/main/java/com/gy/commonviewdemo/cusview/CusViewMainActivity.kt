package com.gy.commonviewdemo.cusview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.DemoData
import com.gy.commonviewdemo.MainAdapter
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.edittext.EdittextActivity
import com.gy.commonviewdemo.cusview.gradient.GradientActivity
import com.gy.commonviewdemo.cusview.redpacket.FloatRedPacketActivity
import com.gy.commonviewdemo.cusview.redpacket.RedPacketActivity
import com.gy.commonviewdemo.cusview.segment.Segment
import com.gy.commonviewdemo.cusview.text.TextViewActivity
import com.gy.commonviewdemo.cusview.threeD.CusView3DActivity
import kotlinx.android.synthetic.main.cus_view_main.*

class CusViewMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cus_view_main)

        segment_view.setSegments(mutableListOf<Segment>().apply {
            add(Segment().apply {
                color = Color.parseColor("#6DB9FF")
                title = "偏瘦"
                endText = "18.5"
            })
            add(Segment().apply {
                color = Color.parseColor("#59EDD2")
                title = "理想"
                endText = "24.0"
            })
            add(Segment().apply {
                color = Color.parseColor("#FAA571")
                title = "偏胖"
                endText = "28.0"
            })
            add(Segment().apply {
                color = Color.parseColor("#FA797C")
                title = "肥胖"
            })
        })

        segment_view.setPercent(0.8f)
        val adapter = MainAdapter(listOf(
            DemoData("edittext相关",EdittextActivity::class.java,this),
            DemoData("3D切换", CusView3DActivity::class.java,this),
            DemoData("TextView相关", TextViewActivity::class.java,this),
            DemoData("渐变效果", GradientActivity::class.java,this),
            DemoData("红包雨", RedPacketActivity::class.java,this),
            DemoData("随机漂浮红包", FloatRedPacketActivity::class.java,this),
        ))

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapter

    }
}