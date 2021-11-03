package com.gy.commonviewdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import com.gy.commonviewdemo.edittext.EdittextActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_edit.setOnClickListener(this)

    }

    override fun onClick(v: View) {
       when(v.id){
           R.id.btn_edit -> {
               val intent = Intent(this,EdittextActivity::class.java)
               startActivity(intent)
           }
       }
    }
}