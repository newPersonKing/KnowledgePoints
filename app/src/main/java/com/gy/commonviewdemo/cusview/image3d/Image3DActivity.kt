package com.gy.commonviewdemo.cusview.image3d

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_image_3d.*

class Image3DActivity : AppCompatActivity() {

    private var mHandler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_3d)
        iv_2d.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        iv_2d.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.luck))
        iv_2d.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        iv_2d.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.luck))
        iv_2d.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        mHandler.postDelayed({
            iv_2d.setRollDirection(0)
            iv_2d.toNext()
        },2000)

        iv_3d.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        iv_3d.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.luck))
        iv_3d.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        iv_3d.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.luck))
        iv_3d.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        mHandler.postDelayed({
            iv_3d.setRollDirection(0)
            iv_3d.toNext()
        },2000)

        iv_SepartConbine.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        iv_SepartConbine.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.luck))
        iv_SepartConbine.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        iv_SepartConbine.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.luck))
        iv_SepartConbine.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        mHandler.postDelayed({
            iv_SepartConbine.toNext()
        },2000)

        iv_RollInTurn.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        iv_RollInTurn.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.luck))
        iv_RollInTurn.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        iv_RollInTurn.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.luck))
        iv_RollInTurn.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        mHandler.postDelayed({
            iv_RollInTurn.toNext()
        },2000)

        iv_Jalousie.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        iv_Jalousie.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.luck))
        iv_Jalousie.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        iv_Jalousie.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.luck))
        iv_Jalousie.addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
        iv_Jalousie.setPartNumber(10)
        iv_Jalousie.setRollDirection(0)
        mHandler.postDelayed({
            iv_Jalousie.toNext()
        },2000)
    }
}