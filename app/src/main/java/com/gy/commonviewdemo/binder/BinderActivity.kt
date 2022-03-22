package com.gy.commonviewdemo.binder

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_binder.*


// mmap Memory Mapping
class BinderActivity : AppCompatActivity(){

    var proxy : IGradeInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder)

        btn_binder_service.setOnClickListener {
            val intent = Intent(this,BinderService::class.java)

            bindService(intent,object : ServiceConnection{
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    proxy =   BinderProxy.asInterface(service)
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    proxy = null
                }

            },BIND_AUTO_CREATE)
        }

        btn_get_data.setOnClickListener {
            proxy?.getStudentGrade(Student());
        }
    }

}