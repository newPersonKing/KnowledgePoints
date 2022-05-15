package com.gy.commonviewdemo.recyclerView.layoutmanager

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

// 弹幕
class LaneLayoutManager : RecyclerView.LayoutManager() {
    private val TAG = javaClass.simpleName
    /**
     * the return value of [layoutView], it means filling view into lane should be over
     */
    private val LAYOUT_FINISH = -1

    /**
     * the index related to data in adapter
     */
    private var adapterIndex = 0

    /**
     *
     */
    private var lastLaneEndView: View? = null

    /**
     * all the [Lane] in the RecyclerView
     */
    private var lanes = mutableListOf<Lane>()

    /**
     * the vertical gap of comment view
     */
    var verticalGap = 5

    /**
     * the horizontal gap of comment view
     */
    var horizontalGap = 3

    /**
     * define the layout params for child view in RecyclerView
     * override this is a must for customized [RecyclerView.LayoutManager]
     */
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    /**
     * define how to layout child view in RecyclerView
     * override this is a must for customized [RecyclerView.LayoutManager]
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        fillLanes(recycler, lanes)
    }

    /**
     * define how to scroll views in RecyclerView
     * override this is a must for customized [RecyclerView.LayoutManager]
     */
    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return scrollBy(dx, recycler)
    }

    /**
     * define whether scrolling horizontally is allowed
     * override this is a must for customized [RecyclerView.LayoutManager]
     */
    override fun canScrollHorizontally(): Boolean {
        return true
    }

    /**
     * scroll the all the [Lane]s in RecyclerView by [dx]
     */
    private fun scrollBy(dx: Int, recycler: RecyclerView.Recycler?): Int {
        if (childCount == 0 || dx == 0) return 0
        // 主要更新每一行 最后一个view 的坐标信息
        updateLanesEnd(lanes)
        val absDx = abs(dx)
        // fill new views into lanes after scrolled
        lanes.forEach { lane ->
            // 如果在滑动 dx 的范围内 下一个view 可以展示 添加
            if (lane.isDrainOut(absDx)) layoutViewByScroll(recycler, lane)
        }
        // recycle views in lanes after scrolled
        recycleGoneView(lanes, absDx, recycler)
        // make lane move left
        offsetChildrenHorizontal(-absDx)
        return dx
    }

    /**
     * fill children into [Lane]
     */
    private fun fillLanes(recycler: RecyclerView.Recycler?, lanes: MutableList<Lane>) {
        lastLaneEndView = null
        // 布局 内容 布局规则 从上到下 从左到右  按照adapterIndex 依次增加
        while (hasMoreLane(height - lanes.bottom())) {
            val consumeSpace = layoutView(recycler, lanes)
            if (consumeSpace == LAYOUT_FINISH) break
        }
    }

    private fun recycleGoneView(lanes: List<Lane>, dx: Int, recycler: RecyclerView.Recycler?) {
        recycler ?: return
        lanes.forEach { lane ->
            getChildAt(lane.startLayoutIndex)?.let { startView ->
                if (isGoneByScroll(startView, dx)) {
                    removeAndRecycleView(startView, recycler)
                    updateLaneIndexAfterRecycle(lanes, lane.startLayoutIndex)
                    lane.startLayoutIndex += lanes.size - 1
                }
            }
        }
    }

    /**
     * get the bottom of the last [Lane]
     */
    fun List<Lane>.bottom() = lastOrNull()?.getEndView()?.bottom ?: 0

    /**
     * after view is recycled and remove from RecyclerView, the layout index in lane should be minus 1
     */
    private fun updateLaneIndexAfterRecycle(lanes: List<Lane>, recycleIndex: Int) {
        lanes.forEachIndexed { index, lane ->
            if (lane.startLayoutIndex > recycleIndex) {
                lane.startLayoutIndex--
            }
            if (lane.endLayoutIndex > recycleIndex) {
                lane.endLayoutIndex--
            }
        }
    }

    /**
     * layout a single view when scrolling
     */
    private fun layoutViewByScroll(recycler: RecyclerView.Recycler?, lane: Lane) {
        val view = recycler?.getViewForPosition(adapterIndex)
        view ?: return
        measureChildWithMargins(view, 0, 0)
        addView(view)
        // layout even
        val left = lane.end + horizontalGap
        val top = lane.getEndView()?.top ?: paddingTop
        val right = left + view.measuredWidth
        val bottom = top + view.measuredHeight
        layoutDecorated(view, left, top, right, bottom)
        lane.apply {
            end = right
            endLayoutIndex = childCount - 1
        }
        adapterIndex++
    }

    /**
     * layout a single view in one [Lane]
     */
    private fun layoutView(recycler: RecyclerView.Recycler?, lanes: MutableList<Lane>): Int {
        // adapterIndex 初始是 0
        val view = recycler?.getViewForPosition(adapterIndex)
        view ?: return LAYOUT_FINISH
        // 测量view 的宽高
        measureChildWithMargins(view, 0, 0)
        // 垂直margin
        val verticalMargin = (view.layoutParams as? RecyclerView.LayoutParams)?.let { it.topMargin + it.bottomMargin } ?: 0
        // lastLaneEndView 初始为null
        val consumed = getDecoratedMeasuredHeight(view) + if (lastLaneEndView == null) 0 else verticalGap + verticalMargin
        // 1 第一次 lanes.bottom() 返回 0 consumed 是view 的高 lanes 增加了第一个 adapterIndex = 0
        // 2 第二次 lanes.bottom() 返回 第一个view 的高度
        if (height - lanes.bottom() - consumed > 0) {
            lanes.add(emptyLane(adapterIndex))
        } else return LAYOUT_FINISH

        addView(view)
        // layout even
        // 1 第一次 进来 lane.end 是屏幕的 width - hGap top 就是view 的顶部
        val lane = lanes.last()
        val left = lane.end + horizontalGap
        val top = if (lastLaneEndView == null) paddingTop else lastLaneEndView!!.bottom + verticalGap
        val right = left + view.measuredWidth
        val bottom = top + view.measuredHeight
        layoutDecorated(view, left, top, right, bottom)
        lane.apply {
            end = right
            endLayoutIndex = childCount - 1
        }

        adapterIndex++
        lastLaneEndView = view
        return consumed
    }

    /**
     * whether [view] will be invisible in RecyclerView if scrolled [dx] to the left
     */
    private fun isGoneByScroll(view: View, dx: Int): Boolean = getEnd(view) - dx < 0

    /**
     * update every [Lane]'s end to the newest position by next scroll starts,
     * which is the most right pixel of last view in Lane according to the RecyclerView
     */
    private fun updateLanesEnd(lanes: MutableList<Lane>) {
        lanes.forEach { lane ->
            lane.getEndView()?.let { lane.end = getEnd(it) }
        }
    }

    /**
     * get the right most pixel according to RecyclerView of [view], take decoration and margin into consideration
     */
    private fun getEnd(view: View?) = if (view == null) Int.MIN_VALUE else getDecoratedRight(view) + (view.layoutParams as RecyclerView.LayoutParams).rightMargin

    /**
     * whether continue to layout child
     */
    private fun hasMoreLane(remainSpace: Int) = remainSpace > 0 && adapterIndex in 0 until itemCount

    /**
     * return the layout index according to the adapter index
     */
    private fun getLayoutIndex(adapterIndex: Int): Int {
        val firstChildIndex = getChildAt(0)?.let { (it.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition } ?: 0
        return adapterIndex - firstChildIndex
    }

    /**
     * live comment is composed of several [Lane]
     * @param end pixel offset according to the left of RecyclerView, where the layout of follow-up view in the lane should start
     * @param endLayoutIndex the  layout index of the last view in lane
     */
    data class Lane(var end: Int, var endLayoutIndex: Int, var startLayoutIndex: Int)

    /**
     * get the right most view in the lane
     */
    private fun Lane.getEndView(): View? = getChildAt(endLayoutIndex)

    /**
     * whether [Lane] drains out
     */
    private fun Lane.isDrainOut(dx: Int): Boolean = getEnd(getEndView()) - dx < width

    /**
     * create an empty [Lane] object
     */
    private fun emptyLane(adapterIndex: Int) = Lane(-horizontalGap + width, -1, getLayoutIndex(adapterIndex))
}