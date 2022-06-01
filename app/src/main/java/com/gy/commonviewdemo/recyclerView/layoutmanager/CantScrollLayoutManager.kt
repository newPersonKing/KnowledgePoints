package com.gy.commonviewdemo.recyclerView.layoutmanager

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CantScrollLayoutManager(context: Context) : LinearLayoutManager(context) {

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        val realDy =  if(dy > 0 && !isCanScrollDown){
            0
        } else dy
        return super.scrollVerticallyBy(realDy, recycler, state)
    }

    private var isCanScrollDown = true
    fun setIsCanScrollDown(isCanScrollDown:Boolean){
        this.isCanScrollDown = isCanScrollDown
    }
}