package com.gy.commonviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.binder.BinderActivity
import com.gy.commonviewdemo.clipboardManager.ClipBoardActivity
import com.gy.commonviewdemo.cusview.CusViewMainActivity
import com.gy.commonviewdemo.cusview.edittext.EdittextActivity
import com.gy.commonviewdemo.cusview.gradient.GradientActivity
import com.gy.commonviewdemo.cusview.text.SpanEnterActivity
import com.gy.commonviewdemo.cusview.text.TextViewActivity
import com.gy.commonviewdemo.cusview.text.rich_text.*
import com.gy.commonviewdemo.db.ContentProviderActivity
import com.gy.commonviewdemo.drag_and_drop.DragAndDropActivity
import com.gy.commonviewdemo.flow.FlowActivity
import com.gy.commonviewdemo.kotlin.KotlinActivity
import com.gy.commonviewdemo.notification.NotificationActivity
import com.gy.commonviewdemo.notification.NotificationMainActivity
import com.gy.commonviewdemo.picture_in_picture.PictureInPictureActivity
import com.gy.commonviewdemo.recyclerView.RvMainActivity
import com.gy.commonviewdemo.rxjava.rxjava2.RxJava2Activity
import com.gy.commonviewdemo.statusbar.StatusBarActivity
import com.gy.commonviewdemo.systemapi.SystemApiActivity
import com.gy.commonviewdemo.systemui.SystemUiActivity
import com.gy.commonviewdemo.video.VideoMainActivity
import com.gy.commonviewdemo.viewpager.ViewPagerActivity
import com.gy.commonviewdemo.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_main.*


// postInvalidateOnAnimation
// ValueAnimator 更新回调  这两个方法 都是在下一帧刷新的时候 毁回调

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainAdapter(listOf(
            DemoData("edittext相关",EdittextActivity::class.java,this),
            DemoData("webview相关",WebViewActivity::class.java,this),
            DemoData("viewpager",ViewPagerActivity::class.java,this),
            DemoData("btn_system_ui",SystemUiActivity::class.java,this),
            DemoData("rv相关",RvMainActivity::class.java,this),
            DemoData("自定义一view相关",CusViewMainActivity::class.java,this),
            DemoData("TextView相关",TextViewActivity::class.java,this),
            DemoData("flow相关",FlowActivity::class.java,this),
            DemoData("富文本相关", SpanEnterActivity::class.java,this),
            DemoData("通知",NotificationMainActivity::class.java,this),
            DemoData("contentProvider共享数据",ContentProviderActivity::class.java,this),
            DemoData("复制粘贴",ClipBoardActivity::class.java,this),
            DemoData("拖拽以及释放",DragAndDropActivity::class.java,this),
            DemoData("画中画",PictureInPictureActivity::class.java,this),
            DemoData("rxJava2操作符",RxJava2Activity::class.java,this),
            DemoData("状态栏颜色",StatusBarActivity::class.java,this),
            DemoData("system-api",SystemApiActivity::class.java,this),
            DemoData("kotlin相关知识点",KotlinActivity::class.java,this),
            DemoData("binder相关知识点",BinderActivity::class.java,this),
            DemoData("渐变效果",GradientActivity::class.java,this),
            DemoData("视频相关",VideoMainActivity::class.java,this),
        ))

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapter

    }

}