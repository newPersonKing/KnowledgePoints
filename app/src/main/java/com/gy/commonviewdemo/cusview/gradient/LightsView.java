package com.gy.commonviewdemo.cusview.gradient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.gy.commonviewdemo.R;

public class LightsView extends View {
    private final DisplayMetrics mDM;
    private TextPaint mCommonPaint;
    private Bitmap mBitmap;
    private RadialGradient radialGradientLarge = null;
    private RadialGradient radialGradientNormal = null;
    private float x;
    private float y;
    private boolean isPress = false;
    private Matrix matrix = new Matrix();
    public LightsView(Context context) {
        this(context, null);
    }

    public LightsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public LightsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDM = getResources().getDisplayMetrics();
        initPaint();
        setClickable(true); //触发hotspot
    }

    private void initPaint() {
        //否则提供给外部纹理绘制
        mCommonPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mCommonPaint.setAntiAlias(true);
        mCommonPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCommonPaint.setStrokeCap(Paint.Cap.ROUND);
        mCommonPaint.setFilterBitmap(true);
        mCommonPaint.setDither(true);
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (width < 1 || height < 1) {
            return;
        }
        if (radialGradientLarge == null) {
            radialGradientLarge = new RadialGradient(0, 0,
                    dp2px(100),
                    new int[]{Color.TRANSPARENT, 0x01ffffff, 0x33ffffff, 0x66000000, 0x77000000, 0xff000000},
                    new float[]{0.1f, 0.3f, 0.5f, 0.7f, 0.9f, 0.95f},
                    Shader.TileMode.CLAMP);
        }
        if (radialGradientNormal == null) {
            radialGradientNormal = new RadialGradient(0, 0,
                    dp2px(50),
                    new int[]{Color.TRANSPARENT, 0x01ffffff, 0x33ffffff, 0x66000000, 0x77000000, 0xff000000},
                    new float[]{0.1f, 0.3f, 0.5f, 0.7f, 0.9f, 0.95f},
                    Shader.TileMode.CLAMP);
        }

        canvas.drawBitmap(mBitmap, 0, 0, null);

        matrix.setTranslate(x, y);
        radialGradientLarge.setLocalMatrix(matrix);
        radialGradientNormal.setLocalMatrix(matrix);
        if(isPressed()) {
            mCommonPaint.setShader(radialGradientLarge);
        }else{
            mCommonPaint.setShader(radialGradientNormal);
        }
        canvas.drawPaint(mCommonPaint);
    }

    @Override
    public void dispatchDrawableHotspotChanged(float x, float y) {
        super.dispatchDrawableHotspotChanged(x, y);
        this.x = x;
        this.y = y;
        postInvalidate();
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        postInvalidate();
    }

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mDM);
    }

}

