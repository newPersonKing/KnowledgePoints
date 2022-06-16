package com.gy.commonviewdemo.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R

class AccessibilityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessibility_service)
//        startService(Intent(this,MyAccessibilityService::class.java))
    }



}