package com.benmu.drop.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by lzq on 2018/6/11.
 */

public class PackageManagerUtils {
    public static boolean isInstallApp(Context mContext, String packageClassName) {
        PackageManager pm = mContext.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageClassName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}

