package com.benmu.drop.activity.module;

import android.app.Activity;
import android.content.Intent;

import com.benmu.drop.R;
import com.benmu.drop.activity.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * @author luzhenqiang
 * @desc 印度的几种支付方式
 * @date 2018/5/28 上午9:53
 * @since 1.0.0
 */

public class PayModule extends WXModule {

    @JSMethod
    public void startPayRequest(
            String name, String description, String image, String amount, String contact, String email,
            JSCallback PaySuccessCallback, JSCallback PayFailedCallback) {
        ((MainActivity) mWXSDKInstance.getContext()).setPayCallBack(PaySuccessCallback, PayFailedCallback);
        ((MainActivity) mWXSDKInstance.getContext()).startPayment(name, description, image, amount, contact,email);

    }
}
