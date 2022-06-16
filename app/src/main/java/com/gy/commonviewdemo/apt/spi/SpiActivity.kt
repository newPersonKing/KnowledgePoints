package com.gy.commonviewdemo.apt.spi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_spi.*
import java.util.*

class SpiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spi)

        btn_common.setOnClickListener {
            Log.i("ccccccccccc","setOnClickListener")
            val load = ServiceLoader.load(SpiInterface::class.java)
            load.forEach {
                Log.i("ccccccccccc","231231")
                it.run()
            }
        }
    }
}