package com.gy.commonviewdemo.wifi

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.*
import android.os.Build
import androidx.annotation.RequiresApi
import com.gy.commonviewdemo.LogUtil


object WifiUtil {
    private lateinit var application: Application
    var scanResults = mutableListOf<ScanResult>()
        private set
    val mWifiManager by lazy {
        application.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }
    fun init(application: Application){
        this.application = application
    }

    fun openWifi(){
        mWifiManager.isWifiEnabled = true
    }

    fun startScan(){
        mWifiManager.startScan()
    }

    fun updateScanResult(){
//        this.scanResults = mWifiManager.scanResults
//        scanResults.forEach {
//            LogUtil.e(it.toString())
//        }
        refreshWifiList()
        scanResults.forEach {
            LogUtil.e(it.SSID)
        }
    }

    @SuppressLint("MissingPermission")
    fun connectWifi(context: Context, scanResults: List<ScanResult>,  ssid: String, connect: (success: Boolean, scanResult: ScanResult) -> Unit) {
        if (!mWifiManager.isWifiEnabled) {
            return
        }
        val scanResult = scanResults.singleOrNull { it.SSID == ssid }
        if (scanResult != null) {
            mWifiManager.configuredNetworks.forEach {
                LogUtil.e("configuredNetworks===${it.SSID}")
            }
            val config = mWifiManager.configuredNetworks.singleOrNull { it.SSID.replace("\"", "") == ssid }
            if (config != null && config.status != WifiConfiguration.Status.DISABLED) {
                LogUtil.e("找到了历史wifi:${scanResult.SSID}")
                connect.invoke(mWifiManager.enableNetwork(config.networkId, true), scanResult)
            } else {
                val type = getCipherType(scanResult.capabilities)
                if (type == WifiCapability.WIFI_CIPHER_NO_PASS) {
                    // 没找到历史wifi
                    val padWifiNetwork =
                        createWifiConfig(
                            scanResult.SSID,
                            type = getCipherType(scanResult.capabilities))
                    val netId = mWifiManager.addNetwork(padWifiNetwork)
                    LogUtil.e("不需要密码连接wifi:${scanResult.SSID}")
                    connect.invoke(mWifiManager.enableNetwork(netId, true), scanResult)
                } else {
                    LogUtil.e("需要密码连接wifi:${scanResult.SSID}")
                    connect.invoke(false, scanResult)
                }
            }
        } else {
            LogUtil.e("connectWifi 没有找到")
        }
    }

