package com.gy.commonviewdemo.constraint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_constraint_layout_demo.*

class ConstraintLayoutDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout_demo)
        iv_placeholder.setOnClickListener {
            left_placeholder.setContentId(R.id.iv_placeholder)
        }
    }

}