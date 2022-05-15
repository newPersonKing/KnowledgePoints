package com.gy.commonviewdemo.recyclerView

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.recyclerView.adapter.LaneAdapter
import com.gy.commonviewdemo.recyclerView.layoutmanager.LaneLayoutManager
import kotlinx.android.synthetic.main.activity_rv_scroll_one.*
import java.util.*

class RvLaneActivity : AppCompatActivity() , View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_scroll_lane)
        btn_start_scroll.setOnClickListener(this)

        val layoutManager = LaneLayoutManager()
        rv.layoutManager = layoutManager
        rv.adapter = LaneAdapter()
        rv.setOnTouchListener { v, event -> true }

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    rv.smoothScrollBy(10,0)
                }
            }
        },0,30)

    }

    override fun onClick(v: View) {

    }

}

