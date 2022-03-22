package com.gy.commonviewdemo.binder;

import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;

public class BinderProxy implements IGradeInterface {
    // 被代理的Binder
    private final IBinder mBinder;
    private final int REQUEST_CODE = 100;
    // 私有化构造方法
    private BinderProxy(IBinder binder) {
        mBinder = binder;
    }

    // 通过Binde读取成绩
    @Override
    public int getStudentGrade(Student student) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        Student replyStudent  = new Student();
        data.writeParcelable(student,0);
        try {
            if (mBinder == null) {
                throw new IllegalStateException("Need Bind Remote Server...");
            }
            mBinder.transact(REQUEST_CODE, data, reply, 0);
            replyStudent = (Student)(reply.readParcelable(BinderProxy.class.getClassLoader()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return replyStudent.code;
    }

    // 实例化Binder代理类的对象
    public static IGradeInterface asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }

        if (iBinder instanceof IGradeInterface) {
            Log.i("ccccccccc","当前进程");
            // 如果是同一个进程的请求，则直接返回Binder
            return (IGradeInterface) iBinder;
        } else {
            Log.i("ccccccccc","远程进程");
            // 如果是跨进程查询则返回Binder的代理对象
            return new BinderProxy(iBinder);
        }
    }

}