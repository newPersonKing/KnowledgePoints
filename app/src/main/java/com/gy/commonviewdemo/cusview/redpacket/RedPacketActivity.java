package com.gy.commonviewdemo.cusview.redpacket;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.gy.commonviewdemo.R;
import com.gy.commonviewdemo.cusview.redpacket.bezuerred.BezierRedPacket;

public class RedPacketActivity extends AppCompatActivity implements View.OnClickListener {
    private BezierRedPacket redRainView1;
    private Button start, stop;
    private TextView money;
    private int totalmoney = 0;
    AlertDialog.Builder ab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.red_rain);
        ab = new AlertDialog.Builder(RedPacketActivity.this);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        money = (TextView) findViewById(R.id.money);
        redRainView1 = (BezierRedPacket) findViewById(R.id.red_packets_view1);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start) {
            startRedRain();
        } else if (v.getId() == R.id.stop) {
            stopRedRain();
        }
    }

    /**
     * 开始下红包雨
     */
    private void startRedRain() {
        redRainView1.startRain();
    }

    /**
     * 停止下红包雨
     */
    private void stopRedRain() {
        totalmoney = 0;//金额清零
        redRainView1.stopRainNow();
    }
}
