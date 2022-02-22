package com.gy.commonviewdemo.cusview.text.rich_text.url_image_span

import android.graphics.drawable.Drawable

/**
 * Created by beniozhang on 2021/5/4.
 */
interface DrawableProvider {
    fun get(request: URLImageSpanRequest): Drawable
}