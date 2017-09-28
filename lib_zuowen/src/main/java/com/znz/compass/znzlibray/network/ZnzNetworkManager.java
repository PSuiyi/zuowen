package com.znz.compass.znzlibray.network;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.network_status.NetUtils;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by panqiming on 2016/11/8.
 */

public class ZnzNetworkManager {
    private Context context;
    private static ZnzNetworkManager instance;
    private DataManager mDataManager;

    public static synchronized ZnzNetworkManager getInstance(Context mContext) {
        if (instance == null) {
            instance = new ZnzNetworkManager(mContext.getApplicationContext());
        }
        return instance;
    }

    public ZnzNetworkManager(Context context) {
        this.context = context;
        mDataManager = DataManager.getInstance(context);
    }

    public void request(Observable<ResponseBody> observable, ZnzHttpListener listener) {
        //判断网络情况
        if (!NetUtils.isNetworkAvailable(context)) {
            mDataManager.showToast(R.string.NoSignalException);
            return;
        }

        if (observable == null) {
            return;
        }

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
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
                            if (responseStr.contains("statusCode=90000")) {
                                mDataManager.tokenTimeOut(context);
                                return;
                            }

                            JSONObject responseJson;
                            try {
                                responseJson = JSON.parseObject(responseStr);
                            } catch (Exception e) {
                                listener.onFail(e.getMessage());
                                return;
                            }

                            if (responseJson.getString("statusCode").equals("00000") || responseJson.getString("statusCode").equals("1")) {
                                listener.onSuccess(responseJson);
                            } else if (responseJson.getString("statusCode").equals("90000")) {
                                mDataManager.tokenTimeOut(context);
                            } else {
                                listener.onFail(responseJson.getString("msg"));
                                mDataManager.showToast(responseJson.getString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            mDataManager.showToast(e.getMessage());
                            listener.onFail(e.getMessage());
                        }
                    }
                });
    }

}
