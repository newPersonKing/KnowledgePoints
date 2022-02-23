package com.gy.commonviewdemo.notification.alerter.utils


import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import androidx.annotation.DimenRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.notification.alerter.Alert

fun Alert.getDimenPixelSize(@DimenRes id: Int) = resources.getDimensionPixelSize(id)

@RequiresApi(Build.VERSION_CODES.P)
fun Alert.notchHeight() = rootWindowInsets?.displayCutout?.safeInsetTop
        ?: 0

// 获取 theme中设置的全局属性 需要在theme中设置 selectableItemBackground 属性
fun Context.getRippleDrawable(): Drawable? {
	val typedValue = TypedValue()
	theme.resolveAttribute(R.attr.selectableItemBackground, typedValue, true)
	val imageResId = typedValue.resourceId
	return ContextCompat.getDrawable(this, imageResId)
}