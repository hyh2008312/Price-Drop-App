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
            String razorpayOrderId,
            String name, String description, String image, String amount, String contact, String email,
            JSCallback PaySuccessCallback, JSCallback PayFailedCallback) {
        ((MainActivity) mWXSDKInstance.getContext()).setPayCallBack(PaySuccessCallback, PayFailedCallback);
        ((MainActivity) mWXSDKInstance.getContext()).startPayment(razorpayOrderId,name, description, image, amount, contact,email);

    }
    @JSMethod
    public void startPaytmRequest(
            String orderId ,String custId,String txnAmount,String mobileNo,String email,String
            calllbackUrl ,String checkSumHash, JSCallback PaySuccessCallback, JSCallback PayFailedCallback) {
        ((MainActivity) mWXSDKInstance.getContext()).setPaytmCallBack(PaySuccessCallback, PayFailedCallback);
        ((MainActivity) mWXSDKInstance.getContext()).onStartTransaction(orderId,custId, txnAmount, mobileNo, email, calllbackUrl,checkSumHash);

    }
    @JSMethod
    public void startPayUmoneyRequest(String txnId, String amount ,String email ,String phone,
            String productName , String firstName, String merchantHash, JSCallback PaySuccessCallback, JSCallback PayFailedCallback) {
        ((MainActivity) mWXSDKInstance.getContext()).setPayUCallBack(PaySuccessCallback, PayFailedCallback);
        ((MainActivity) mWXSDKInstance.getContext()).startPayUmoneyRequest(txnId,amount, email, phone, productName,firstName,merchantHash);
    }
}
