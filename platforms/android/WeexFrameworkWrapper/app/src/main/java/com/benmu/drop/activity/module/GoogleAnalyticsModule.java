package com.benmu.drop.activity.module;

import android.app.Activity;
import android.widget.Toast;

import com.benmu.drop.SocialCommerApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

import static android.R.attr.name;

/**
 * google Analytics for web
 * Created by lzq on 2018/6/13.
 */

public class GoogleAnalyticsModule extends WXModule {
    /**
     * @desc 统计每个屏幕
     * @date 2018/6/13
     */
    @JSMethod
    public void trackingScreen(String screenName) {
        SocialCommerApplication application = (SocialCommerApplication) ((Activity) mWXSDKInstance.getContext()).getApplication();
        Tracker sTracker = application.getDefaultTracker();
        sTracker.setScreenName(screenName);
        sTracker.send(new HitBuilders.ScreenViewBuilder().build());
        Toast.makeText(mWXSDKInstance.getContext(), screenName, Toast.LENGTH_SHORT).show();
    }

    /**
     * @desc 事件打点
     * @date 2018/6/14
     */
    @JSMethod
    public void recordEvent(String Category, String Action, String Label, long Value) {
        SocialCommerApplication application = (SocialCommerApplication) ((Activity) mWXSDKInstance.getContext()).getApplication();
        Tracker sTracker = application.getDefaultTracker();
        sTracker.send(new HitBuilders.EventBuilder()
                .setCategory(Category)
                .setAction(Action)
                .setLabel(Label)
                .setValue(Value)
                .build());
        Toast.makeText(mWXSDKInstance.getContext(), Category + Action, Toast.LENGTH_SHORT).show();
    }
}
