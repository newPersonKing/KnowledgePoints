package com.gy.commonviewdemo.recyclerView.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.gy.commonviewdemo.R

class ThreeAdapter : RecyclerView.Adapter<ThreeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_three_item,parent,false)
        return ThreeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ThreeViewHolder, position: Int) {

        holder.itemView.findViewById<AppCompatTextView>(R.id.rv_item_tv).text = "这是第${position}个item"

    }

    override fun getItemCount(): Int {
       return 4
    }


}


class ThreeViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){

}