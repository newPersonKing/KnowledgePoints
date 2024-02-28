package com.gy.commonviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import kotlin.Unit;

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

    private SurfaceView getSurfaceView(View view){
        SurfaceView surfaceView = traverseView(view);
        return surfaceView;
    }

    private SurfaceView traverseView(View view){

        if(view instanceof SurfaceView)return (SurfaceView) view;
        if(view instanceof ViewGroup){
            for (int i = 0 ; i< ((ViewGroup) view).getChildCount() ; i++ ){
                View child = ((ViewGroup) view).getChildAt(i);
                if(child instanceof  SurfaceView)return (SurfaceView) child;
            }

            for (int i = 0 ; i< ((ViewGroup) view).getChildCount() ; i++ ){
                SurfaceView surfaceView = traverseView(((ViewGroup) view).getChildAt(i));
                if(surfaceView != null){
                    return surfaceView;
                }
            }
        }

        return null;
    }

    //截屏 surfaceView
    private void takePicture(View view){
        SurfaceView surfaceView = getSurfaceView(view);

        Surface surface = surfaceView.getHolder().getSurface();

        if(!surface.isValid()) return;

        Bitmap bitmap;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            PixelCopy.request(
                    surfaceView, bitmap, new PixelCopy.OnPixelCopyFinishedListener() {
                        @Override
                        public void onPixelCopyFinished(int copyResult) {
                            if(copyResult == PixelCopy.SUCCESS){
                                bitmapToByteArray(bitmap);
                            }
                        }
                    },
                    new Handler(Looper.getMainLooper())
            );
        }
        else{
            bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            canvas.setBitmap(null);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapToByteArray(bitmap);
        }
    }

    private void bitmapToByteArray(Bitmap bitmap) {
//        MediaStore.Images.Media.insertImage(view.context.contentResolver,
//                bitmap,
//                null,
//                null);
    }
}
