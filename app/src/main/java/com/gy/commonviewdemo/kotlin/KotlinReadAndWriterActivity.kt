package com.gy.commonviewdemo.kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.kotlin.readandwriter.Preference
import com.gy.commonviewdemo.kotlin.readandwriter.PropertiesDelegate
import kotlinx.android.synthetic.main.activity_kotlin_read_and_writer.*

// kotlin 代理属性
class KotlinReadAndWriterActivity : AppCompatActivity(){

    var name by Preference(this,"")

    var password by PropertiesDelegate<String>("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_read_and_writer)

        put.setOnClickListener {
            name = "神奇的世界:${System.currentTimeMillis()}"
        }

        get.setOnClickListener {
            Toast.makeText(this,name,Toast.LENGTH_SHORT).show()
        }
    }

}
