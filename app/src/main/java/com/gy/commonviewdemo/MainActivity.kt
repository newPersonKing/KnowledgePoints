package com.gy.commonviewdemo

import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Presentation
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.accessibility.AccessibilityActivity
import com.gy.commonviewdemo.apt.spi.SpiActivity
import com.gy.commonviewdemo.behavior.BehaviorMainActivity
import com.gy.commonviewdemo.binder.BinderActivity
import com.gy.commonviewdemo.camera.CameraActivity
import com.gy.commonviewdemo.clipboardManager.ClipBoardActivity
import com.gy.commonviewdemo.constraint.ConstraintLayoutDemoActivity
import com.gy.commonviewdemo.cusview.CusViewMainActivity
import com.gy.commonviewdemo.cusview.text.SpanEnterActivity
import com.gy.commonviewdemo.db.ContentProviderActivity
import com.gy.commonviewdemo.drag_and_drop.DragAndDropActivity
import com.gy.commonviewdemo.drawable.DrawableActivity
import com.gy.commonviewdemo.flow.FlowActivity
import com.gy.commonviewdemo.globaltouch.GlobalTouchActivity
import com.gy.commonviewdemo.kotlin.KotlinActivity
import com.gy.commonviewdemo.notification.NotificationMainActivity
import com.gy.commonviewdemo.picture_in_picture.PictureInPictureActivity
import com.gy.commonviewdemo.recyclerView.RvMainActivity
import com.gy.commonviewdemo.rxjava.rxjava2.RxJava2Activity
import com.gy.commonviewdemo.sensor.SensorActivity
import com.gy.commonviewdemo.statusbar.StatusBarActivity
import com.gy.commonviewdemo.systemapi.SystemApiActivity
import com.gy.commonviewdemo.systemui.SystemUiActivity
import com.gy.commonviewdemo.viewpager.ViewPagerActivity
import com.gy.commonviewdemo.wallpaper.WallpagerActivity
import com.gy.commonviewdemo.webview.WebViewActivity
import com.gy.commonviewdemo.wifi.WifiActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


// postInvalidateOnAnimation
// ValueAnimator 更新回调  这两个方法 都是在下一帧刷新的时候 毁回调

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainAdapter(listOf(
            DemoData("webview相关",WebViewActivity::class.java,this),
            DemoData("viewpager",ViewPagerActivity::class.java,this),
            DemoData("btn_system_ui",SystemUiActivity::class.java,this),
            DemoData("rv相关",RvMainActivity::class.java,this),
            DemoData("自定义一view相关",CusViewMainActivity::class.java,this),
            DemoData("drawable",DrawableActivity::class.java,this),
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
            DemoData("SPI 机制了解",SpiActivity::class.java,this),
            DemoData("无障碍服务", AccessibilityActivity::class.java,this),
            DemoData("ConstraintLayout学习", ConstraintLayoutDemoActivity::class.java,this),
            DemoData("Camera相机", CameraActivity::class.java,this),
            DemoData("Behavior相关", BehaviorMainActivity::class.java,this),
            DemoData("Wifi连接", WifiActivity::class.java,this),
            DemoData("全局事件", GlobalTouchActivity::class.java,this),
            DemoData("壁纸相关", WallpagerActivity::class.java,this),
            DemoData("传感器Sensor", SensorActivity::class.java,this),
        ))

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapter

        var timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                Log.i("ccccccccccc","scheduleAtFixedRate")
            }
        }

        timer.scheduleAtFixedRate(timerTask,0,1000)
        rv_main.postDelayed({
            timerTask.cancel()
            timer.purge()
        },3000)


        rv_main.postDelayed(Runnable {
            Log.i("ccccccccccc","postDelayed")
            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//            moveToFront(this)
//            start(this)
//            openQQGroup("ziOrsLP0XJLo1-99qpPsVN_e44I1y9M")
        },3000)
        printDeviceInfo()

