package com.gy.commonviewdemo.imgpix.led

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_draw_led.*

class DrawLedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_led)

        val bitmapDrawable1 : BitmapDrawable = resources.getDrawable(R.mipmap.girl) as BitmapDrawable
        val bitmapDrawable2 : BitmapDrawable = resources.getDrawable(R.mipmap.avatar1) as BitmapDrawable;

        led_view.addDrawer { canvas, width, height, paint ->
//            val matrix = Matrix()
//            canvas.translate(width/2f,height/2f);
//            matrix.preTranslate(-width/2f,-height/4f);
//            val bitmap1 = bitmapDrawable1.getBitmap();
//            canvas.drawBitmap(bitmap1,matrix,paint);
//
//            matrix.postTranslate(width/2f,height/4f);
//            val bitmap2 = bitmapDrawable2.getBitmap();
//            canvas.drawBitmap(bitmap2,matrix,paint);
            paint.color = Color.CYAN
            paint.textSize = 120f;
            canvas.drawText("你 好，L E D", 100f, 200f, paint);
            canvas.drawText("85%", 100f, 350f, paint);

            canvas.drawCircle(width*3 / 4f, height / 4f, 100f, paint);

        }
    }

}