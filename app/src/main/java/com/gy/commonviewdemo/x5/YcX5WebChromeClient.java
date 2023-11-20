package com.gy.commonviewdemo.x5;

import android.app.Activity;

import com.ycbjie.webviewlib.X5WebChromeClient;
import com.ycbjie.webviewlib.X5WebView;

public class YcX5WebChromeClient extends X5WebChromeClient {
    public YcX5WebChromeClient(X5WebView webView, Activity activity) {
        super(webView,activity);
        //重写你需要的方法即可
    }
}
