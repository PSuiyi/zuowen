package com.znz.zuowen.base;

import android.os.Bundle;

import com.znz.compass.znzlibray.base.BaseActivity;
import com.znz.compass.znzlibray.base_znz.IModel;

/**
 * Date： 2017/9/4 2017
 * User： PSuiyi
 * Description：
 */

public abstract class BaseAppActivity<M extends IModel> extends BaseActivity<M> {

    @Override
    protected void initializeAppBusiness() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mModel != null) {
            mModel.MODestory();
        }
    }
}
