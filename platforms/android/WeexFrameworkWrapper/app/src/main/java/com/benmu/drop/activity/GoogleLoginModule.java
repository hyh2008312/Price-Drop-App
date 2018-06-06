package com.benmu.drop.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.benmu.drop.R;
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
 * @desc google login
 * @date 2018/5/25 下午4:38
 * @since 1.0.1
 */

public class GoogleLoginModule extends WXModule {
    private JSCallback googleSingoutCallback;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 12321;

    @Override
    public void onActivityCreate() {
        super.onActivityCreate();
    }

    // 判断是否第三方登录  // 判断accout 是否为空
    public void isCheckGoogleLogin() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mWXSDKInstance.getContext());

    }

    // 开始进行google登录
    @JSMethod
    public void startGoogleLogin(
            JSCallback jsSuccessCallback, JSCallback jsFailedCallback) {
        ((MainActivity)mWXSDKInstance.getContext()).setGoogleCallback(jsSuccessCallback,jsFailedCallback);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mWXSDKInstance.getContext().getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(mWXSDKInstance.getContext(), gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        ((Activity) mWXSDKInstance.getContext()).startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // 开始进行google退出
    @JSMethod
    public void startGoogleLSignOut(
            JSCallback jsSuccessCallback) {
        this.googleSingoutCallback = jsSuccessCallback;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mWXSDKInstance.getContext().getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(mWXSDKInstance.getContext(), gso);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener((Activity) mWXSDKInstance.getContext(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        googleSingoutCallback.invoke(new Object());
                        Toast.makeText(mWXSDKInstance.getContext(),"退出成功",Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
