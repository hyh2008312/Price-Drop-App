package com.benmu.drop.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.benmu.drop.activity.bean.AppDto;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * @author luzhenqiang
 * @desc 常用的工具类提供给H5端
 * @date 2018/6/6 下午3:09
 * @since 1.0.0
 */

public class CommonUtils extends WXModule {
    public static final String TAG = CommonUtils.class.getSimpleName();

    @JSMethod
    public void getAppVersionCode(JSCallback jsCallback) {
        PackageManager pm = mWXSDKInstance.getContext().getPackageManager();
        AppDto appInfo = new AppDto();
        if (pm != null) {
            PackageInfo pi;
            try {
                pi = pm.getPackageInfo(mWXSDKInstance.getContext().getPackageName(), 0);
                if (pi != null) {
                    appInfo.setCode(200);
                    appInfo.setVersionCode(pi.versionCode);
                    appInfo.setVersionName(pi.versionName);
                    jsCallback.invoke(appInfo);
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.d(TAG, "未发现" + mWXSDKInstance.getContext().getPackageName() + "这个包。NameNotFoundException ：", e);
                appInfo.setCode(300);
                jsCallback.invoke(appInfo);
            }
        } else {
            appInfo.setCode(300);
            jsCallback.invoke(appInfo);
        }
    }
}
