package com.gy.commonviewdemo.cusview.masic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.gy.commonviewdemo.R;

public class TilesView extends View {
    private final DisplayMetrics mDM;
    private TextPaint mCommonPaint;
    private RectF mainRect = new RectF();
    private BitmapCanvas bitmapCanvas; //Canvas 封装的
    private Bitmap[] mBitmaps;
    private RectF dstRect = new RectF();
    Path path = new Path();
    private float blockWidth = 50f;
    private int xPosition = -2;
    private int index = 0;
    private boolean isTicking = false;
    public TilesView(Context context) {
        this(context, null);
    }
    public TilesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TilesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDM = getResources().getDisplayMetrics();
        initPaint();
    }

    private void initPaint() {
        //否则提供给外部纹理绘制
        mCommonPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mCommonPaint.setAntiAlias(true);
        mCommonPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCommonPaint.setStrokeCap(Paint.Cap.ROUND);
        mCommonPaint.setFilterBitmap(true);
        mCommonPaint.setDither(true);
        mBitmaps = new Bitmap[3];
        mBitmaps[0] = decodeBitmap(R.mipmap.girl);
        mBitmaps[1] = decodeBitmap(R.mipmap.avatar1);
        mBitmaps[2] = decodeBitmap(R.mipmap.avatar1);
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
        bitmapCanvas = null;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (width < 1 || height < 1) {
            return;
        }
        if (bitmapCanvas == null || bitmapCanvas.bitmap == null || bitmapCanvas.bitmap.isRecycled()) {
            bitmapCanvas = new BitmapCanvas(Bitmap.createBitmap(mBitmaps[index].getWidth(), mBitmaps[index].getHeight(), Bitmap.Config.ARGB_8888));
        }
        int nextIndex = (index + 1) % mBitmaps.length;
        canvas.drawBitmap(mBitmaps[nextIndex],0,0,mCommonPaint);

        int col = (int) Math.ceil(mBitmaps[index].getWidth() / blockWidth);
        int row = (int) Math.ceil(mBitmaps[index].getHeight() / blockWidth);
        mCommonPaint.setStyle(Paint.Style.FILL);

        //   path.reset();
//        for (int x = 0; x < row; x++) {
//            for (int y = 0; y < col; y++) {
//                gridRectF.set(x * blockWidth, y * blockWidth, x * blockWidth + blockWidth, y * blockWidth + blockWidth);
//                canvas.drawRect(gridRectF, mCommonPaint);
//                path.addRect(gridRectF, Path.Direction.CCW);
//            }
//        }

        diagonalEffect(col,row,xPosition,path);
        canvas.drawBitmap(bitmapCanvas.bitmap, 0, 0, mCommonPaint);

        if (isTicking && xPosition >= 0 && xPosition < col * 2) {
            clockTick();
        } else if(isTicking){
            xPosition = -1;
            index = nextIndex;
            isTicking = false;
        }
    }

    private void diagonalEffect(int col, int row, int xPosition,Path path) {
        int x = xPosition;
        int y = 0;
        while (x >= 0 && y <= row) {
            if (x < col && y < row) {
                dstRect.set((int) (x * blockWidth), (int) (y * blockWidth), (int) (x * blockWidth + blockWidth), (int) (y * blockWidth + blockWidth));
                //  bitmapCanvas.drawBitmap(mBitmaps[index], dstRect, dstRect, mCommonPaint);
                path.addRect(dstRect, Path.Direction.CCW);  //加入网格分片
            }
            x--;
            y++;
        }
        int save = bitmapCanvas.save();
        mCommonPaint.setShader(new BitmapShader(mBitmaps[index], Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        bitmapCanvas.drawPath(path,mCommonPaint);
        bitmapCanvas.restoreToCount(save);

    }

    public void tick() {
        isTicking = true;
        xPosition = -1;
        path.reset();
        clockTick();
    }

    private void clockTick() {
        xPosition += 1;
        postInvalidateDelayed(16);
    }


    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mDM);
    }

    public float sp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, mDM);
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

