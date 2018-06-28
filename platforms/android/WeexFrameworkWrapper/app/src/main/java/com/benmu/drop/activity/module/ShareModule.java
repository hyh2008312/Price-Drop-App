package com.benmu.drop.activity.module;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.benmu.drop.SocialCommerApplication;
import com.benmu.drop.activity.bean.ShareBean;
import com.benmu.drop.utils.PackageManagerUtils;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;


/**
 * @author luzhenqiang
 * @desc js与android 分享之间的交互
 * @date 2018/5/24 下午2:11
 * @since 1.0
 */

public class ShareModule extends WXModule {
    private JSCallback facebookSuccessCallback;
    private JSCallback facebookFailedCallback;

    private JSCallback facebookMessengerSuccessCallback;
    private JSCallback facebookMessengerFailedCallback;

    private JSCallback whatsappSuccessCallback;
    private JSCallback WhatsappFailedCallback;

    @JSMethod
    public void shareFacebook(String title, String detail, String Url, String imageUrl,
                              JSCallback jsSuccessCallback, JSCallback jsFailedCallback) {
        this.facebookSuccessCallback = jsSuccessCallback;
        this.facebookFailedCallback = jsFailedCallback;
        UMWeb web = new UMWeb(Url);
        web.setTitle(title);//标题
        web.setThumb(new UMImage(mWXSDKInstance.getContext(),imageUrl));  //缩略图
        web.setDescription(detail);//描述
        new ShareAction((Activity) mWXSDKInstance.getContext())
                .setPlatform(SHARE_MEDIA.FACEBOOK)//传入平台
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Toast.makeText(mWXSDKInstance.getContext(), "start", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        ShareBean bean = new ShareBean();
                        bean.setState(200);
                        bean.setType("facebook");
                        facebookSuccessCallback.invoke(bean);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ShareBean bean = new ShareBean();
                        bean.setState(100);
                        bean.setType("facebook");
                        facebookFailedCallback.invoke(bean);
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ShareBean bean = new ShareBean();
                        bean.setState(100);
                        bean.setType("facebook");
                        facebookFailedCallback.invoke(bean);
                    }
                })//回调监听器
                .share();

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

        boolean isHaveWhatsapp = PackageManagerUtils.isInstallApp(mWXSDKInstance.getContext(),"com.whatsapp");
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
        whatsappSuccessCallback.invoke(bean);
        /* new ShareAction((Activity) mWXSDKInstance.getContext())
                .setPlatform(SHARE_MEDIA.WHATSAPP)//传入平台
                .withText(content)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Toast.makeText(mWXSDKInstance.getContext(), "waiting", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        ShareBean bean = new ShareBean();
                        bean.setState(200);
                        bean.setType("whatsapp");
                        whatsappSuccessCallback.invoke(bean);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ShareBean bean = new ShareBean();
                        bean.setState(100);
                        bean.setType("whatsapp");
                        WhatsappFailedCallback.invoke(bean);
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ShareBean bean = new ShareBean();
                        bean.setState(100);
                        bean.setType("whatsapp");
                        WhatsappFailedCallback.invoke(bean);
                        Toast.makeText(mWXSDKInstance.getContext(), "cancel", Toast.LENGTH_SHORT).show();
                    }
                })//回调监听器
                .share();*/
        SocialCommerApplication application = (SocialCommerApplication) ((Activity) mWXSDKInstance.getContext()).getApplication();
        Tracker sTracker = application.getDefaultTracker();
        sTracker.send(new HitBuilders.EventBuilder()
                .setCategory("DropStart")
                .setAction("Share to Drop the Price Further")
                .setLabel("Whatsapp")
                .build());
    }

    @JSMethod
    public void shareFacebookMessenger(String title, String detail, String Url, String imageUrl,
                              JSCallback jsSuccessCallback, JSCallback jsFailedCallback) {
        this.facebookMessengerSuccessCallback = jsSuccessCallback;
        this.facebookMessengerFailedCallback = jsFailedCallback;
        UMWeb web = new UMWeb(Url);
        web.setTitle(title);//标题
        web.setThumb(new UMImage(mWXSDKInstance.getContext(),imageUrl));  //缩略图
        web.setDescription(detail);//描述
        new ShareAction((Activity) mWXSDKInstance.getContext())
                .setPlatform(SHARE_MEDIA.FACEBOOK_MESSAGER)//传入平台
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Toast.makeText(mWXSDKInstance.getContext(), "waiting", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        ShareBean bean = new ShareBean();
                        bean.setState(200);
                        bean.setType("facebookMessager");
                        facebookMessengerSuccessCallback.invoke(bean);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ShareBean bean = new ShareBean();
                        bean.setState(100);
                        bean.setType("facebookMessager");
                        facebookMessengerFailedCallback.invoke(bean);
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ShareBean bean = new ShareBean();
                        bean.setState(100);
                        bean.setType("facebookMessager");
                        facebookMessengerFailedCallback.invoke(bean);
                    }
                })//回调监听器
                .share();

        SocialCommerApplication application = (SocialCommerApplication) ((Activity) mWXSDKInstance.getContext()).getApplication();
        Tracker sTracker = application.getDefaultTracker();
        sTracker.send(new HitBuilders.EventBuilder()
                .setCategory("DropStart")
                .setAction("Share to Drop the Price Further")
                .setLabel("FacebookMessenger")
                .build());
    }
}
