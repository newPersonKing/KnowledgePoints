package com.gy.commonviewdemo.wallpaper.drawimage

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import com.gy.commonviewdemo.R
import java.util.*

/**
 * @author: drawf
 * @date: 2019-08-12
 * @see: <a href=""></a>
 * @description: 文字时钟动态壁纸服务
 */
class TestDrawImageWallpaperService : WallpaperService() {

    override fun onCreateEngine(): Engine {
        return MyEngine()
    }

    inner class MyEngine : Engine() {
        val valueAnimator = ValueAnimator.ofFloat(0f, 90f).apply {
            repeatCount = INFINITE
            duration = 2000
        }
        private var d3Image = TestRoll3DView(this@TestDrawImageWallpaperService)
            .apply {
                setRollMode(TestRoll3DView.RollMode.Whole3D)
                addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
                addImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
                setRollDuration(2000)
                setPartNumber(10)
            }
        private val mHandler = Handler(Looper.getMainLooper())
        private var mTimer: Timer? = null

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            Log.d("clock", "onCreate")
        }

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            Log.d("clock", "onSurfaceCreated")
        }

        override fun onSurfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            Log.d("clock", "onSurfaceChanged")
        }

        /**
         * Called to inform you of the wallpaper becoming visible or
         * hidden.  <em>It is very important that a wallpaper only use
         * CPU while it is visible.</em>.
         *
         * 当壁纸显示或隐藏是会回调该方法。
         * 很重要的一点是，要只在壁纸显示的时候做绘制操作（占用CPU）。
         */
        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            Log.d("clock", "onVisibilityChanged >>> $visible")
            if (visible) {
                startClock()
            } else {
                stopClock()
            }
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            Log.d("clock", "onSurfaceDestroyed")
            stopClock()
        }

        override fun onDestroy() {
            super.onDestroy()
            Log.d("clock", "onDestroy")
        }

        /**
         * 开始绘制
         */
        private fun startClock() {
            if (mTimer != null) return
            valueAnimator.start()
            valueAnimator.removeAllUpdateListeners()
            valueAnimator.addUpdateListener {
                surfaceHolder.lockCanvas()?.let { canvas ->
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
//                            mClockView.initWidthHeight(canvas.width.toFloat(), canvas.height.toFloat())
//                            mClockView.draw(canvas)
                    val value = valueAnimator.animatedValue as Float
                    d3Image.initSize(canvas.width,canvas.height)
                    d3Image.setRotateDegree(value)
                    d3Image.draw(canvas)
//                            val bitmapWidth = bitmap.width
//                            val bitmapHeight = bitmap.height
//                            val canvasWidth = canvas.width
//                            val canvasHeight = canvas.height

//                            canvas.drawBitmap(bitmap,0f,0f,Paint().apply {
//                                alpha = 100
//                            })
                    surfaceHolder.unlockCanvasAndPost(canvas)
//                            Log.d("clock", "doInvalidate >>> 触发绘制")
                }
            }
//            var bitmap = BitmapFactory.decodeResource(resources,R.mipmap.girl)
//            mHandler.post {
//                if (mTimer != null && surfaceHolder != null) {
//                    surfaceHolder.lockCanvas()?.let { canvas ->
//                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
////                            mClockView.initWidthHeight(canvas.width.toFloat(), canvas.height.toFloat())
////                            mClockView.draw(canvas)
//                        d3Image.initSize(canvas.width,canvas.height)
//                        d3Image.draw(canvas)
////                            val bitmapWidth = bitmap.width
////                            val bitmapHeight = bitmap.height
////                            val canvasWidth = canvas.width
////                            val canvasHeight = canvas.height
//
////                            canvas.drawBitmap(bitmap,0f,0f,Paint().apply {
////                                alpha = 100
////                            })
//                        surfaceHolder.unlockCanvasAndPost(canvas)
////                            Log.d("clock", "doInvalidate >>> 触发绘制")
//                    }
//                }
//            }
//            mTimer = timer(period = 1000) {
//                mHandler.post {
//                    d3Image.toNext()
//                }
//
//            }
        }

        /**
         * 停止绘制
         */
        private fun stopClock() {
            mTimer?.cancel()
            mTimer = null
        }
    }

}