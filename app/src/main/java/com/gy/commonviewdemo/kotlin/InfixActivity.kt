package com.gy.commonviewdemo.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.LogUtil
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_infix.*


class InfixActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infix)
        btn_one.setOnClickListener {
            val result =  100 add 100
            LogUtil.e("InfixActivity","result_$result")
        }

        btn_two.setOnClickListener {
            val account = Account()
            account add 100
            LogUtil.e("InfixActivity","result_${account.balance}")

        }
    }
}

infix fun Int.add(addNum:Int) : Int {
    return this + addNum
}

//类增加中缀函数
class Account {

    var balance = 100

    infix fun add(addNum:Int){
        this.balance += addNum
    }

}
