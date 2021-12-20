package com.gy.commonviewdemo.recyclerView.layoutmanager

import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class ThreeLayoutManager : RecyclerView.LayoutManager(){

    val tag = javaClass.simpleName

    private var rv:RecyclerView? = null

    fun attachToRecyclerView(recyclerView:RecyclerView){
        rv = recyclerView
        recyclerView.addOnItemTouchListener(OnItemTouch())
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )
    }


    override fun canScrollHorizontally(): Boolean {
        return true
    }

    // 每一次手指滑动 滑动的距离
    private var mTouchOffset = 0
    // 每一次手指滑动 允许滑动的最大距离
    private var maxTouchOffset = 300
    //dx(dy) <0为 向右(下) 滚动，>0为向左(上) 滚动；
    // 水平滑动位移
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        Log.i(tag,"dx:$dx")

        val preTouchOffset = mTouchOffset
        val touchOffset = preTouchOffset + dx
        if(touchOffset > maxTouchOffset){
            // 这里直接直接调用 rv 的cancel 还是会触发scrollHorizontallyBy
            (rv?.parent as? View)?.dispatchTouchEvent(MotionEvent.obtain(mLastTouchEvent).apply { action = MotionEvent.ACTION_CANCEL  })

            rv?.smoothScrollBy(-maxTouchOffset,0)
//            mTouchOffset = 0
//            mCurrentStartPosition ++
        }else{
            if(touchOffset <= 0) mTouchOffset = 0 else mTouchOffset = touchOffset
            // 这里回收的作用
            // 1 主要是保证 getViewForPosition 根据index 获取view 在addView 之后能正常获取到
            detachAndScrapAttachedViews(recycler)
            realLayout(recycler,state)
        }
        // 如果这里返回 0 smoothScrollBy 这个方法就失效了
        return dx
    }


    private val marginUnit = 12F // 相邻 View 的距离
    private var mMaxOffset = 0F        // 最上方的 View 和最下方的 View 的距离
    private var mDisplayCount = 0 // 展示的数量
    private val maxDisplayCount = 3

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {

        val displayCount = Math.min(maxDisplayCount, state.itemCount)
        mDisplayCount = displayCount

        // 如果没有可以布局的item
        if(displayCount == 0){
            removeAndRecycleAllViews(recycler)
            return
        }
        detachAndScrapAttachedViews(recycler)
        realLayout(recycler,state)

        super.onLayoutChildren(recycler, state)
    }


    private var mCurrentStartPosition = 0
    private fun realLayout(recycler: RecyclerView.Recycler, state: RecyclerView.State){
        // 获取当前viewGroup 的数量
        val childCount = childCount
        Log.i(tag,"childCount:$childCount")
        // 返回adapter的itemCount
        val itemCount = state.itemCount
        Log.i(tag,"itemCount:$itemCount")
        val  startPosition = mCurrentStartPosition
        val endPosition = mCurrentStartPosition+mDisplayCount
        for(i in endPosition downTo startPosition){
            val  realChildIndex = i % state.itemCount
            val view  = recycler.getViewForPosition(realChildIndex)
            // 每一个item 应该向左偏移的 间距个数
            val indexDistance = mDisplayCount - (i -startPosition)
            // i 越大 越靠右
            val distance = indexDistance * 100
            Log.i(tag,"distance:${distance}")
            // 往viewGroup 中添加view 根据添加先后 来决定层级
            addView(view)
            measureChildWithMargins(view,0,0)

            Log.i(tag,"viewSize:width${view.measuredWidth},height:${view.measuredHeight}")
            Log.i(tag,"container:width${width},height:${height}")

            // 如果是第一个item 也就是最顶部的item 特殊处理 跟着滑动 处理
            var left = width - view.measuredWidth - distance
            var right = width
            if(i == startPosition){
                left -= mTouchOffset
                if(left <= 0){
                    left = 0
                }
                right -= mTouchOffset
            }
            layoutDecorated(view, left, indexDistance * 50 ,left + view.measuredWidth,view.measuredHeight + indexDistance * 50)
        }

        mMaxOffset = marginUnit * (mDisplayCount - 1)
    }
    private var mLastTouchEvent : MotionEvent? = null
    private inner class OnItemTouch : RecyclerView.OnItemTouchListener{
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            mLastTouchEvent = e
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        }

    }

//    /**
//     * 该类的作用主要是控制速度，其他的参数比如 targetPosition 并没有作用，因为开始滚动时就已经能确定 targetPosition
//     */
//    private inner class MySmoothScroller(private val velocityX: Int) : androidx.recyclerview.widget.LinearSmoothScroller(context){
//
//        init {
//            // 此处并没有实际意义，SmoothScroller 必须有一个 targetPosition。此处设置一个肯定存在的 position
//            targetPosition = mCurrentPosition
//        }
//
//        override fun onTargetFound(targetView: View, state: androidx.recyclerview.widget.RecyclerView.State, action: Action) {
//            val dx: Int = if(velocityX > 0 || mCurrentOffset > width.toFloat() / 2F){
//                width - mCurrentOffset
//            }else{
//                -mCurrentOffset
//            }
//            val time = calculateTimeForScrolling(dx)
//            if (time > 0) {
//                action.update(dx, 0, time, mDecelerateInterpolator)
//            }
//        }
//
//        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
//            //  MILLISECONDS_PER_INCH / displayMetrics.densityDpi
//            return 600F / displayMetrics.densityDpi
//        }
//    }

}