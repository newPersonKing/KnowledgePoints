package com.gy.commonviewdemo.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

class FlowViewModel : ViewModel(){

    private val _shareFlow = MutableSharedFlow<Int>(0,1, BufferOverflow.DROP_OLDEST)

    val sharedFlow: SharedFlow<Int> = _shareFlow

    init {

      viewModelScope
    }


    // 1 每一个 CoroutineScope 含有一个 coroutineContext 字段

}
