package com.gy.commonviewdemo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(val datas:List<DemoData>) : RecyclerView.Adapter<MainAdapter.MainAdapterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapterHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_adapter,parent,false)
        return  MainAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: MainAdapterHolder, position: Int) {
        val data = datas[position]
        val btnMain = holder.itemView.findViewById<Button>(R.id.btn_main)
        btnMain.text = data.title

        btnMain.setOnClickListener {
            val  intent = Intent(data.context,data.activity)
            data.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return datas.size
    }

    class  MainAdapterHolder(itemView:View) : RecyclerView.ViewHolder(itemView){


    }
}

data class DemoData(val title:String,val activity:Class<*>,val context:Context);