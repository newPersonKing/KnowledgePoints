package com.gy.commonviewdemo.cusview.redpacket;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.gy.commonviewdemo.R;

import java.util.ArrayList;

public class RedPacketTest extends View {
    private int[] mImgIds = new int[]{
            R.drawable.cat
    };//红包图片
    private int count;//红包数量
    private int speed;//下落速度
    private float maxSize;//红包大小的范围
    private float minSize;//红包大小的范围
    private int mWidth;//view宽度
    private ValueAnimator animator;//属性动画，用该动画来不断改变红包下落的坐标值

    private Paint paint;//画笔
    private long prevTime;
    private ArrayList<RedPacket> redpacketlist = new ArrayList<>();//红包数组

    public RedPacketTest(Context context) {
        super(context);
        init();
    }

    public RedPacketTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RedPacketStyle);
        count = typedArray.getInt(R.styleable.RedPacketStyle_count, 20);
        speed = typedArray.getInt(R.styleable.RedPacketStyle_speed, 20);
        minSize = typedArray.getFloat(R.styleable.RedPacketStyle_min_size, 0.5f);
        maxSize = typedArray.getFloat(R.styleable.RedPacketStyle_max_size, 1.2f);
        typedArray.recycle();
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setAntiAlias(true);
        animator = ValueAnimator.ofFloat(0, 1);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        initAnimator();
    }

    private void initAnimator() {
        //每次动画更新的时候，更新红包下落的坐标值
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                long nowTime = System.currentTimeMillis();
                float secs = (float) (nowTime - prevTime) / 1000f;
                prevTime = nowTime;
                for (int i = 0; i < redpacketlist.size(); ++i) {
                    RedPacket  redPacket = redpacketlist.get(i);
                    //更新红包的下落的位置y
                    redPacket.y += (redPacket.speed * secs);

                    //如果y坐标大于view的高度 说明划出屏幕，y重新设置起始位置，以及中奖属性
                    if (redPacket.y > getHeight()) {
                        redPacket.y = 0 - redPacket.height;
                        redPacket.isRealRed = redPacket.isRealRedPacket();
                    }
                    //更新红包的旋转的角度
                    redPacket.rotation = redPacket.rotation
                            + (redPacket.rotationSpeed * secs);
                }
                invalidate();
            }
        });
        //属性动画无限循环
        animator.setRepeatCount(ValueAnimator.INFINITE);
        //属性值线性变换
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(2000);
    }


    /**
     *停止动画
     */
    public void stopRainNow() {
        //清空红包数据
        clear();
        //重绘
        invalidate();
        //动画取消
        animator.cancel();
    }


    /**
     * 开始动画
     */
    public void startRain() {
        //清空红包数据
        clear();
        //添加红包
        setRedpacketCount(count);
        prevTime = System.currentTimeMillis();
        //动画开始
        animator.start();

    }

    public void setRedpacketCount(int count) {
        if (mImgIds == null || mImgIds.length == 0)
            return;
        for (int i = 0; i < count; ++i) {
            //获取红包原始图片
            Bitmap originalBitmap = decodeBitmapFromResource();
            //生成红包实体类
            RedPacket redPacket = new RedPacket(getContext(), originalBitmap, speed,maxSize,minSize,mWidth);
            //添加进入红包数组
            redpacketlist.add(redPacket);
        }
    }

    //在这个方法里我们对图片做处理
    private Bitmap decodeBitmapFromResource(){

        Log.d("log","bitmap原始图片内存大小："+BitmapFactory.decodeResource(getResources(), R.drawable.cat).getAllocationByteCount());
        //获取  BitmapFactory.Options的实例
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig= Bitmap.Config.RGB_565;
        //inJustDecodeBounds参数设为true并加载图片。
        //这样做的原因是inJustDecodeBounds为true时
        //BitmapFactory只会解析图片的原始宽高信息，并不会真正的加载图片。
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.cat, options);
        Log.d("log","原始图片的宽width：" +options.outWidth+"原始图片的高height："+options.outHeight);
//        Log.d("log","bitmap原始图片内存大小："+ bitmap.getAllocationByteCount());
        //原始图片的宽高已经存放在options中。options.outWidth和options.outHeight
        //我们可以用它去做一些判断，得到我们想要的采样率
        options.inSampleSize = calculateInSampleSize(options,100,100);
        //得到采样率后，将BitmapFacpry.Options的inSampleSize参数设为false并重新加载图片。
        //此时是真正的加载图片。
        options.inJustDecodeBounds =false;
        Bitmap newbitmap=BitmapFactory.decodeResource(getResources(),R.drawable.cat,options);
        Log.d("log","压缩后图片的宽width：" +options.outWidth+"压缩后图片的高height："+options.outHeight);
        Log.d("log","bitmap压缩后图片内存大小："+ newbitmap.getByteCount());
        return  BitmapFactory.decodeResource(getResources(),R.drawable.cat,options);
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * 暂停红包雨
     */
    public void pauseRain() {
        animator.cancel();
    }

    /**
     * 重新开始
     */
    public void restartRain() {
        animator.start();
    }

    /**
     * 清空红包数据，并回收红包中的bitmap
     */
    private void clear() {
        for (RedPacket redPacket :redpacketlist) {
            redPacket.recycle();
        }
        redpacketlist.clear();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取自定义view的宽度
        mWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        //遍历红包数组，绘制红包
        for (int i = 0; i < redpacketlist.size(); i++) {
            RedPacket  redPacket = redpacketlist.get(i);
            //将红包旋转redPacket.rotation角度后 移动到（redPacket.x，redPacket.y）进行绘制红包
            Matrix m = new Matrix();
            m.setTranslate(-redPacket.width / 2, -redPacket.height / 2);
            m.postRotate(redPacket.rotation);
            m.postTranslate(redPacket.width / 2 + redPacket.x, redPacket.height / 2 + redPacket.y);
            //绘制红包
            canvas.drawBitmap(redPacket.bitmap, m, paint);
        }
        Paint cPaint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(200,200,200,cPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                //根据点击的坐标点，判断是否点击在红包的区域
                RedPacket redPacket = isRedPacketClick(motionEvent.getX(), motionEvent.getY());
                if (redPacket != null) {
                    //如果点击在红包上，重新设置起始位置，以及中奖属性
                    redPacket.y = 0 - redPacket.height;
                    redPacket.isRealRed = redPacket.isRealRedPacket();
                    if (onRedPacketClickListener != null) {
                        onRedPacketClickListener.onRedPacketClickListener(redPacket);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    //根据点击坐标点，遍历红包数组，看是否点击在红包上
    private RedPacket isRedPacketClick(float x, float y) {
        for (int i = redpacketlist.size() - 1; i >= 0; i --) {
            if (redpacketlist.get(i).isContains(x, y)) {
                return redpacketlist.get(i);
            }
        }
        return null;
    }

    public interface OnRedPacketClickListener {
        void onRedPacketClickListener(RedPacket redPacket);
    }

    private OnRedPacketClickListener onRedPacketClickListener;
    public void setOnRedPacketClickListener(OnRedPacketClickListener onRedPacketClickListener) {
        this.onRedPacketClickListener = onRedPacketClickListener;
    }
}

