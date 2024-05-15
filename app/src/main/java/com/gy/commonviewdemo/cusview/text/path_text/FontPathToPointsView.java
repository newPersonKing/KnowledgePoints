package com.gy.commonviewdemo.cusview.text.path_text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.text.TextPaint;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FontPathToPointsView extends View {

    private TextPaint mTextPaint;
    private DisplayMetrics mDM;

    private String text = "母亲节快乐";
    private float measureTextWidth;
    float[] hslColor = new float[3];

    private final Map<String,FontText> textPoints = new ArrayMap<String,FontText>();

    private Paint.FontMetrics fm = new Paint.FontMetrics();
    private TextPaint mPaint;

    private void initPaint() {
        mDM = getResources().getDisplayMetrics();
        //否则提供给外部纹理绘制
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setStrokeCap(Paint.Cap.ROUND);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(sp2px(50));

        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(5f);
    }

    public FontPathToPointsView(Context context) {
        super(context);
    }

    public FontPathToPointsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FontPathToPointsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        textPoints.clear();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (text == null) {
            return;
        }

        if (textPoints.isEmpty()) {
            measureTextWidth = mTextPaint.measureText(text);
            for (int i = 0; i < text.length(); i++) {
                String word = text.substring(i, i + 1);
                textPoints.put(word,new FontText(i,textPathToPoints(word, mTextPaint),randomColor()));
            }
        }


        if (textPoints.isEmpty()) {
            return;
        }
        int height = getHeight();
        int width = getWidth();

        float halfOfTextWidth = measureTextWidth / 2f; //计算中心点一半的长度

        float baseline = getTextPaintBaseline(mTextPaint); //计算BaseLine
        int count = canvas.save();
        canvas.translate(width / 2f, height / 2f);  //平移到View中心点


        float spanSize = measureTextWidth / textPoints.size();
        int finishCount = 0;  //统计完成绘制的文字总数

        for (Map.Entry<String,FontText> entry : textPoints.entrySet()){

            FontText textPoint = entry.getValue();
            int size = textPoint.currentSize;
            int pointSize = textPoint.pointFS.size();
            float offset = textPoint.index * spanSize; //文字X轴方向的偏移

            mPaint.setColor(textPoint.color);
            for (int i = 0; i < size; i++) {
                PointF pointF = textPoint.pointFS.get(i);
                //绘制点
                canvas.drawPoint(pointF.x - halfOfTextWidth + offset, pointF.y + baseline, mPaint);
            }
            textPoint.currentSize = Math.min(++size,pointSize);
            if(textPoint.currentSize == pointSize){
                finishCount++; // 当前绘制到的位置和pointSize
            }
        }
        canvas.restoreToCount(count);

        if(finishCount == textPoints.size()){
            //所有的文字都完成绘制的，过1s之后重新绘制
            for (Map.Entry<String,FontText> entry : textPoints.entrySet()){
                entry.getValue().currentSize = 0;
            }
            postInvalidateDelayed(1000);
        }else {
            postInvalidateDelayed(16);
        }

    }

    public static List<PointF> textPathToPoints(String text, TextPaint paint) {
        Path fontPath = new Path();
        paint.getTextPath(text, 0, text.length(), 0f, paint.getFontSpacing(), fontPath);
        fontPath.close();
        PathMeasure pathMeasure = new PathMeasure(fontPath, false);
        List<PointF> points = new ArrayList<>();
        float[] pos = new float[2];
        do {
            float distance = 0f;
            while (distance < pathMeasure.getLength()) {
                distance += 5f;
                pathMeasure.getPosTan(distance, pos, null);
                points.add(new PointF(pos[0], pos[1]));
            }
        } while (pathMeasure.nextContour());
        return points;
    }

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mDM);
    }

    public float sp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, mDM);
    }

    public float getTextPaintBaseline(Paint p) {
        p.getFontMetrics(fm);
        Paint.FontMetrics fontMetrics = fm;
        return (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
    }

    static class FontText{
        int index; //当前文字在Text中的索引
        int currentSize; //用户控制绘制到什么位置
        int color; // 颜色
        List<PointF> pointFS; // 点位
        public FontText(int index,List<PointF> textPathToPoints,int color) {
            this.pointFS = textPathToPoints;
            this.index = index;
            this.color = color;
        }
    }

    private int randomColor() {
        hslColor[0] = (float) (Math.random() * 360);
        hslColor[1] = 0.5f;
        hslColor[2] = 0.5f;
        return HSLToColor(hslColor);
    }

    @ColorInt
    static int HSLToColor(float[] hsl) {
        final float h = hsl[0];
        final float s = hsl[1];
        final float l = hsl[2];

        final float c = (1f - Math.abs(2 * l - 1f)) * s;
        final float m = l - 0.5f * c;
        final float x = c * (1f - Math.abs((h / 60f % 2f) - 1f));

        final int hueSegment = (int) h / 60;

        int r = 0, g = 0, b = 0;

        switch (hueSegment) {
            case 0:
                r = Math.round(255 * (c + m));
                g = Math.round(255 * (x + m));
                b = Math.round(255 * m);
                break;
            case 1:
                r = Math.round(255 * (x + m));
                g = Math.round(255 * (c + m));
                b = Math.round(255 * m);
                break;
            case 2:
                r = Math.round(255 * m);
                g = Math.round(255 * (c + m));
                b = Math.round(255 * (x + m));
                break;
            case 3:
                r = Math.round(255 * m);
                g = Math.round(255 * (x + m));
                b = Math.round(255 * (c + m));
                break;
            case 4:
                r = Math.round(255 * (x + m));
                g = Math.round(255 * m);
                b = Math.round(255 * (c + m));
                break;
            case 5:
            case 6:
                r = Math.round(255 * (c + m));
                g = Math.round(255 * m);
                b = Math.round(255 * (x + m));
                break;
        }

        r = constrain(r, 0, 255);
        g = constrain(g, 0, 255);
        b = constrain(b, 0, 255);

        return Color.rgb(r, g, b);
    }

    private static int constrain(int amount, int low, int high) {
        return amount < low ? low : Math.min(amount, high);
    }
}
