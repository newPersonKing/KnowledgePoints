package com.gy.commonviewdemo.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.gy.commonviewdemo.R;

public class BehaviorContentLinearLayout extends LinearLayout implements BehaviorContentInter {

    float origintras = 0;
    float remainTrans = 0;
    public BehaviorContentLinearLayout(Context context) {
        this(context,null);
    }

    public BehaviorContentLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        if(attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BehaviorContentLinearLayout);
            origintras = typedArray.getDimension(R.styleable.BehaviorContentLinearLayout_behavior_origin_trans,0);
            remainTrans = typedArray.getDimension(R.styleable.BehaviorContentLinearLayout_behavior_remain_trans,0);
            typedArray.recycle();
        }

        //设置view的原始偏移量
        setTranslationY(getOriginTrans());
    }

    public BehaviorContentLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*view的初始偏移量*/
    @Override
    public float getOriginTrans() {
        return origintras;
    }

    @Override
    public float getRemainTrans() {
        return remainTrans;
    }
}
