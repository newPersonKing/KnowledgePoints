package com.gy.commonviewdemo.cusview.text.scroll_textview

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.text.getRect
import kotlinx.android.synthetic.main.activity_scroll_textview.*
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class ScrollTextViewActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_textview)

        //1 getPrimaryHorizontal 与  getSecondaryHorizontal 获取的值是一样的
        //2 getLineForOffset 获取下标对应的文本属于第几行
        get_left_position.setOnClickListener {
            val layout = test_tv.layout
            val left1 =  layout.getPrimaryHorizontal(0)
            Log.i("cccccccccc","left===$left1")
            val left2 =  layout.getPrimaryHorizontal(50)
            Log.i("cccccccccc","left===$left2")
        }

        get_right_position.setOnClickListener {
            val layout = test_tv.layout
            val right = layout.getSecondaryHorizontal(0)
            Log.i("cccccccccc","right===$right")
        }

        get_lines.setOnClickListener {
            val layout = test_tv.layout
            val line1 = layout.getLineForOffset(5)
            Log.i("cccccccccc","line1===$line1")
            val line2 = layout.getLineForOffset(40)
            Log.i("cccccccccc","line2===$line2")
        }

        // 1 对于 一个固定的textview getLineBounds 返回的值是固定的，不受 scrollBy 影响
        get_lines_bounds.setOnClickListener {
            val layout = test_tv.layout
            val rect1 = Rect()
            layout.getLineBounds(1,rect1);
            Log.i("cccccccccc","rect1===$rect1")
            val rect2 = Rect()
            layout.getLineBounds(2,rect2);
            Log.i("cccccccccc","rect2===$rect2")
            val rect3 = Rect()
            layout.getLineBounds(3,rect3);
            Log.i("cccccccccc","rect2===$rect3")
        }


        var startIndex = 0
        get_bounds.setOnClickListener {
            val rect = test_tv.getRect(0,50)
            Log.i("ccccccccccc","rect==$rect")
        }

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                get_bounds.post {
                    startIndex ++;
                    val rect = eng_text.textView.getRect(startIndex,startIndex+1)
                    eng_text.makeItVisible2(rect)
                    Log.i("ccccccccccc","rect==$rect")
                }
            }
        },300,300)

    }


}