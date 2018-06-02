package com.benmu.drop;

import android.app.Application;

import com.benmu.framework.BMInitConfig;
import com.benmu.framework.BMWXApplication;
import com.benmu.framework.BMWXEngine;
import com.umeng.commonsdk.UMConfigure;

/**
 * @author luzhenqiang
 * @desc
 * @date 2018/5/22 下午3:55
 * @since 1.0.0
 */

public class SocialCommerApplication extends BMWXApplication {
    public Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initUmengSDK();

    }

    private void initUmengSDK() {
        UMConfigure.init(this, "5b03c2fca40fa3604400007f", "google", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(true);
    }

}
