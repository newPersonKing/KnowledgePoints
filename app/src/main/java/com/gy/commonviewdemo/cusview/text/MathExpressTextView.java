package com.gy.commonviewdemo.cusview.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MathExpressTextView extends View {

    private final List<TextInfo> TEXT_INFOS = new ArrayList<>();
    private int textSpace = 15;
    private String TAG = "MathExpressTextView";
    protected Paint mTextPaint;
    protected Paint mSubTextPaint;
    protected Paint mMarkTextPaint;
    protected float mContentWidth = 0f;
    protected float mContentHeight = 0f;
    protected float mMaxSize = 0;


    public void setMaxTextSize(float sizePx) {
        mMaxSize = sizePx;

        mTextPaint.setTextSize(mMaxSize);
        mSubTextPaint.setTextSize(mMaxSize / 3f);
        mMarkTextPaint.setTextSize(mMaxSize / 2f);

        invalidate();
    }

    public void setTextSpace(int textSpace) {
        this.textSpace = textSpace;
    }

    public void setContentHeight(float height) {
        mContentHeight = height;
        invalidate();
    }

    public MathExpressTextView(Context context){
        this(context,null);
    }
    public MathExpressTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setEditDesignTextInfos();
    }

    public MathExpressTextView setText(String text, String subText, String supText, float space) {
        this.TEXT_INFOS.clear();
        TextInfo.Builder tb = new TextInfo.Builder(text, mTextPaint, mSubTextPaint)
                .subText(subText)
                .supText(supText)
                .textSpace(space);

        this.TEXT_INFOS.add(tb.build());
        return this;
    }

    public MathExpressTextView appendMarkText(String text) {
        TextInfo.Builder tb = new TextInfo.Builder(text, mMarkTextPaint, mMarkTextPaint);
        this.TEXT_INFOS.add(tb.build());
        return this;
    }

    public MathExpressTextView appendText(String text, String subText, String supText, float space) {
        TextInfo.Builder tb = new TextInfo.Builder(text, mTextPaint, mSubTextPaint)
                .subText(subText)
                .supText(supText)
                .textSpace(space);

        this.TEXT_INFOS.add(tb.build());
        return this;
    }

    private void setEditDesignTextInfos() {

        if (!isInEditMode()) return;
//        setText("2H", "2", "", 10)
//				.appendMarkText("+");
//        		appendText("O", "2", "", 10);
//        		appendMarkText("=");
//        		appendText("2H", "2", "", 10);
//        		appendText("O", "", "", 10);

//		setText("sin(Θ+α)", "", "", 10)
//				.appendMarkText("=");
//		appendText("sinΘcosα", "", "", 10);
//		appendMarkText("+");
//		appendText("cosΘsinα", "", "", 10);

        setText("cos2Θ", "1", "", 10)
                .appendMarkText("=");
        appendText("cos", "", "2", 10);
        appendText("Θ", "1", "", 10);
        appendMarkText("-");
        appendText("sin", "", "2", 10);
        appendText("Θ", "1", "", 10);

    }
    public Paint getTextPaint() {
        return mTextPaint;
    }

    public Paint getSubTextPaint() {
        return mSubTextPaint;
    }

    public Paint getMarkTextPaint() {
        return mMarkTextPaint;
    }

    private float dpTopx(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    private void init() {

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.STROKE);

        mMarkTextPaint = new Paint();
        mMarkTextPaint.setColor(Color.WHITE);
        mMarkTextPaint.setAntiAlias(true);
        mMarkTextPaint.setStyle(Paint.Style.STROKE);

        mSubTextPaint = new Paint();
        mSubTextPaint.setColor(Color.WHITE);
        mSubTextPaint.setAntiAlias(true);
        mSubTextPaint.setStyle(Paint.Style.STROKE);

        setMaxTextSize(dpTopx(30));

    }


    private void setSubTextShader() {
        if (this.colors != null) {
            float textHeight = mSubTextPaint.descent() - mSubTextPaint.ascent();
            float textOffset = (textHeight / 2) - mSubTextPaint.descent();
            Rect bounds = new Rect();
            mSubTextPaint.getTextBounds("%", 0, 1, bounds);
            mSubTextPaint.setShader(new LinearGradient(0, mContentHeight / 2 + textOffset - mMaxSize / 100 * 22f, 0,
                    mContentHeight / 2 + textOffset - mMaxSize / 100 * 22f - bounds.height(), colors, positions, Shader.TileMode.CLAMP));
        } else {
            mSubTextPaint.setShader(null);
        }

    }

    private void setMarTextShader() {
        if (this.colors != null) {
            float textHeight = mMarkTextPaint.descent() - mMarkTextPaint.ascent();
            float textOffset = (textHeight / 2) - mMarkTextPaint.descent();
            Rect bounds = new Rect();
            mMarkTextPaint.getTextBounds("%", 0, 1, bounds);
            mMarkTextPaint.setShader(new LinearGradient(0, mContentHeight / 2 + textOffset - mMaxSize / 100 * 22f, 0,
                    mContentHeight / 2 + textOffset - mMaxSize / 100 * 22f - bounds.height(), colors, positions, Shader.TileMode.CLAMP));
        } else {
            mMarkTextPaint.setShader(null);
        }

    }

    private void setTextShader() {
        if (this.colors != null) {
            float textHeight = mTextPaint.descent() - mTextPaint.ascent();
            float textOffset = (textHeight / 2) - mTextPaint.descent();
            Rect bounds = new Rect();
            mTextPaint.getTextBounds("A", 0, 1, bounds);
            mTextPaint.setShader(new LinearGradient(0, mContentHeight / 2 + textOffset, 0, mContentHeight / 2 + textOffset - bounds.height(), colors, positions, Shader.TileMode.CLAMP));
        } else {
            mTextPaint.setShader(null);
        }
    }


    public void setColor(int unitColor, int numColor) {
        mSubTextPaint.setColor(unitColor);
        mTextPaint.setColor(numColor);
    }

    RectF contentRect = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mContentWidth <= 0) {
            mContentWidth = getWidth();
        }
        if (mContentHeight <= 0) {
            mContentHeight = getHeight();
        }

        if (mContentWidth == 0 || mContentHeight == 0) return;

        setTextShader();
        setSubTextShader();
        setMarTextShader();

        if (TEXT_INFOS.size() == 0) return;

        int width = getWidth();
        int height = getHeight();


        contentRect.left = (width - mContentWidth) / 2f;
        contentRect.top = (height - mContentHeight) / 2f;
        contentRect.right = contentRect.left + mContentWidth;
        contentRect.bottom = contentRect.top + mContentHeight;

        int id = canvas.save();
        float centerX = contentRect.centerX();
        float centerY = contentRect.centerY();
        canvas.translate(centerX, centerY);

        contentRect.left = -centerX;
        contentRect.right = centerX;
        contentRect.top = -centerY;
        contentRect.bottom = centerY;


        float totalTextWidth = 0l;
        int textCount = TEXT_INFOS.size();

        for (int i = 0; i < textCount; i++) {
            totalTextWidth += TEXT_INFOS.get(i).getTextWidth();
            if (i < textCount - 1) {
                totalTextWidth += textSpace;
            }
        }

        drawGuideBaseline(canvas, contentRect, totalTextWidth);

        float startOffsetX = -(totalTextWidth) / 2f;
        for (int i = 0; i < textCount; i++) {
            TEXT_INFOS.get(i).draw(canvas, startOffsetX, contentRect.centerY());
            startOffsetX += TEXT_INFOS.get(i).getTextWidth() + textSpace;
        }

        canvas.restoreToCount(id);

    }

    private void drawGuideBaseline(Canvas canvas, RectF contentRect, float totalTextWidth) {

        if (!isInEditMode()) return;

        Paint guidelinePaint = new Paint();
        guidelinePaint.setAntiAlias(true);
        guidelinePaint.setStrokeWidth(0);
        guidelinePaint.setStyle(Paint.Style.FILL);

        RectF hline = new RectF();
        hline.top = -1;
        hline.bottom = 1;
        hline.left = -totalTextWidth / 2;
        hline.right = totalTextWidth / 2;
        canvas.drawRect(hline, guidelinePaint);

        RectF vline = new RectF();
        hline.left = -1;
        vline.top = contentRect.top;
        vline.bottom = contentRect.bottom;
        vline.right = 1;

        canvas.drawRect(vline, guidelinePaint);
    }


    private static class TextInfo {
        Paint subOrSupTextPaint = null;
        String subText = null;
        String supText = null;
        Paint textPaint = null;
        String text;
        float space;

        private TextInfo(String text, String subText, String supText, Paint textPaint, Paint subOrSupTextPaint, float space) {
            this.text = text;
            if (this.text == null) {
                this.text = "";
            }
            this.subText = subText;
            this.supText = supText;
            this.space = space;
            this.textPaint = textPaint;
            this.subOrSupTextPaint = subOrSupTextPaint;
        }

        public void draw(Canvas canvas, float startX, float startY) {

            if (this.textPaint == null) {
                return;
            }

            canvas.drawText(this.text, startX, startY + getTextPaintBaseline(this.textPaint), this.textPaint);

            if (this.subOrSupTextPaint == null) {
                return;
            }
            if (this.supText != null) {
                RectF rect = new RectF();
                rect.left = startX + space + getTextWidth(this.text, this.textPaint);
                rect.top = -getTextHeight(this.textPaint) / 2;
                rect.bottom = 0;
                rect.right = rect.left + getTextWidth(supText, this.subOrSupTextPaint);
                canvas.drawText(supText, rect.left, rect.centerY() + getTextPaintBaseline(this.subOrSupTextPaint), this.subOrSupTextPaint);
            }


            if (this.subText != null) {
                RectF rect = new RectF();
                rect.left = startX + space + getTextWidth(this.text, this.textPaint);
                rect.top = 0;
                rect.bottom = getTextHeight(this.textPaint) / 2;
                rect.right = rect.left + getTextWidth(subText, this.subOrSupTextPaint);
                canvas.drawText(subText, rect.left, rect.centerY() + getTextPaintBaseline(this.subOrSupTextPaint), this.subOrSupTextPaint);
            }

        }

        /**
         * 基线到中线的距离=(Descent+Ascent)/2-Descent
         * 注意，实际获取到的Ascent是负数。公式推导过程如下：
         * 中线到BOTTOM的距离是(Descent+Ascent)/2，这个距离又等于Descent+中线到基线的距离，即(Descent+Ascent)/2=基线到中线的距离+Descent。
         */
        public static float getTextPaintBaseline(Paint p) {
            Paint.FontMetrics fontMetrics = p.getFontMetrics();
            return (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
        }


        public float getTextWidth() {

            if (textPaint == null) {
                return 0;
            }

            float width = 0;

            width = getTextWidth(this.text, textPaint);

            float subTextWidth = 0;
            if (this.subText != null && subOrSupTextPaint != null) {
                subTextWidth = getTextWidth(this.subText, subOrSupTextPaint) + space;
            }

            float supTextWidth = 0;
            if (this.supText != null && subOrSupTextPaint != null) {
                supTextWidth = getTextWidth(this.supText, subOrSupTextPaint) + space;
            }
            return width + Math.max(subTextWidth, supTextWidth);
        }


        //获取文本最小宽度（真实宽度）
        private static int getTextRealWidth(String text, Paint paint) {
            if (TextUtils.isEmpty(text)) return 0;
            Rect rect = new Rect(); // 文字所在区域的矩形
            paint.getTextBounds(text, 0, text.length(), rect);
            //获取最小矩形，该矩形紧贴文字笔画开始的位置
            return rect.width();
        }

        //获取文本最小高度（真实高度）
        private static int getTextRealHeight(String text, Paint paint) {
            if (TextUtils.isEmpty(text)) return 0;
            Rect rect = new Rect(); // 文字所在区域的矩形
            paint.getTextBounds(text, 0, text.length(), rect);
            //获取最小矩形，该矩形紧贴文字笔画开始的位置
            return rect.height();
        }

        //真实宽度 + 笔画左右两侧间隙（一般绘制的的时候建议使用这种，左右两侧的间隙和字形有关）
        private static int getTextWidth(String text, Paint paint) {
            if (TextUtils.isEmpty(text)) return 0;
            return (int) paint.measureText(text);
        }

        //真实宽度 + 笔画上下两侧间隙（符合文本绘制基线）
        private static int getTextHeight(Paint paint) {
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();
            int textHeight = ~fm.top - (~fm.top - ~fm.ascent) - (fm.bottom - fm.descent);
            return textHeight;
        }


        private static class Builder {

            Paint subOrSupTextPaint = null;
            Paint textPaint = null;
            String subText = null;
            String supText = null;
            String text;
            float space;

            public Builder(String text, Paint textPaint, Paint subOrSupTextPaint) {
                this.text = text;
                this.textPaint = textPaint;
                this.subOrSupTextPaint = subOrSupTextPaint;
            }

            public Builder subText(String subText) {
                this.subText = subText;
                return this;
            }

            public Builder supText(String supText) {
                this.supText = supText;
                return this;
            }

            public Builder textSpace(float space) {
                this.space = space;
                return this;
            }

            public TextInfo build() {
                return new TextInfo(text, this.subText, this.supText, this.textPaint, this.subOrSupTextPaint, this.space);
            }
        }

    }
    private int[] colors = new int[]{
            0xC0FFFFFF, 0x9fFFFFFF,
            0x98FFFFFF, 0xA5FFFFFF,
            0xB3FFFFFF, 0xBEFFFFFF,
            0xCCFFFFFF, 0xD8FFFFFF,
            0xE5FFFFFF, 0xFFFFFFFF};
    private float[] positions = new float[]{
            0f, 0.05f,
            0.3f, 0.4f,
            0.5f, 0.6f,
            0.7f, 0.8f,
            0.9f, 1f};

    public void setShaderColors(int[] colors) {
        this.colors = colors;
    }

    public void setShaderColors(int c) {
        this.colors = new int[]{c, c, c, c,
                c, c, c, c, c, c};
    }

}

