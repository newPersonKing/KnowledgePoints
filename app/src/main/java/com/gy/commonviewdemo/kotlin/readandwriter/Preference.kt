package com.gy.commonviewdemo.kotlin.readandwriter

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preference<T>(val context: Context, val name: String, val default: T, val prefName: String = "default") :
    ReadWriteProperty<Any?, T> {

    constructor(context: Context, default: T, prefName: String = "default"): this(context, "", default, prefName)

    private val prefs by lazy { context.getSharedPreferences(prefName, Context.MODE_PRIVATE) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(findProperName(property), default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(findProperName(property), value)
    }

    private fun findProperName(property: KProperty<*>) = if(name.isEmpty()) property.name else name

    private fun <U> findPreference(name: String, default: U): U = with(prefs) {
        val res :Any =   when (default) {
            is Long -> getLong(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            is String -> getString(name, default) ?:""
            else -> throw IllegalArgumentException("Unsupported type")
        }
        res as U
    }

    private fun <U> putPreference(name: String, value: U) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("Unsupported type")
        }.apply()
    }
}