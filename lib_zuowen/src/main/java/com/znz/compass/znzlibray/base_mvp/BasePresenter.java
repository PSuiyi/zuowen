package com.znz.compass.znzlibray.base_mvp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.znz.compass.znzlibray.common.DataManager;

public abstract class BasePresenter<M, V> {
    public Context context;
    public DataManager mDataManager;
    public M mModel;
    public V mView;

    public void setVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onAttached();

        if (mView instanceof Activity) {
            context = (Context) mView;
        }
        if (mView instanceof Fragment) {
            context = ((Fragment) mView).getActivity();
        }

        mDataManager = DataManager.getInstance(context);
    }

    public abstract void onAttached();

    public void onDetached() {
    }
}
