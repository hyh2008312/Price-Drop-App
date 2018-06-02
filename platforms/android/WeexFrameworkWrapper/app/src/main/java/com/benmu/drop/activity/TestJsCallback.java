package com.benmu.drop.activity;

import android.app.Activity;
import android.widget.Toast;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

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
        /*try {
            Intent vIt = new Intent("android.intent.action.SEND");
            vIt.setPackage("com.whatsapp");
            vIt.setType("text/plain");
            vIt.putExtra(Intent.EXTRA_TEXT, content + " " + url);
            mWXSDKInstance.getContext().startActivity(vIt);
        } catch (Exception ex) {
        }*/
        Toast.makeText(mWXSDKInstance.getContext(), "diaoyong", Toast.LENGTH_SHORT).show();
        UMWeb  web = new UMWeb("https://www.google.com");
        web.setTitle("Facebook share test title");//标题
        web.setThumb(new UMImage(mWXSDKInstance.getContext(),"https://media.socialcommer.com/source/web/pic/logo-20180427.png"));  //缩略图
        web.setDescription("my description my description my description my description");//描述
        new ShareAction((Activity) mWXSDKInstance.getContext())
                .setPlatform(SHARE_MEDIA.FACEBOOK)//传入平台
                .withMedia(web)
                .withText("Hey! I just found this item and need your help to drop the price before it sells out:  \n" +
                        "\n" +
                        "XXXXXXXXXXX (产品名字)\n" +
                        "Rs. 10.00  (原价划掉)\n" +
                        "\n" +
                        "13520 people have got their favorite items at the lowest price on PriceDrop! Join me and save big together. ")
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Toast.makeText(mWXSDKInstance.getContext(), "开始", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Toast.makeText(mWXSDKInstance.getContext(), "成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Toast.makeText(mWXSDKInstance.getContext(), "错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Toast.makeText(mWXSDKInstance.getContext(), "取消", Toast.LENGTH_SHORT).show();
                    }
                })//回调监听器
                .share();

        /*new ShareAction((Activity) mWXSDKInstance.getContext()).withText("hello")
                .setDisplayList(SHARE_MEDIA.FACEBOOK)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                }).open();*/
    }
}