    @SuppressLint("MissingPermission")
    fun createWifiConfig(
        ssid: String,
        password: String = "",
        type: WifiCapability
    ): WifiConfiguration {
        //初始化WifiConfiguration
        val config = WifiConfiguration()
        config.allowedAuthAlgorithms.clear()
        config.allowedGroupCiphers.clear()
        config.allowedKeyManagement.clear()
        config.allowedPairwiseCiphers.clear()
        config.allowedProtocols.clear()

        //指定对应的SSID
        config.SSID = "\"" + ssid + "\""

        //如果之前有类似的配置
        val tempConfig = mWifiManager.configuredNetworks.singleOrNull { it.SSID == "\"$ssid\"" }
        if (tempConfig != null) {
            //则清除旧有配置  不是自己创建的network 这里其实是删不掉的
            val isDisable = mWifiManager.disableNetwork(tempConfig.networkId)
            val isRemove = mWifiManager.removeNetwork(tempConfig.networkId)
            val isSave = mWifiManager.saveConfiguration()
            LogUtil.e("清除wifi配置:${tempConfig.SSID + (isDisable && isRemove && isSave)}")
        }


        
        //不需要密码的场景
        if (type == WifiCapability.WIFI_CIPHER_NO_PASS) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            //以WEP加密的场景
        } else if (type == WifiCapability.WIFI_CIPHER_WEP) {
            config.hiddenSSID = true
            config.wepKeys[0] = "\"" + password + "\""
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            config.wepTxKeyIndex = 0
            //以WPA加密的场景
        } else if (type == WifiCapability.WIFI_CIPHER_WPA) {
            config.preSharedKey = "\"" + password + "\""
            config.hiddenSSID = true
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
            config.status = WifiConfiguration.Status.ENABLED
        }
        return config
    }

    fun getCipherType(capabilities: String): WifiCapability {
        return when {
            capabilities.contains("WEB") -> {
                WifiCapability.WIFI_CIPHER_WEP
            }
            capabilities.contains("PSK") -> {
                WifiCapability.WIFI_CIPHER_WPA
            }
            capabilities.contains("WPS") -> {
                WifiCapability.WIFI_CIPHER_NO_PASS
            }
            else -> {
                WifiCapability.WIFI_CIPHER_NO_PASS
            }
        }
    }

    enum class WifiCapability {
        WIFI_CIPHER_WEP, WIFI_CIPHER_WPA, WIFI_CIPHER_NO_PASS
    }

    //wifi强度获取
    fun getNetworkWifiLevel(wifiInfo: ScanResult): Int {
        //获得信号强度值
        val level = wifiInfo.level
        //根据获得信号的强度发送信息
        return if (level <= 0 && level >= -50) { //最强
            5
        }
        else if (level < -50 && level >= -70) { //较强
            4
        }
        else if (level < -70 && level >= -80) { //较弱
            3
        }
        else if (level < -80 && level >= -100) { //微弱
            2
        }
        else {
            1
        }
    }

    fun refreshWifiList() {
//        if (!mWifiManager.isWifiEnabled) {
//            return
//        }

        scanResults.clear()

        // 原始wifi列表
        val originList = mWifiManager.scanResults
        // 当前wifi
        val currentWifi = originList.singleOrNull {
            it.BSSID == mWifiManager.connectionInfo.bssid &&
                    mWifiManager.connectionInfo.supplicantState == SupplicantState.COMPLETED
        }


        // 添加wifi到列表
        for ((i, _) in originList.withIndex()) {

            // 该热点SSID是否已在列表中
            val position: Int = getItemPosition(scanResults, originList[i])
            // 过滤没有名字的，没有意义
            if (originList[i].SSID.isNotEmpty()) {
                if (position != -1) { // 已在列表
                    // 相同SSID热点，取信号强的
                    if (scanResults[position].level < originList[i].level) {
                        scanResults.removeAt(position)
                        scanResults.add(position, originList[i])
                    }
                } else {
                    scanResults.add(originList[i])
                }
            }

            // 按信号强度排序
            scanResults.sortWith(Comparator { t1, t2 -> t2.level - t1.level })
        }

        // 置顶当前连接的wifi
        var p = -1
        currentWifi?.let {
            for ((i, value) in scanResults.withIndex()) {
                if (currentWifi.BSSID == value.BSSID) {
                    p = i
                }
            }
//            updateWifiName()
        }

        if (p != -1 && currentWifi != null) {
            scanResults.removeAt(p)
            scanResults.add(0, currentWifi)
//            mWifiAdapter.connectPosition = 0
        } else {
//            mWifiAdapter.connectPosition = -1
        }
//        mWifiAdapter.notifyDataSetChanged()

        // 处于连接中的wifi，成功了才切换状态
//        if (mLastStatus != 2 || (mLastStatus == 2 && currentWifi != null)) {
//            if (mWifiList.isNotEmpty()) {
//                updateWifiStatus(1)
//            } else {
//                updateWifiStatus(4)
//            }
//        }
//
//        // 刷新列表
//        updateListOpenOrNot()
    }

    /// 该热点SSID是否已在列表中
    private fun getItemPosition(saveList:List<ScanResult>,item:ScanResult):Int{
        val index = saveList.indexOfFirst {
            it.SSID == item.SSID
        }
        return index
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun connectWifiUp10(scanResult: ScanResult,pwd:String){
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkSpecifier = WifiNetworkSpecifier.Builder()
            .setSsid(scanResult.SSID)
            .setWpa2Passphrase(pwd)
            .build()
        val networkRequest =  NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .setNetworkSpecifier(networkSpecifier)
            .build();

        cm.requestNetwork(networkRequest,object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                LogUtil.e("连接成功")
            }

            override fun onUnavailable() {
                super.onUnavailable()
                LogUtil.e("连接失败")
            }
        })

    }
}