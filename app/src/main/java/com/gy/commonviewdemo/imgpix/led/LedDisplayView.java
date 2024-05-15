package com.gy.commonviewdemo.imgpix.led;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LedDisplayView extends View {
    private final DisplayMetrics mDM;
    private TextPaint mGridPaint;
    private TextPaint mCommonPaint;
    private List<IDrawer> drawers = new ArrayList<>();
    private Bitmap brushBitmap = null;
    private float padding = 2; //分界线大小
    private float squareWidth = 5;  //网格大小
    private List<Rect> gridRects = new ArrayList<>();
    int[] sampleColors = null;
    private Canvas brushCanvas = null;

    public LedDisplayView(Context context) {
        this(context, null);
    }

    public LedDisplayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LedDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDM = getResources().getDisplayMetrics();
        initPaint();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

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


    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mDM);
    }

    public float sp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, mDM);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (brushBitmap != null && !brushBitmap.isRecycled()) {
            brushBitmap.recycle();
        }
        brushBitmap = null;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (width <= padding || height <= padding) {
            return;
        }

        if (brushBitmap == null) {
            brushBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            brushCanvas = new Canvas(brushBitmap);
        }

        for (int i = 0; i < drawers.size(); i++) {
            int saveCount = brushCanvas.save();
            drawers.get(i).draw(brushCanvas, width, height, mCommonPaint);
            brushCanvas.restoreToCount(saveCount);
        }


        float blockWidth = (squareWidth + padding);
        int w = width;
        int h = height;
        int columNum = (int) Math.ceil(w / blockWidth);
        int rowNum = (int) Math.ceil(h / blockWidth);

        if (gridRects.isEmpty() && squareWidth > 1f) {
            //通过rowNum * columNum方式降低时间复杂度
            for (int i = 0; i < rowNum * columNum; i++) {

                int col = i % columNum;
                int row = (i / columNum);

                Rect rect = new Rect();
                rect.left = (int) (col * blockWidth);
                rect.top = (int) (row * blockWidth);
                rect.right = (int) (col * blockWidth + squareWidth);
                rect.bottom = (int) (row * blockWidth + squareWidth);
                //记录网格点
                gridRects.add(rect);
            }

        }
        int color = mGridPaint.getColor();

        //这里是重点 ,LED等可以看作一只灯泡，灯泡区域要么全亮，要们全不亮
        for (int i = 0; i < gridRects.size(); i++) {
            Rect rect = gridRects.get(i);

            if (brushBitmap.getWidth() <= rect.right) {
                continue;
            }
            if (brushBitmap.getHeight() <= rect.bottom) {
                continue;
            }

            if (sampleColors == null) {
                sampleColors = new int[9];
            }

            //取7个点采样，纯粹是为了性能考虑，如果想要更准确的颜色，可以多采样几个点

            sampleColors[0] = brushBitmap.getPixel(rect.left, rect.top);  // left-top
            sampleColors[1] = brushBitmap.getPixel(rect.right, rect.top); // right-top
            sampleColors[2] = brushBitmap.getPixel(rect.right, rect.bottom); // right-bottom
            sampleColors[3] = brushBitmap.getPixel(rect.left, rect.bottom); // left-bottom
            sampleColors[4] = brushBitmap.getPixel(rect.left + rect.width() / 2, rect.top + rect.height() / 2); //center

            sampleColors[5] = brushBitmap.getPixel(rect.left + rect.width() / 2, rect.top + rect.height() / 4);  //top line
            sampleColors[6] = brushBitmap.getPixel(rect.left + rect.width() * 3 / 4, rect.top + rect.height() / 2); //right line
            sampleColors[7] = brushBitmap.getPixel(rect.left + rect.width() / 4, rect.top + rect.height() / 2); // left line
            sampleColors[8] = brushBitmap.getPixel(rect.left + rect.width() / 2, rect.top + rect.height() * 3 / 4);  // bottom line

            int alpha = 0;
            int red = 0;
            int green = 0;
            int blue = 0;
            int num = 0;

            for (int c : sampleColors) {
                if (c == Color.TRANSPARENT) {
                    //剔除全透明的颜色，必须剔除
                    continue;
                }
                int alphaC = Color.alpha(c);
                if (alphaC <= 0) {
                    //剔除alpha为0的颜色，当然可以改大一点，防止降低清晰度
                    continue;
                }
                alpha += alphaC;
                red += Color.red(c);
                green += Color.green(c);
                blue += Color.blue(c);
                num++;
            }

            if (num < 1) {
                continue;
            }

            //求出平均值
            int rectColor = Color.argb(alpha / num, red / num, green / num, blue / num);
            if (rectColor != Color.TRANSPARENT) {
                mGridPaint.setColor(rectColor);
                //   canvas.drawRect(rect, mGridPaint);  //绘制矩形
                canvas.drawCircle(rect.centerX(), rect.centerY(), squareWidth / 2, mGridPaint);  //绘制圆
            }
        }
        mGridPaint.setColor(color);

    }


    private void initPaint() {
        // 实例化画笔并打开抗锯齿
        mGridPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mGridPaint.setAntiAlias(true);
        mGridPaint.setColor(Color.LTGRAY);
        mGridPaint.setStyle(Paint.Style.FILL);
        mGridPaint.setStrokeCap(Paint.Cap.ROUND);  //否则网格绘制

        //否则提供给外部纹理绘制
        mCommonPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mCommonPaint.setAntiAlias(true);
        mCommonPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCommonPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    public void addDrawer(IDrawer drawer) {
        if (drawer == null) return;
        this.drawers.add(drawer);
        gridRects.clear();
        postInvalidate();
    }

    public void removeDrawer(IDrawer drawer) {
        if (drawer == null) return;
        this.drawers.remove(drawer);
        gridRects.clear();
        postInvalidate();
    }

    public void clearDrawer() {
        this.drawers.clear();
        gridRects.clear();
        postInvalidate();
    }

    public List<IDrawer> getDrawers() {
        return new ArrayList<>(drawers);
    }

    public interface IDrawer {
        void draw(Canvas canvas, int width, int height, Paint paint);
    }

}

