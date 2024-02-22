package com.gy.commonviewdemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Utils {


    /**
     * 是否为鸿蒙系统
     *
     * @return true为鸿蒙系统
     */
    public static boolean isHarmonyOs() {
        try {
            Class<?> buildExClass = Class.forName("com.huawei.system.BuildEx");
            Object osBrand = buildExClass.getMethod("getOsBrand").invoke(buildExClass);
            return "Harmony".equalsIgnoreCase(osBrand.toString());
        } catch (Throwable x) {
            return false;
        }
    }

    /**
     * 获取鸿蒙系统版本号
     *
     * @return 版本号
     */
    public static String getHarmonyVersion() {
        return getProp("hw_sc.build.platform.version", "");
    }

    private static String getProp(String property, String defaultValue) {
        try {
            Class spClz = Class.forName("android.os.SystemProperties");
            Method method = spClz.getDeclaredMethod("get", String.class);
            String value = (String) method.invoke(spClz, property);
            if (TextUtils.isEmpty(value)) {
                return defaultValue;
            }
            return value;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

     public  static String getLocalIPAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {

                NetworkInterface intf = en.nextElement();

                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {

                    InetAddress inetAddress = enumIpAddr.nextElement();

                    Log.i("ccccccccc","inetAddress===="+inetAddress.getHostAddress());
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // return inetAddress.getAddress().toString();
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("BaseScanTvDeviceClient", "获取本机IP false =" +  ex.toString());
        }

        return null;
    }

    public static String getVpnIp(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            LinkProperties linkProperties = connectivityManager.getLinkProperties(network);

            if (linkProperties != null && linkProperties.getInterfaceName() != null) {
                // 返回VPN IP地址
                return linkProperties.getLinkAddresses().toString();
            }
        }

        return null; // 如果找不到VPN IP，返回null或者其他默认值
    }
}
