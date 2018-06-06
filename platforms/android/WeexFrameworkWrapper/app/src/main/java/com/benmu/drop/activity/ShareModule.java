package com.benmu.drop.activity;

import android.app.Activity;
import android.widget.Toast;

import com.benmu.drop.activity.bean.ShareBean;
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


    }

    @JSMethod
    public void shareWhatsapp(String content, String imageUrl,
                              JSCallback jsSuccessCallback, JSCallback jsFailedCallback) {
        this.whatsappSuccessCallback = jsSuccessCallback;
        this.WhatsappFailedCallback = jsFailedCallback;
        new ShareAction((Activity) mWXSDKInstance.getContext())
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
                        bean.setType("facebook");
                        whatsappSuccessCallback.invoke(bean);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ShareBean bean = new ShareBean();
                        bean.setState(100);
                        bean.setType("facebook");
                        WhatsappFailedCallback.invoke(bean);
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ShareBean bean = new ShareBean();
                        bean.setState(100);
                        bean.setType("facebook");
                        WhatsappFailedCallback.invoke(bean);
                        Toast.makeText(mWXSDKInstance.getContext(), "cancel", Toast.LENGTH_SHORT).show();
                    }
                })//回调监听器
                .share();
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


    }
}
