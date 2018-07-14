package com.benmu.drop.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.benmu.drop.activity.bean.LoginDto;
import com.benmu.drop.activity.bean.PayDto;

import com.benmu.drop.activity.module.FacebookLoginModule;
import com.benmu.drop.activity.module.GoogleAnalyticsModule;
import com.benmu.drop.activity.module.GoogleLoginModule;
import com.benmu.drop.activity.module.PayModule;
import com.benmu.drop.activity.module.ShareModule;
import com.benmu.drop.utils.CommonUtils;
import com.benmu.framework.activity.AbstractWeexActivity;
import com.benmu.drop.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerGenericTemplateElement;
import com.facebook.share.model.ShareMessengerURLActionButton;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXException;

import org.json.JSONObject;

import java.util.Arrays;


public class MainActivity extends AbstractWeexActivity implements PaymentResultWithDataListener {
    private JSCallback googleSuccessCallback;
    private JSCallback googleFailedCallback;
    // facebook login callback
    private JSCallback FacebookSuccessCallback;
    private JSCallback FacebookFailedCallback;
    // 支付回调
    private JSCallback paySuccessCallback;
    private JSCallback payFailedCallback;
    private static final int RC_SIGN_IN = 12321;

    private CallbackManager mCallbackManager;
    private CallbackManager mShareCallbackManager;
    private CallbackManager mMessengerCallbackManager;
    private ShareDialog shareDialog ;
    private MessageDialog messageDialog;
    private static final int SHARE_FACEBOOK=10086;
    private static final int MESSENGER_FACEBOOK=10087;
    private String amount;

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
            WXSDKEngine.registerModule("GoogleAnalyticsModule", GoogleAnalyticsModule.class);
            WXSDKEngine.registerModule("PayModule", PayModule.class);
            WXSDKEngine.registerModule("CommonUtils", CommonUtils.class);
            WXSDKEngine.registerModule("FacebookLoginModule", FacebookLoginModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }

    public void initFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        LoginResult ss = loginResult ;
                        Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this,"cancel",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(MainActivity.this,"onError",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void startFacebookLogin(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
    }
    public void shareFacebook(String title, String detail, String url, String imageUrl,
                              final JSCallback jsSuccessCallback, final JSCallback jsFailedCallback){
        mShareCallbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(mShareCallbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                jsSuccessCallback.invoke(result);
            }

            @Override
            public void onCancel() {
                jsFailedCallback.invoke(new Object());
            }

            @Override
            public void onError(FacebookException error) {
                jsFailedCallback.invoke(error);
            }
        },SHARE_FACEBOOK);

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(url))
                    .setQuote(detail)
                    .build();
            shareDialog.show(linkContent);
        }
    }
    public void shareFacebookMessenger(String title, String detail,String buttonWord, String url, String imageUrl,
                               JSCallback jsSuccessCallback,  JSCallback jsFailedCallback){
        jsSuccessCallback.invoke(new Object());
        ShareMessengerURLActionButton actionButton =
                new ShareMessengerURLActionButton.Builder()
                        .setTitle(buttonWord)
                        .setUrl(Uri.parse(url))
                        .build();
        ShareMessengerGenericTemplateElement genericTemplateElement =
                new ShareMessengerGenericTemplateElement.Builder()
                        .setTitle(title)
                        .setSubtitle(detail)
                        .setImageUrl(Uri.parse(imageUrl))
                        .setButton(actionButton)
                        .build();
        ShareMessengerGenericTemplateContent genericTemplateContent =
                new ShareMessengerGenericTemplateContent.Builder()
                        .setPageId("Your page ID") // Your page ID, required
                        .setGenericTemplateElement(genericTemplateElement)
                        .build();
        if (MessageDialog.canShow(genericTemplateContent.getClass())) {
            MessageDialog.show(this, genericTemplateContent);
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
        if (mCallbackManager != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (mShareCallbackManager!=null && requestCode == SHARE_FACEBOOK){
            mShareCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public void setPayCallBack(JSCallback paySuccessCallback, JSCallback payFailedCallback) {
        this.paySuccessCallback = paySuccessCallback;
        this.payFailedCallback = payFailedCallback;
    }

    public void setGoogleCallback(JSCallback googleSuccessCallback, JSCallback googleFailedCallback) {
        this.googleSuccessCallback = googleSuccessCallback;
        this.googleFailedCallback = googleFailedCallback;
    }

    public void setFacebookCallback(JSCallback FacebookSuccessCallback, JSCallback googleFailedCallback) {
        this.FacebookSuccessCallback = FacebookSuccessCallback;
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
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData data) {
        PayDto pay = new PayDto();
        pay.setCode(2000);
        pay.setRazorPaymentId(data.getPaymentId());
        pay.setRazorSignature(data.getSignature());
        pay.setRazorOrderId(data.getOrderId());
        pay.setRazorAmount(this.amount);
        paySuccessCallback.invoke(pay);
    }

    // 支付失败
    @Override
    public void onPaymentError(int code, String response, PaymentData data) {
        PayDto pay = new PayDto();
        pay.setCode(code);
        pay.setError(response);
        payFailedCallback.invoke(pay);
    }

    public void startPayment(String razorpayOrderId ,String name, String description, String image, String amount, String contact, String email) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;
        this.amount = amount ;
        final Checkout co = new Checkout();
        co.setImage(R.mipmap.ic_launcher);
        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", description);
            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");
            options.put("amount", amount);
            options.put("order_id", razorpayOrderId);
            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", contact);
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            payFailedCallback.invoke(e);
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

}
