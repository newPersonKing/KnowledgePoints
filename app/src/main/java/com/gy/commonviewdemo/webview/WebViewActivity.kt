package com.gy.commonviewdemo.webview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.viewpager.widget.ViewPager
import com.gy.commonviewdemo.R
import com.tencent.smtt.utils.FileProvider
import kotlinx.android.synthetic.main.activity_webview.tv_click
import kotlinx.android.synthetic.main.activity_webview.webview
import org.json.JSONArray
import org.json.JSONException
import java.io.File


class WebViewActivity : Activity(),View.OnClickListener {

    private var mUploadCallbackAboveL : ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

//        var intent = Intent()
//        intent.apply {
//            setAction(Intent.ACTION_VIEW)
//            addCategory(Intent.CATEGORY_BROWSABLE)
//            intent.setData(Uri.parse("http://192.168.68.196:8080/report.html"))
//        }
//        startActivity(intent)

//        webview.loadUrl("file:///android_asset/test.html")
//        webview.loadUrl("https://shared.ivydad.com/goods/shop?page_id=84&_hideShareButton=1")
//        webview.loadUrl("https://web.reponieasyngeasmergaecent.xyz/Easy+Reasoning+Game/h5games/index.html")
        webview.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
            }
        }

        webview.webChromeClient = WebChromeClient()
//        val webSettings = webview.settings
//        webSettings.useWideViewPort = true
//        webSettings.javaScriptEnabled = true
////        webSettings.displayZoomControls = false
//        webSettings.setSupportMultipleWindows(true)
//        webSettings.javaScriptCanOpenWindowsAutomatically = true
////        webSettings.loadWithOverviewMode = true
////        webSettings.builtInZoomControls = true
//        webSettings.domStorageEnabled = true

        initSetting()
//        Handler().postDelayed({
//            webview.loadUrl("https://www.macaoph4.com/m/home?affiliateCode=6666")
//        },3000)
//        webview.loadUrl("https://m.dianping.com/ugcdetail/243510438?sceneType=0&bizType=29&utm_source=ugcshare&msource=Appshare2021&utm_medium=h5&shareid=BUIpcgIYoU_1725097804076")

        webview.loadUrl("https://www.hayudao.com/superscene/#id=00000000000051160")
//        webview.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(
//                view: WebView?,
//                request: WebResourceRequest?
//            ): Boolean {
//                Log.i("cccccccc","url====${request?.url.toString()}")
//                return super.shouldOverrideUrlLoading(view, request)
//            }
//
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                Log.i("cccccccc","onPageFinished")
//
//            }
//        }
//
//        webview.settings.userAgentString = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36"


