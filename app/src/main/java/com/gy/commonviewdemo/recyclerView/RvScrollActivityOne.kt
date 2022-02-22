package com.gy.commonviewdemo.recyclerView

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.recyclerView.adapter.CommonLinearAdapter
import kotlinx.android.synthetic.main.activity_rv_scroll_one.*

/*通过改变滑动速度 来实现快速滚到到指定位置*/

class RvScrollActivityOne : AppCompatActivity() , View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_scroll_one)
        btn_start_scroll.setOnClickListener(this)

        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        rv.adapter = CommonLinearAdapter()
    }

    override fun onClick(v: View) {

        when(v.id){
            R.id.btn_start_scroll -> {
                val smoothScroll = LinearSmoothScrollerImplOne(rv,this)
                smoothScroll.targetPosition = 10
                rv.layoutManager?.startSmoothScroll(smoothScroll)
            }
        }
    }

}

class LinearSmoothScrollerImplOne(val recyclerView:RecyclerView,context:Context) : BaseLinearSmoothScroller(context) {

    // 根据要滑动的距离 修改滑动速度
    // 缺点：1 每一次滚动之间，会有速度不连贯的情况，且滚动停止时的减速时间太少，给人感觉动画比较生硬。
    override fun calculateTimeForScrolling(dx: Int): Int {
        if (Math.abs(dx) > recyclerView.measuredHeight) {
            return (super.calculateTimeForScrolling(dx) * 0.2f).toInt()
        } else {
            return super.calculateTimeForScrolling(dx);
        }
    }

}
