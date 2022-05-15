package com.gy.commonviewdemo.recyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.DemoData
import com.gy.commonviewdemo.MainAdapter
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.rv_main.*

class RvMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_main)

        val adapter = MainAdapter(listOf(
            DemoData("列表视频触发播放时机", VideoListPlayTimeActivity::class.java,this),
            DemoData("layoutManager 三个item切换效果", RvThreeDemo::class.java,this),
            DemoData("滑动到指定位置 solution-1", RvScrollActivityOne::class.java,this),
            DemoData("滑动到指定位置 solution-2", RvScrollActivityTwo::class.java,this),
            DemoData("滑动到指定位置 solution-3", RvScrollActivityThree::class.java,this),
            DemoData("弹幕效果", RvLaneActivity::class.java,this),
        ))

        rv_rv_main.layoutManager = LinearLayoutManager(this)
        rv_rv_main.adapter = adapter

    }

}