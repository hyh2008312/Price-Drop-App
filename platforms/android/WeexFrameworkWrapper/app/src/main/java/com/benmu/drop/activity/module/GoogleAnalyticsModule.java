package com.benmu.drop.activity.module;

import android.app.Activity;
import android.icu.text.BreakIterator;
import android.os.Bundle;
import android.widget.Toast;

import com.benmu.drop.SocialCommerApplication;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;


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
       // Toast.makeText(mWXSDKInstance.getContext(), screenName, Toast.LENGTH_SHORT).show();
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
      //  Toast.makeText(mWXSDKInstance.getContext(), Category + Action, Toast.LENGTH_SHORT).show();
    }
    @JSMethod
    public void facebookRecordEvent(String recordType ,String contentID, String contentType, String currency, String valueToSum) {
        AppEventsLogger logger = AppEventsLogger.newLogger(mWXSDKInstance.getContext());
        Bundle parameters = new Bundle();
        parameters.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType);
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentID);
        parameters.putString(AppEventsConstants.EVENT_PARAM_VALUE_TO_SUM, valueToSum);
        // 1,fb_mobile_content_view 2,fb_mobile_initiated_checkout 3,fb_mobile_purchase
        if (AppEventsConstants.EVENT_NAME_VIEWED_CONTENT.equals(recordType)){
            logger.logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT, parameters);
            //Toast.makeText(mWXSDKInstance.getContext(), "fb_mobile_content_view", Toast.LENGTH_SHORT).show();
        }else if (AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT.equals(recordType)){
            logger.logEvent(AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT, parameters);
            //Toast.makeText(mWXSDKInstance.getContext(), "fb_mobile_initiated_checkout", Toast.LENGTH_SHORT).show();
        }else if (AppEventsConstants.EVENT_NAME_PURCHASED.equals(recordType)){
            logger.logEvent(AppEventsConstants.EVENT_NAME_PURCHASED, parameters);
            //Toast.makeText(mWXSDKInstance.getContext(), "fb_mobile_purchase", Toast.LENGTH_SHORT).show();
        }
    }
}
