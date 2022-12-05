package com.gy.commonviewdemo.behavior;

public interface BehaviorContentInter {

    /*content 初始偏移量*/
    float getOriginTrans();
    /*最终剩余的偏移量 都是相对于屏幕顶部*/
    float getRemainTrans();
}
