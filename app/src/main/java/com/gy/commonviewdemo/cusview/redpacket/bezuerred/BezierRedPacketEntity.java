package com.gy.commonviewdemo.cusview.redpacket.bezuerred;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.gy.commonviewdemo.cusview.redpacket.BezierEvaluator;


public class BezierRedPacketEntity {
    public float x, y;
    public float rotation;
    public int duration = 500;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int index;
    private boolean isRecycle = false;
    boolean isCanDraw = false;

    public BezierRedPacketEntity(int startX, int startY, int endX, int endY,int index) {
        rotation = (float) Math.random() * 180 - 90;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.index = index;
    }
    private ValueAnimator animator;//属性动画，用该动画来不断改变红包下落的坐标值

    public void initAnimation(){
        if(animator !=null){
            animator.cancel();
            animator.removeAllListeners();
        }
        if(isRecycle)return;
        duration = (int) (1000 + 1000 * Math.random());
        int ctr1X = (int) (startX + (endX - startX) * Math.random());
        int ctr1Y = (int) (startY + (endY - startY) * Math.random());

        int ctr2X = (int) (startX + (endX - startX) * Math.random());
        int ctr2Y = (int) (startY + (endY - startY) * Math.random());

        BezierEvaluator evaluator = new BezierEvaluator(new PointF(ctr1X, ctr1Y),new PointF(ctr2X,ctr2Y));
        //指定动画移动轨迹
        animator = ValueAnimator.ofObject(evaluator,
                new PointF(startX, startY),//起始点
                new PointF(endX, endY));//结束点
        //每次动画更新的时候，更新红包下落的坐标值
        animator.addUpdateListener(animation -> {
            PointF pointF = (PointF) animation.getAnimatedValue();
            x = pointF.x;
            y = pointF.y;
            isCanDraw = true;
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                initAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        //属性动画无限循环
//        animator.setRepeatCount(ValueAnimator.INFINITE);
        //属性值线性变换
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(duration);
        animator.setStartDelay(100 * (index % 10));
        animator.start();
        isCanDraw = false;
    }

    public void startAnimation(){
        isRecycle = false;
        initAnimation();
    }

    /**
     * 回收图片
     */
    public void recycle() {
        isRecycle = true;
        animator.cancel();
        animator = null;
        x = startX;
        y = startY;
    }
}