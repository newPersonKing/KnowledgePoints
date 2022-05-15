package com.gy.commonviewdemo.cusview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.DemoData
import com.gy.commonviewdemo.MainAdapter
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.edittext.EdittextActivity
import com.gy.commonviewdemo.cusview.gradient.GradientActivity
import com.gy.commonviewdemo.cusview.jigsaw.JigsawActivity
import com.gy.commonviewdemo.cusview.text.TextViewActivity
import com.gy.commonviewdemo.cusview.threeD.CusView3DActivity
import kotlinx.android.synthetic.main.cus_view_main.rv_main

class CusViewMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cus_view_main)


        val adapter = MainAdapter(listOf(
            DemoData("edittext相关",EdittextActivity::class.java,this),
            DemoData("3D切换", CusView3DActivity::class.java,this),
            DemoData("TextView相关", TextViewActivity::class.java,this),
            DemoData("渐变效果", GradientActivity::class.java,this),
            DemoData("操作多个拼图处理的自定义View", JigsawActivity::class.java,this),
        ))

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapter

    }
}