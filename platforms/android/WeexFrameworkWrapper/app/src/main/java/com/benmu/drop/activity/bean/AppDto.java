package com.benmu.drop.activity.bean;

/**
 * Created by lzq on 2018/6/6.
 */

public class AppDto {
    private int code ; // 200 成功  300 失败
    private int versionCode;
    private String versionName;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
