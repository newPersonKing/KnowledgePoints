package com.gy.commonviewdemo.x5;

import android.content.Context;

import com.ycbjie.webviewlib.MyX5WebViewClient;
import com.ycbjie.webviewlib.X5WebView;

public class YcX5WebViewClient extends MyX5WebViewClient {
    public YcX5WebViewClient(X5WebView webView, Context context) {
        super(webView, context);
    }

    //重写你需要的方法即可
}