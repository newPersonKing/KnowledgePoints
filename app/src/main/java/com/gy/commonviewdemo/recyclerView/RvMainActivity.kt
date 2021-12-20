package com.gy.commonviewdemo.recyclerView

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.rv_main.*

class RvMainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_main)

        btn_three.setOnClickListener(this)

    }


    override fun onClick(v: View) {
        when(v.id){

            R.id.btn_three -> {

                val intent = Intent(this,RvThreeDemo::class.java)
                startActivity(intent)

            }

        }
    }
}