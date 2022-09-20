package com.gy.commonviewdemo.kotlin.ext

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.os.HandlerCompat

// 获取SystemService
//public inline fun <reified T : Any> Context.getSystemService(): T? =
//    ContextCompat.getSystemService(this, T::class.java)
//
///**
// * Executes [block] on a [TypedArray] receiver. The [TypedArray] holds the attribute
// * values in [set] that are listed in [attrs]. In addition, if the given [AttributeSet]
// * specifies a style class (through the `style` attribute), that style will be applied
// * on top of the base attributes it defines.
// *
// * @param set The base set of attribute values.
// * @param attrs The desired attributes to be retrieved. These attribute IDs must be
// *              sorted in ascending order.
// * @param defStyleAttr An attribute in the current theme that contains a reference to
// *                     a style resource that supplies defaults values for the [TypedArray].
// *                     Can be 0 to not look for defaults.
// * @param defStyleRes A resource identifier of a style resource that supplies default values
// *                    for the [TypedArray], used only if [defStyleAttr] is 0 or can not be found
// *                     in the theme. Can be 0 to not look for defaults.
// *
// * @see Context.obtainStyledAttributes
// * @see android.content.res.Resources.Theme.obtainStyledAttributes
// */
/**
 * 自定义属性读取
 */
//public inline fun Context.withStyledAttributes(
//    set: AttributeSet? = null,
//    attrs: IntArray,
//    @AttrRes defStyleAttr: Int = 0,
//    @StyleRes defStyleRes: Int = 0,
//    block: TypedArray.() -> Unit
//) {
//    obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes).apply(block).recycle()
//}
//
///**
// * Executes [block] on a [TypedArray] receiver. The [TypedArray] holds the the values
// * defined by the style resource [resourceId] which are listed in [attrs].
// *
// * @param attrs The desired attributes. These attribute IDs must be sorted in ascending order.
// *
// * @see Context.obtainStyledAttributes
// * @see android.content.res.Resources.Theme.obtainStyledAttributes
// */
//public inline fun Context.withStyledAttributes(
//    @StyleRes resourceId: Int,
//    attrs: IntArray,
//    block: TypedArray.() -> Unit
//) {
//    obtainStyledAttributes(resourceId, attrs).apply(block).recycle()
//}
