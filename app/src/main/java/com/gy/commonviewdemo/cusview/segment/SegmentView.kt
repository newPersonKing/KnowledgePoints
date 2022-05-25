package com.gy.commonviewdemo.cusview.segment

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.ext.drawCenterText

class SegmentView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0
    private var segmentCount = 0
    private var space = 0f
    private var eachSegmentWidth = 0f
    private var eachSegmentHeight = 0f
    private var segmentContainerHeight = 0f
    private var topTextHeight = 0f
    private var bottomTextHeight = 0f
    private var rate = 1/3f // 文本跟图案的高度比
    private val INVALID_COLOR = -1
    private val textPaint = Paint() // 绘制文本
    private var percent = 0.5f // 圆圈绘制位置
    private var circleRadius = 0f // 绘制圆圈位置
    private var circleStrokeWidth = 0f

    private val path = Path()
    private val segmentPaint = Paint().apply {
        style = Paint.Style.FILL
    }

    private val bottomTextPaint = Paint()

    private val circlePaint = Paint().apply {
        style = Paint.Style.STROKE
    }

    private val segments  = mutableListOf<Segment>()

    init {
        val typeArray = context.obtainStyledAttributes(attrs,R.styleable.SegmentView)
        var textColor = typeArray.getColor(R.styleable.SegmentView_segment_text_color,INVALID_COLOR)
        if(textColor == INVALID_COLOR) textColor = Color.GREEN
        textPaint.color = textColor

        val topTextSize = typeArray.getDimension(R.styleable.SegmentView_segment_text_size,30f)
        textPaint.textSize = topTextSize

        segmentCount = typeArray.getInt(R.styleable.SegmentView_segment_item_count,3)

        circleStrokeWidth = typeArray.getDimension(R.styleable.SegmentView_segment_circle_stroke_width,0f)
        circleRadius = typeArray.getDimension(R.styleable.SegmentView_sengment_circle_radius,0f)
        val circleColor = typeArray.getColor(R.styleable.SegmentView_segment_circle_stroke_color,Color.WHITE)
        circlePaint.apply {
            strokeWidth = circleStrokeWidth
            color = circleColor
        }

        val bottomTextSize = typeArray.getDimension(R.styleable.SegmentView_segment_bottom_text_size,0f)
        val bottomTextColor = typeArray.getColor(R.styleable.SegmentView_segment_bottom_text_color,Color.WHITE)
        bottomTextPaint.apply {
            textSize = bottomTextSize
            color = bottomTextColor
        }

        eachSegmentHeight = typeArray.getDimension(R.styleable.SegmentView_segment_item_height,0f)

        space = typeArray.getDimension(R.styleable.SegmentView_segment_h_space,0f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        val wholeSpace  = (segmentCount - 1) * space
        eachSegmentWidth = (mWidth - wholeSpace) / segmentCount
        segmentContainerHeight = rate * mHeight
        if(eachSegmentHeight == 0f) eachSegmentHeight = segmentContainerHeight
        topTextHeight =  rate * mHeight
        bottomTextHeight = rate * mHeight
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(mWidth == 0 || mHeight == 0) return
        drawSegment(canvas)
        drawText(canvas)
        drawPercentCircle(canvas)
        drawBottomText(canvas)
    }


    private fun drawText(canvas: Canvas){
        canvas.save()
        for (i in 0 until segmentCount){
            val segment = getSegment(i)
            drawCenterText(segment.title,textPaint, Rect(0,0,eachSegmentWidth.toInt(),eachSegmentHeight.toInt()),canvas)
            canvas.translate(eachSegmentWidth * 1f + space,0f)
        }
        canvas.restore()
    }

    private fun drawSegment(canvas: Canvas){
        canvas.save()
        canvas.translate(0f,topTextHeight + (segmentContainerHeight - eachSegmentHeight)/2)
        for ( i in 0 until segmentCount){
            when (i) {
                0 -> {
                    drawStartSegment(canvas,i)
                }
                segmentCount -1 -> {
                    drawEndSegment(canvas,i)
                }
                else -> {
                    drawCenterSegment(canvas,i)
                }
            }
            canvas.translate(eachSegmentWidth * 1f + space,0f)
        }
        canvas.restore()
    }

    private fun drawCenterSegment(canvas: Canvas,index:Int){
        val segment = getSegment(index)
        segmentPaint.color = segment.color
        canvas.drawRect(0f,0f,eachSegmentWidth*1f,eachSegmentHeight*1f,segmentPaint)
    }

    private fun drawStartSegment(canvas: Canvas,index:Int){
        val segment = getSegment(index)
        path.reset()
        path.moveTo((eachSegmentWidth - eachSegmentHeight / 2)*1f, 0f)
        path.addArc(0f,0f,eachSegmentHeight * 1f,eachSegmentHeight*1f,-90f,-180f)
        path.lineTo(eachSegmentWidth * 1f,eachSegmentHeight * 1f)

        path.lineTo(eachSegmentWidth * 1f,0f)
        path.close()
        canvas.drawPath(path,Paint().apply {
            style = Paint.Style.FILL
            color = segment.color
            strokeWidth = 30f
        })

    }

    private fun drawEndSegment(canvas: Canvas,index:Int){
        val segment = if(index < segments.size) segments[index] else Segment()
        path.reset()
        path.moveTo(0f,0f)
        path.lineTo((eachSegmentWidth - eachSegmentHeight / 2)*1f,0f)
        path.addArc((eachSegmentWidth - eachSegmentHeight)*1f,0f,eachSegmentWidth*1f,eachSegmentHeight*1f,-90f,180f)
        path.lineTo(0f,eachSegmentHeight *1f)
        path.lineTo(0f,0f)
        canvas.drawPath(path,Paint().apply {
            style = Paint.Style.FILL
            color = segment.color
            strokeWidth = 30f
        })
    }

    private fun drawPercentCircle(canvas: Canvas){
        // 计算绘制在 第几个 segment
        val index = (percent * segmentCount).toInt()
        val distance = percent * (eachSegmentWidth * segmentCount) + (index - 1) * space
        val centerX = distance + circleRadius * 2
        var centerY = topTextHeight + segmentContainerHeight /2
        canvas.drawCircle(centerX,centerY,circleRadius,circlePaint)
    }

    private fun drawBottomText(canvas: Canvas){
        canvas.save()
        canvas.translate(0f,topTextHeight + segmentContainerHeight)
        for(i in 0 until segmentCount - 1){
            val segment = getSegment(i)
            drawCenterText(segment.endText,bottomTextPaint, Rect(0,0,(eachSegmentWidth * 2 + space).toInt(),bottomTextHeight.toInt()),canvas)
            canvas.translate(eachSegmentWidth * 1f + space,0f)
        }
        canvas.restore()
    }

    fun setPercent(percent:Float){
         this.percent = when{
            percent < 0 ->{
                0f
            }
            percent > 1 ->{
                1f
            }
            else-> {
                percent
            }
        }
        postInvalidate()
    }

    fun setSegments(segments:List<Segment>){
        this.segments.clear()
        this.segments.addAll(segments)
        postInvalidate()
    }

    private fun getSegment(index: Int):Segment{
        return if(index < segments.size) segments[index] else Segment()
    }

}