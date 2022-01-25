package com.gy.commonviewdemo.livedata.singleLiveEvent;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private static final String TAG = "SingleLiveEvent";

    private final AtomicBoolean mPending = new AtomicBoolean(false);


    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {

        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
        }

        // Observe the internal MutableLiveData
        // 1 如果多次注册 观察者 那么当数据改变的时候 所有的观察者 都会回调这里
        // 2 如果有一个观察者成功处理了事件,那么其他的观察者就会忽略这个事件
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                // 如果已经是true 那么更新为false 并且调用 observer.onChanged
                // 如果是false 那么这里就不会更新 如果更新成功这里才会返回true
                if (mPending.compareAndSet(true, false)) {
                    observer.onChanged(t);
                }
            }
        });
    }

    // 1 设置 value 前 更新 mPending 的值 为true
    // 2 再调用setValue 通知更新
    // 3 会回调到onChanged 方法 这个时候 compareAndSet 会返回true 并把mPending 更新为false
    // 4 当SingleLiveEvent 被重新注册的时候 因为mPending 为false，所以新的注册者收不到上一次的值
    @MainThread
    public void setValue(@Nullable T t) {
        mPending.set(true);
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        setValue(null);
    }
}
