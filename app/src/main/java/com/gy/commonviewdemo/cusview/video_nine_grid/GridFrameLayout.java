package com.gy.commonviewdemo.cusview.video_nine_grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class GridFrameLayout extends FrameLayout {
    private Path clipPath;
    private Path tmpPath = new Path();
    private RectF clipRect;
    private Paint paint;
    //由于有的视频存在黑边，添加如下offset便于剔除黑边，保留纯画面区域
    private int offsetTop = 0;
    private int offsetBottom = 0;
    private int offsetRight = 0;
    private int offsetLeft = 0;
    private PaintFlagsDrawFilter mPaintFlagsDrawFilter;

    private int row = 3;  //行数
    private int col = 4; //列数

    private int lineWidth = 0;


    public GridFrameLayout(Context context) {
        super(context);
        init();
    }

    public GridFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GridFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        lineWidth = dpToPx(5);
    }

    public void setRow(int row) {
        this.row = row;
    }
    public void setColum(int col) {
        this.col = col;
    }
    public void setOffsetTop(int offsetTop) {
        this.offsetTop = offsetTop;
    }
    public void setOffsetBottom(int offsetBottom) {
        this.offsetBottom = offsetBottom;
    }

    public void setOffsetRight(int offsetRight) {
        this.offsetRight = offsetRight;
    }

    public void setOffsetLeft(int offsetLeft) {
        this.offsetLeft = offsetLeft;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        clipRect = null;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        DrawFilter drawFilter = canvas.getDrawFilter();

        int saveCount = canvas.save();
        //保存当前状态

        canvas.setDrawFilter(mPaintFlagsDrawFilter);
        if (clipPath == null) {
            clipPath = new Path();
        }
        if (clipRect == null) {
            clipRect = new RectF(offsetLeft, offsetTop, width - offsetRight, height - offsetBottom);
        } else {
            clipRect.set(offsetLeft, offsetTop, width - offsetRight, height - offsetBottom);
        }

        clipPath.reset();
        float radius = dpToPx(10);
        float[] radii = new float[]{
                radius, radius,
                radius, radius,
                radius, radius,
                radius, radius
        };

        clipPath.addRoundRect(clipRect, radii, Path.Direction.CCW);

        float columWidth = clipRect.width() / col;
        float rowHeight = clipRect.height() / row;

        for (int i = 1; i < col; i++) {
            tmpPath.reset();
            float position = i * columWidth - lineWidth / 2;
            tmpPath.addRect(offsetLeft + position, offsetTop, offsetLeft + position + lineWidth / 2, height - offsetBottom, Path.Direction.CCW);
            clipPath.op(tmpPath, Path.Op.DIFFERENCE);
        }
        for (int i = 1; i < row; i++) {
            tmpPath.reset();
            float position = i * rowHeight - lineWidth / 2;
            tmpPath.addRect(offsetLeft, offsetTop + position, width - offsetRight, offsetTop + position + lineWidth / 2, Path.Direction.CCW);
            clipPath.op(tmpPath, Path.Op.DIFFERENCE);
        }

        canvas.clipPath(clipPath);
        //裁剪画布，注意，这里不仅裁剪外围，内部挖空区域也会被裁剪
        //为什么在dispatchDraw中使用，因为dispatchDraw方便控制子View的绘制
        super.dispatchDraw(canvas);

        canvas.restoreToCount(saveCount);
        //恢复到之前的区域

        canvas.setDrawFilter(drawFilter);
        if (hasFocus()) {
            canvas.drawPath(clipPath, paint); //有焦点时画一个边框
        }
    }
    private int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * dps);
    }
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }
}
