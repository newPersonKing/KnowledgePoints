package com.gy.commonviewdemo.recyclerView

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

open class BaseLinearSmoothScroller(context: Context) : LinearSmoothScroller(context){

    /*计算滑动 dx 需要的时间*/
    override fun calculateTimeForScrolling(dx: Int): Int {
        return super.calculateTimeForScrolling(dx)
    }

    /*返回 滑动每一像素 需要的时间*/
    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
        return super.calculateSpeedPerPixel(displayMetrics)
    }

    override fun onTargetFound(targetView: View, state: RecyclerView.State, action: Action) {
        super.onTargetFound(targetView, state, action)
    }

    /*snapPreference 让view 可见的时候 相对的是哪条边*/
    /*snapPreference 是 getHorizontalSnapPreference 返回值*/
    override fun calculateDxToMakeVisible(view: View?, snapPreference: Int): Int {
        return super.calculateDxToMakeVisible(view, snapPreference)
    }

    /*snapPreference 是getVerticalSnapPreference 的返回值*/
    override fun calculateDyToMakeVisible(view: View?, snapPreference: Int): Int {
        return super.calculateDyToMakeVisible(view, snapPreference)
    }

    /*判断 要找到 targetView 的滑动方向*/
    override fun getVerticalSnapPreference(): Int {
        return super.getVerticalSnapPreference()
    }

    override fun getHorizontalSnapPreference(): Int {
        return super.getHorizontalSnapPreference()
    }
}