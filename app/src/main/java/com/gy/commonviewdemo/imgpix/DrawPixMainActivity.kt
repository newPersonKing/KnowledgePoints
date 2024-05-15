package com.gy.commonviewdemo.imgpix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.*
import com.gy.commonviewdemo.imgpix.led.DrawLedActivity
import com.gy.commonviewdemo.imgpix.pix2.DrawPix2Activity
import com.gy.commonviewdemo.imgpix.pix3.DrawPix3Activity
import com.gy.commonviewdemo.imgpix.pix4.DrawPix4Activity
import com.gy.commonviewdemo.imgpix.pix5.DrawPix5Activity
import com.gy.commonviewdemo.imgpix.pix6.Pix6Activity
import kotlinx.android.synthetic.main.activity_main.*


// postInvalidateOnAnimation
// ValueAnimator 更新回调  这两个方法 都是在下一帧刷新的时候 毁回调

class DrawPixMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         ReadJsonFileUtil.read(this)
        val adapter = MainAdapter(listOf(
            DemoData("图片转灰度图",DrawPixActivity::class.java,this),
            DemoData("图片转灰度图1", DrawPix2Activity::class.java,this),
            DemoData("图片转灰度图2", DrawPix3Activity::class.java,this),
            DemoData("图片转灰度图3", DrawPix4Activity::class.java,this),
            DemoData("图片转灰度图4", DrawPix5Activity::class.java,this),
            DemoData("图片转灰度图5", Pix6Activity::class.java,this),
            DemoData("draw Led", DrawLedActivity::class.java,this),
        ))

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapter

    }


}