package com.gy.commonviewdemo.cusview.path.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.gy.commonviewdemo.R;

public class PathMoveView extends View {
    private Bitmap mBikeBitmap;
    // 圆路径
    private Path mPath;
    // 路径测量
    private PathMeasure mPathMeasure;

    // 当前移动值
    private float fraction = 0;
    private Matrix mBitmapMatrix;
    private ValueAnimator animator;
    // PathMeasure 测量过程中的坐标
    private float[] position = new float[2];
    // PathMeasure 测量过程中矢量方向与x轴夹角的的正切值
    private float[] tan = new float[2];
    private RectF rectHolder = new RectF();
    private Paint mDrawerPaint;

    public PathMoveView(Context context) {
        super(context);
        init(context);

    }

    public PathMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public PathMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        // 初始化 画笔 [抗锯齿、不填充、红色、线条2px]
        mDrawerPaint = new Paint();
        mDrawerPaint.setAntiAlias(true);
        mDrawerPaint.setStyle(Paint.Style.STROKE);
        mDrawerPaint.setColor(Color.WHITE);
        mDrawerPaint.setStrokeWidth(2);

        // 获取图片
        mBikeBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.avatar1, null);
        // 初始化矩阵
        mBitmapMatrix = new Matrix();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = 0;
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            height = (int) dp2px(120);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(getMeasuredHeight(), getMeasuredWidth());
        } else {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }

        setMeasuredDimension(getMeasuredWidth(), height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();
        if (width <= 1 || height <= 1) {
            return;
        }

        if (mPath == null) {
            mPath = new Path();
        } else {
            mPath.reset();
        }
        rectHolder.set(-100, -100, 100, 100);

        mPath.moveTo(-getWidth() / 2F, 0);
        mPath.lineTo(-(getWidth() / 2F + 200) / 2F, -400);
        mPath.lineTo(-200, 0);
        mPath.arcTo(rectHolder, 180, 180, false);
        mPath.quadTo(300, -200, 400, 0);
        mPath.lineTo(500, 0);

        if (mPathMeasure == null) {
            mPathMeasure = new PathMeasure();
            mPathMeasure.setPath(mPath, false);
        }

        int saveCount = canvas.save();
        // 移动坐标矩阵到View中间
        canvas.translate(getWidth() / 2F, getHeight() / 2F);

        // 获取 position(坐标) 和 tan(正切斜率)，注意矢量方向与x轴的夹角
        mPathMeasure.getPosTan(mPathMeasure.getLength() * fraction, position, tan);

        // 计算角度（斜率），注意矢量方向与x轴的夹角
        float degree = (float) Math.toDegrees(Math.atan2(tan[1], tan[0]));
        int bmpWidth = mBikeBitmap.getWidth();
        int bmpHeight = mBikeBitmap.getHeight();
        // 重置为单位矩阵
        mBitmapMatrix.reset();
        // 旋转单位举证，中心点为图片中心
        mBitmapMatrix.postRotate(degree, bmpWidth / 2, bmpHeight / 2);
        // 将图片中心和移动位置对齐
        mBitmapMatrix.postTranslate(position[0] - bmpWidth / 2,
                position[1] - bmpHeight / 2);


        // 画圆路径
        canvas.drawPath(mPath, mDrawerPaint);
        // 画自行车，使用矩阵旋转方向
        canvas.drawBitmap(mBikeBitmap, mBitmapMatrix, mDrawerPaint);
        canvas.restoreToCount(saveCount);
    }

    public void start() {

        if (animator != null) {
            animator.cancel();
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setDuration(6000);
        // 匀速增长
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 第一种做法：通过自己控制，是箭头在原来的位置继续运行
                fraction = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
        this.animator = valueAnimator;
    }

    public void stop() {
        if (animator == null) return;
        animator.cancel();
    }

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}

