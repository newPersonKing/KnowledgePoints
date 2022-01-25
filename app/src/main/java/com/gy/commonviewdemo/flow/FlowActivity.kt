package com.gy.commonviewdemo.flow

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_flow.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class FlowActivity : AppCompatActivity(),View.OnClickListener{


    private val model :FlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)

        start_simple_flow.setOnClickListener(this)
        start_call_back_flow.setOnClickListener(this)
        start_flow_with_lifecycle.setOnClickListener(this)
        cancel_flow_test.setOnClickListener(this)
        flow_buffer.setOnClickListener(this)
        flow_conflate.setOnClickListener(this)

        // lifeCycle 扩展
        // 1 退到后台 会停止发送 回到前台继续发送
        lifecycleScope.launchWhenStarted {
            // 超时取消 协程
            withTimeoutOrNull(200000) {
                model.callBackFlow
//                .stateIn(
//                    scope = this,
//                    started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
//                    initialValue = "这是初始值")
                    .collect {
                        Log.i("cccccccccc", "it====$it")
                    }
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.start_simple_flow -> {
                MainScope().launch {
                    // flowOn 指定他上一级 代码的执行线程
                    FlowUtil.simpleFlow().flowOn(Dispatchers.IO).collect {
                        Log.i("ccccccccccccc","thread===${Thread.currentThread().name}")
                        Log.i("ccccccc","simpleFlow:$it")
                    }
                }
            }
            R.id.start_call_back_flow -> {
                model.hasLocationPermission.value = true
            }
            R.id.start_flow_with_lifecycle ->{
                MainScope().launch {
                    // flowOn 指定他上一级 代码的执行线程
                    FlowUtil.simpleFlow().flowWithLifecycle(lifecycle).collect {
                        Log.i("ccccccc","flow_with_lifecycle:$it")
                    }
                }
            }
            R.id.cancel_flow_test -> {
                MainScope().launch {
                    FlowUtil.testCancelFlow().collect {
                        Log.i("ccccccccccc","cancel==$it")
                        if(it == 3) cancel()
                    }
                }
            }
            R.id.flow_buffer -> {

                MainScope().launch {
                    // 如果不设置Buffer 生产一个 消费一个
                    // 设置buffer 可以多生产 下游处理 下游处理不过来 会放到缓存
                    FlowUtil.testBufferFlow().buffer(50).collect {
                        delay(1000)
                        Log.i("cccccccccc","buffer_collect_$it")
                    }
                }
            }

            R.id.flow_conflate -> {
                MainScope().launch {

                    // 当上游发送消息太快 会被丢弃一些消息
                    // collectLatest 只会处理最后一个元素
                    FlowUtil.testConflateFlow().conflate().collect {
                        delay(3000)
                        Log.i("cccccccccc","conflate_collect_$it")
                    }
                }
            }
        }
    }
}