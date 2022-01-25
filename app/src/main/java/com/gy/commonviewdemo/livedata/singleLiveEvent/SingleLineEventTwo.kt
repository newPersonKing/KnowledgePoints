package com.gy.commonviewdemo.livedata.singleLiveEvent

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEventTwo<T> : MutableLiveData<T>() {

    private val observers = CopyOnWriteArraySet<ObserverWrapper<T>>()


    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        // 每个观察者 还是提交给 原liveData
        super.observe(owner, wrapper)
    }

    override fun removeObservers(owner: LifecycleOwner) {
        observers.clear()
        super.removeObservers(owner)
    }

    override fun removeObserver(observer: Observer<in T>) {
        observers.remove(observer)
        super.removeObserver(observer)
    }

    @MainThread
    override fun setValue(t: T?) {
        observers.forEach { it.newValue() }
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

    private class ObserverWrapper<T>(private val observer: Observer<in T>) : Observer<T> {

        // 每个观察的事件处理逻辑 自己控制
        private val pending = AtomicBoolean(false)

        override fun onChanged(t: T?) {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending.set(true)
        }
    }
}