package com.gy.commonviewdemo.notification

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_open.setOnClickListener {
            openNotify()
        }
    }

    private fun openNotify(){
        val helper =  NotificationHelper(this)
        val builder = helper.getNotification("测试","这是内容")
        //点击通知打开软件，使用PendingIntent.getActivity()
        val resultIntent =  Intent(this, PendingActivity::class.java)
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // android 12 之后 需要显示的声明PendingIntent.FLAG_IMMUTABLE 或者PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(application, 0, resultIntent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        helper.notify(1,builder)
    }

}