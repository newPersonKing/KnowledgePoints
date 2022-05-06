package com.gy.commonviewdemo.video

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.DemoData
import com.gy.commonviewdemo.MainAdapter
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.edittext.EdittextActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_video_main.*

class VideoMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_main)

        val adapter = MainAdapter(listOf(
            DemoData("列表视频触发播放时机", VideoListPlayTimeActivity::class.java,this),
        ))

        rv_video_main.layoutManager = LinearLayoutManager(this)
        rv_video_main.adapter = adapter

    }
}