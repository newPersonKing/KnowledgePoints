package com.gy.commonviewdemo.recyclerView.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gy.commonviewdemo.R

class LaneAdapter : RecyclerView.Adapter<LaneAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 知识点 recyclerView item 不充满全屏的问题    LayoutInflater.from(parent.context).inflate(R.layout.item_common_linear,null)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lane,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position % 2 == 0){
            val tvIndex =  holder.itemView.findViewById<TextView>(R.id.tv_index)
            tvIndex.text = "这是${position}个item"
            tvIndex.visibility = View.VISIBLE
        }else {
            val tvIndex =  holder.itemView.findViewById<TextView>(R.id.tv_index)
            tvIndex.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return 3000
    }


    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

    }
}