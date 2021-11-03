package com.gy.commonviewdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    val liveData = MutableLiveData<Int>()

}