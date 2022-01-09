package com.gy.commonviewdemo.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.gy.commonviewdemo.db.model.UserColumns


class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, "name", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
       db.execSQL(UserColumns.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + UserColumns.TABLE_NAME)
    }

}