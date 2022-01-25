package com.gy.commonviewdemo.livedata.cusevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

class ListViewModel : ViewModel() {
    private val _navigateToDetails = MutableLiveData<Event<String>>()

    val navigateToDetails : LiveData<Event<String>>
        get() = _navigateToDetails

    fun userClicksOnButton(itemId: String) {
        _navigateToDetails.value = Event(itemId)  // Trigger the event by setting a new Event as a new value
    }
}

private fun test(){
//    ListViewModel().navigateToDetails.observe(this, Observer {
//    it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
//        // 根据 getContentIfNotHandled 返回的值决定这个事件是否被处理过
//    }
}

