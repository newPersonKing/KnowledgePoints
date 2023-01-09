package com.gy.commonviewdemo.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.*
import android.util.Log
import com.gy.commonviewdemo.LogUtil

class WifiBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (intent.action === WIFI_STATE_CHANGED_ACTION) {
                when (intent.getIntExtra(EXTRA_WIFI_STATE, WIFI_STATE_UNKNOWN)) {
                    // 已关闭
                    WIFI_STATE_DISABLED -> {
//                        updateWifiStatus(6)
                    }
                    // 关闭中
                    WIFI_STATE_DISABLING -> {}
                    // 可用
                    WIFI_STATE_ENABLED -> {}
                    // 正在开启
                    WIFI_STATE_ENABLING -> {
//                        updateWifiStatus(5)
                    }
                    // 未知
                    WIFI_STATE_UNKNOWN -> {
//                        updateWifiStatus(1)
                    }
                }
            } else if (NETWORK_STATE_CHANGED_ACTION == intent.action) {
                val info: NetworkInfo = intent.getParcelableExtra(EXTRA_NETWORK_INFO)!!
                if (NetworkInfo.State.DISCONNECTED == info.state) { // wifi没连接上,连接已断开
                    LogUtil.e("连接已断开")
                } else if (NetworkInfo.State.CONNECTED == info.state) { //wifi连接上了
                    LogUtil.e("wifi连接上了")
//                    refreshWifiList()
                } else if (NetworkInfo.State.CONNECTING == info.state) { // 正在连接
                    LogUtil.e("wifi正在连接")
                }
            } else if (SCAN_RESULTS_AVAILABLE_ACTION == intent.action) {
                val success = intent.getBooleanExtra(EXTRA_RESULTS_UPDATED, false)
                if (success) {
                    WifiUtil.updateScanResult()
                } else {
                    // 获取列表失败
//                    scanFailure()
                }
                // wifi连接结果通知   WIFI连接请求状态发生改变
            } else if (SUPPLICANT_STATE_CHANGED_ACTION == intent.action) {
                // 密码错误
                val error = intent.getIntExtra(EXTRA_SUPPLICANT_ERROR, -1)
                if (error == ERROR_AUTHENTICATING) {
                    LogUtil.e("获取连接状态：密码错误")
                    // todo 连接失败
                    return
                }

                // 获取连接状态
                val supplicantState: SupplicantState? =
                    intent.getParcelableExtra(EXTRA_NEW_STATE)
                when (supplicantState) {
                    // 成功
                    SupplicantState.COMPLETED -> {
                        LogUtil.e("获取连接状态：成功")
                        // todo 连接成功
                    }
                    // 不活跃的
                    SupplicantState.INACTIVE -> {
                        LogUtil.e("获取连接状态：不活跃的")
                        // todo 连接失败
                    }
                    // 接口禁用
                    SupplicantState.INTERFACE_DISABLED -> {
                        LogUtil.e("获取连接状态：接口禁用")
                    }
                    SupplicantState.DISCONNECTED -> {
                        LogUtil.e("获取连接状态：断开连接")
                    }
                    SupplicantState.SCANNING -> {
                        LogUtil.e("获取连接状态：正在扫描")
                    }
                    SupplicantState.AUTHENTICATING -> {
                        LogUtil.e("获取连接状态：正在验证")
                    }
                    SupplicantState.ASSOCIATING -> {
                        LogUtil.e("获取连接状态：正在关联")
                    }
                    SupplicantState.ASSOCIATED -> {
                        LogUtil.e("获取连接状态：已经关联")
                    }
                    SupplicantState.FOUR_WAY_HANDSHAKE -> {
                        LogUtil.e("获取连接状态：四次握手")
                    }
                    SupplicantState.GROUP_HANDSHAKE -> {
                        LogUtil.e("获取连接状态：组握手")
                    }
                    SupplicantState.DORMANT -> {
                        LogUtil.e("获取连接状态：休眠")
                    }
                    SupplicantState.UNINITIALIZED -> {
                        LogUtil.e("获取连接状态：未初始化")
                    }
                    SupplicantState.INVALID -> {
                        LogUtil.e("获取连接状态：无效的")
                    }
                    else -> {
                        LogUtil.e("获取连接状态：wifi连接结果通知")
                    }
                }

            } else if ("android.net.conn.CONNECTIVITY_CHANGE" == intent.action) {
                LogUtil.e("网络类型切换了")
//                updateNetWorkType()
            }
        }
    }
}