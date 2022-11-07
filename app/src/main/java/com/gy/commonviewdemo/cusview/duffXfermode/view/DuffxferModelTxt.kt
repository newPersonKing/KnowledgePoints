package com.gy.commonviewdemo.cusview.duffXfermode.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.gy.commonviewdemo.R

class DuffxferModelTxt @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0
    private val mainPaint = Paint()
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//
//        val rectBitmap = Bitmap.createBitmap(mWidth,mHeight,Bitmap.Config.ARGB_8888)
//        val rectCanvas = Canvas(rectBitmap)
//        rectCanvas.drawRect(0f,mHeight/2f,mWidth/1f,mHeight/1f,Paint().apply {
//            color = Color.BLUE
//        })


        //创建指定颜色的bitmap resolveColorBitmap
        val paintBitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.paint)
        val paintBitmapWidth = paintBitmap.width
        val paintBitmapHeight = paintBitmap.height
        val desHeight = ( mWidth * paintBitmapHeight/ (paintBitmapWidth * 1f)).toInt()
        val resolveColorBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        val resolveColorCanvas = Canvas(resolveColorBitmap)

        val whiteBitmap = Bitmap.createBitmap(mWidth,mHeight,Bitmap.Config.ARGB_8888)
        val whiteCanvas = Canvas(whiteBitmap)
        whiteCanvas.drawRect(0f,(mHeight - desHeight).toFloat(),mWidth/1f,mHeight*1f,Paint().apply {
            color = Color.WHITE
        })

        val layerId = resolveColorCanvas.saveLayer(
            0f, 0f, width.toFloat(),
            height.toFloat(), null)
        resolveColorCanvas.drawBitmap(whiteBitmap,0f,0f,mainPaint)
        //todo 这里的混合模式 只是混合 相交区域 不相交区域 不处理
        mainPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        val src = Rect(0,0,paintBitmap.width,paintBitmap.height)
        val dest = Rect(0,height-desHeight,mWidth,mHeight)
        resolveColorCanvas.drawBitmap(paintBitmap,src,dest,mainPaint)
        resolveColorCanvas.restoreToCount(layerId)
        canvas.drawBitmap(resolveColorBitmap,0f,0f,Paint())
        mainPaint.xfermode = null
        // 将指定颜色的bitmap 与文字混合
        //绘制的文字
        val txtBitmap = Bitmap.createBitmap(mWidth,mHeight,Bitmap.Config.ARGB_8888)
        val txtCanvas = Canvas(txtBitmap)
        txtCanvas.drawText("这是文字这是文字这是文字",0f,mHeight*2/3f,Paint().apply {
            color = Color.BLACK
            textSize =100f
        })
        canvas.drawBitmap(txtBitmap,100f,80f,mainPaint)
        val dest1 = Rect(0,height-desHeight-30,mWidth,mHeight-30)
        canvas.drawBitmap(paintBitmap,src,dest1,mainPaint)
        val finalCanvasLayerId = canvas.saveLayer(
            0f, 0f, width.toFloat(),
            height.toFloat(), null)
        canvas.drawBitmap(txtBitmap,100f,80f,mainPaint)
        mainPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        canvas.drawBitmap(resolveColorBitmap,0f,-30f,mainPaint)
        canvas.restoreToCount(finalCanvasLayerId)

    }
}