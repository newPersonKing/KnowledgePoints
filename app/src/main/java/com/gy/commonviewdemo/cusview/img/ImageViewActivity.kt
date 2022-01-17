package com.gy.commonviewdemo.cusview.img

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R

// 解读ImageView的wrap_content和adjustViewBounds的工作原理
class ImageViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img)
    }

}

// https://mp.weixin.qq.com/s/N3ZWgwbWUznPte2AIS4rRA
// 知识点 解析

// 1 前提 不设置fitXY 图片要保持原比例

// 1 不设置 adjustViewBounds 为true
// 对于iv_4 宽度 高度 都是 wrapContent  onMeasure 宽高 设置的是 drawable 与 父容器 宽高 较小的值 因为图片像素值 远大于 容器宽高 所以设置的就是 容器的宽高

// 对于 iv_4 第一次 确认的也是 drawable 与 父容器中较小的值 但是设置 adjustViewBounds 会根据drawable的宽高比 调整高度 或者宽度 保证最终展示的是 图片原有的宽高比

// 对于 iv_1 因为 drawable 的 宽高 都直接小于容器的宽高 所以 直接wrap-content 就可以正常展示图片的尺寸 并且 图片的尺寸 与 imageView 的尺寸是一致的