package com.gy.commonviewdemo.sharedpreferences;

import android.os.Handler;
import android.os.Message;

public class SpCompatCallback implements Handler.Callback {


    public SpCompatCallback(){
    }

    //handleServiceArgs
    private static final int SERVICE_ARGS = 115;
    //handleStopService
    private static final int STOP_SERVICE = 116;
    //handleSleeping
    private static final int SLEEPING = 137;
    //handleStopActivity
    private static final int STOP_ACTIVITY_SHOW = 103;
    //handleStopActivity
    private static final int STOP_ACTIVITY_HIDE = 104;
    //handlePauseActivity
    private static final int PAUSE_ACTIVITY = 101;
    //handlePauseActivity
    private static final int PAUSE_ACTIVITY_FINISHING = 102;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case SERVICE_ARGS:
                SpHelper.beforeSpBlock("SERVICE_ARGS");
                break;
            case STOP_SERVICE:
                SpHelper.beforeSpBlock("STOP_SERVICE");
                break;
            case SLEEPING:
                SpHelper.beforeSpBlock("SLEEPING");
                break;
            case STOP_ACTIVITY_SHOW:
                SpHelper.beforeSpBlock("STOP_ACTIVITY_SHOW");
                break;
            case STOP_ACTIVITY_HIDE:
                SpHelper.beforeSpBlock("STOP_ACTIVITY_HIDE");
                break;
            case PAUSE_ACTIVITY:
                SpHelper.beforeSpBlock("PAUSE_ACTIVITY");
                break;
            case PAUSE_ACTIVITY_FINISHING:
                SpHelper.beforeSpBlock("PAUSE_ACTIVITY_FINISHING");
                break;
            default:
                break;
        }
        return false;
    }
}