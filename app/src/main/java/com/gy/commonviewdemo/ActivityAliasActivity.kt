package com.gy.commonviewdemo

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_alias.*

class ActivityAliasActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alias)

        click_text.setOnClickListener {
            val intent =  Intent()
            intent.component = ComponentName(this,"com.gy.commonviewdemo.test")
            startActivity(intent)
        }
    }
}