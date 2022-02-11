package com.gy.commonviewdemo.db

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.db.model.UserColumns
import kotlinx.android.synthetic.main.activity_content_provider.*

class ContentProviderActivity : AppCompatActivity(),View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)

        btn_insert.setOnClickListener(this)
        btn_delete.setOnClickListener(this)
        btn_query.setOnClickListener(this)
        btn_query_by_id.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){

            R.id.btn_insert -> {
                val uri = UserColumns.CONTENT_URI
                val contentValues = ContentValues()
                contentValues.put(UserColumns.USERNAME, "gy" + System.currentTimeMillis())
                contentValues.put(UserColumns.PASSWORD, "123456")
                contentValues.put(UserColumns.EMAIL, "123456@qq.com")
                contentValues.put(UserColumns.PHONE, "190000000001")
                contentValues.put(UserColumns.PROFILE_IMAGE_URL, "WWW.BAIDU.COM")
                contentValues.put(UserColumns.CREATAT, "2022.01.01")
                contentValues.put(UserColumns.UPDATEAT, "2022.01.02")
                /*contentResolver 操作那个Provider 根据uri 中的 AUTHORITY 进行匹配 AUTHORITY 是在xml 中设置Provider 设置的*/
                contentResolver.insert(uri, contentValues)
                Toast.makeText(this, "插入成功", Toast.LENGTH_LONG).show()
            }
            R.id.btn_delete -> {
                val uri = UserColumns.CONTENT_URI
                contentResolver.delete(uri, " ${UserColumns.PHONE}= 190000000001 ", null)
            }

            R.id.btn_query -> {
                val uri = UserColumns.CONTENT_URI
                val cursor = contentResolver.query(uri, null, null, null, null)
                cursor ?: return
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    val nameIndex = cursor.getColumnIndex(UserColumns.USERNAME)
                    val name = cursor.getString(nameIndex)
                    Log.i("ccccccccc", "name====$name")
                    cursor.moveToNext()
                }
            }
            R.id.btn_query_by_id -> {
                val uri =
                    Uri.parse("content://" + UserColumns.AUTHORITY + "/" + UserColumns.TABLE_NAME+"/account/gy1644566812974")
                val cursor = contentResolver.query(uri, null, null, null, null)
                cursor ?: return
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    val nameIndex = cursor.getColumnIndex(UserColumns.USERNAME)
                    val name = cursor.getString(nameIndex)
                    Log.i("ccccccccc", "name====$name")
                    cursor.moveToNext()
                }
            }

        }
    }

}