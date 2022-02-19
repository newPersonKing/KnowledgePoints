package com.gy.commonviewdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.gy.commonviewdemo.clipboardManager.ClipBoardActivity
import com.gy.commonviewdemo.cusview.CusViewMainActivity
import com.gy.commonviewdemo.cusview.edittext.EdittextActivity
import com.gy.commonviewdemo.cusview.text.TextViewActivity
import com.gy.commonviewdemo.cusview.text.rich_text.*
import com.gy.commonviewdemo.db.ContentProviderActivity
import com.gy.commonviewdemo.drag_and_drop.DragAndDropActivity
import com.gy.commonviewdemo.flow.FlowActivity
import com.gy.commonviewdemo.notification.NotificationActivity
import com.gy.commonviewdemo.picture_in_picture.PictureInPictureActivity
import com.gy.commonviewdemo.recyclerView.RvMainActivity
import com.gy.commonviewdemo.rxjava.rxjava2.RxJava2Activity
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
        btn_textview.setOnClickListener(this)
        btn_flow.setOnClickListener(this)
        btn_rich_text.setOnClickListener(this)
        btn_image_span.setOnClickListener(this)
        btn_click_span.setOnClickListener(this)
        btn_replacement_span.setOnClickListener(this)
        btn_span_watcher.setOnClickListener(this)
        btn_notification.setOnClickListener(this)
        btn_clip_board.setOnClickListener(this)
        btn_content_provider.setOnClickListener(this)
        btn_drag_and_drop.setOnClickListener(this)
        btn_picture_in_picture.setOnClickListener(this)
        btn_rxjava_2.setOnClickListener(this)
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
            R.id.btn_textview ->{
                val intent = Intent(this,TextViewActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_flow -> {
                val intent = Intent(this,FlowActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_rich_text -> {
                val intent = Intent(this,RichTextActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_image_span -> {
                val intent = Intent(this,ImageSpanActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_click_span -> {
                val intent = Intent(this,ClickSpanActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_replacement_span -> {
                val intent = Intent(this,ReplacementSpanActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_span_watcher -> {
                val intent = Intent(this,SpanWatcherActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_notification -> {
                val intent = Intent(this,NotificationActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_content_provider -> {
                val intent = Intent(this,ContentProviderActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_clip_board -> {
                val intent = Intent(this,ClipBoardActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_drag_and_drop -> {
                val intent = Intent(this,DragAndDropActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_picture_in_picture -> {
                val intent = Intent(this,PictureInPictureActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_rxjava_2 -> {
                val intent = Intent(this,RxJava2Activity::class.java)
                startActivity(intent)
            }
        }
    }
}