package com.gy.commonviewdemo.cusview.text.rich_text

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.text.*
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.text.rich_text.span.ExImageSpan
import com.gy.commonviewdemo.cusview.text.rich_text.span.MarginImageSpan
import com.gy.commonviewdemo.cusview.text.rich_text.url_image_span.URLImageSpan
import kotlinx.android.synthetic.main.activity_image_span.*
import kotlin.math.min

class ImageSpanActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_span)

        imageSpanOne()

        imageSpanBounds()

        bigImageSpan()

        netWorkImageSpan()

        netWorkImageSpanTwo()

        marginImageSpan()

        textToImageSpan()

        viewToImageSpan()
    }

    // 当Image高度小于文字高度时，可以通过ImageSpan的verticalAlignment属性来控制其对齐方式，
    // 即设置DynamicDrawableSpan.ALIGN_BOTTOM、DynamicDrawableSpan.ALIGN_BASELINE、DynamicDrawableSpan.ALIGN_CENTER。
    private fun imageSpanOne(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月。是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读、起点中文网、新丽传媒等业界品牌。")
        textString.setSpan(ImageSpan(this, R.mipmap.lemon, DynamicDrawableSpan.ALIGN_BOTTOM), 1, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_image_span_one.text = textString
    }

    private fun imageSpanBounds(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月。是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读、起点中文网、新丽传媒等业界品牌。")
        val drawable = ContextCompat.getDrawable(this, R.mipmap.lemon)
        drawable?.let {
            drawable.setBounds(0, 0, 48, 48)
            textString.setSpan(ImageSpan(drawable), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        tv_image_span_bounds.text = textString
    }

    // 图片尺寸 大于文字高度
    private fun bigImageSpan(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月。是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读、起点中文网、新丽传媒等业界品牌。")
        val drawable = ContextCompat.getDrawable(this, R.mipmap.lemon)
        drawable?.let {
            it.bounds  = Rect(0, 0, 100, 100)
        }
        textString.setSpan((ExImageSpan(drawable!!, DynamicDrawableSpan.ALIGN_CENTER)), 1, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_big_image_span.text = textString
    }

    private fun netWorkImageSpan(){
        val imageUrl = "https://qdclient-resources-1252317822.cos.ap-chengdu.myqcloud.com/Android/test/maomao1.jpeg"

        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月，是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读，起点中文网，新丽传媒等业界品牌。")
        val drawable = ContextCompat.getDrawable(this, R.mipmap.lemon)

        tv_network_image_span.setText(textString, TextView.BufferType.SPANNABLE)
        val spannableText = tv_network_image_span.text as Spannable
        drawable?.let {
            drawable.setBounds(0, 0, 48, 48)
            spannableText.setSpan(ImageSpan(drawable), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        Glide.with(this).asBitmap().load(imageUrl).into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Handler().postDelayed({
                    val bitmapDrawable: Drawable = BitmapDrawable(resources, resource)
                    bitmapDrawable.setBounds(0, 0, 100, 100)
                    spannableText.setSpan(ImageSpan(bitmapDrawable), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }, 3000)
            }
        })
    }

    private fun netWorkImageSpanTwo() {
        val ss =
            SpannableString("<img>To be or not to be, that is the question（生存还是毁灭，这是一个值得考虑的问题）")

        // build 返回 DynamicDrawableSpan
        val urlImageSpan = URLImageSpan.Builder()
            .url("https://qdclient-resources-1252317822.cos.ap-chengdu.myqcloud.com/Android/test/maomao1.jpeg")
            .override(100, 100)
            .build(tv_network_image_span_two)
        ss.setSpan(urlImageSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_network_image_span_two.setText(ss, TextView.BufferType.SPANNABLE) // 必需设置
    }

    private fun marginImageSpan(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月。")
        val drawable = ContextCompat.getDrawable(this, R.mipmap.luck)
        drawable?.let {
            it.bounds  = Rect(0, 0, 100, 100)
        }
        textString.setSpan((MarginImageSpan(drawable!!, DynamicDrawableSpan.ALIGN_CENTER,30,30,imageWidth = 100)), 1, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_margin_image_span.text = textString
    }

    private fun textToImageSpan(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月。")
        tv_text_to_image_span.post {
            val drawable = createDrawable(et_content,et_content.text)

            textString.setSpan((ImageSpan(drawable)), 1, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            tv_text_to_image_span.text = textString
        }

    }



    private fun createDrawable(editText: EditText, source: CharSequence): Drawable {
        val editLayout = editText.layout
        val width = editLayout.width
        var layout = DynamicLayout(
            SpannableStringBuilder(),
            editLayout.paint,
            editLayout.width,
            editLayout.alignment,
            editLayout.spacingMultiplier,
            editLayout.spacingAdd,
            true
        )
        val builder = layout.text as SpannableStringBuilder
        builder.clear()
        builder.append(source)
        val want = editLayout.paint.measureText(builder, 0, builder.length).toInt()
        if (want != layout.width) {
            layout = DynamicLayout(
                builder,
                layout.paint,
                min(want, width),
                layout.alignment,
                layout.spacingMultiplier,
                layout.spacingAdd,
                true
            )
        }
        val bitmap = Bitmap.createBitmap(layout.width, layout.height, Bitmap.Config.ARGB_8888)
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        layout.draw(Canvas(bitmap))
        return BitmapDrawable(resources,bitmap).apply { bounds = rect }
    }

    // 将一个View转化为Bitmap，再作为ImageSpan进行插入。
    private fun viewToImageSpan(){
        val textString = SpannableStringBuilder("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月。")
        tv_view_to_image_span.post {
            val drawable = convertViewToDrawableNew(tv_view_to_image)

            textString.setSpan((ImageSpan(this,drawable)), 1, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            tv_view_to_image_span.text = textString
        }
    }

    private fun convertViewToDrawableNew(view: View): Bitmap {
        val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(spec, spec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val background = view.background
        background?.draw(canvas)
        view.draw(canvas)
        return bitmap
    }
}