//        val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
//        startActivity(intent)


    }

    //看一下设备信息
    private fun printDeviceInfo() {
        val deviceInfo = arrayOf(
            "产品：${android.os.Build.PRODUCT}",
            "制造商：${android.os.Build.MANUFACTURER}",
            "型号：${android.os.Build.MODEL}",
            "品牌：${android.os.Build.BRAND}",
            "主板：${android.os.Build.BOARD}",
            "设备标识：${android.os.Build.FINGERPRINT}",
            "版本号：${android.os.Build.ID}",
            "用户：${android.os.Build.USER}",
            "CPU_ABI：${android.os.Build.CPU_ABI}",
            "CPU_ABI2：${android.os.Build.CPU_ABI2}",
            "标签：${android.os.Build.TAGS}",
            "Android SDK版本：${android.os.Build.VERSION.SDK}",
            "Android SDK版本号：${android.os.Build.VERSION.SDK_INT}",
            "Android 版本（RELEASE）：${android.os.Build.VERSION.RELEASE}",
            "驱动（DEVICE）：${android.os.Build.DEVICE}",
            "DISPLAY：${android.os.Build.DISPLAY}",
            "HARDWARE：${android.os.Build.HARDWARE}",
            "主机（HOST）：${android.os.Build.HOST}",
            "版本类型（TYPE）：${android.os.Build.TYPE}",
            "TIME：${android.os.Build.TIME}",
            "RADIO：${android.os.Build.RADIO}",
            "序列号（SERIAL）：${android.os.Build.SERIAL}",
            "UNKNOWN：${android.os.Build.UNKNOWN}"
        )
        deviceInfo.forEach {
            LogUtil.e("cccccc===${it}")
        }
        LogUtil.e("cccccc===printDeviceInfo==${Utils.isHarmonyOs()}")
        LogUtil.e("cccccc===printDeviceInfo==${Utils.getHarmonyVersion()}")
    }



    // 1 对于android 10 7 startActivity 可以直接启动
    // 2 android 8.0 华为 startActivity
    // 3 小米 12 是可以直接启动 alarmManager
    // 4 google 12 可以直接启动
    // 5 N3 v7.1.2 startActivity
    // 6 Redmi Note 5 9 startActivity
    // 7 鸿蒙不可以
    // 8 Redmi 10X moveToFront alarmManager 都要配合 createVirtualDisplay
    fun start(context: Context) {
        //android 10 以上版本,且延时等于0时，手动增加延时
        var delay = 0
//        TaskUtil.moveAppTaskToFront(context)
        val intent = Intent(context, BlackTechActivity::class.java)
//        val bundle = Bundle()
//        bundle.putString(ExKeepConstant.SCENES, params.scenesName)
//        bundle.putByteArray(ExKeepConstant.ADS, ParcelableUtils.toByteArray(params.ads))
//        intent.putExtras(bundle)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        if (Build.VERSION.SDK_INT >= 29) {
            Log.i("ccccccccccc","alarmManager")
            if (delay == 0) {
                delay = 800
            }
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_IMMUTABLE,
            )
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 800,
                pendingIntent
            )
        } else {
            Log.i("ccccccccccc","startActivity")
            context.applicationContext.startActivity(intent)
        }

    }

    private fun openQQGroup(key:String) {
        try {
            val intent =  Intent(Intent.ACTION_VIEW);
            intent.data = Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D" + key);
            startActivity(intent);
        } catch (e:Exception){
             Log.i("ccccccccccccccc",e.toString())
        }
    }

//    val fontScale = 1f
//    override fun getResources(): Resources {
//        val res = super.getResources()
//        val configuration = res.configuration
//        if (configuration.fontScale != fontScale) { //fontScale要缩放的比例
//            configuration.fontScale = fontScale
//            res.updateConfiguration(configuration, res.displayMetrics)
//        }
//        return res
//    }

    override fun onPause() {
        super.onPause()
        val virtualDisplay =
            (getSystemService(DISPLAY_SERVICE) as DisplayManager).createVirtualDisplay(
                "get",
                1,
                1,
                160,
                null,
                0
            )
        val presentation = Presentation(this, virtualDisplay.display)
        presentation.show()
    }

    fun moveToFront(context: Context) {
        Log.i("ccccccccccc","moveToFront")
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        activityManager?.getRunningTasks(100)?.forEach { taskInfo ->
            if (taskInfo.topActivity?.packageName == context.packageName) {
                Log.i("ccccccccccc","Try to move to front")
                activityManager.moveTaskToFront(taskInfo.id, 0)
                return
            }
        }
    }

}