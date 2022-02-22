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
        btn_scroll_one.setOnClickListener(this)
        btn_scroll_two.setOnClickListener(this)
        btn_scroll_three.setOnClickListener(this)

    }


    override fun onClick(v: View) {
        when(v.id){

            R.id.btn_three -> {

                val intent = Intent(this,RvThreeDemo::class.java)
                startActivity(intent)

            }

            R.id.btn_scroll_one -> {
                val intent = Intent(this,RvScrollActivityOne::class.java)
                startActivity(intent)
            }

            R.id.btn_scroll_two -> {
                val intent = Intent(this,RvScrollActivityTwo::class.java)
                startActivity(intent)
            }

            R.id.btn_scroll_three -> {
                val intent = Intent(this,RvScrollActivityThree::class.java)
                startActivity(intent)
            }

        }
    }
}