package com.znz.compass.znzlibray.base_mvp;

import com.znz.compass.znzlibray.base_znz.BaseZnzActivity;
import com.znz.compass.znzlibray.base_znz.IModel;

/**
 * Package_Name： com.znz.compass.znzlibray.base_mvp
 * Project_Name： builder
 * Date： 2016/12/5 2016
 * User： PSuiyi
 * Description：
 */

public abstract class BaseMvpActivity<P extends BasePresenter, M extends IModel> extends BaseZnzActivity implements BaseView {
    protected P mPresenter;

    @Override
    protected void initializeAdvance() {
        initializeMVP();
        initializeVariate();
        initializeNavigation();
        initializeView();
        loadDataFromServer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDetached();
    }

    /**
     * 加载layout，fragment中需要返回int数组，int[0]是控件id，int[1]控制是否使用znzToolbar
     *
     * @return
     */
    protected abstract int[] getLayoutResource();

    /**
     * 初始化MVP相关
     */
    protected abstract void initializeMVP();

    /**
     * 初始化数据
     */
    protected abstract void initializeVariate();

    /**
     * 导航栏初始化
     */
    protected abstract void initializeNavigation();

    /**
     * 视图初始化
     */
    protected abstract void initializeView();

    /**
     * 调用接口
     */
    protected abstract void loadDataFromServer();
}