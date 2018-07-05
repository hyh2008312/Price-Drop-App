package com.benmu.drop.activity.module;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.benmu.drop.R;
import com.benmu.drop.activity.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * @author luzhenqiang
 * @desc facebook login
 * @date 2018/7/5 下午2:15
 * @since 1.3.4
 */

public class FacebookLoginModule extends WXModule {

    @JSMethod
    public void startFacebookLogin(JSCallback jsSuccessCallback, JSCallback jsFailedCallback) {
        ((MainActivity) mWXSDKInstance.getContext()).initFacebook();
        ((MainActivity) mWXSDKInstance.getContext()).setFacebookCallback(jsSuccessCallback, jsFailedCallback);
        ((MainActivity) mWXSDKInstance.getContext()).startFacebookLogin();
    }

}
