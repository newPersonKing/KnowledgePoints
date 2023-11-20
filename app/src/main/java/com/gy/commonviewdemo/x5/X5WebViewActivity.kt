package com.gy.commonviewdemo.x5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.ycbjie.webviewlib.X5WebView

class X5WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_x5_web)

        val webview = findViewById<X5WebView>(R.id.web_view).apply {
            webViewClient = YcX5WebViewClient(this, this@X5WebViewActivity);
            webChromeClient = YcX5WebChromeClient(this,this@X5WebViewActivity)
            setGeolocationEnabled(true)
        }
        webview.settings.apply {
            domStorageEnabled = true
            javaScriptEnabled = true
        }
//        webview.loadUrl("https://qz-m.ecapi.cn/kfc/set/city?type=1&source=https%3A%2F%2Fqz-m.ecapi.cn%2Fkfc%2Frestaurant%2Fselect%3FplatformId%3D10436")
        webview.loadUrl("https://oil.21ds.cn/?token=eee4d3cbd1a1bce1967a7ccd8d3dec98")
    }
}