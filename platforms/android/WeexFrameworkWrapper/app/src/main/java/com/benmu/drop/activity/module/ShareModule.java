package com.benmu.drop.activity.module;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.benmu.drop.SocialCommerApplication;
import com.benmu.drop.activity.MainActivity;
import com.benmu.drop.activity.bean.ShareBean;
import com.benmu.drop.utils.PackageManagerUtils;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;


/**
 * @author luzhenqiang
 * @desc js与android 分享之间的交互
 * @date 2018/5/24 下午2:11
 * @since 1.0
 */

public class ShareModule extends WXModule {
    private JSCallback whatsappSuccessCallback;
    private JSCallback WhatsappFailedCallback;

    @JSMethod
    public void shareFacebook(String title, String detail, String url, String imageUrl,
                              JSCallback jsSuccessCallback, JSCallback jsFailedCallback) {
        Toast.makeText(mWXSDKInstance.getContext(), "Waiting...", Toast.LENGTH_SHORT).show();
        ((MainActivity) mWXSDKInstance.getContext()).shareFacebook(title, detail, url, imageUrl, jsSuccessCallback,
                jsFailedCallback);
        SocialCommerApplication application = (SocialCommerApplication) ((Activity) mWXSDKInstance.getContext()).getApplication();
        Tracker sTracker = application.getDefaultTracker();
        sTracker.send(new HitBuilders.EventBuilder()
                .setCategory("DropStart")
                .setAction("Share to Drop the Price Further")
                .setLabel("Facebook")
                .build());
    }

    @JSMethod
    public void shareWhatsapp(String content, String imageUrl,
                              JSCallback jsSuccessCallback, JSCallback jsFailedCallback) {

        boolean isHaveWhatsapp = PackageManagerUtils.isInstallApp(mWXSDKInstance.getContext(), "com.whatsapp");
        if (!isHaveWhatsapp) {
            Toast.makeText(mWXSDKInstance.getContext(), "To share to WhatsApp, you need to download its app first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        mWXSDKInstance.getContext().startActivity(sendIntent);
        this.whatsappSuccessCallback = jsSuccessCallback;
        this.WhatsappFailedCallback = jsFailedCallback;
        ShareBean bean = new ShareBean();
        bean.setState(200);
        bean.setType("whatsapp");
        this.whatsappSuccessCallback.invoke(bean);
        SocialCommerApplication application = (SocialCommerApplication) ((Activity) mWXSDKInstance.getContext()).getApplication();
        Tracker sTracker = application.getDefaultTracker();
        sTracker.send(new HitBuilders.EventBuilder()
                .setCategory("DropStart")
                .setAction("Share to Drop the Price Further")
                .setLabel("Whatsapp")
                .build());
    }

    @JSMethod
    public void shareFacebookMessenger(String title, String detail, String buttonWord, String url, String imageUrl,
                                       JSCallback jsSuccessCallback, JSCallback jsFailedCallback) {
        ((MainActivity) mWXSDKInstance.getContext()).shareFacebookMessenger(title, detail, buttonWord, url, imageUrl, jsSuccessCallback,
                jsFailedCallback);
        SocialCommerApplication application = (SocialCommerApplication) ((Activity) mWXSDKInstance.getContext()).getApplication();
        Tracker sTracker = application.getDefaultTracker();
        sTracker.send(new HitBuilders.EventBuilder()
                .setCategory("DropStart")
                .setAction("Share to Drop the Price Further")
                .setLabel("FacebookMessenger")
                .build());
    }


}
