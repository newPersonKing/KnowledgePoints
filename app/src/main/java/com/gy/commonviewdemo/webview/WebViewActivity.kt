package com.gy.commonviewdemo.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        webview.loadUrl("file:///android_asset/test.html")

    }

    override fun onClick(v: View) {

    }
}