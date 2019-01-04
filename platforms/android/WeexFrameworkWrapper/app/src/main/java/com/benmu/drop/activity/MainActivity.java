package com.benmu.drop.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.benmu.drop.SocialCommerApplication;
import com.benmu.drop.activity.bean.LoginDto;
import com.benmu.drop.activity.bean.PayDto;

import com.benmu.drop.activity.bean.PaytmBean;
import com.benmu.drop.activity.bean.PayuBean;
import com.benmu.drop.activity.module.FacebookLoginModule;
import com.benmu.drop.activity.module.GoogleAnalyticsModule;
import com.benmu.drop.activity.module.GoogleLoginModule;
import com.benmu.drop.activity.module.PayModule;
import com.benmu.drop.activity.module.ShareModule;
import com.benmu.drop.activity.utils.AppEnvironment;
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
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;


public class MainActivity extends AbstractWeexActivity implements PaymentResultWithDataListener {
    public static final String TAG = "MainActivity : ";
    private JSCallback googleSuccessCallback;
    private JSCallback googleFailedCallback;
    // facebook login callback
    private JSCallback FacebookSuccessCallback;
    private JSCallback FacebookFailedCallback;
    // 支付回调
    private JSCallback paySuccessCallback;
    private JSCallback payFailedCallback;
    // 支付回调paytm
    private JSCallback paytmSuccessCallback;
    private JSCallback paytmFailedCallback;
    // 支付回调 payU
    private JSCallback payuSuccessCallback;
    private JSCallback payuFailedCallback;
    private static final int RC_SIGN_IN = 12321;

    private CallbackManager mCallbackManager;
    private CallbackManager mShareCallbackManager;
    private CallbackManager mMessengerCallbackManager;
    private ShareDialog shareDialog;
    private MessageDialog messageDialog;
    private static final int SHARE_FACEBOOK = 10086;
    private static final int MESSENGER_FACEBOOK = 10087;
    private String amount;

