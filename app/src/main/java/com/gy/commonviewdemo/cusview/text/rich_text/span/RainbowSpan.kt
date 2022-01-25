package com.gy.commonviewdemo.cusview.text.rich_text.span

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

class RainbowSpan(val textLength: Int) : CharacterStyle(), UpdateAppearance {

    // 修改绘制文字的Paint
    override fun updateDrawState(paint: TextPaint) {
        paint.style = Paint.Style.FILL
        val shader: Shader = LinearGradient(
            0F, 0F, paint.textSize * textLength, 0F,
            Color.RED, Color.BLUE, Shader.TileMode.CLAMP
        )
        paint.shader = shader
    }
}