package com.gy.commonviewdemo.cusview.gradient;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * 流光动画
 */
public class FlowingLightView extends View {

    private Paint mPaint;
    private Path mPath;
    private LinearGradient mLinearGradient;
    private ValueAnimator mValueAnimator;

    public FlowingLightView(Context context) {
        this(context, null);
    }

    public FlowingLightView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowingLightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPath = new Path();
    }

    private void initPointAndAnimator(int w, int h) {
        Point point1 = new Point(0, 0);
        Point point2 = new Point(w, 0);
        Point point3 = new Point(w, h);
        Point point4 = new Point(0, h);

        mPath.moveTo(point1.x, point1.y);
        mPath.lineTo(point2.x, point2.y);
        mPath.lineTo(point3.x, point3.y);
        mPath.lineTo(point4.x, point4.y);
        mPath.close();

        // 斜率k
        float k =  1f * h / w;
        // 偏移
        float offset = 1f * w / 8;
        // 0f - offset * 2 为数值左边界（屏幕外左侧）， w + offset * 2为数值右边界（屏幕外右侧）
        // 目的是使光影走完一遍，加一些时间缓冲，不至于每次光影移动的间隔都那么急促
        mValueAnimator = ValueAnimator.ofFloat(0f - offset/2, w + offset/2);
        mValueAnimator.setRepeatCount(-1);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setDuration(8000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mLinearGradient = new LinearGradient(value, k * value, value + 10, k * (value + 10),
                        new int[]{Color.parseColor("#00FFE5EE"), Color.parseColor("#FFE5EE"), Color.parseColor("#00FFE5EE")}, null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                invalidate();
            }
        });

        mValueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initPointAndAnimator(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mValueAnimator.cancel();
    }
}
