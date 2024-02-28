package com.gy.commonviewdemo.cusview.edittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import com.gy.commonviewdemo.R;

public class StrokeEditText extends AppCompatEditText {

    private EditText outlineTextView;
    private int strokeColor;

    public StrokeEditText(Context context) {
        super(context);
        outlineTextView = new EditText(context);
        init();
    }

    public StrokeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        outlineTextView = new EditText(context, attrs);
        init();
    }

    public StrokeEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        outlineTextView = new EditText(context, attrs, defStyle);
        init();
    }

    public void init() {
        outlineTextView.setEnabled(false);
        strokeColor = getContext().getResources().getColor(R.color.bottom_color);
        TextPaint paint = outlineTextView.getPaint();
        paint.setStrokeWidth(4f);
        paint.setStyle(Paint.Style.STROKE);
        outlineTextView.setTextColor(strokeColor);
        outlineTextView.setHintTextColor(strokeColor);
        outlineTextView.setGravity(getGravity());
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        outlineTextView.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 设置轮廓文字
        CharSequence outlineText = outlineTextView.getText();
        if (outlineText == null || !outlineText.equals(this.getText())) {
            outlineTextView.setText(getText());
            postInvalidate();
        }
        outlineTextView.measure(widthMeasureSpec, heightMeasureSpec);

        CharSequence hint = outlineTextView.getHint();

        if (hint == null || !hint.equals(this.getHint())) {
            outlineTextView.setHint(getHint());
            postInvalidate();
        }
        outlineTextView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        outlineTextView.layout(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        outlineTextView.draw(canvas);
        super.onDraw(canvas);
    }

    @Override
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        outlineTextView.scrollTo(horiz, vert);
    }
}