package com.gy.commonviewdemo.exposure

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class ExposureLayout : FrameLayout {
    /**
     * 定义曝光处理类
     */
    private val mExposureHandler by lazy {
        ExposureHandler(this)
    }
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    /**
     * 添加到视图
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mExposureHandler.onAttachedToWindow()
    }
    /**
     * 从视图中移除
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mExposureHandler.onDetachedFromWindow()
    }
    /**
     * 视图焦点改变
     */
    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        mExposureHandler.onWindowFocusChanged(hasWindowFocus)
    }
    /**
     * 视图可见性
     */
    override fun onVisibilityAggregated(isVisible: Boolean) {
        super.onVisibilityAggregated(isVisible)
        mExposureHandler.onVisibilityAggregated(isVisible)
    }
    /**
     * 曝光回调
     */
    fun setExposureCallback(callback: IExposureCallback) {
        mExposureHandler.setExposureCallback( callback)
    }
    /**
     * 设置曝光条件 曝光区域大小，例如展示超过50%才算曝光
     */
    fun setShowRatio(ratio: Float) {
        mExposureHandler.setShowRatio(ratio)
    }

    /**
     * 设置曝光最小时间，例如必须曝光超过两秒才算曝光
     */
    fun setTimeLimit(timeLimit:Int){
        mExposureHandler.setTimeLimit(timeLimit)
    }
}