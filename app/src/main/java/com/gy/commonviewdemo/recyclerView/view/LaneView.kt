package com.gy.commonviewdemo.recyclerView.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.recyclerView.adapter.LaneAdapter
import com.gy.commonviewdemo.recyclerView.layoutmanager.LaneLayoutManager

class LaneView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var mHandler:Handler = Handler(Looper.getMainLooper())
    private var recyclerView:RecyclerView? = null
    private var baseDuration = 2000L
    private var baseDistance = 1000
    init {

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.LaneView)
        var speedMultiple = typeArray.getFloat(R.styleable.LaneView_speed_multiple,1f)
        baseDistance = (baseDistance * speedMultiple).toInt()
        recyclerView = RecyclerView(context)
        addView(recyclerView,LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT))
        recyclerView?.layoutManager = LaneLayoutManager()
        recyclerView?.background = ColorDrawable(Color.BLUE)
        setAdapter(LaneAdapter())
    }

    private val moveRunnable = Runnable {
        move()
    }

    fun <VH:RecyclerView.ViewHolder> setAdapter(adapter: RecyclerView.Adapter<VH>){
        recyclerView?.adapter = adapter
    }

    fun start(){
        mHandler.post(moveRunnable)
    }

    private fun move(){
        mHandler.postDelayed(moveRunnable,baseDuration)
        recyclerView?.smoothScrollBy(baseDistance,0, LinearInterpolator(),baseDuration.toInt())
    }

}