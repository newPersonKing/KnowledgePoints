package com.gy.commonviewdemo.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.PowerManager
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.LogUtil
import com.gy.commonviewdemo.R

class SensorTypeProximity : AppCompatActivity(), SensorEventListener {
    private var mWakeLock: PowerManager.WakeLock? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_proximity)
        val manager =  getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // 获取距离传感器
        val sensor = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        // 注册监听
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        val powerManager = getSystemService(Context.POWER_SERVICE) as? PowerManager
        if (powerManager != null) {
            mWakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "cs:phoneCallActivityTag")
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        LogUtil.e("distance===${event?.values?.get(0)}")
        LogUtil.e("it.sensor?.maximumRange===${event?.sensor?.maximumRange}")
        event?.also {
            var mNearFace = it.values[0] < it.sensor?.maximumRange ?: 0f
            if (mNearFace) {
                turnOnProximitySensor()
            } else {
                turnOffProximitySensor()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
         LogUtil.e("accuracy===$accuracy")
    }

    private fun turnOnProximitySensor() {
        if (mWakeLock != null && mWakeLock?.isHeld == false) {
            mWakeLock?.acquire()
        }
    }

    private fun turnOffProximitySensor() {
        if (mWakeLock != null && mWakeLock?.isHeld == true) {
            mWakeLock?.release(0)
        }
    }
}