package com.gy.commonviewdemo.sharedpreferences;

import android.os.Handler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// 剖析 SharedPreference apply 引起的 ANR 问题
// https://mp.weixin.qq.com/s/IFgXvPdiEYDs5cDriApkxQ
public class SPHook {

    public static void tryHackActivityThreadH(){
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentAtyThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object activityThread = currentAtyThreadMethod.invoke(null);

            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler handler = (Handler) mHField.get(activityThread);

            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);
            mCallbackField.set(handler,new SpCompatCallback());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Throwable e){
            e.printStackTrace();
        }
    }
}
