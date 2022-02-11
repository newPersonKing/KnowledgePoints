package com.gy.commonviewdemo.clipboardManager

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.db.model.UserColumns
import kotlinx.android.synthetic.main.activity_clipboard.*

class ClipBoardActivity : AppCompatActivity(),View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clipboard)

        btn_copy_text.setOnClickListener(this)
        btn_copy_uri.setOnClickListener(this)
        btn_copy_intent.setOnClickListener(this)
        btn_get_text.setOnClickListener(this)
        btn_get_uri.setOnClickListener(this)
        btn_get_intent.setOnClickListener(this)
    }

    override fun onClick(v: View) {

        when(v.id){
            R.id.btn_copy_text -> {
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("复制文本","复制文本内容")
                clipBoard.setPrimaryClip(clip)
            }
            R.id.btn_copy_uri -> {
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val copyUri: Uri = UserColumns.CONTENT_URI
                val clip: ClipData = ClipData.newUri(contentResolver, "URI", copyUri)
                clipBoard.setPrimaryClip(clip)
            }
            R.id.btn_copy_intent -> {
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val intent = Intent(this,ClipBoardActivity::class.java)
                val clip: ClipData = ClipData.newIntent("intent", intent)
                clipBoard.setPrimaryClip(clip)
            }
            R.id.btn_get_text -> {
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = clipBoard.primaryClip
                clip?:return
                val item = clip.getItemAt(0)
                val content = item.text
                Log.i("ccccccccccc","复制文本内容士====$content")
            }
            R.id.btn_get_uri -> {
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = clipBoard.primaryClip
                clip?:return
                val item = clip.getItemAt(0)
                val uri = item.uri
                // 返回的是自定义Provider 重写getType 返回的值
                val type = contentResolver.getType(uri)
                Log.i("ccccccccccc","type====$type")
                val cursor = contentResolver.query(uri,null,null,null,null)
                cursor ?: return
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    val nameIndex = cursor.getColumnIndex(UserColumns.USERNAME)
                    val name = cursor.getString(nameIndex)
                    Log.i("ccccccccc", "name====$name")
                    cursor.moveToNext()
                }
            }
            R.id.btn_get_intent -> {
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = clipBoard.primaryClip
                clip?:return
                val item = clip.getItemAt(0)
                val intent = item.intent

                startActivity(intent)
            }
        }

    }

}