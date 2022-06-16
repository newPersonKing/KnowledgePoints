package com.gy.commonviewdemo.accessibility

import android.accessibilityservice.AccessibilityService
import android.graphics.PixelFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.*
import android.view.accessibility.AccessibilityEvent
import com.gy.commonviewdemo.R

class MyAccessibilityService: AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.i("ccccccccc","MyAccessibilityService")
        initView()
    }

    private fun initView() {
        // 在屏幕顶部添加一个 View
        val wm =  getSystemService(WINDOW_SERVICE) as? WindowManager
        val lp = WindowManager.LayoutParams().apply {
            type = TYPE_ACCESSIBILITY_OVERLAY // 因为此权限才能展示处理
//            layoutInDisplayCutoutMode = LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            format = PixelFormat.TRANSLUCENT
            flags = flags or
                    FLAG_LAYOUT_NO_LIMITS or
                    FLAG_NOT_TOUCHABLE or  // 透传触摸事件
                    FLAG_NOT_FOCUSABLE or  // 透传输入事件
                    FLAG_LAYOUT_IN_SCREEN
            width = MATCH_PARENT
            height = MATCH_PARENT
        }
        // 通过 LayoutInflater 创建 View
        val rootView = LayoutInflater.from(this).inflate(R.layout.activity_spi, null)
        wm?.addView(rootView, lp)
    }
}

