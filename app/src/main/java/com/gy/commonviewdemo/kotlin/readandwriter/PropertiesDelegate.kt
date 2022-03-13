package com.gy.commonviewdemo.kotlin.readandwriter

import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

// 主要就是这个path 的确定 以及 android 读取对应文件的方式
class PropertiesDelegate<T>(val path: String) {

    val properties: Properties by lazy {
        val prop = Properties()
        try {
            javaClass.getResourceAsStream(path).use {
                prop.load(it)
            }
        } catch (e: Exception) {
            //logger.error(e.message, e)
            try {
                ClassLoader.getSystemClassLoader().getResourceAsStream(path).use {
                    prop.load(it)
                }
            } catch (e: Exception) {
                //logger.error(e.message, e)
                FileInputStream(path).use {
                    prop.load(it)
                }
            }
        }

        prop
    }

    // TODO 根据 类型 进行转换
    operator fun  getValue(thisRef: Any, property: KProperty<*>): T {
        val value = properties[property.name]
        val classOfT = property.returnType.classifier as KClass<*>
        return value as T
    }

    operator fun <T> setValue(thisRef: Any, property: KProperty<*>, value: T) {
        properties[property.name] = value
        File(path).outputStream().use {
            properties.store(it, "")
        }
    }
}
