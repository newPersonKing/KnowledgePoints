package com.gy.commonviewdemo.cusview.triangle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.gy.commonviewdemo.R

class TriangleOnRectView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private var triangleHeight = 10f
    private var mWidth = 0
    private var mHeight = 0
    private var direction = 0 // 0 left 1 top 2 right 3 bottom
    private var rectF:RectF? = null
    private var cornerRadius = 0f
    private var percent = 0.5f
    private var bgColor = Color.TRANSPARENT
    private val paint = Paint().apply {
        style = Paint.Style.FILL
    }

    private val trianglePath = Path()

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.TriangleOnRectView)
        percent = typeArray.getFloat(R.styleable.TriangleOnRectView_triangle_trans_percent,0f)
        bgColor = typeArray.getColor(R.styleable.TriangleOnRectView_triangle_bg_color,Color.TRANSPARENT)
        cornerRadius = typeArray.getDimension(R.styleable.TriangleOnRectView_triangle_corner_radius,0f)
        direction = typeArray.getInt(R.styleable.TriangleOnRectView_triangle_position,0)
        triangleHeight = typeArray.getDimension(R.styleable.TriangleOnRectView_triangle_height,0f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        rectF = when(direction){
            0 -> RectF(triangleHeight,0f,mWidth * 1f,mHeight *1f)
            1 -> RectF(0f,triangleHeight ,mWidth * 1f,mHeight *1f)
            2 -> RectF(0f,0f,(mWidth - triangleHeight) * 1f,mHeight *1f)
            3 -> RectF(0f,0f,mWidth * 1f,(mHeight - triangleHeight))
            else -> RectF()
        }

        when(direction){
            0 ->  {
                resolve(path = trianglePath,0f,mHeight * percent,triangleHeight,mHeight * percent - triangleHeight/2,triangleHeight,mHeight * percent + triangleHeight/2)
            }
            1 -> {
                resolve(path = trianglePath,
                    mWidth * percent,0f,
                    mWidth * percent - triangleHeight/2,triangleHeight,
                    mWidth * percent + triangleHeight/2,triangleHeight)
            }
            2 -> {
                resolve(path = trianglePath,
                    mWidth.toFloat() ,mHeight * percent,
                    mWidth.toFloat() - triangleHeight,mHeight * percent - triangleHeight/2,
                    mWidth.toFloat() - triangleHeight,mHeight * percent + triangleHeight/2)
            }
            3 -> {
                resolve(path = trianglePath,
                    mWidth * percent ,mHeight.toFloat(),
                    mWidth* percent - triangleHeight/2,mHeight.toFloat() - triangleHeight,
                    mWidth* percent + triangleHeight/2,mHeight.toFloat() - triangleHeight)
            }
            else -> RectF()
        }


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val rect = rectF?:return
        paint.color = bgColor

        canvas?.drawRoundRect(rect, cornerRadius, cornerRadius,paint)
        canvas?.drawPath(trianglePath,paint)
    }

    private fun resolve(path: Path,centerX:Float,centerY:Float,startX:Float,startY:Float,endX:Float,endY:Float){

        val centerX1 = (centerX - startX) * 0.5f + startX
        val centerY1 = (centerY - startY) * 0.5f + startY

        val centerX2 = (endX - centerX) * 0.5f + endX
        val centerY2 = (endY - centerY) * 0.5f + endY

        path.moveTo(startX,startY)
        path.lineTo(centerX1,centerY1)
        path.quadTo(centerX,centerY,centerX2,centerY2)
        path.lineTo(endX,endY)
        path.lineTo(startX,startY)

    }
}