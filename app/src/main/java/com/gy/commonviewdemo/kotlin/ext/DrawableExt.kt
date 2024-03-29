//package com.gy.commonviewdemo.kotlin.ext
//
//import android.graphics.Bitmap
//import android.graphics.Canvas
//import android.graphics.drawable.BitmapDrawable
//import android.graphics.drawable.Drawable
//import androidx.annotation.Px
//import androidx.core.graphics.component1
//import androidx.core.graphics.component2
//import androidx.core.graphics.component3
//import androidx.core.graphics.component4
//import androidx.core.graphics.drawable.toBitmap
//
//public fun Drawable.toBitmap(
//    @Px width: Int = intrinsicWidth,
//    @Px height: Int = intrinsicHeight,
//    config: Bitmap.Config? = null
//): Bitmap {
//    if (this is BitmapDrawable) {
//        if (config == null || bitmap.config == config) {
//            // Fast-path to return original. Bitmap.createScaledBitmap will do this check, but it
//            // involves allocation and two jumps into native code so we perform the check ourselves.
//            if (width == bitmap.width && height == bitmap.height) {
//                return bitmap
//            }
//            return Bitmap.createScaledBitmap(bitmap, width, height, true)
//        }
//    }
//
//    val (oldLeft, oldTop, oldRight, oldBottom) = bounds
//
//    val bitmap = Bitmap.createBitmap(width, height, config ?: Bitmap.Config.ARGB_8888)
//    setBounds(0, 0, width, height)
//    draw(Canvas(bitmap))
//
//    setBounds(oldLeft, oldTop, oldRight, oldBottom)
//    return bitmap
//}
//
///**
// * Updates this drawable's bounds. This version of the method allows using named parameters
// * to just set one or more axes.
// *
// * @see Drawable.setBounds
// */
//public fun Drawable.updateBounds(
//    @Px left: Int = bounds.left,
//    @Px top: Int = bounds.top,
//    @Px right: Int = bounds.right,
//    @Px bottom: Int = bounds.bottom
//) {
//    setBounds(left, top, right, bottom)
//}
