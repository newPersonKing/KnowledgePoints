package com.gy.commonviewdemo.flow

import kotlinx.coroutines.flow.*


val  shareflow = MutableSharedFlow<String>()

fun test(){
   val flow =  shareflow.asSharedFlow().onSubscription {

    }

//    flow.collect {
//
//    }
}

// MutableSharedFlow 的三个构造函数参数 replay  extraBufferCapacity onBufferOverflow

// 三个参数 都使用默认中 即 replay = 0 extraBufferCapacity= 0 onBufferOverflow = BufferOverflow.SUSPEND
// 1 这个 shareFlow 有三个事件 跟两个订阅者，第一个事件在还没有订阅者的时候发送，这个事件将永远消失。
// 2 第二个事件发送的时候，已经有一个订阅者了，所以这个订阅者成功拿到了这个事件。
// 3 当发送第三个事件的时候，另一个订阅者也出现了，单第一个订阅者正处于suspend，并保持，直到第三个事件到来，这意味着第三个事件没办法提交给第一个订阅者，
// 这个时候shareFlow 有两种选择，缓存该事件，当订阅者从suspend 中恢复的时候,发送该事件给订阅者。
// 4 但是现在 总的缓存区为 0 , 所以缓存溢出，并且onBufferOverflow = BufferOverflow.SUSPEND ，所以flow 会挂起,直到所有的订阅者都正常接收该事件。订阅者恢复，flow也恢复，可以正常提交事件。


// 当replay 为1 的时候
// 1 当shareFlow 到达第一个没有任何活动订阅者事件时，他不在暂停，因为relay为1 即 总缓存为1 ，因此flow 缓存了第一个事件，并必须执行。
// 2 当第二个事件到达的时候，缓存区 没有多余的空间，所以他suspend了
// 3 flow 保持suspend，知道订阅者恢复，那么订阅者就可以拿到缓存中的第一个事件以及第二个事件，但是第一个事件永远消失了，因为缓存是1，第二个事件已经顶替第一个事件放到缓存。
// 4 在第三个事件到达之前，出现了新的订阅者，新的订阅者可以直接拿到第二个事件，当第三个事件到达后，所有的订阅者都可以拿到事件。
// 5 shareFlow 缓存了第三个事件，并丢弃了之前的事件。

// 当 extraBufferCapacity = 1 and onBufferOverflow = BufferOverflow.DROP_OLDEST
// 1 当第一个事件来到的时候，因为没有订阅者，缓存为1 所以直接缓存第一个事件。
// 2 当第二个事件来到的时候,第一个订阅者已经订阅,所以第一个订阅者可以拿到第二个事件，由于onBufferOverflow = BufferOverflow.DROP_OLDEST 所以shareFlow 放弃了第一个事件，直接缓存第二个新事件。
// 3 接着注册第第二个订阅者，第二个订阅者并不会收到第二个事件的副本，因为replay = 0
// 4 当第三个事件到来的时候，flow 缓存了这个事件，放弃了之前的事件。
