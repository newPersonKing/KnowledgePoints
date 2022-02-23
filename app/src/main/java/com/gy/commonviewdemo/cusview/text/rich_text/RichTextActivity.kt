package com.gy.commonviewdemo.cusview.text.rich_text

import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.cusview.text.rich_text.span.BlockSpaceSpan
import com.gy.commonviewdemo.cusview.text.rich_text.span.FrameSpan
import com.gy.commonviewdemo.cusview.text.rich_text.span.RainbowSpan
import com.gy.commonviewdemo.cusview.text.rich_text.span.TextRoundSpan
import kotlinx.android.synthetic.main.activity_rich_text.*

// span 扩展函数
// https://developer.android.google.cn/kotlin/ktx/extensions-list#androidxcoretext
class RichTextActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rich_text)

        backGroundColorSpan()

        clickSpan()

        foregroundColorSpan()

        drawableMarginSpan()

        maskFilterSpan()

        strikethroughSpan()

        underlineSpan()

        quoteSpan()

        bulletSpan()

        absoluteSizeSpan()

        imageSpan()

        iconMarginSpan()

        relativeSizeSpan()

        scaleXSpan()

        styleSpan()

        subscriptSpan()

        superscriptSpan()

        textAppearanceSpan()

        typefaceSpan()

        urlSpan()

        alignmentSpan()

        leadingMarginSpan()

        lineBackgroundSpan()

        lineHeightSpan()

        frameSpan()

        blockSpaceSpan()

        textRoundSpan()

        rainBowSpan()

        bufferTextSpan()
    }

    private fun backGroundColorSpan(){
        val string = SpannableString("Text with a background color span")
        string.setSpan(BackgroundColorSpan(Color.RED), 12, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_background_color_span.text = string
    }

    private fun clickSpan(){
        val string = SpannableString("XXXXXXXX").apply {
            this.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(this@RichTextActivity, "触发点击", Toast.LENGTH_SHORT).show()
                }
            }, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        tv_click_span.movementMethod = LinkMovementMethod.getInstance()
        tv_click_span.text = string
    }

    private fun foregroundColorSpan(){
        val span = ForegroundColorSpan(Color.YELLOW)
        val spannableString = SpannableString("CONTENT")
        spannableString.setSpan(
            span,
            0,
            spannableString.length / 2,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv_foreground_color_span.text = spannableString
    }

    //DrawableMarginSpan  文本开头插入Drawable
    private fun drawableMarginSpan(){
        val drawable = getDrawable(R.mipmap.ic_launcher)
        val span = SpannableString("xxxxxxxxxx")
        span.setSpan(DrawableMarginSpan(drawable!!), 0, span.length, 0)
        tv_drawable_margin_span.text = span
    }

    //MaskFilterSpan 修饰效果，如模糊(BlurMaskFilter)、浮雕(EmbossMaskFilter)
    // 浮雕效果 看不见
    private fun maskFilterSpan(){
        // Blur a character
        val blurSpan = MaskFilterSpan(BlurMaskFilter(30f * 2, BlurMaskFilter.Blur.NORMAL))
        // Emboss a character
        val emBossSpan = MaskFilterSpan(EmbossMaskFilter(floatArrayOf(1f, 1f, 1f), 0.4f, 6f, 3.5f))
        val span = SpannableString("测试修饰效果，模糊以及浮雕")
        span.setSpan(blurSpan, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(emBossSpan, 4, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_mask_filter_span.text = span
    }

    private fun strikethroughSpan(){
        val span = StrikethroughSpan()
        val spannableString = SpannableString("测试删除线")
        spannableString.setSpan(span, 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_strike_through_span.text = spannableString
    }

    private fun underlineSpan(){
        val span = UnderlineSpan()
        val spannableString = SpannableString("测试下划线")
        spannableString.setSpan(span, 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_underline_span.text = spannableString
    }

    // QuoteSpan   段落前引用
    @RequiresApi(Build.VERSION_CODES.P)
    private fun quoteSpan(){
        val span = QuoteSpan(Color.RED, 50, 50)
        val spannableString = SpannableString("段落前引用")
        spannableString.setSpan(span, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_quote_span.text = spannableString
    }

    // BulletSpan：段落前圆点
    @RequiresApi(Build.VERSION_CODES.P)
    private fun bulletSpan(){
        val span = BulletSpan(15, Color.RED, 15)
        val spannableString = SpannableString("段落前圆点")
        spannableString.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_bullet_span.text = spannableString
    }

    //AbsoluteSizeSpan 字体绝对大小（文本字体）
    private fun absoluteSizeSpan(){
        val string = SpannableString("Text with absolute size span")
        string.setSpan(AbsoluteSizeSpan(55, true), 10, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_absolute_size_span.text = string
    }

    private fun imageSpan(){
        val span = ImageSpan(this, R.mipmap.ic_launcher)
        val spannableString = SpannableString("测试ImageSpan")
        // ImageSpan 会替换掉指定的位置
        spannableString.setSpan(span, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_image_span.text = spannableString
    }

    private fun iconMarginSpan(){
        // 如果图片是vector 在高版本会解析失败
        val bmp = BitmapFactory.decodeResource(resources, R.mipmap.avatar1)
        val span = SpannableString("IconMarginSpan")
        // 可以在这里生成自己想要的尺寸的bmp
        span.setSpan(IconMarginSpan(bmp), 0, span.length, 0)
        span.setSpan(IconMarginSpan(bmp, 20), 0, span.length, 0)
        tv_icon_margin_span.text = span
    }

    // RelativeSizeSpan 字体相对大小（文本字体）
    private fun relativeSizeSpan(){
        val span = RelativeSizeSpan(2.0f)
        val spannableString = SpannableString("RelativeSizeSpan")
        spannableString.setSpan(
            span,
            0,
            spannableString.length / 2,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv_relative_size_span.text = spannableString
    }

    private fun scaleXSpan(){
        val span = ScaleXSpan(2.0f)
        val spannableString = SpannableString("ScaleXSpan")
        spannableString.setSpan(
            span,
            0,
            spannableString.length / 2,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv_scaleX_span.text = spannableString
    }

    private fun styleSpan(){
        val string = SpannableString("Bold and italic text")
        string.setSpan(StyleSpan(Typeface.BOLD), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        string.setSpan(StyleSpan(Typeface.ITALIC), 9, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_style_span.text = string
    }

    // 如果只有一行 注意设置高度 不然 下标容易看不全
    private fun subscriptSpan(){
        val span = SubscriptSpan()
        val spannableString = SpannableString("SubscriptSpan")
        spannableString.setSpan(span, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_subscript_span.text = spannableString
    }

    private fun superscriptSpan(){
        val span = SuperscriptSpan()
        val spannableString = SpannableString("SuperscriptSpan")
        spannableString.setSpan(
            span,
            0,
            spannableString.length / 2,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv_superscript_span.text = spannableString
    }

    private fun textAppearanceSpan(){
        val span = TextAppearanceSpan(this, R.style.SpecialTextAppearance)
        val spannableString = SpannableString("TextAppearanceSpan")
        spannableString.setSpan(span,0,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_text_appearance_span.text = spannableString
    }

    private fun typefaceSpan(){
//        val myTypeface = Typeface.create(
//            ResourcesCompat.getFont(context, R.font.some_font),
//            Typeface.BOLD
//        )
//        val string = SpannableString("Text with typeface span.")
//        string.setSpan(TypefaceSpan(myTypeface), 10, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        string.setSpan(TypefaceSpan("monospace"), 19, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun urlSpan(){
        val string = SpannableString("Text with a url span")
        string.setSpan( (object : URLSpan("http://www.developer.android.com"){
            override fun onClick(widget: View) {
                super.onClick(widget)
            }
        }), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_url_span.movementMethod =  LinkMovementMethod.getInstance()
        tv_url_span.text = string
    }

    // AlignmentSpan.Standard  文本对齐方式
    private fun alignmentSpan(){
        val string = SpannableString("Text with opposite alignmentText ")
        string.setSpan(
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), 0,
            string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
       tv_alignment_span.text = string
    }

    private fun leadingMarginSpan(){
        val ss = SpannableString("LeadingMarginSpanLeadingMarginSpanLeadingMarginSpanLeadingMarginSpanLeadingMarginSpanLeadingMarginSpanLeadingMarginSpanLeadingMarginSpanLeadingMarginSpanLeadingMarginSpan")
        ss.setSpan(LeadingMarginSpan.Standard(60), 0, ss.length, 0)
        ss.setSpan(LeadingMarginSpan.Standard(60, 20), 0, ss.length, 0)
        tv_leading_margin_span.text = ss
    }

    // 按行设置背景色
    private fun lineBackgroundSpan(){
        val textString = SpannableString("阅文集团(股票代码：0772.HK)由腾讯文学与原盛大文学整合而成，成立于2015年3月。是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读、起点中文网、新丽传媒等业界品牌。")
        textString.setSpan(LineBackgroundSpan.Standard(Color.CYAN), 0, textString.length/2, SPAN_INCLUSIVE_INCLUSIVE)
        tv_line_background_span.text = textString

    }

    private fun lineHeightSpan(){
        val textString = SpannableString("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月。是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读、起点中文网、新丽传媒等业界品牌。")
        textString.setSpan(LineHeightSpan.Standard(90), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_line_height_span.text = textString
    }

    private fun frameSpan(){
        // 这里的实现有问题 span的宽度 不能大于tv_frame_span 的宽度 不然展示有问题
        val textString = SpannableString("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月。是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读、起点中文网、新丽传媒等业界品牌。")
        textString.setSpan(FrameSpan(), 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_frame_span.text = textString
    }

    // 增加段间距
    private fun blockSpaceSpan(){
        val paragraphFirst = "这是第一段。这是第一段。这是第一段。这是第一段。这是第一段。\n"
        val paragraphSecond = "这是第二段。这是第二段。这是第二段。这是第二段。这是第二段。"
        val spaceString = "[space]"
        val paragraphText = SpannableString(paragraphFirst + spaceString + paragraphSecond)
        paragraphText.setSpan(BlockSpaceSpan(10), paragraphFirst.length, paragraphFirst.length + spaceString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_block_space_span.text = paragraphText
    }

    private fun textRoundSpan(){
        val textString = SpannableString("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月。是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读、起点中文网、新丽传媒等业界品牌。")
        textString.setSpan(TextRoundSpan(3, 200), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_text_round_span.text = textString
    }

    private fun rainBowSpan(){
        val textString = SpannableString("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月。是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读、起点中文网、新丽传媒等业界品牌。")
        textString.setSpan(RainbowSpan(10), 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textString.setSpan(RainbowSpan(10), 12, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_rain_bow.text = textString
    }

    // 为现有Text创建Span
    //当TextView调用setText之后，TextView中的文本将变为不可变类型，这时候如果要修改文本或者为现有文本创建Span，就必须重新构建Spannable对象并再次调用setText
    // ，而这就会重新触发渲染和布局，在这种场景下，会造成Spannable的资源浪费，这时候，就可以使用BufferType.SPANNABLE的方式来创建Spannable。
    private fun bufferTextSpan(){
        val textString = SpannableString("阅文集团由腾讯文学与原盛大文学整合而成，成立于2015年3月。是数字阅读平台和文学IP培育平台，旗下囊括QQ阅读、起点中文网、新丽传媒等业界品牌。")
        textString.setSpan(RainbowSpan(10), 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_buffer_text_span.setText(textString,TextView.BufferType.SPANNABLE)
        tv_buffer_text_span.postDelayed({
            val span = tv_buffer_text_span.text as Spannable
            span.setSpan(RainbowSpan(10), 12, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        },2000)

    }
}