package com.gy.commonviewdemo.recyclerView

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.recyclerView.adapter.PageLinearAdapter
import com.gy.commonviewdemo.recyclerView.layoutmanager.CantScrollLayoutManager
import kotlinx.android.synthetic.main.activity_rv_cant_scroll.*
import kotlinx.android.synthetic.main.activity_rv_scroll_one.rv


// 只可以某一个方向翻页
class RvCantScrollActivity : AppCompatActivity() , View.OnClickListener{

    var layoutManager : CantScrollLayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_cant_scroll)
        btn_stop_scroll.setOnClickListener(this)

        val pagerSnapHelper = PagerSnapHelper()

        pagerSnapHelper.attachToRecyclerView(rv)

        layoutManager = CantScrollLayoutManager(this)
        rv.layoutManager = layoutManager
        rv.adapter = PageLinearAdapter()

    }

    override fun onClick(v: View) {
       when(v.id){
           R.id.btn_stop_scroll ->{
               layoutManager?.setIsCanScrollDown(false)
           }
       }
    }

}

