package com.gy.commonviewdemo.webview

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView

class CusWebChromeClient(val onShowFileChooser:(ValueCallback<Array<Uri>>?,FileChooserParams)->Unit) : WebChromeClient(){


    // 知识点1 ：返回值如果为true 那么filePathCallback.onReceiveValue 一定要回调 不然第二次 不会回调这个方
    // 2 如果返回false 就会一直回调这个方法 但是filePathCallback.onReceiveValue 这个回调就不可以调用 会报错
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        fileChooserParams?:return false
        onShowFileChooser.invoke(filePathCallback,fileChooserParams)
        return true
    }

}