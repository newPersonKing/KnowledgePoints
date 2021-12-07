package com.gy.commonviewdemo.systemui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_system_ui.*

class SystemUiActivity : AppCompatActivity(),View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_ui)

        btn_sys_bar.setOnClickListener(this)
        btn_sys_bar_gone.setOnClickListener(this)
        btn_show_hide_status.setOnClickListener(this)
        btn_show_hide_navigation.setOnClickListener(this)
        btn_show_hide_ime.setOnClickListener(this)
        btn_status_light.setOnClickListener(this)

        // 1. 使内容区域全屏
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 2. 设置 System bar 透明
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        // 3. 可能出现视觉冲突的 view 处理 insets
        ViewCompat.setOnApplyWindowInsetsListener(btn_status_light) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            Log.i("ccccccccc","insets====${insets.toString()}")
            WindowInsetsCompat.CONSUMED
        }


    }

    var isLight = false
    override fun onClick(v: View) {
       when(v.id){

           R.id.btn_sys_bar ->{
               val resourceId = resources.getIdentifier("status_bar_height","dimen","android")
               val barHeight1 = resources.getDimensionPixelSize(resourceId)

               // API 需要升级 androidx.core:core-ktx
               val insets = ViewCompat.getRootWindowInsets(window.decorView)?.getInsets(WindowInsetsCompat.Type.statusBars())
               val top = insets?.top ?:0
               val bottom = insets?.bottom ?:0

               tv_message.text = "barHeight1:$barHeight1;top:$top;bottom:$bottom"
           }

           R.id.btn_sys_bar_gone -> {
               // API 需要升级 androidx.core:core-ktx
               val insets = ViewCompat.getRootWindowInsets(window.decorView)?.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.statusBars())
               val top = insets?.top ?:0
               val bottom = insets?.bottom ?:0
               tv_message.text = "top:$top;bottom:$bottom"
           }

           R.id.btn_show_hide_status -> {
              val isVisible =  ViewCompat.getRootWindowInsets(window.decorView)?.isVisible(WindowInsetsCompat.Type.statusBars())?:false

               if(isVisible){
                   ViewCompat.getWindowInsetsController(btn_sys_bar)?.hide(WindowInsetsCompat.Type.statusBars())
               }else {
                   ViewCompat.getWindowInsetsController(btn_sys_bar)?.show(WindowInsetsCompat.Type.statusBars())
               }
               tv_message.text = "状态来是否可见:${!isVisible},"
           }

           R.id.btn_show_hide_navigation -> {

               // 1 如果系统已经设置成手势的 那么导航键是出不来的
               // 2 这个切换的点击太快 有时候不生效
               // 3 在部分手机上 如果开始导航 如果导航键看不见 点击屏幕导航键自己会出来
               // 4 这个isVisible 好像一直返回的都是true
               val isVisible =  ViewCompat.getRootWindowInsets(window.decorView)?.isVisible(WindowInsetsCompat.Type.navigationBars())?:false

               if(isVisible){
                   ViewCompat.getWindowInsetsController(btn_sys_bar)?.hide(WindowInsetsCompat.Type.navigationBars())
               }else {
                   ViewCompat.getWindowInsetsController(btn_sys_bar)?.show(WindowInsetsCompat.Type.navigationBars())
               }

               tv_message.text = "导航栏来是否可见:${ViewCompat.getRootWindowInsets(window.decorView)?.isVisible(WindowInsetsCompat.Type.navigationBars())?:false},"

           }

           R.id.btn_show_hide_ime -> {
               val isVisible =  ViewCompat.getRootWindowInsets(window.decorView)?.isVisible(WindowInsetsCompat.Type.ime())?:false

               tv_message.text = "虚拟键盘是否可见:${!isVisible}"
               // 打开虚拟键盘 是有的view 必须是能获取焦点的
               if(isVisible){
                   ViewCompat.getWindowInsetsController(et_test)?.hide(WindowInsetsCompat.Type.ime())
               }else {
                   ViewCompat.getWindowInsetsController(et_test)?.show(WindowInsetsCompat.Type.ime())
               }

           }

           R.id.btn_status_light -> {

               ViewCompat.getWindowInsetsController(btn_sys_bar)?.isAppearanceLightStatusBars = isLight
               // 这个属性没有生效 不知道为嘛
               ViewCompat.getWindowInsetsController(btn_sys_bar)?.isAppearanceLightNavigationBars = isLight

               isLight = !isLight

           }




       }
    }


}