package com.gy.commonviewdemo.cusview.path.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.gy.commonviewdemo.R;

public class CirclePathArrowView extends View implements ValueAnimator.AnimatorUpdateListener {

    private static final boolean IS_DEBUG = true;
    private final Paint mPathPaint;
    private Bitmap arrowBitmap = null;
    private ValueAnimator animator;
    private float degree = 0;
    private float phase = 0;
    private float speed = dp2px(1);
    private float offsetDegree = 5;  //补偿角度

    public CirclePathArrowView(Context context) {
        this(context, null);
    }

    public CirclePathArrowView(Context context,  AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePathArrowView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPathPaint.setAntiAlias(true);
        mPathPaint.setFilterBitmap(false);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(dp2px(1));
        mPathPaint.setColor(0xaaffffff);

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

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private RectF rectF = new RectF();
    private Path path = new Path();
    private PaintFlagsDrawFilter paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private Matrix matrix = new Matrix();

    private Bitmap bmp;
    Canvas canvasBitmap;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2F;
        float centerY = getHeight() / 2F;

        float radius = Math.min(getWidth(), getHeight()) / 3F - mPathPaint.getStrokeWidth();

        mPathPaint.setPathEffect(new DashPathEffect(new float[]{40, 20}, phase));

        path.reset();
        path.addCircle(centerX, centerY, radius, Path.Direction.CCW);
        canvas.drawPath(path, mPathPaint);

        canvas.setDrawFilter(paintFlagsDrawFilter);
        if (arrowBitmap != null && !arrowBitmap.isRecycled()) {
            double radians = Math.toRadians(degree);

            float bmpCenterX = arrowBitmap.getWidth() / 2F;
            float bmpCenterY = arrowBitmap.getHeight() / 2F;
            float arrowRadius = Math.max(bmpCenterX, bmpCenterY);

            float x = (float) (radius * Math.cos(radians) + centerX);
            float y = (float) (radius * Math.sin(radians) + centerY);


            //这里变幻图像，主要解决2个问题：【1】原图不是正方形，【2】原图变幻问题
            if(bmp == null) {
                bmp = Bitmap.createBitmap((int) arrowRadius * 2, (int) arrowRadius * 2, Bitmap.Config.ARGB_8888);
                canvasBitmap = new Canvas(bmp);
            }
            bmp.eraseColor(Color.TRANSPARENT);

            //矩阵变幻以图片本身左上角为坐标原点，而不是Canvas坐标，因此使用Matrix

            matrix.reset();
            //预处理，移动原图坐标系，让原图中心点对齐bmp中心点，计算x，y方向的偏移量
            float dx = arrowRadius * 2 - bmpCenterX * 2;
            float dy = arrowRadius * 2 - bmpCenterY * 2;
            matrix.preTranslate(dx, dy);

            //预处理，在新坐标系中，找到坐标原点到旋转中心的偏移量
            float pX = arrowRadius - dx; //px,py 也是偏移量，不是绝对坐标
            float pY = arrowRadius - dx;
            matrix.preRotate(degree + 90 + offsetDegree, pX, pY);
            canvasBitmap.drawBitmap(arrowBitmap, matrix, mPathPaint);

            if (IS_DEBUG) {
                canvas.drawBitmap(arrowBitmap, matrix, mPathPaint);
            }

            rectF.left = x - arrowRadius;
            rectF.right = x + arrowRadius;
            rectF.top = y - arrowRadius;
            rectF.bottom = y + arrowRadius;
            int color = mPathPaint.getColor();
            mPathPaint.setColor(Color.MAGENTA);

            //将新图会知道矩形区域
            canvas.drawBitmap(bmp, null, rectF, null);

            canvas.drawRect(rectF, mPathPaint);
            mPathPaint.setColor(color);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (arrowBitmap == null || arrowBitmap.isRecycled()) {
            arrowBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lemon);
        }
        if (animator != null) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(0, 360).setDuration(5000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(this);
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (arrowBitmap != null) {
            arrowBitmap.recycle();
            arrowBitmap = null;
        }
        if (animator != null) {
            animator.cancel();
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        degree = (float) animation.getAnimatedValue();
        invalidate();
        phase += speed;
        if (phase > Integer.MAX_VALUE) {
            phase = phase % speed;
        }
    }
}
