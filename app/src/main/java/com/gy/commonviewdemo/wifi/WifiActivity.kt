package com.gy.commonviewdemo.wifi

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.gy.commonviewdemo.LogUtil
import com.gy.commonviewdemo.R
import com.gy.commonviewdemo.camera.Constants.Companion.REQUEST_CAMERA
import kotlinx.android.synthetic.main.activity_wifi.*

class WifiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)
        WifiUtil.openWifi()
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }else {
            WifiUtil.startScan()
        }

        btn_connect.setOnClickListener {
            connectWifi(WifiUtil.scanResults.first {
                it.SSID == "RMWiFi"
            })
        }
    }

    private val receiver = WifiBroadcastReceiver()
    override fun onStart() {
        super.onStart()
        val filter =  IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)

        /*wifi连接状态监听*/
        filter.addAction(SUPPLICANT_STATE_CHANGED_ACTION)// wifi连接结果
        filter.addAction(WIFI_STATE_CHANGED_ACTION) // wifi开启关闭状态
        filter.addAction(NETWORK_STATE_CHANGED_ACTION)// wifi连接状态
        filter.addAction(SCAN_RESULTS_AVAILABLE_ACTION)// 监听wifi列表变化（开启一个热点或者关闭一个热点）
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")//

        registerReceiver(receiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                WifiUtil.startScan()
            }else {
                Toast.makeText(this, "同意权限才可以正常使用功能", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun connectWifi(item: ScanResult) {
        if (item.BSSID == WifiUtil.mWifiManager.connectionInfo.bssid) {
            Toast.makeText(this,"连接错误",Toast.LENGTH_SHORT).show()
            return
        }

        WifiUtil.connectWifi(this, WifiUtil.scanResults, item.SSID) { success, scanResult ->
            if (success) {
                // 这个时候找到wifi，开始连接
            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    WifiUtil.connectWifiUp10(scanResult,"")
                }else {
                    // 未成功找到wifi添加，尝试输入密码
                    val padWifiNetwork =
                        WifiUtil.createWifiConfig(
                            scanResult.SSID,
                            "Chuangxin001", //用户主动输入密码
                            WifiUtil.getCipherType(scanResult.capabilities)
                        )

                    val netId = WifiUtil.mWifiManager.addNetwork(padWifiNetwork)
                    val success2 = WifiUtil.mWifiManager.enableNetwork(netId, true)

                    if (success2) {
                        LogUtil.e("这个时候找到wifi，开始连Lo{success2}")
                        //todo 连接成功 等待连接成功的状态通知
                    } else {
                        LogUtil.e("创建连接失败")
                        Toast.makeText(this,"创建连接失败",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}