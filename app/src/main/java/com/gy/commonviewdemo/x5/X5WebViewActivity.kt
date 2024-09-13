package com.gy.commonviewdemo.x5

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
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

        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                Log.i("cccccccc","url====${request?.url.toString()}")
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                view!!.evaluateJavascript(
                    "(function() { " +
                            "   var images = document.querySelectorAll('.swiper-item-pic'); " +
                            "   var imageUrls = []; " +
                            "   for (var i = 0; i < images.length; i++) { " +
                            "       imageUrls.push(images[i].getAttribute('src')); " +
                            "   } " +
                            "   return imageUrls.join('|'); " +
                            "})();"
                ) {
                    // 注意：value 是 JavaScript 代码执行后的返回值，是一个字符串，包含了所有轮播图片的 URL，以 '|' 分隔。
                     Log.i("cccccccc",it)
                    // 接下来你可以处理这些图片URL，比如加载或者显示。
                }

            }
        }
//
//        webview.loadUrl("https://qz-m.ecapi.cn/kfc/set/city?type=1&source=https%3A%2F%2Fqz-m.ecapi.cn%2Fkfc%2Frestaurant%2Fselect%3FplatformId%3D10436")
//        webview.loadUrl("https://oil.21ds.cn/?token=eee4d3cbd1a1bce1967a7ccd8d3dec98")
        webview.loadUrl("https://m.dianping.com/ugcdetail/243510438?sceneType=0&bizType=29&utm_source=ugcshare&msource=Appshare2021&utm_medium=h5&shareid=BUIpcgIYoU_1725097804076")
    }
}