package com.gy.commonviewdemo.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.DemoData
import com.gy.commonviewdemo.MainAdapter
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.notification.alerter.AlerterActivity
import kotlinx.android.synthetic.main.activity_notification_main.*

class NotificationMainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_main)

        val adapter = MainAdapter(listOf(
            DemoData("通知", NotificationActivity::class.java,this),
            DemoData("Alerter", AlerterActivity::class.java,this),
        ))

        rv_notification_main.layoutManager = LinearLayoutManager(this)
        rv_notification_main.adapter = adapter
    }

}