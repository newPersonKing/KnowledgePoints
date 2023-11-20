package com.gy.commonviewdemo.imgpix.pix5

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log

class BUtil {

    companion object {
        fun dealBackground(bm: Bitmap): Bitmap {
            for (i in 0 until bm.width) {
                for (j in 0 until bm.height) {
                    val color = bm.getPixel(i, j)
//                获取像素点的RGB颜色值
                    val R: Int = Color.red(color)
                    val G: Int = Color.green(color)
                    val B: Int = Color.blue(color)
//                将接近白色的背景替换成为白色的背景，用于之后处理透明
//                将不是白色的背景替换为红色也就是指纹的纹路
                    if (R > 240 && G > 240 && B > 240) {
                        val newColor: Int = Color.argb(0, 255, 255, 255)
                        bm.setPixel(i, j, newColor)
                    } else {
                        val newColor: Int = Color.argb(10, 0, 0, 0)
                        bm.setPixel(i, j, newColor)
                    }
                }
            }
            Log.i("cccccccc","finish")
            return bm
        }
    }
}