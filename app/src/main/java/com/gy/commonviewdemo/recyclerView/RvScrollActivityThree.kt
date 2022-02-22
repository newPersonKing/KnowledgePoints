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

class RvScrollActivityThree : AppCompatActivity() , View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_scroll_one)
        btn_start_scroll.setOnClickListener(this)

        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        rv.adapter = CommonLinearAdapter()

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun onClick(v: View) {

        when(v.id){
            R.id.btn_start_scroll -> {
                val smoothScroll = LinearSmoothScrollerImplThree(rv,this)
                smoothScroll.targetPosition = 2000
                rv.layoutManager?.startSmoothScroll(smoothScroll)

                rv.postDelayed({
                    rv.scrollToPosition(2000)
                },1000)
            }
        }
    }

    // 判断 targetView 是否已经绘制
    private fun targetViewIsInLayout(position:Int):Boolean{
        val targetView = rv.layoutManager?.findViewByPosition(position)
        return targetView != null
    }

}

class LinearSmoothScrollerImplThree(val recyclerView:RecyclerView,context:Context) : BaseLinearSmoothScroller(context) {


}
