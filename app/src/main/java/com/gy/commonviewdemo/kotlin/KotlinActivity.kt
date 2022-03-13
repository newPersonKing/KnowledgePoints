package com.gy.commonviewdemo.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gy.commonviewdemo.DemoData
import com.gy.commonviewdemo.MainAdapter
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_kotlin.*


class KotlinActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        val adapter = MainAdapter(listOf(
            DemoData("kotlin代理属性", KotlinReadAndWriterActivity::class.java,this),
        ))

        rv_kotlin.layoutManager = LinearLayoutManager(this)
        rv_kotlin.adapter = adapter

    }

}