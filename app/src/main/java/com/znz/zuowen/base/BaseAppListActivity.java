package com.znz.zuowen.base;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.socks.library.KLog;
import com.znz.compass.znzlibray.base.BaseListActivity;
import com.znz.compass.znzlibray.base_znz.IModel;
import com.znz.compass.znzlibray.common.ZnzConstants;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Date： 2017/9/4 2017
 * User： PSuiyi
 * Description：
 */

public abstract class BaseAppListActivity<M extends IModel, T> extends BaseListActivity<M, T> {

    @Override
    protected void initializeAppBusiness() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
        if (mModel != null) {
            mModel.MODestory();
        }
    }

    /**
     * 列表访问网络请求
     *
     * @param action
     */
    protected void customeRefreshRequest(final int action) {
        if (mModel == null) {
            KLog.e("请实例化model");
            setTempDataList();
            return;
        }

        currentAction = action;

        //是用clear还是重新new都有道理，暂时无法取舍
        params = new HashMap<>();

        if (requestCustomeRefreshObservable() == null) {
            setTempDataList();
        } else {
            if (action == ACTION_PULL_TO_REFRESH) {
                currentPageIndex = 0;
            }

            if (isNormalList) {
                params.put("limit", "100");
            } else {
                params.put("limit", "10");
            }
            params.put("offset", currentPageIndex + "");

            subscriberList = new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    onRefreshFail(e.toString());
                    e.printStackTrace();
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (!StringUtil.isBlank(noDataDes)) {
                        adapter.setEmptyView(R.layout.widget_pull_to_refresh_no_data, noDataDes);
                    } else {
                        adapter.setEmptyView(R.layout.widget_pull_to_refresh_no_data);
                    }
                    adapter.loadMoreFail();
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        //写在这数据是加载完才删除
                        if (action == ACTION_PULL_TO_REFRESH) {
                            dataList.clear();
                        }

                        String responseStr = responseBody.string();
                        if (responseStr.contains("statusCode=90000")) {
                            mDataManager.tokenTimeOut(context);
                            return;
                        }

                        JSONObject jsonObject = JSON.parseObject(responseStr);
                        int totalCount = 0;
                        if (jsonObject.getString("status").equals("1")) {
                            try {
                                if (!isNormalList) {
                                    totalCount = StringUtil.stringToInt(JSON.parseObject(jsonObject.getString("data")).getString("total"));
                                }
                                responseJson = JSON.parseObject(jsonObject.getString("data"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (StringUtil.isBlank(jsonObject.getString("data"))) {
                                    if (!StringUtil.isBlank(noDataDes)) {
                                        adapter.setEmptyView(R.layout.widget_pull_to_refresh_no_data, noDataDes);
                                    } else {
                                        adapter.setEmptyView(R.layout.widget_pull_to_refresh_no_data);
                                    }
                                    adapter.setEnableLoadMore(false);
                                    Observable.timer(ZnzConstants.LODING_TIME, TimeUnit.MILLISECONDS)
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .doOnCompleted(() -> mSwipeRefreshLayout.setRefreshing(false))
                                            .subscribe();
                                }

                                if (!jsonObject.getString("data").equals("{}")) {
                                    onRefreshSuccess(jsonObject.getString("data"));
                                }

                                if (dataList.isEmpty()) {
                                    if (!StringUtil.isBlank(noDataDes)) {
                                        adapter.setEmptyView(R.layout.widget_pull_to_refresh_no_data, noDataDes);
                                    } else {
                                        adapter.setEmptyView(R.layout.widget_pull_to_refresh_no_data);
                                    }
                                    adapter.setEnableLoadMore(false);
                                } else {
                                    if (totalCount > (currentPageIndex + 1) * 10) {
                                        adapter.setEnableLoadMore(true);
                                    } else {
                                        adapter.setEnableLoadMore(false);
                                        adapter.loadMoreEnd(true);
                                    }
                                }

                                if (action == ACTION_PULL_TO_REFRESH) {
                                    Observable.timer(ZnzConstants.LODING_TIME, TimeUnit.MILLISECONDS)
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .doOnCompleted(() -> mSwipeRefreshLayout.setRefreshing(false))
                                            .subscribe();
                                }
                                adapter.loadMoreComplete();

                                //页码自增
                                currentPageIndex++;
                            }
                        } else if (jsonObject.getString("statusCode").equals("90000")) {
                            mDataManager.tokenTimeOut(context);
                        } else {
                            mDataManager.showToast(jsonObject.getString("msg"));
                            Observable.timer(ZnzConstants.LODING_TIME, TimeUnit.MILLISECONDS)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .doOnCompleted(() -> mSwipeRefreshLayout.setRefreshing(false))
                                    .subscribe();
                            if (!StringUtil.isBlank(noDataDes)) {
                                adapter.setEmptyView(R.layout.widget_pull_to_refresh_no_data, noDataDes);
                            } else {
                                adapter.setEmptyView(R.layout.widget_pull_to_refresh_no_data);
                            }
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            requestCustomeRefreshObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriberList);
        }
    }
}
