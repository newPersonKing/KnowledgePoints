package com.gy.commonviewdemo.picture_in_picture

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_picture_in_picture.*

/*https://developer.android.google.cn/guide/topics/ui/picture-in-picture*/
class PictureInPictureActivity : AppCompatActivity(),View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_in_picture)

        btn_enter_picture_in_picture.setOnClickListener(this)

    }

    override fun onClick(v: View) {
       when(v.id){
           R.id.btn_picture_in_picture -> {
               if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                   enterPictureInPictureMode()
               }
           }
       }
    }

}