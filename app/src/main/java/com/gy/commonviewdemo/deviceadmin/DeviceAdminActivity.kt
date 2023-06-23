package com.gy.commonviewdemo.deviceadmin

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.LogUtil
import java.lang.reflect.Method

class DeviceAdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val manager =  getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
//启动第三方组件
        val componentName =  ComponentName(this, DeviceManageReceiver::class.java)
// 判断是否为设备管理器
        if (manager.isAdminActive(componentName)) {
            LogUtil.e("isDeviceAdminActive 已经激活")
        } else {
            LogUtil.e("isDeviceAdminActive 未激活")
            //如果不是，则构建一个intent，action参数的意思为添加一个设备管理者
            val intent =  Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            startActivity(intent);
        }

        val dpm = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager

//        mRefSetActiveAdmin(componentName,true)
    }

    private fun mRefSetActiveAdmin(policyReceiver:ComponentName,  refreshing:Boolean) {
        val dpm  =  getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val refDPM = dpm.javaClass
        try {
            val methods = refDPM.declaredMethods;
            var refSetActiveAdmin:Method?  = null;
            for ( method in methods) {
                if(method.name.equals("setActiveAdmin")){
                    if(method.parameterTypes.size == 2){
                        refSetActiveAdmin = method;//Tips 为什么要用遍历的方式获取，因为用普通的参数类型方式无法获取到，这个情况遇到很多次了，明明包含该方法但就是无法获取到，有大神可以解释一下么。
                        break;
                    }

                }
            }

            refSetActiveAdmin!!.setAccessible(true);
            refSetActiveAdmin!!.invoke(dpm, policyReceiver, refreshing);
        }  catch ( e:Exception) {
            e.printStackTrace();
        }
    }
}