package com.gy.commonviewdemo.livedata

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object LiveDataUntil {

    fun one(){
        // 确保他在后台执行
        liveData(Dispatchers.IO) {
            // 可以执行一个suspend 的方法
            emit(test())
        }
    }

    suspend fun test():Int{

        return withContext(Dispatchers.IO){
            delay(30000)
            3
        }
    }


    // 将liveData的转换 切换到 工作线程
//    val liveData: LiveData<String> =
//        Transformations.switchMap(sourceliveData) {
//            liveData(viewModelScope.coroutineContext + Dispatchers.IO)
//            val data = someTranformFunction(it)
//            emit(data)
//        }
//}
}