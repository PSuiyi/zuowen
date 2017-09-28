package com.znz.compass.znzlibray.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.base_znz.BaseZnzActivity;
import com.znz.compass.znzlibray.base_znz.IModel;
import com.znz.compass.znzlibray.network_status.NetUtils;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;


/**
 * 列表基类
 */

public abstract class BaseListActivity<M extends IModel, T> extends BaseZnzActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    protected ArrayList<T> dataList = new ArrayList<>();
    protected M mModel;
    protected int currentPageIndex = 0; // 当前页码
    protected RecyclerView rvRefresh;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected BaseQuickAdapter adapter;
    protected JSONObject responseJson;
    protected Map<String, String> params;//请求参数
    protected Subscriber<ResponseBody> subscriberList;
    protected int currentAction;

    public static final int ACTION_PULL_TO_REFRESH = 1;
    public static final int ACTION_LOAD_MORE_REFRESH = 2;
    protected boolean isNormalList;
    protected String noDataDes;


    @Override
    protected void initializeAdvance() {
        initializeVariate();
        initializeRefreshView();
        initializeNavigation();
        initializeAppBusiness();
        initializeView();

        if (adapter != null) {
            adapter.setOnLoadMoreListener(this, rvRefresh);
            if (isNormalList) {
                adapter.setEnableLoadMore(false);
            }
        }

        if (!NetUtils.isNetworkAvailable(context)) {
            onNetworkDisConnected();
        } else {
            loadDataFromServer();
        }
    }

    protected void initializeRefreshView() {
        //刷新控件初始化
        rvRefresh = bindViewById(activity, R.id.rvCommonRefresh);
        mSwipeRefreshLayout = bindViewById(activity, R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(mDataManager.getColor(R.color.colorPrimary));
        if (getLayoutManager() != null) {
            rvRefresh.setLayoutManager(getLayoutManager());
        } else {
            rvRefresh.setLayoutManager(new LinearLayoutManager(activity));
        }
        //初次加载出现loading
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
        });
        customeRefreshRequest(ACTION_PULL_TO_REFRESH);
    }

    /**
     * 加载layout，fragment中需要返回int数组，int[0]是控件id，int[1]控制是否使用znzToolbar
     *
     * @return
     */
    protected abstract int[] getLayoutResource();

    /**
     * 初始化数据
     */
    protected abstract void initializeVariate();

    /**
     * 导航栏初始化
     */
    protected abstract void initializeNavigation();

    /**
     * 初始化app逻辑
     */
    protected abstract void initializeAppBusiness();

    /**
     * recyclerview加载方式
     *
     * @return
     */
    protected abstract RecyclerView.LayoutManager getLayoutManager();

    /**
     * 视图初始化
     */
    protected abstract void initializeView();

    /**
     * 调用接口
     */
    protected abstract void loadDataFromServer();

    /**
     * 接口请求成功
     *
     * @param response 返回值str
     */
    protected abstract void onRefreshSuccess(String response);

    /**
     * 接口请求失败
     *
     * @param error
     */
    protected abstract void onRefreshFail(String error);


    /**
     * 自定义请求
     *
     * @return
     */
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        return null;
    }


    /**
     * 设置不能刷新和加载
     */
    protected void setNoRefreshAndLoad() {
        isNormalList = true;
    }

    /**
     * 设置没有数据描述
     *
     * @param des
     */
    protected void setNoDataDes(String des) {
        this.noDataDes = des;
    }

    /**
     * 监听到网络连接的状态
     *
     * @param type
     */
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        loadDataFromServer();
        resetRefresh();
        if (znzRemind != null) {
            znzRemind.setVisibility(View.GONE);
            znzRemind.hideNoWifi();
        }

        if (llNetworkStatus != null) {
            llNetworkStatus.setVisibility(View.GONE);
        }
    }

    /**
     * 监听到网络未连接的状态
     */
    @Override
    protected void onNetworkDisConnected() {
        if (llNetworkStatus != null) {
            llNetworkStatus.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 手动刷新
     */
    protected void resetRefresh() {
        currentPageIndex = 0;
        customeRefreshRequest(ACTION_PULL_TO_REFRESH);
    }

    @Override
    public void onLoadMoreRequested() {
        customeRefreshRequest(ACTION_LOAD_MORE_REFRESH);
    }

    @Override
    public void onRefresh() {
        if (!NetUtils.isNetworkAvailable(context)) {
            onNetworkDisConnected();
        } else {
            customeRefreshRequest(ACTION_PULL_TO_REFRESH);
        }
    }

    protected abstract void customeRefreshRequest(int actionPullToRefresh);

    /**
     * 临时数据
     */
    protected void setTempDataList() {
        //临时数据，写UI时用
        if (dataList.isEmpty()) {
            List<BaseZnzBean> tempList = new ArrayList<>();
            tempList.add(new BaseZnzBean());
            tempList.add(new BaseZnzBean());
            tempList.add(new BaseZnzBean());
            tempList.add(new BaseZnzBean());
            tempList.add(new BaseZnzBean());
            tempList.add(new BaseZnzBean());
            tempList.add(new BaseZnzBean());
            tempList.add(new BaseZnzBean());

            dataList.clear();
            dataList.addAll((Collection<? extends T>) tempList);

            if (adapter != null) {
                adapter.loadMoreComplete();
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }

//        //没有数据的情况,真实环境中使用
//        rvRefresh.enableLoadMore(false);
//        rvRefresh.onRefreshCompleted();
//        adapter.setNoData(true);
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscriberList != null) {
            if (!subscriberList.isUnsubscribed()) {
                subscriberList.unsubscribe();
            }
        }
    }
}
