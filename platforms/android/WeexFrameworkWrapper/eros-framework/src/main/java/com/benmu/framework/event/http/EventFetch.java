package com.benmu.framework.event.http;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.benmu.framework.constant.Constant;
import com.benmu.framework.constant.WXEventCenter;
import com.benmu.framework.http.okhttp.OkHttpUtils;
import com.benmu.framework.http.okhttp.callback.StringCallback;
import com.benmu.framework.http.okhttp.exception.CancelException;
import com.benmu.framework.http.okhttp.exception.HttpException;
import com.benmu.framework.http.okhttp.exception.IrregularUrlException;
import com.benmu.framework.http.okhttp.utils.L;
import com.benmu.framework.manager.ManagerFactory;
import com.benmu.framework.manager.impl.AxiosManager;
import com.benmu.framework.manager.impl.ImageManager;
import com.benmu.framework.manager.impl.ModalManager;
import com.benmu.framework.manager.impl.ParseManager;
import com.benmu.framework.manager.impl.PersistentManager;
import com.benmu.framework.manager.impl.dispatcher.DispatchEventManager;
import com.benmu.framework.model.AxiosGet;
import com.benmu.framework.model.AxiosPost;
import com.benmu.framework.model.AxiosResultBean;
import com.benmu.framework.model.UploadImageBean;
import com.benmu.framework.model.UploadResultBean;
import com.benmu.framework.model.WeexEventBean;
import com.benmu.framework.utils.JsPoster;
import com.benmu.framework.utils.TextUtil;
import com.benmu.wxbase.EventGate;
import com.lzy.imagepicker.bean.ImageItem;
import com.squareup.otto.Subscribe;
import com.taobao.weex.bridge.JSCallback;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import okhttp3.Call;


/**
 * Created by Carry on 2017/8/16.
 */

public class EventFetch extends EventGate {
    private JSCallback mUploadAvatar;
    private Context mUploadContext;

    @Override
    public void perform(Context context, WeexEventBean weexEventBean, String type) {
        String params = weexEventBean.getJsParams();
        if (WXEventCenter.EVENT_FETCH.equals(type)) {
            fetch(params, context, weexEventBean.getJscallback());
        } else if (WXEventCenter.EVENT_IMAGE_UPLOAD.equals(type)) {
            uploadImage(params, context, weexEventBean.getJscallback());
        }
    }

    public void fetch(String params, final Context context, final JSCallback jscallback) {

        ParseManager parseManager = ManagerFactory.getManagerService(ParseManager.class);
        AxiosManager axiosManager = ManagerFactory.getManagerService(AxiosManager.class);
        JSONObject object = parseManager.parseObject(params);
        final String mUrl = object.getString("url");

        Boolean noRepeat = object.getBoolean("noRepeat");
        if (noRepeat != null && noRepeat) {
            axiosManager.cancel(mUrl);
        }
        switch (object.getString("method")) {

            case OkHttpUtils.METHOD.GET:
                AxiosGet axiosGet = parseManager.parseObject(params, AxiosGet.class);
                get(context, axiosManager, axiosGet, jscallback);
                break;
            case OkHttpUtils.METHOD.POST:
                AxiosPost axiosPost = parseManager.parseObject(params, AxiosPost.class);
                post(context, axiosManager, axiosPost, jscallback);
                break;
            case OkHttpUtils.METHOD.HEAD:
                AxiosGet axiosHead = parseManager.parseObject(params, AxiosGet.class);
                head(context, axiosManager, axiosHead, jscallback);
                break;
            case OkHttpUtils.METHOD.DELETE:
                AxiosPost axiosDelete = parseManager.parseObject(params, AxiosPost.class);
                delete(context, axiosManager, axiosDelete, jscallback);
                break;
            case OkHttpUtils.METHOD.PUT:
                AxiosPost axiosPut = parseManager.parseObject(params, AxiosPost.class);
                put(context, axiosManager, axiosPut, jscallback);
                break;
            case OkHttpUtils.METHOD.PATCH:
                AxiosPost axiosPatch = parseManager.parseObject(params, AxiosPost.class);
                patch(context, axiosManager, axiosPatch, jscallback);
                break;
        }
    }

    private void patch(final Context context, AxiosManager axiosManager, AxiosPost axiosPatch,
                       final JSCallback jscallback) {
        axiosManager.patch(axiosPatch.url, axiosPatch.data, axiosPatch.header, new StringCallback
                () {
            @Override
            public void onError(Call call, Exception e, int id) {
                parseError(context, e, jscallback);
            }

            @Override
            public void onResponse(String response, int id) {
                parseResponse(response, jscallback, code);
            }
        }, axiosPatch.url, axiosPatch.timeout);
    }

