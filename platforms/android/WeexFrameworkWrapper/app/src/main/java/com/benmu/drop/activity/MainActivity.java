package com.benmu.drop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.benmu.drop.activity.bean.LoginDto;
import com.benmu.drop.activity.utils.HttpServerConfig;
import com.benmu.framework.activity.AbstractWeexActivity;
import com.benmu.drop.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXException;
import com.taobao.weex.devtools.common.LogUtil;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AbstractWeexActivity implements PaymentResultListener {
    private JSCallback googleSuccessCallback;
    private JSCallback googleFailedCallback;
    // 支付回调
    private JSCallback paySuccessCallback;
    private JSCallback payFailedCallback;
    private static final int RC_SIGN_IN = 12321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPayment();
        renderPage();
        try {
            WXSDKEngine.registerModule("lzqTestModule", TestJsCallback.class);
            WXSDKEngine.registerModule("ShareModule", ShareModule.class);
            WXSDKEngine.registerModule("GoogleLoginModule", GoogleLoginModule.class);
            WXSDKEngine.registerModule("PayModule", PayModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }

    private void initPayment() {
        Checkout.preload(getApplicationContext());
    }

    private void initView() {
        mContainer = (ViewGroup) findViewById(R.id.layout_container);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void setPayCallBack(JSCallback paySuccessCallback, JSCallback payFailedCallback) {
        this.paySuccessCallback = paySuccessCallback;
        this.payFailedCallback = payFailedCallback;
    }

    public void setGoogleCallback(JSCallback googleSuccessCallback, JSCallback googleFailedCallback) {
        this.googleSuccessCallback = googleSuccessCallback;
        this.googleFailedCallback = googleFailedCallback;
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // send the google tokenID to my server to get the information of login.
            // sendTokenIdToServer(account);
            LoginDto dto = new LoginDto();
            dto.setTokenId(account.getIdToken());
            googleSuccessCallback.invoke(dto);
        } catch (ApiException e) {
            this.googleFailedCallback.invoke(new Object());
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            // Log.w('ddd', "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    // 支付成功
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        paySuccessCallback.invoke(new Object());
    }

    // 支付失败
    @Override
    public void onPaymentError(int code, String response) {
        payFailedCallback.invoke(new Object());
    }

    public void startPayment(String name, String description, String image, String amount, String email, String contact) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", description);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", image);
            options.put("currency", "INR");
            options.put("amount", amount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", contact);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            payFailedCallback.invoke(new Object());
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


}
