package com.gy.commonviewdemo.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


/*监听的实现了 CoordinatorLayout内实现了NestedScrollingChild接口的子View的滑动状态变化*/
public class JDDetailBehavior extends CoordinatorLayout.Behavior<View> {

    float realMove = 0;
    /*这俩构造函数必须有*/
    public JDDetailBehavior(Context context){
        this(context,null);
    }

    public JDDetailBehavior(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    /*因为父布局是CoordinatorLayout 一般通过设置translate 来设置位置*/
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                  @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

        BehaviorContentInter realChild = (BehaviorContentInter) child;
        float originTras = realChild.getOriginTrans();
        float remainTrans = realChild.getRemainTrans();

        if((dy>0 && child.getTranslationY() == remainTrans) || (dy<0 && child.getTranslationY() == originTras)){
            return;
        }

        realMove += dy;

        if(dy > 0){
            /*向上滑动 大于阈值 交给子view 自己处理*/
            if(Math.abs(realMove) >= originTras - remainTrans){
                child.setTranslationY(remainTrans);
                realMove = originTras - remainTrans;
                consumed[1] = dy;
                return;
            }
        }else{
            /*向下滑动 偏移量变成0 交给子view 处理*/
            if(realMove <= 0){
                child.setTranslationY(originTras);
                realMove = 0;
                consumed[1] = dy;
                return;
            }
        }
        /*child 的实际偏移量*/
        float trasy =  child.getTranslationY() - dy;
        child.setTranslationY(trasy);
        consumed[1] = dy;
    }

    /*代表是否处理此次滑动 也必须重写*/
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return child instanceof BehaviorContentInter;
    }
}
