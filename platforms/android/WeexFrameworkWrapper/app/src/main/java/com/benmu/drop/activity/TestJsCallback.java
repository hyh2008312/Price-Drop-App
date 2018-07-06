package com.benmu.drop.activity;

import android.app.Activity;
import android.widget.Toast;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * Created by lzq on 2018/5/15.
 */

public class TestJsCallback extends WXModule {
    @JSMethod
    public void printLog(String msg, final JSCallback jsCallback) {
        Toast.makeText(mWXSDKInstance.getContext(), msg, Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
                String ddd = "android方法正在被调用了哦";
                jsCallback.invoke(ddd);
            }
        }).start();
    }

    @JSMethod
    public void shareToWhatsApp(String url, String content) {
    }
}
