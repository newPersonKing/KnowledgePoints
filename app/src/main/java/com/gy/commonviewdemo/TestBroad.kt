package com.gy.commonviewdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class TestBroad : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val intentAction = intent?.action ?:""
        if(intentAction == "floatStateChange"){
            val userId  = intent?.getStringExtra("userId") ?:""
            val offerId  = intent?.getStringExtra("offerId") ?:""
            val action  = intent?.getStringExtra("action") ?:""
        }

        Log.i("ccccccc","onReceive===${intentAction}")

    }
}