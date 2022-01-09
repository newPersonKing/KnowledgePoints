package com.gy.commonviewdemo.db

import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.gy.commonviewdemo.db.model.UserColumns

class DBUtils {

    private lateinit var context: Context

    //查询写法
    fun query(){
        //自己直接填写过滤条件
        val cursor: Cursor? = context.contentResolver.query(
            UserColumns.CONTENT_URI,
            null,
            UserColumns._ID + " =? ",
            arrayOf("10"),
            UserColumns.UPDATEAT + " DESC"
        )

        // 在Uri 路径后面拼接 path 自己解析path
        val userName = "gy"
        val uri = Uri.withAppendedPath(
            UserColumns.CONTENT_URI,
            "account/$userName"
        )
        val cursor1: Cursor? = context.contentResolver.query(uri, null, null, null, null)

        cursor1?.registerContentObserver(object : ContentObserver(Handler(Looper.getMainLooper())) {

            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
            }
        })
        // cursor1 setNotificationUri 可以让自己监听到数据的变化 如果有插入 或者删除 可以返回最新的数据
        // setNotificationUri 研究这个是干什么的
    }

    fun update(){
        // ContentValues 中填写更新内容 过滤条件 可以 设置where ， selectionArgs 或者 自己根据Uri解析path
        context.contentResolver.update(UserColumns.CONTENT_URI, ContentValues(), null, null)
    }

    fun delete(){
        //  过滤条件 可以 设置where ， selectionArgs 或者 自己根据Uri解析path
        val row = context.contentResolver.delete(
            UserColumns.CONTENT_URI,
            " name= '${UserColumns.TABLE_NAME}'", null
        )
    }

}