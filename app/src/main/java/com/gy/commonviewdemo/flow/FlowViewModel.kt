package com.gy.commonviewdemo.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

class FlowViewModel : ViewModel(){

    val hasLocationPermission = MutableStateFlow(false)

    val callBackFlow = hasLocationPermission.filter { it }.mapLatest {
       it
    }.flatMapConcat {
        FlowUtil.getCallbackFlow()
    }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = "这是初始值")

}


// stateIn 含义的理解 代表这个flow 在哪个state 下可以继续执行上游的任务 如果跟 viewModelScope 绑定 即使退到后台 也会继续执行 如果跟activity 绑定 那么对到后台 就不继续执行