package com.benmu.drop.utils;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.benmu.drop.R;
import com.benmu.drop.activity.bean.AppDto;
import com.benmu.drop.service.UpdateService;
import com.benmu.framework.utils.PermissionUtils;
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

    @JSMethod
    public void updateAndroidApp(String apkUrl) {
        final String GOOGLE_PLAY = "com.android.vending";//这里对应的是谷歌商店，跳转别的商店改成对应的即可
        try {
            Uri uri = Uri.parse("market://details?id=com.socialcommer.wx");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage(GOOGLE_PLAY);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mWXSDKInstance.getContext().startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(mWXSDKInstance.getContext(),"Don't discover Google play!", Toast.LENGTH_SHORT).show();
        }
    }
}
