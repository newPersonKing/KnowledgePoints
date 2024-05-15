package com.gy.commonviewdemo.cusview.path

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.DemoData
import com.gy.commonviewdemo.MainAdapter
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.anim.AnimActivity
import com.gy.commonviewdemo.cusview.edittext.EdittextActivity
import kotlinx.android.synthetic.main.activity_path_home.*
import kotlinx.android.synthetic.main.cus_view_main.*
import kotlinx.android.synthetic.main.cus_view_main.rv_main

class PathExampleActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path_home)

        path_move.post {
            path_move.start();
        }

        val adapter = MainAdapter(listOf(
            DemoData("edittext相关", EdittextActivity::class.java,this),
        ))

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapter
    }

}