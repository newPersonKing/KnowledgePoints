package com.gy.commonviewdemo.cusview.redpacket.bezuerred;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.gy.commonviewdemo.R;
import java.util.ArrayList;
import java.util.function.Consumer;

// 曲线红包 指定开始结束位置 按曲线运动
public class BezierRedPacket extends View {
    private int[] mImgIds = new int[]{
            R.mipmap.red_packet
    };//红包图片
    private int count;//红包数量
    private int mWidth;//view宽度
    private int mHeight; // view高度

    private Paint paint;//画笔
    private ArrayList<BezierRedPacketEntity> redpacketlist = new ArrayList<>();//红包数组
    private Bitmap bitmap;

    private ValueAnimator animator;
    public BezierRedPacket(Context context) {
        super(context);
        init();
    }

    public BezierRedPacket(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RedPacketStyle);
        count = typedArray.getInt(R.styleable.RedPacketStyle_count, 20);
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
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        this.bitmap = decodeBitmapFromResource(mImgIds[0]);
        animator = ValueAnimator.ofInt(0,1);//结束点
        animator.setDuration(10000);
        animator.addUpdateListener(animation -> invalidate());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                stopRainNow();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }



    /**
     *停止动画
     */
    public void stopRainNow() {
        //清空红包数据
        clear();
        if(animator.isRunning()){
            animator.cancel();
        }
        //重绘
        invalidate();
    }


    /**
     * 开始动画
     */
    public void startRain() {
        //清空红包数据
        clear();
        //添加红包
        setRedPacketCount(count);
        animator.start();
    }

    public void setRedPacketCount(int count) {
        if (mImgIds == null || mImgIds.length == 0)
            return;
        for (int i = 0; i < count; ++i) {
            BezierRedPacketEntity redPacket = new BezierRedPacketEntity(mWidth/2, mHeight/2, mWidth,0,i);
            //添加进入红包数组
            redpacketlist.add(redPacket);
            redPacket.startAnimation();
        }
    }

    //在这个方法里我们对图片做处理
    private Bitmap decodeBitmapFromResource(int imgId){

        Log.d("log","bitmap原始图片内存大小："+BitmapFactory.decodeResource(getResources(), imgId).getAllocationByteCount());
        //获取  BitmapFactory.Options的实例
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig= Bitmap.Config.RGB_565;
        //inJustDecodeBounds参数设为true并加载图片。
        //这样做的原因是inJustDecodeBounds为true时
        //BitmapFactory只会解析图片的原始宽高信息，并不会真正的加载图片。
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),imgId, options);
        Log.d("log","原始图片的宽width：" +options.outWidth+"原始图片的高height："+options.outHeight);
//        Log.d("log","bitmap原始图片内存大小："+ bitmap.getAllocationByteCount());
        //原始图片的宽高已经存放在options中。options.outWidth和options.outHeight
        //我们可以用它去做一些判断，得到我们想要的采样率
        options.inSampleSize = calculateInSampleSize(options,80,80);
        //得到采样率后，将BitmapFacpry.Options的inSampleSize参数设为false并重新加载图片。
        //此时是真正的加载图片。
        options.inJustDecodeBounds =false;
        Bitmap newbitmap=BitmapFactory.decodeResource(getResources(),imgId,options);
        Log.d("log","压缩后图片的宽width：" +options.outWidth+"压缩后图片的高height："+options.outHeight);
        Log.d("log","bitmap压缩后图片内存大小："+ newbitmap.getByteCount());
        return  BitmapFactory.decodeResource(getResources(),imgId,options);
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
     * 清空红包数据，并回收红包中的bitmap
     */
    private void clear() {
        for (BezierRedPacketEntity redPacket :redpacketlist) {
            redPacket.recycle();
        }
        redpacketlist.clear();
    }



    @Override
    protected void onDraw(final Canvas canvas) {
        //遍历红包数组，绘制红包
        for (int i = 0; i < redpacketlist.size(); i++) {
            BezierRedPacketEntity  redPacket = redpacketlist.get(i);
            if(redPacket.isCanDraw){
                //将红包旋转redPacket.rotation角度后 移动到（redPacket.x，redPacket.y）进行绘制红包
                Matrix m = new Matrix();
                m.postRotate(redPacket.rotation);
                m.postTranslate(redPacket.x, redPacket.y);
                //绘制红包
                canvas.drawBitmap(bitmap, m, paint);
            }
        }
    }
}

