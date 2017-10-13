package com.znz.compass.znzlibray.base_znz;

import android.content.Context;
import android.support.annotation.IntDef;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.network_status.NetUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Date： 2017/3/27 2017
 * User： PSuiyi
 * Description：
 */

public class BaseModel<V extends IView> implements IModel {
    protected Context context = null;
    protected DataManager mDataManager;
    protected V mView;

    private Subscriber<ResponseBody> subscriber;

    public static final int LODING_NONE = 0x01;
    public static final int LODING_PD = 0x02;
    public static final int LODING_LODING = 0x03;

    @IntDef({LODING_NONE, LODING_PD, LODING_LODING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LODING_TYPE {
    }


    public BaseModel(Context context, V mView) {
        this.context = context;
        this.mView = mView;
        mDataManager = DataManager.getInstance(context);
    }

    /**
     * 公共请求方法
     *
     * @param observable
     * @param listener
     */
    public void request(Observable<ResponseBody> observable, ZnzHttpListener listener) {
        request(observable, listener, LODING_NONE);
    }

    /**
     * 公共请求方法
     *
     * @param observable
     * @param listener
     * @param lodingType
     */
    public void request(Observable<ResponseBody> observable, ZnzHttpListener listener, @LODING_TYPE int lodingType) {

        handleLoding(true, lodingType);

        //判断网络情况
        if (!NetUtils.isNetworkAvailable(context)) {
            return;
        }

        if (observable == null) {
            return;
        }

        subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFail(e.getMessage());
                mDataManager.showToast(e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String responseStr = responseBody.string();
                    JSONObject responseJson;
                    try {
                        responseJson = JSON.parseObject(responseStr);
                    } catch (Exception e) {
                        listener.onFail(e.getMessage());
                        return;
                    }

                    if (responseJson.getString("result").equals("0")) {
                        listener.onSuccess(responseJson);
                        handleLoding(false, lodingType);
                    } else if (responseJson.getString("result").equals("90000")) {
                        mDataManager.tokenTimeOut(context);
                    } else {
                        listener.onFail(responseJson.getString("message"));
                        mDataManager.showToast(responseJson.getString("message"));
                        handleLoding(false, lodingType);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mDataManager.showToast(e.getMessage());
                    listener.onFail(e.getMessage());
                    handleLoding(false, lodingType);
                }
            }
        };

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 处理loding还是pd
     *
     * @param lodingType 0，没有，1、pd 2、loding
     */
    private void handleLoding(boolean show, int lodingType) {
        if (show) {
            switch (lodingType) {
                case LODING_NONE:
                    break;
                case LODING_PD:
                    mView.UIShowPd();
                    break;
                case LODING_LODING:
                    mView.UIShowLoding();
                    break;
            }
        } else {
            switch (lodingType) {
                case LODING_NONE:
                    break;
                case LODING_PD:
                    mView.UIHidePd();
                    break;
                case LODING_LODING:
                    mView.UIHideLoding();
                    break;
            }
        }
    }

    @Override
    public void MOAttach() {

    }

    @Override
    public void MODestory() {
        if (subscriber != null) {
            if (!subscriber.isUnsubscribed()) {
                subscriber.unsubscribe();
            }
        }
    }
}
