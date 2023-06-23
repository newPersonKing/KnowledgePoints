package com.gy.commonviewdemo

import android.content.Context
import java.io.File
import java.io.FileReader
import java.nio.file.Files

object ReadJsonFileUtil {
    fun read(context:Context){
        val dir = context.filesDir.absolutePath
        val jsonFile = File("${dir}/a.json")
        LogUtil.e("json===${jsonFile.exists()}")
        if(jsonFile.exists()){
           val json = FileReader(jsonFile).readText()
           LogUtil.e("json===${json}")
        }
    }
}