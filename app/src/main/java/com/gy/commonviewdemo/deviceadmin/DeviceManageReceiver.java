package com.gy.commonviewdemo.deviceadmin;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DeviceManageReceiver extends DeviceAdminReceiver {
    private final String TAG = "DeviceManageReceiver";

    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.d(TAG, "设备管理可用 ------onEnabled-------");
        super.onEnabled(context, intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "设备管理不可用 --------onReceive-----");
        super.onReceive(context, intent);
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
//        return super.onDisableRequested(context, intent);
        // "这是一个可选的消息，警告有关禁止用户的请求";
        return "关闭后不可使用一些功能";
    }

}
