package com.gy.commonviewdemo.kotlin.ext

import androidx.core.os.bundleOf

fun createBundle(){
    //官方扩展
    bundleOf(
        //中缀函数 infix 的使用
        "key" to "value"
    )
}
