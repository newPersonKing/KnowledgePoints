package com.gy.commonviewdemo.recyclerView

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.recyclerView.adapter.CommonLinearAdapter
import kotlinx.android.synthetic.main.activity_rv_scroll_one.*

/*当滚动触发时，刚开始正常滚动，同时设置一个延时任务，当滚动在 x ms 内没结束时，直接停止滚动，然后触发一个无动画滚动，瞬间到达目标位置。*/

class RvScrollActivityTwo : AppCompatActivity() , View.OnClickListener{

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
                val smoothScroll = LinearSmoothScrollerImplTwo(rv,this)
                smoothScroll.targetPosition = 2000
                rv.layoutManager?.startSmoothScroll(smoothScroll)

                rv.postDelayed({
                    rv.scrollToPosition(2000)
                },1000)
            }
        }
    }

}

class LinearSmoothScrollerImplTwo(val recyclerView:RecyclerView,context:Context) : BaseLinearSmoothScroller(context) {


}
