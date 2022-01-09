package com.gy.commonviewdemo.webview

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.webkit.*
import androidx.core.content.FileProvider
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_webview.*
import java.io.File

class WebViewActivity : AppCompatActivity(),View.OnClickListener {

    private var mUploadCallbackAboveL : ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        webview.loadUrl("file:///android_asset/test.html")

        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        webview.webChromeClient = CusWebChromeClient{ callback,chooseParams->
            mUploadCallbackAboveL = callback
            takePhoto()
        }
        initSetting()
    }

    private fun initSetting(){
        val settings = webview.settings
        settings.useWideViewPort = true

// 是否使用overview mode加载页面，默认值 false// 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
//        settings.loadWithOverviewMode = true // 设置这个值会让字体看着很小
        settings.domStorageEnabled = true
        settings.defaultTextEncodingName = "UTF-8"
        settings.allowContentAccess = true // 是否可访问Content Provider的资源，默认值 true
        settings.allowFileAccess = true // 是否可访问本地文件，默认值 true
        settings.defaultFixedFontSize = 30
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        settings.allowFileAccessFromFileURLs = false
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        settings.allowUniversalAccessFromFileURLs = false
        //开启JavaScript支持
        settings.setJavaScriptEnabled(true);
        // 支持缩放
        settings.setSupportZoom(true);

    }

    val PHOTO_REQUEST = 100
    val VIDEO_REQUEST = 200
    private var imageUri : Uri? = null
    /**
     * 拍照
     */
    private fun takePhoto() {
        val fileUri =  File(getExternalFilesDir("")!!.path + "/" + SystemClock.currentThreadTimeMillis() + ".jpg");
         imageUri = Uri.fromFile(fileUri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(this, "$packageName.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
        }
        PhotoUtils.takePicture(this, imageUri, PHOTO_REQUEST);
    }

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