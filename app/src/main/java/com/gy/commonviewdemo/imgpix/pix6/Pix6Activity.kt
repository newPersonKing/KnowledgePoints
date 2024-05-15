package com.gy.commonviewdemo.imgpix.pix6

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_img_px_6.*

class Pix6Activity : AppCompatActivity() {

    lateinit var  finalBitmap : Bitmap
    private lateinit var finalBitmap1 : Bitmap
    lateinit var finalBitmap2 : Bitmap
    lateinit var finalBitmap3 : Bitmap
    lateinit var finalBitmap4 : Bitmap
    lateinit var finalBitmap5 : Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_px_6)

        val srcBitmap = BitmapFactory.decodeResource(resources,R.mipmap.test)

        Thread {
            finalBitmap = BitmapUtil.bitmap2OTSUBitmap(srcBitmap)
            finalBitmap1 = BitmapUtil.bitmap2LightBitmap(srcBitmap)
            finalBitmap2 = BitmapUtil.bitmap2GrayBitmap(srcBitmap)
            finalBitmap3 = BitmapUtil.grayBitmap2BinaryBitmap(srcBitmap, false)
            finalBitmap4 = BitmapUtil.grayAverageBitmap2BinaryBitmap(srcBitmap)
            iv_target.post {
                iv_target.setImageBitmap(finalBitmap)
                iv_target_1.setImageBitmap(finalBitmap1)

                iv_target_2.setImageBitmap(finalBitmap2)
                iv_target_3.setImageBitmap(finalBitmap3)
                image_source.setImageBitmap(finalBitmap4)
            }
        }.start()
    }



}