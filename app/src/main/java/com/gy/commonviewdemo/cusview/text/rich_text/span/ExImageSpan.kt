package com.gy.commonviewdemo.cusview.text.rich_text.span

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import java.lang.ref.WeakReference

class ExImageSpan(drawable: Drawable, verticalAlignment: Int) : ImageSpan(drawable, verticalAlignment) {

    private var drawableRef: WeakReference<Drawable>? = null

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        val d = getCachedDrawable()
        d?.let {
            val rect = d.bounds
            if (fm != null) {
                val fmPaint = paint.fontMetricsInt
                val textHeight = fmPaint.descent - fmPaint.ascent
                val imageHeight = rect.bottom - rect.top
                if (imageHeight > textHeight && verticalAlignment == ALIGN_CENTER) {
                    fm.ascent = fmPaint.ascent - (imageHeight - textHeight) / 2
                    fm.top = fmPaint.ascent - (imageHeight - textHeight) / 2
                    fm.bottom = fmPaint.descent + (imageHeight - textHeight) / 2
                    fm.descent = fmPaint.descent + (imageHeight - textHeight) / 2
                } else {
                    fm.ascent = -rect.bottom
                    fm.descent = 0
                    fm.top = fm.ascent
                    fm.bottom = 0
                }
            }
            return rect.right
        }
        return 0
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        when (verticalAlignment) {
            ALIGN_CENTER -> {
                canvas.save()
                val fmPaint = paint.fontMetricsInt
                val fontHeight = fmPaint.descent - fmPaint.ascent
                val centerY = y + fmPaint.descent - fontHeight / 2
                val transY = centerY - (drawable.bounds.bottom - drawable.bounds.top) / 2
                canvas.translate(x, transY.toFloat())
                drawable.draw(canvas)
                canvas.restore()
            }
            else -> {
                canvas.save()
                val transY: Int = top + paint.fontMetricsInt.ascent - paint.fontMetricsInt.top
                canvas.translate(x, transY.toFloat())
                drawable.draw(canvas)
                canvas.restore()
            }
        }
    }

    private fun getCachedDrawable(): Drawable? {
        val wr: WeakReference<Drawable>? = drawableRef
        var d: Drawable? = null
        if (wr != null) {
            d = wr.get()
        }
        if (d == null) {
            d = drawable
            drawableRef = WeakReference<Drawable>(d)
        }
        return d
    }
}