package com.gy.commonviewdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gy.commonviewdemo.edittext.EdittextActivity
import com.gy.commonviewdemo.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_edit.setOnClickListener(this)
        btn_webview.setOnClickListener(this)
    }

    override fun onClick(v: View) {
       when(v.id){
           R.id.btn_edit -> {
               val intent = Intent(this,EdittextActivity::class.java)
               startActivity(intent)
           }
           R.id.btn_webview -> {
               val intent = Intent(this,WebViewActivity::class.java)
               startActivity(intent)
           }
           }
       }
}