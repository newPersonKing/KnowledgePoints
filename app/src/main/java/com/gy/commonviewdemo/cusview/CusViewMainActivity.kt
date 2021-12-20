package com.gy.commonviewdemo.cusview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.threeD.CusView3DActivity
import com.gy.commonviewdemo.recyclerView.RvMainActivity
import kotlinx.android.synthetic.main.cus_view_main.*

class CusViewMainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cus_view_main)

        btn_3D.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
          R.id.btn_3D -> {
              val intent = Intent(this,CusView3DActivity::class.java)
              startActivity(intent)
          }
        }
    }
}