package com.gy.commonviewdemo.cusview.masic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.gy.commonviewdemo.R;

public class BitmapMosaicView extends View {
    private final DisplayMetrics mDM;
    private TextPaint mCommonPaint;
    private RectF mainRect = new RectF();
    private BitmapCanvas bitmapCanvas; //Canvas 封装的
    private BitmapCanvas srcThumbCanvas; //Canvas 封装的
    private Bitmap mBitmap; //猫图
    private RectF blockRect = new RectF(); //猫头区域

    public BitmapMosaicView(Context context) {
        this(context, null);
    }
    public BitmapMosaicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public BitmapMosaicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDM = getResources().getDisplayMetrics();
        initPaint();
    }

    private void initPaint() {
        //否则提供给外部纹理绘制
        mCommonPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mCommonPaint.setAntiAlias(true);
        mCommonPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCommonPaint.setStrokeCap(Paint.Cap.ROUND);
        mBitmap = decodeBitmap(R.mipmap.girl);

    }
    private Bitmap decodeBitmap(int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        return BitmapFactory.decodeResource(getResources(), resId, options);
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (bitmapCanvas != null && bitmapCanvas.bitmap != null && !bitmapCanvas.bitmap.isRecycled()) {
            bitmapCanvas.bitmap.recycle();
        }
        if (srcThumbCanvas != null && srcThumbCanvas.bitmap != null && !srcThumbCanvas.bitmap.isRecycled()) {
            srcThumbCanvas.bitmap.recycle();
        }
        bitmapCanvas = null;

    }

    private Rect srcRectF = new Rect();
    private Rect dstRectF = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (width < 1 || height < 1) {
            return;
        }
        if (bitmapCanvas == null || bitmapCanvas.bitmap == null) {
            bitmapCanvas = new BitmapCanvas(Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888));
        } else {
            bitmapCanvas.bitmap.eraseColor(Color.TRANSPARENT);
        }
        if (srcThumbCanvas == null || srcThumbCanvas.bitmap == null) {
            srcThumbCanvas = new BitmapCanvas(Bitmap.createBitmap(mBitmap.getWidth()/35, mBitmap.getHeight()/35, Bitmap.Config.ARGB_8888));
        } else {
            srcThumbCanvas.bitmap.eraseColor(Color.TRANSPARENT);
        }
        float radius = Math.min(width / 2f, height / 2f);

        //关闭双线性过滤
        int flags = mCommonPaint.getFlags();
        mCommonPaint.setFlags(flags &~ Paint.FILTER_BITMAP_FLAG);
        mCommonPaint.setFilterBitmap(false);
        mCommonPaint.setDither(false);


        srcRectF.set(0,0,mBitmap.getWidth(),mBitmap.getHeight());
        dstRectF.set(0,0, srcThumbCanvas.bitmap.getWidth(), srcThumbCanvas.bitmap.getHeight());

        int save = bitmapCanvas.save();
        srcThumbCanvas.drawBitmap(mBitmap, srcRectF, dstRectF, mCommonPaint);

        srcRectF.set(dstRectF);
        dstRectF.set(0,0,bitmapCanvas.bitmap.getWidth(),bitmapCanvas.bitmap.getHeight());
        bitmapCanvas.drawBitmap(srcThumbCanvas.bitmap, srcRectF,dstRectF, mCommonPaint);
        // 这个区域到时候可以自己调制，想让什么部位有马赛克那就会有马赛克
        blockRect.set(bitmapCanvas.bitmap.getWidth() - 560, 10, bitmapCanvas.bitmap.getWidth() - 100, 410);
        bitmapCanvas.restoreToCount(save);
        int saveCount = canvas.save();
        canvas.translate(width / 2f, height / 2f);
        mainRect.set(-radius, -radius, radius, radius);
        canvas.drawBitmap(bitmapCanvas.bitmap, null, mainRect, mCommonPaint);
        canvas.restoreToCount(saveCount);

    }
    static class BitmapCanvas extends Canvas {
        Bitmap bitmap;
        public BitmapCanvas(Bitmap bitmap) {
            super(bitmap);
            //继承在Canvas的绘制是软绘制，因此理论上可以绘制出阴影
            this.bitmap = bitmap;
        }
        public Bitmap getBitmap() {
            return bitmap;
        }
    }
}
