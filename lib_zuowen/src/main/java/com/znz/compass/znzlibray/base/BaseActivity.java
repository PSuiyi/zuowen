package com.znz.compass.znzlibray.base;

import android.view.View;

import com.znz.compass.znzlibray.base_znz.BaseZnzActivity;
import com.znz.compass.znzlibray.base_znz.IModel;
import com.znz.compass.znzlibray.network_status.NetUtils;

/**
 * activity基类
 */
public abstract class BaseActivity<M extends IModel> extends BaseZnzActivity {
    protected M mModel;

    @Override
    protected void initializeAdvance() {
        initializeVariate();
        initializeNavigation();
        initializeAppBusiness();
        initializeView();
        if (!NetUtils.isNetworkAvailable(context)) {
            onNetworkDisConnected();
        } else {
            loadDataFromServer();
        }
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
     * 视图初始化
     */
    protected abstract void initializeView();

    /**
     * 调用接口
     */
    protected abstract void loadDataFromServer();


    /**
     * 监听到网络连接的状态
     *
     * @param type
     */
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        loadDataFromServer();

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
}