    private void put(final Context context, AxiosManager axiosManager, AxiosPost axiosPut, final
    JSCallback jscallback) {
        axiosManager.put(axiosPut.url, axiosPut.data, axiosPut.header, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                parseError(context, e, jscallback);
            }

            @Override
            public void onResponse(String response, int id) {
                parseResponse(response, jscallback, code);
            }
        }, axiosPut.url, axiosPut.timeout);
    }

    private void delete(final Context context, AxiosManager axiosManager, AxiosPost axiosDelete,
                        final JSCallback jscallback) {
        axiosManager.delete(axiosDelete.url, axiosDelete.data, axiosDelete.header, new
                StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        parseError(context, e, jscallback);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseResponse(response, jscallback, code);
                    }
                }, axiosDelete.url, axiosDelete.timeout);
    }


    private void head(final Context context, AxiosManager axiosManager, AxiosGet axiosHead, final
    JSCallback jscallback) {
        axiosManager.head(axiosHead.url, axiosHead.data, axiosHead.header, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                parseError(context, e, jscallback);
            }

            @Override
            public void onResponse(String response, int id) {
                parseResponse(response, jscallback, code);
            }
        }, axiosHead.url, axiosHead.timeout);
    }

    private void post(final Context context, AxiosManager axiosManager, AxiosPost axiosPost,
                      final JSCallback
                              jscallback) {
        axiosManager.post(axiosPost.url, axiosPost.data, axiosPost.header, new
                StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        parseError(context, e, jscallback);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseResponse(response, jscallback, code);
                    }
                }, axiosPost.url, axiosPost.timeout);
    }


    private void get(final Context context, AxiosManager axiosManager, AxiosGet axiosGet, final
    JSCallback jscallback) {
        axiosManager.get(axiosGet.url, axiosGet.data, axiosGet.header, new
                StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        parseError(context, e, jscallback);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseResponse(response, jscallback, code);
                    }
                }, axiosGet.url, axiosGet.timeout);
    }


    private void parseError(Context context, Exception e, JSCallback callback) {
        if (e instanceof CancelException) {
            //request canceled
            ModalManager.BmLoading.dismissLoading(context);
            return;
        }
        AxiosResultBean bean = new AxiosResultBean();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            bean.status = httpException.getmErrorCode();
            bean.errorMsg = httpException.getmErrorMessage();
        } else if (e instanceof ConnectException ) {
            bean.status = 10;
            bean.errorMsg = "It seems your internet is not stable. Please try again or reopen your app!";
        } else if (e instanceof UnknownHostException) {
            bean.status = 10;
            bean.errorMsg = "It seems your internet is not stable. Please try again or reopen your app!";
        } else if (e instanceof IrregularUrlException) {
            IrregularUrlException irregularUrlException = (IrregularUrlException) e;
            bean.status = 9;
            bean.errorMsg = irregularUrlException.getmErrosMeeage();
        }

        if (callback != null) {
            callback.invoke(bean);
        }
    }

    private void parseResponse(String response, JSCallback callBack, int code) {
        try {
            AxiosResultBean bean = new AxiosResultBean();
            if (callBack != null && !TextUtils.isEmpty(response)) {
                bean.status = code;
                bean.errorMsg = "";
                bean.data = JSON.parse(response);
                callBack.invoke(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            L.e("json 解析错误");
            AxiosResultBean bean = new AxiosResultBean();
            bean.status = -1;
            bean.errorMsg = "json 解析错误";
            if (callBack != null) {
                callBack.invoke(bean);
            }
        }

    }

    public void uploadImage(String json, Context context, JSCallback jsCallback) {
        mUploadAvatar = jsCallback;
        mUploadContext = context;
        UploadImageBean bean = ManagerFactory.getManagerService(ParseManager.class).parseObject
                (json, UploadImageBean.class);
        ManagerFactory.getManagerService(DispatchEventManager.class).getBus().register(this);
        ImageManager imageManager = ManagerFactory.getManagerService(ImageManager
                .class);
        ArrayList<ImageItem> items = new ArrayList<>();
        if (bean.images == null || bean.images.size() == 0) {
            Toast.makeText(context, "没传递上传的图片~", Toast.LENGTH_SHORT).show();
            return;
        }
        for (String path : bean.images) {
            ImageItem item = new ImageItem();
            item.path = path;
            items.add(item);
        }
        imageManager.UpMultipleImageData(context, items, bean);
    }

    /**
     * 上传完成后 回调
     */
    @Subscribe
    public void OnUploadResult(UploadResultBean uploadResultBean) {
        if (uploadResultBean != null && mUploadAvatar != null) {
            JsPoster.postSuccess(TextUtil.conversionObject(uploadResultBean.data), mUploadAvatar);
        }
        ModalManager.BmLoading.dismissLoading(mUploadContext);
        ManagerFactory.getManagerService(DispatchEventManager.class).getBus().unregister(this);
        mUploadAvatar = null;
        ManagerFactory.getManagerService(PersistentManager.class).deleteCacheData(Constant
                .ImageConstants.UPLOAD_IMAGE_BEAN);
    }

}