    private boolean isCanBack = true;
    private JSCallback jsCanBackCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
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
                        LoginResult ss = loginResult;
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void startFacebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
    }

    public void shareFacebook(String title, String detail, String url, String imageUrl,
                              final JSCallback jsSuccessCallback, final JSCallback jsFailedCallback) {
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
        }, SHARE_FACEBOOK);

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(url))
                    .setQuote(detail)
                    .build();
            shareDialog.show(linkContent);
        }
    }

    public void shareFacebookMessenger(String title, String detail, String buttonWord, String url, String imageUrl,
                                       JSCallback jsSuccessCallback, JSCallback jsFailedCallback) {
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
        if (mShareCallbackManager != null && requestCode == SHARE_FACEBOOK) {
            mShareCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            this.payuResult(data);
        }else if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_CANCELED){
            this.payuFailedCallback.invoke("failed");
        }
    }

    public void payuResult(Intent data){
        TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                .INTENT_EXTRA_TRANSACTION_RESPONSE);
        ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);
        // Check which object is non-null
        if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
            if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                //Success Transaction
                String payuResponse = transactionResponse.getPayuResponse();
                if(!TextUtils.isEmpty(payuResponse)){
                    try {
                        PayuBean bean = new PayuBean();
                        JSONObject jsonObject = new JSONObject(payuResponse);
                        JSONObject result = jsonObject.getJSONObject("result");
                        bean.setCode(200);
                        bean.setAmount(result.getString("amount"));
                        bean.setAdditionalCharges(result.getString("additionalCharges"));
                        bean.setEmail(result.getString("email"));
                        bean.setFirstname(result.getString("firstname"));
                        bean.setHash(result.getString("hash"));
                        bean.setProductinfo(result.getString("productinfo"));
                        bean.setStatus(result.getString("status"));
                        bean.setKey(result.getString("key"));
                        bean.setTxnid(result.getString("txnid"));
                        Log.d(TAG,bean.getAmount()+bean.getAdditionalCharges()+
                                bean.getEmail()+bean.getFirstname()+bean.getStatus());
                        this.payuSuccessCallback.invoke(bean);
                    } catch (JSONException e) {
                        PayuBean bean = new PayuBean();
                        bean.setCode(400);
                        this.payuFailedCallback.invoke(bean);
                        e.printStackTrace();
                    }
                }
            } else {
                //Failure Transaction
                PayuBean bean = new PayuBean();
                bean.setCode(400);
                this.payuFailedCallback.invoke(bean);
            }
            // Response from Payumoney
            String payuResponse = transactionResponse.getPayuResponse();
            Log.d(TAG, "======>"+payuResponse);
            // Response from SURl and FURL
            String merchantResponse = transactionResponse.getTransactionDetails();
            Log.d(TAG, "======>"+merchantResponse);
            /*new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage("Payu's Data : " + payuResponse + "\n\n\n Merchant's Data: " + merchantResponse)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    }).show();*/
        } else if (resultModel != null && resultModel.getError() != null) {
            //Failure Transaction
            PayuBean bean = new PayuBean();
            bean.setCode(300);
            this.payuFailedCallback.invoke(bean);
            Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
        }
    }

    public void setPayCallBack(JSCallback paySuccessCallback, JSCallback payFailedCallback) {
        this.paySuccessCallback = paySuccessCallback;
        this.payFailedCallback = payFailedCallback;
    }

    public void setPaytmCallBack(JSCallback paySuccessCallback, JSCallback payFailedCallback) {
        this.paytmSuccessCallback = paySuccessCallback;
        this.paytmFailedCallback = payFailedCallback;
    }
    public void setPayUCallBack(JSCallback paySuccessCallback, JSCallback payFailedCallback) {
        this.payuSuccessCallback = paySuccessCallback;
        this.payuFailedCallback = payFailedCallback;
    }

    public void setGoogleCallback(JSCallback googleSuccessCallback, JSCallback googleFailedCallback) {
        this.googleSuccessCallback = googleSuccessCallback;
        this.googleFailedCallback = googleFailedCallback;
    }

    public void setFacebookCallback(JSCallback FacebookSuccessCallback, JSCallback googleFailedCallback) {
        this.FacebookSuccessCallback = FacebookSuccessCallback;
        this.FacebookFailedCallback = googleFailedCallback;
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

    public void startPayment(String razorpayOrderId, String name, String description, String image, String amount, String contact, String email) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;
        this.amount = amount;
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

    //paytm 的支付方式
    public void onStartTransaction(String orderId, String custId, String txnAmount, String mobileNo, String email, String
            calllbackUrl, String checkSumHash) {
        PaytmPGService Service = PaytmPGService.getProductionService();
        // PaytmPGService Service = PaytmPGService.getStagingService();
        HashMap<String, String> paramMap = new HashMap<String, String>();

        // these are mandatory parameters
        paramMap.put("MID", "JMDTec16243179908223");
        paramMap.put("ORDER_ID", orderId);
        paramMap.put("CUST_ID", custId);
        paramMap.put("TXN_AMOUNT", txnAmount);
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail109");
        paramMap.put("WEBSITE", "APPPROD");
        paramMap.put("MOBILE_NO", mobileNo);
        paramMap.put("EMAIL", email);
        paramMap.put("CALLBACK_URL", calllbackUrl);
        //paramMap.put("CALLBACK_URL" , "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=Order_153223772120");
        paramMap.put("CHECKSUMHASH", checkSumHash);

        PaytmOrder Order = new PaytmOrder(paramMap);

		/*PaytmMerchant Merchant = new PaytmMerchant(
                "https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp",
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");*/

        Service.initialize(Order, null);

        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // Some UI Error Occurred in Payment Gateway Activity.
                        // // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to //
                        // initialization of webview. // Error Message details
                        // the error occurred.
                        PaytmBean paybean = new PaytmBean();
                        paybean.setCode(300);
                        paybean.setErrmsg("UIError");
                        paytmFailedCallback.invoke(paybean);
                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        // Log.d("LOG", "Payment Transaction is successful " + inResponse);
                        Log.d("LOG", "Payment Transaction is successful " + inResponse.toString());

                        PaytmBean paybean = new PaytmBean();
                        paybean.setStatus(inResponse.getString("STATUS"));

                        if ("TXN_SUCCESS".equals(paybean.getStatus())) {
                            //成功
                            paybean.setCode(200);
                            paybean.setCheckSumHash(inResponse.getString("CHECKSUMHASH"));
                            paybean.setOrderId(inResponse.getString("ORDERID"));
                            paybean.setTxnAmount(inResponse.getString("TXNAMOUNT"));
                            paybean.setRespCode(inResponse.getString("RESPCODE"));
                            /*paybean.setBANKNAME(inResponse.getString("BANKNAME"));
                            paybean.setTXNDATE(inResponse.getString("TXNDATE"));
                            paybean.setTXNID(inResponse.getString("TXNID"));
                            paybean.setPAYMENTMODE(inResponse.getString("PAYMENTMODE"));
                            paybean.setGATEWAYNAME(inResponse.getString("GATEWAYNAME"));*/
                            paybean.setRespMsg(inResponse.getString("RESPMSG"));
                            paybean.setErrmsg("pay success");
                            paytmSuccessCallback.invoke(paybean);
                        } else {
                            //失败
                            paybean.setCode(300);
                            paybean.setCheckSumHash(inResponse.getString("CHECKSUMHASH"));
                            paybean.setOrderId(inResponse.getString("ORDERID"));
                            paybean.setTxnAmount(inResponse.getString("TXNAMOUNT"));
                            paybean.setRespCode(inResponse.getString("RESPCODE"));
                            paybean.setErrmsg(inResponse.getString("RESPMSG"));
                            paytmFailedCallback.invoke(paybean);
                        }
                        // Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() { // If network is not
                        // available, then this
                        // method gets called.
                        PaytmBean paybean = new PaytmBean();
                        paybean.setCode(300);
                        paybean.setErrmsg("networkNotAvailable");
                        paytmFailedCallback.invoke(paybean);
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        Toast.makeText(getApplicationContext(), "clientAuthenticationFailed", Toast.LENGTH_SHORT).show();
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of payt_STATUS is 2. //
                        // Error Message describes the reason for failure.
                        PaytmBean paybean = new PaytmBean();
                        paybean.setCode(300);
                        paybean.setErrmsg("clientAuthenticationFailed");
                        paytmFailedCallback.invoke(paybean);
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {

                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        PaytmBean paybean = new PaytmBean();
                        paybean.setCode(400);
                        paybean.setErrmsg("onBackPressedCancelTransaction");
                        paytmFailedCallback.invoke(paybean);
                        Toast.makeText(MainActivity.this, "Back pressed. Transaction cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        PaytmBean paybean = new PaytmBean();
                        paybean.setCode(400);
                        paybean.setErrmsg("onTransactionCancel");
                        paytmFailedCallback.invoke(paybean);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }

                });
    }


    public void setIsCanBack(boolean isCanBack, JSCallback jsCallback) {
        this.isCanBack = isCanBack;
        this.jsCanBackCallback = jsCallback;
    }
    public void changeCanBack(boolean isCanBack) {
        this.isCanBack = isCanBack;
    }

    @Override
    public void onBackPressed() {
        if (isCanBack) {
            super.onBackPressed();
        } else {
            this.jsCanBackCallback.invokeAndKeepAlive(new Object());
        }

    }

    // payUmoney支付
    public void startPayUmoneyRequest(String txnId, String amount ,String email ,String phone,
                                       String productName , String firstName, String merchantHash) {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("Complete");

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("Payment");

        //
        AppEnvironment appEnvironment = ((SocialCommerApplication) getApplication()).getAppEnvironment();

        payUmoneyConfig.disableExitConfirmation(false);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        PayUmoneySdkInitializer.PaymentParam mPaymentParams;
//
//        double amountMoney = 0;
//        try {
//            amountMoney = Double.parseDouble(amount);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";
        builder.setAmount(amount)
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(appEnvironment.debug())
                .setKey(appEnvironment.merchant_Key())
                .setMerchantId(appEnvironment.merchant_ID());
        try {
            mPaymentParams = builder.build();
            mPaymentParams.setMerchantHash(merchantHash);
            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, MainActivity.this, R.style.AppTheme_Blue, true);
        } catch (Exception e) {
            // some exception occurred
            //Failure Transaction
            PayuBean bean = new PayuBean();
            bean.setCode(300);
            this.payuFailedCallback.invoke(bean);
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
