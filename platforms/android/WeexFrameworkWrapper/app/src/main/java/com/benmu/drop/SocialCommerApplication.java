package com.benmu.drop;

import android.app.Application;

import com.benmu.framework.BMWXApplication;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * @author luzhenqiang
 * @desc
 * @date 2018/5/22 下午3:55
 * @since 1.0.0
 */

public class SocialCommerApplication extends BMWXApplication {
    public Application mInstance;
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initGoogleAnalytics();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

    /**
     * 初始化google analytics
     */
    private void initGoogleAnalytics() {
        sAnalytics = GoogleAnalytics.getInstance(this);
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
            sTracker.enableExceptionReporting(true);
        }

        return sTracker;
    }

}