//        webview.webChromeClient = CusWebChromeClient{ callback,chooseParams->
//            mUploadCallbackAboveL = callback
//            takePhoto()
//        }
//        initSetting()

        tv_click.setOnClickListener {
            Log.i("ccccccc","${webview.childCount}")
           webview.children.forEach {
               Log.i("ccccccc",it.javaClass.simpleName)
           }
//            webview.post {
//                webview.evaluateJavascript("javascript:(function() {" +
//                        "var elements = document.getElementsByClassName('mc-header-wrap');" +
//                        "var result = [];" +
//                        "for (var i = 0; i < elements.length; i++) {" +
//                        "    result.push(elements[i].innerHTML);" +
//                        "}" +
//                        "return JSON.stringify(result);" +  // 将结果转换为JSON字符串返回
//                        "})()", ValueCallback<String?> { value ->
//                    // 这里value是JSON格式的字符串，表示找到的元素内容
//                    Log.i("cccccc","json-----${value}")
//                    if (value != null && !value.isEmpty()) {
//                        try {
//                            val jsonArray = JSONArray(value)
//                            for (i in 0 until jsonArray.length()) {
//                                val content = jsonArray.getString(i)
//                                // 处理每个元素的内容
//                            }
//                        } catch (e: JSONException) {
//                            e.printStackTrace()
//                        }
//                    }
//                })
//
////                var js = "javascript:(function() {" +
////                "  var rect = document.getElementsByClassName('mc-header-wrap')[0].getBoundingClientRect();" +
////                        "  return { 'top': rect.top, 'left': rect.left, 'height': rect.height, 'width': rect.width };" +
////                        "})()";
////
////                webview.evaluateJavascript(js,ValueCallback<String?> {
////                    Log.i("cccccc","it====$it")
////                })
//
////                webview.evaluateJavascript("document.getElementsByClassName('iframe-container')[0].innerHTML+=document.getElementById('mc-header').innerHTML",ValueCallback<String?> {
////                    Log.i("cccccc","it====$it")
////                })
//
////                val jsFunction = "javascript:(function () {" +
////                        "    var element = document.getElementsByClassName('mc-header-wrap')[0];" +
////                        "    if (!element) {" +
////                        "        return false;" +
////                        "    }" +
////                        "    var style = window.getComputedStyle(element);" +
////                        "    if (style.display === 'none' || style.visibility === 'hidden') {" +
////                        "        return false;" +
////                        "    }" +
////                        "    var rect = element.getBoundingClientRect();" +
////                        "    return rect.top < window.innerHeight && rect.bottom >= 0 && rect.left < window.innerWidth && rect.right >= 0;" +
////                        "})()"
////                webview.evaluateJavascript(jsFunction) {
////                    Log.i("cccccccc", "it=====visible====$it")
////                }
////
////                val jsStr = "javascript:(function(){document.getElementsByClassName('mc-header-wrap')[0].style.zIndex = '100';" +
////                        "return 'success'"+
////                        "})()"
////                webview.evaluateJavascript(jsStr) {
////                    Log.i("cccccccc", "it=====color====$it")
////                }
////
////
////                val positionStr = "javascript:(function(){document.getElementById('third-game').style.position = 'relative';" +
////                        "return 'success'"+
////                        "})()"
////
////                val mcHeader = "javascript:(function(){document.getElementById('mc-header').style.position = 'relative';" +
////                        "return 'success'"+
////                        "})()"
////
////                webview.evaluateJavascript(positionStr) {
////                    Log.i("cccccccc", "it=====positionStr====$it")
////                }
////
////                webview.evaluateJavascript(mcHeader) {
////                    Log.i("cccccccc", "it=====mcHeader====$it")
////                }
//            }
//            webview.loadUrl("javascript:showAlert()");
        }
    }

    @JavascriptInterface
    fun jsMethod(){

    }
    private fun checkContentSize(){


        webview.evaluateJavascript("document.compatMode"){
            Log.i("ccccccccccccc","mode====$it")
        }

        // 注意点：只针对 自己本地的html
        // document.documentElement.scrollHeight; 与 document.body.scrollHeight; 在webview 是match——parent的时候 获取到的值是固定的 暂时不知道是什么含义
        // 当内容超过webview 的高度的时候 document.documentElement.scrollHeight; 返回的值 会跟着内容的高度变化变化 document.body.scrollHeight; 还是固定值

        // 只有内容高度 大于webview 的尺寸的时候 读取到的值才是正确的
        webview.evaluateJavascript("document.documentElement.scrollHeight;"){
            Log.i("ccccccccccccc","height1====$it")
        }

        // 只有内容高度 大于webview 的尺寸的时候 读取到的值才是正确的
        webview.evaluateJavascript("document.body.scrollHeight;"){
            Log.i("ccccccccccccc","height2====$it")
        }
        /*document.body.querySelector('.preview-box').scrollHeight*/
        webview.evaluateJavascript("document.body.querySelector('.preview-box').scrollHeight"){
            Log.i("ccccccccccccc","height3====$it")
            // 动态设置webview 的高度
//            val params =  webview.layoutParams
//            params.height = (it.toInt()* resources.displayMetrics.density).toInt()
//            webview.layoutParams = params
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initSetting(){
        val settings = webview.settings
        settings.useWideViewPort = true

// 是否使用overview mode加载页面，默认值 false// 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
//        settings.loadWithOverviewMode = true // 设置这个值会让字体看着很小
//        settings.domStorageEnabled = true
//        settings.defaultTextEncodingName = "UTF-8"
//        settings.allowContentAccess = true // 是否可访问Content Provider的资源，默认值 true
//        settings.allowFileAccess = true // 是否可访问本地文件，默认值 true
//        settings.defaultFixedFontSize = 30
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
//        settings.allowFileAccessFromFileURLs = false
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
//        settings.allowUniversalAccessFromFileURLs = false
        //开启JavaScript支持
        settings.javaScriptEnabled = true;
        // 支持缩放
//        settings.setSupportZoom(true);

    }

    val PHOTO_REQUEST = 100
    val VIDEO_REQUEST = 200
    private var imageUri : Uri? = null
    /**
     * 拍照
     */
//    private fun takePhoto() {
//        val fileUri =  File(getExternalFilesDir("")!!.path + "/" + SystemClock.currentThreadTimeMillis() + ".jpg");
//        imageUri = Uri.fromFile(fileUri);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            imageUri = FileProvider.getUriForFile(this, "$packageName.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
//        }
//        PhotoUtils.takePicture(this, imageUri, PHOTO_REQUEST);
//    }

    override fun onClick(v: View) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PHOTO_REQUEST) {
            if (null == mUploadCallbackAboveL) return;
            val result  = if(data == null || resultCode != RESULT_OK)  null else data.getData()
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            }
        } else if (requestCode == VIDEO_REQUEST) {
            if (null == mUploadCallbackAboveL) return;

            val result = if(data == null || resultCode != RESULT_OK) null else data.getData()
            if (mUploadCallbackAboveL != null) {
                if (resultCode == RESULT_OK) {
                    mUploadCallbackAboveL?.onReceiveValue(arrayOf(result!!));
                    mUploadCallbackAboveL = null;
                } else {
                    mUploadCallbackAboveL?.onReceiveValue(arrayOf());
                    mUploadCallbackAboveL = null;
                }

            }
        }
    }

    private fun onActivityResultAboveL(requestCode:Int, resultCode:Int, data:Intent?) {
        if (requestCode != PHOTO_REQUEST || mUploadCallbackAboveL == null) {
            return;
        }
        var results : Array<Uri>? = null;
        if (resultCode == RESULT_OK) {
            if (data == null) {
                results = arrayOf(imageUri!!)
            } else {
                val dataString = data.dataString
                val clipData = data.clipData
                if (clipData != null) {
                    results =  Array<Uri>(clipData.itemCount){
                        Uri.EMPTY
                    }

                    for(i in 0 until clipData.itemCount){
                        val item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }

                }

                if (dataString != null)
                    results = arrayOf(Uri.parse(dataString))
            }
        }
        mUploadCallbackAboveL?.onReceiveValue(results);
        mUploadCallbackAboveL = null;
    }

}