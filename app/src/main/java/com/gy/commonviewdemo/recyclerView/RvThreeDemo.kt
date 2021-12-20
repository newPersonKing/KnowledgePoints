package com.gy.commonviewdemo.recyclerView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.recyclerView.adapter.ThreeAdapter
import com.gy.commonviewdemo.recyclerView.layoutmanager.ThreeLayoutManager
import kotlinx.android.synthetic.main.rv_three_demo.*


class RvThreeDemo : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.rv_three_demo)

        val layoutManager = ThreeLayoutManager()
        rv_three.layoutManager = layoutManager
        layoutManager.attachToRecyclerView(rv_three)
        rv_three.adapter = ThreeAdapter()


    }

}


