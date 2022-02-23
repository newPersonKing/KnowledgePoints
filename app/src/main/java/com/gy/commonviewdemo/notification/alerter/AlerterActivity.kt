package com.gy.commonviewdemo.notification.alerter

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_alerter.*

class AlerterActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerter)

        btn_common.setOnClickListener {
            Alerter.create(this)
                .setTitle("Alert Title")
                .setText("\"The alert scales to accommodate larger bodies of text. \" +\n" +
                        "                 \"The alert scales to accommodate larger bodies of text. \" +\n" +
                        "                 \"The alert scales to accommodate larger bodies of text.\"")
                .setBackgroundColorRes(R.color.colorAccent)
                .showRightIcon(true)
                .setOnClickListener {

                }
                .setOnShowListener{

                }
                .setOnHideListener{

                }
                .enableSwipeToDismiss()
                .addButton("Okay", R.style.AlertButton, View.OnClickListener {
                    Toast.makeText(this, "Okay Clicked", Toast.LENGTH_LONG).show()
                })
                .setLayoutGravity(Gravity.CENTER)
                .show()
        }
    }
}