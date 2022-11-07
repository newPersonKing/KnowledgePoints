package com.gy.commonviewdemo.recyclerView.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gy.commonviewdemo.R

class CommonLinearAdapter : RecyclerView.Adapter<CommonLinearAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 知识点 recyclerView item 不充满全屏的问题    LayoutInflater.from(parent.context).inflate(R.layout.item_common_linear,null)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_common_linear,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvIndex =  holder.itemView.findViewById<TextView>(R.id.tv_index)
        tvIndex.text = "这是${position}个item"
    }

    override fun getItemCount(): Int {
        return 3000
    }


    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

    }
}