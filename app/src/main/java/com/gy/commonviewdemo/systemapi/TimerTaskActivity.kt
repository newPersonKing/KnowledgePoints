package com.gy.commonviewdemo.systemapi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_timer_task.*
import java.util.*

class TimerTaskActivity : AppCompatActivity(){

    private val timer = Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_task)

        btn_delay_task.setOnClickListener {
            timer.schedule(MyTimerTask(),3000)
        }

        btn_looper_task.setOnClickListener {
            timer.scheduleAtFixedRate(MyTimerTask(),0,1000)
        }

        btn_finish_delay.setOnClickListener {
            // 只是清理掉 状态是 cancel 的任务
            timer.purge()
        }

        btn_finish_loop.setOnClickListener {
            timer.purge()
        }

    }

}

class  MyTimerTask : TimerTask() {
    override fun run() {
        Log.i("MyTimerTask","MyTimerTask：任务在执行")
    }
}