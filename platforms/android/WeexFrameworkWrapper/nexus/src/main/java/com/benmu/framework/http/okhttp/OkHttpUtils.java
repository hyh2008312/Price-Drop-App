package com.benmu.framework.http.okhttp;

import android.text.TextUtils;

import com.benmu.framework.http.okhttp.builder.GetBuilder;
import com.benmu.framework.http.okhttp.builder.HeadBuilder;
import com.benmu.framework.http.okhttp.builder.OtherRequestBuilder;
import com.benmu.framework.http.okhttp.builder.PostFileBuilder;
import com.benmu.framework.http.okhttp.builder.PostFormBuilder;
import com.benmu.framework.http.okhttp.builder.PostStringBuilder;
import com.benmu.framework.http.okhttp.callback.Callback;
import com.benmu.framework.http.okhttp.exception.CancelException;
import com.benmu.framework.http.okhttp.exception.HttpException;
import com.benmu.framework.http.okhttp.request.RequestCall;
import com.benmu.framework.http.okhttp.utils.Platform;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }

        mPlatform = Platform.get();
    }

    public void updateHttpClient(OkHttpClient okHttpClient) {
        if (okHttpClient == null) return;
        mOkHttpClient = okHttpClient;
    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }


    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Exception exception = e;
                if (exception == null) {
                    exception = new IOException();
                }
                if (call.isCanceled()) {
                    exception = new CancelException(exception.getMessage());
                }

                sendFailResultCallback(call, exception, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        //request canceled
                        Exception canceled = new CancelException("canceled !");
                        sendFailResultCallback(call, canceled, finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        //request failed
                        String detail = response.body().string();
                        if (!TextUtils.isEmpty(detail)) {
                            JSONObject object = new JSONObject(detail);
                            String content = object.getString("detail");
                            if (!TextUtils.isEmpty(content)) {
                                Exception failed = new HttpException(response.code(), content);
                                sendFailResultCallback(call, failed, finalCallback, id);
                                return;
                            }
                        }
                        Exception failed = new HttpException(response.code(), "request failed" +
                                response.message());
                        sendFailResultCallback(call, failed, finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback
            callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int
            id) {
        if (callback == null) return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
        public static final String GET = "GET";
        public static final String POST = "POST";
    }
}

