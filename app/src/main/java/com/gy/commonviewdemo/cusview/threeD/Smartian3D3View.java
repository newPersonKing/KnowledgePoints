package com.gy.commonviewdemo.cusview.threeD;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Smartian3D3View extends View {

    private TextPaint mCommonPaint;
    private DisplayMetrics mDM;
    private Matrix matrix = new Matrix();
    private Camera camera = new Camera();
    double xr = Math.toRadians(5f);  //绕x轴旋转
    double yr = 0;  //绕y轴旋转;
    double zr = 0;
    private List<Point> pointList = new ArrayList<>();
    private Random random = new Random();
    private List<Bitmap> bitmaps = new ArrayList<>();

    public Smartian3D3View(Context context) {
        this(context, null);
    }

    public Smartian3D3View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mDM = getResources().getDisplayMetrics();
        //否则提供给外部纹理绘制
        mCommonPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mCommonPaint.setAntiAlias(true);
        mCommonPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCommonPaint.setStrokeCap(Paint.Cap.ROUND);
        mCommonPaint.setFilterBitmap(true);
        mCommonPaint.setDither(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = mDM.widthPixels / 2;
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = widthSize / 2;
        }

        setMeasuredDimension(widthSize, heightSize);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        if (width < 1) return;
        int height = getHeight();
        if (height < 1) return;

        float radius = Math.min(width, height) / 3f;
        int save = canvas.save();
        canvas.translate(width / 2f, height / 2f);


        if (pointList.isEmpty()) {

            int max = 20;
            for (int i = 0; i < max; i++) {
                //均匀排列
                double v = -1.0 + (2.0 * i - 1.0) / max;
                if (v < -1.0) {
                    v = 1.0f;
                }
                float delta = (float) Math.acos(v);
                float alpha = (float) (Math.sqrt(max * Math.PI) * delta);

                Point point = new Point();
                point.x = (float) (radius * Math.cos(alpha) * Math.sin(delta));
                point.y = (float) (radius * Math.sin(alpha) * Math.sin(delta));
                point.z = (float) (radius * Math.cos(delta));
                point.color = argb(random.nextFloat(), random.nextFloat(), random.nextFloat());
                pointList.add(point);
                Bitmap bitmap = Bitmap.createBitmap((int) (radius/2f), (int) (radius/2), Bitmap.Config.ARGB_8888);
                bitmaps.add(bitmap);

            }
        }


        for (int i = 0; i < pointList.size(); i++) {

            Point point = pointList.get(i);

            rotateX(point,xr);
            rotateY(point,yr);
            rotateZ(point,zr);

            // 透视除法，z轴向内的方向
            float scale = (radius + point.z) / (2 * radius);
            point.scale = Math.max(scale, 0.35f);
        }

        mCommonPaint.setStyle(Paint.Style.STROKE);
        mCommonPaint.setColor(Color.GRAY);
        canvas.drawCircle(0,0,radius,mCommonPaint);
        mCommonPaint.setStyle(Paint.Style.FILL);

        //排序，先画背面的，再画正面的
        Collections.sort(pointList, comparator);

        for (int i = 0; i < pointList.size(); i++) {

            int saveCount = canvas.save();
            Point point = pointList.get(i);

            Canvas bitmapCanvas = new Canvas(bitmaps.get(i));
            int saveBitmapCount = bitmapCanvas.save();
            bitmaps.get(i).eraseColor(Color.TRANSPARENT);
            mCommonPaint.setARGB((int) (255 * point.scale), Color.red(point.color),Color.green(point.color),Color.blue(point.color));
            float circleR = Math.min(bitmaps.get(i).getWidth()/2f,bitmaps.get(i).getHeight()/2f)* point.scale;
            bitmapCanvas.drawCircle(bitmaps.get(i).getWidth()/2f,bitmaps.get(i).getHeight()/2f,circleR  ,mCommonPaint);
            bitmapCanvas.restoreToCount(saveBitmapCount);

            float positionX = point.x;
            float positionY = point.y ;

            double rx = Math.sqrt(radius * radius - positionX * positionX);
            float rotationX = -(float) (Math.asin(positionY / rx));
            float rotationY = -(float) (Math.asin(-positionX / radius));

            matrix.reset();
            camera.save();
            //先旋转X，再旋转Y，顺序不能变
            camera.rotateX((float) Math.toDegrees(rotationX));
            camera.rotateY((float) Math.toDegrees(rotationY));
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-bitmaps.get(i).getWidth()/2f, - bitmaps.get(i).getWidth()/2f);
            matrix.postTranslate(point.x , point.y );
            // 旋转单位矩阵，中心点为图片中心
            canvas.drawBitmap(bitmaps.get(i),matrix,mCommonPaint);
            canvas.restoreToCount(saveCount);

        }
        canvas.restoreToCount(save);
        postInvalidateDelayed(32);

    }

    private void rotateZ(Point point, double zr) {

        // 绕Z轴旋转,乘以Z轴的旋转矩阵

        float x = point.x;
        float y = point.y;
        float z = point.z;

        point.x = (float) (x * Math.cos(zr) + y * -Math.sin(zr));
        point.y = (float) (x * Math.sin(zr) + y * Math.cos(zr));
        point.z = z;
    }

    private void rotateY(Point point, double yr) {
        //绕Y轴旋转，乘以Y轴的旋转矩阵
        float x = point.x;
        float y = point.y;
        float z = point.z;

        point.x = (float) (x * Math.cos(yr) + z * Math.sin(yr));
        point.y = y;
        point.z = (float) (x * -Math.sin(yr) + z * Math.cos(yr));
    }

    private void rotateX(Point point, double xr) {
        //绕X轴旋转，乘以X轴的旋转矩阵
        float x = point.x;
        float y = point.y;
        float z = point.z;

        point.x = x;
        point.y = (float) (y * Math.cos(xr) + z * -Math.sin(xr));
        point.z = (float) (y * Math.sin(xr) + z * Math.cos(xr));
    }

    Comparator comparator = new Comparator<Point>() {
        @Override
        public int compare(Point left, Point right) {
            if (left.scale - right.scale > 0) {
                return 1;
            }
            if (left.scale == right.scale) {
                return 0;
            }
            return -1;
        }
    };

    static class Point {
        private int color;
        private float x;
        private float y;
        private float z;

        private float scale = 1f;
    }


    public static int argb(float red, float green, float blue) {
        return ((int) (1 * 255.0f + 0.5f) << 24) |
                ((int) (red * 255.0f + 0.5f) << 16) |
                ((int) (green * 255.0f + 0.5f) << 8) |
                (int) (blue * 255.0f + 0.5f);
    }

}
