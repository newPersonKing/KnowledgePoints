package com.gy.commonviewdemo.flow

import android.util.Log
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*


// https://mp.weixin.qq.com/s/LPhb9ekGv3DVo0Uc-o4Cyw

// 学习知识的网站
// https://xuyisheng.top/
object FlowUtil {

    private val hasLocationPermission = MutableStateFlow(false)


    fun simpleFlow():Flow<Int>{
        return flow {
            for ( i in 0..100){
                delay(3000)
                emit(i)
                Log.i("ccccccccccccc","thread-start===${Thread.currentThread().name}")
            }
        }
    }

    fun flowOfTest():Flow<String>{
        return flowOf("one","two","three").onEach {
            delay(2000)
        }
    }

    fun getCallbackFlow():Flow<String>{

        return callbackFlow {
            Log.i("ccccccc","callbackFlow:start")


            for(i in 0..100){
                // 模仿耗时操作 周期性执行某个操作
                delay(3000)
                // trySend  开始使用的版本是1.4.30 之前应该使用 send
                send("callbackFlow:Result;$i")
                Log.i("ccccccccccccc","退到后台时候是否继续执行==$i")
            }

            awaitClose {
               Log.i("cccccccccccc","awaitClose")
            }

        }
    }

    fun testCancelFlow():Flow<Int>{

        return flowOf(0,1,2,3,4,5).onEach {
            delay(1000)
        }
    }

    fun testBufferFlow():Flow<Int>{

        return flow {
            for ( i in 0..100){
                delay(300)
                emit(i)
                Log.i("ccccccccccccc","buffer_emit_$i")
            }
        }
    }

    fun testConflateFlow():Flow<Int>{
        return flow {
            for ( i in 0..100){
                delay(1000)
                emit(i)
                Log.i("ccccccccccccc","conflate_emit_$i")
            }
        }
    }

    //只向任何新的订阅者发送最新的值
    fun collectLatestFlow():Flow<Int>{
        val shareFlow = MutableSharedFlow<Int>(replay = 1,onBufferOverflow = BufferOverflow.DROP_OLDEST)
        // 只处理值发生改变的情况
        return shareFlow.distinctUntilChanged()
    }


}


// flow 的 filter 方法 分析
// 1 传入的是一个判断条件
// 2 继续调用 transform 方法 transform 代码如下
//public inline fun <T, R> Flow<T>.transform(
//    @BuilderInference crossinline transform: suspend FlowCollector<R>.(value: T) -> Unit
//): Flow<R> = flow { // Note: safe flow is used here, because collector is exposed to transform on each operation
//    collect { value ->
//        // kludge, without it Unit will be returned and TCE won't kick in, KT-28938
//        return@collect transform(value)
//    }
//}
// 可见最终返回一个新的flow，并且直接调用原本flow 的collect 方法 接收flow 传递过来的value
// 也就是说 我们最终collect 的是新返回的flow，当我们开始flow，执行 新返回flow 的{} 中的代码 接着执行 原始flow 的collect 接着就会收到 原始flow 的value，再经过 transform(value)
// 转换成新的 value 继续传递给后生成的flow，也就是我们collect 最终接收的值。只会filter 的 transform 只会根据过滤条件 决定是否 继续往下一级传递。


// flow 的 map 与 mapLatest 流程跟fliter 差不多 只是 会把原始的value 转换成新的类型

// flatMapConcat 需要返回的是新的flow