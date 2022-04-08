package com.gy.commonviewdemo.binder

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.util.Log


class BinderService : Service() {

    val binder = object :  Binder(){

        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            if(code == 100){
                val student = data.readParcelable<Student>(Student::class.java.classLoader)
                val replyStudent = Student()
                replyStudent.code = 200
                replyStudent.name = "郭勇"
                reply?.writeParcelable(replyStudent,0)
                Log.i("cccccccccccc","远程服务调用你")
                return true
            }
            return super.onTransact(code, data, reply, flags)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        binder.run {

        }
        return binder;
    }


}