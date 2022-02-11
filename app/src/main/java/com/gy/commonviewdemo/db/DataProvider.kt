package com.gy.commonviewdemo.db

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.net.Uri
import android.util.Log
import com.gy.commonviewdemo.db.model.UserColumns

class DataProvider : ContentProvider() {

    private lateinit var sUriMatcher: UriMatcher
    lateinit var dbHelper:SQLiteHelper;

    val USERS = 1 //查询全部ID的用户

    val USER_ID = 2 //根据ID来查询单个用户

    val USER_ACCOUNT = 3 //根据用户账号查询用户

    override fun onCreate(): Boolean {
        dbHelper = SQLiteHelper(context!!)

        sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        sUriMatcher.addURI(
            UserColumns.AUTHORITY,
            UserColumns.TABLE_NAME,
            USERS
        )
        sUriMatcher.addURI(
            UserColumns.AUTHORITY,
            UserColumns.TABLE_NAME + "/id/*",
            USER_ID
        )
        sUriMatcher.addURI(
            UserColumns.AUTHORITY,
            UserColumns.TABLE_NAME + "/account/*",
            USER_ACCOUNT
        )

        return true
    }


    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        when(sUriMatcher.match(uri)){
            USERS -> {
                val tableName = uri.pathSegments[0]
                val sqLiteDatabase = dbHelper.readableDatabase
                return queryWithNotify(uri,sqLiteDatabase.query(
                    tableName,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                ))
            }
            USER_ID -> {
                val sqLiteDatabase = dbHelper.readableDatabase
                val tableName = uri.pathSegments[0]
                val id = uri.pathSegments[2]
                val where: String = UserColumns._ID + " =? "
                val whereArgs = arrayOf(id)
                return queryWithNotify(uri,sqLiteDatabase.query(tableName, null, where, whereArgs, null, null, null))
            }
            USER_ACCOUNT -> {
                val sqLiteDatabase = dbHelper.readableDatabase
                val tableName = uri.pathSegments[0]
                val account = uri.pathSegments[2]
                val where = UserColumns.USERNAME + " =? "
                val whereArgs = arrayOf(account)
                return queryWithNotify(uri, sqLiteDatabase.query(tableName, null, where, whereArgs, null, null, null))
            }
        }
        return null
    }


    // 自己根据uri类型 返回不同的类型
    override fun getType(uri: Uri): String? {

        return "DateProvider";
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (values == null) {
            return uri
        }
        Log.i("cccccccccc","插入数据===${values}")
        val sqLiteDatabase = dbHelper.readableDatabase
        val tableName = uri.pathSegments[0]
        val rowId = sqLiteDatabase.insert(tableName, null, values) //插入的行数

        context!!.contentResolver.notifyChange(uri, null)
        if (rowId > 0) {
            //代表插入成功
            val resultUri = ContentUris.withAppendedId(uri, rowId)
        }
        return uri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        var count = 0
        try {
            val sqLiteDatabase = dbHelper.readableDatabase
            val tableName = uri.pathSegments[0]
            count = sqLiteDatabase.delete(tableName, selection, selectionArgs)
            Log.i("cccccccccccc","删除成功====count:$count")
        } catch (e: SQLiteException) {
            Exception("" + e.message)
        }
        return count
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val path = uri.pathSegments
        val table = path[0]
        val id = path[2]
        return dbHelper.writableDatabase.update(
            table,
            values,
            UserColumns._ID + "=?",
            arrayOf(id)
        )
    }

    // TODO 通知刷新
    private fun queryWithNotify(uri: Uri, cursor: Cursor?): Cursor? {
        if (cursor == null) {

        } else {
            cursor.setNotificationUri(context!!.contentResolver, uri)
        }
        return cursor
    }
}