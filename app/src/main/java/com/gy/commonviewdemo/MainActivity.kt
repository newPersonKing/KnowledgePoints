package com.gy.commonviewdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.gy.commonviewdemo.cusview.CusViewMainActivity
import com.gy.commonviewdemo.edittext.EdittextActivity
import com.gy.commonviewdemo.recyclerView.RvMainActivity
import com.gy.commonviewdemo.systemui.SystemUiActivity
import com.gy.commonviewdemo.viewpager.ViewPagerActivity
import com.gy.commonviewdemo.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_edit.setOnClickListener(this)
        btn_webview.setOnClickListener(this)
        btn_time_out.setOnClickListener(this)
        btn_viewpager.setOnClickListener(this)
        btn_system_ui.setOnClickListener(this)
        btn_rv.setOnClickListener(this)
        btn_cus_view.setOnClickListener(this)

        lifecycle.addObserver(object : DefaultLifecycleObserver{
            override fun onCreate(owner: LifecycleOwner) {
                Log.i("cccccccccc","onCreate1")
            }

            override fun onResume(owner: LifecycleOwner) {
                Log.i("ccccccc","onResume1")
            }
        })

        Log.i("cccccccccc","onCreate")
    }

    // 这里执行
    override fun onResume() {
        super.onResume()
        Log.i("ccccccc","onResume")
        ViewCompat.setOnApplyWindowInsetsListener(btn_edit){
                v,insets->
            Log.i("ccccccc","setOnApplyWindowInsetsListener")
            insets
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_edit -> {
                val intent = Intent(this,EdittextActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_webview -> {
                val intent = Intent(this,WebViewActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_time_out ->{
                Log.i("cccccccccccc","1231231232")
            }
            R.id.btn_viewpager -> {
                val intent = Intent(this,ViewPagerActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_system_ui ->{
                val intent = Intent(this,SystemUiActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_rv -> {
                val intent = Intent(this,RvMainActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_cus_view -> {
                val intent = Intent(this,CusViewMainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}