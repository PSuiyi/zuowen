package com.znz.compass.znzlibray.base_znz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.network_status.NetChangeObserver;
import com.znz.compass.znzlibray.network_status.NetStateReceiver;
import com.znz.compass.znzlibray.network_status.NetUtils;
import com.znz.compass.znzlibray.utils.ViewHolder;
import com.znz.compass.znzlibray.views.ZnzLodingDialog;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;

import butterknife.ButterKnife;


/**
 * fragment基类
 */
public abstract class BaseZnzFragment extends Fragment implements IView {
    protected View rootView;
    protected DataManager mDataManager;
    protected Context context;
    protected Activity activity;

    protected ZnzToolBar znzToolBar;
    protected ZnzRemind znzRemind;
    protected LinearLayout llNetworkStatus;

    protected boolean isViewCreated;
    protected boolean isViewVisiable;

    /**
     * network status
     */
    protected NetChangeObserver mNetChangeObserver = null;

    @Override
    public void onAttach(Context mContext) {
        super.onAttach(mContext);
        if (mContext != null) {
            this.context = mContext;
            activity = (Activity) mContext;
        } else {
            this.context = getActivity();
            activity = getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //参数初始化
        mDataManager = DataManager.getInstance(activity);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isViewCreated && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isViewCreated = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        isViewCreated = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isViewVisiable = true;
            return;
        }
        if (isViewVisiable) {
            onFragmentVisibleChange(false);
            isViewVisiable = false;
        }
    }

    /**
     * 用于懒加载
     *
     * @param isVisible
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutResource().length == 0) {
            mDataManager.showToast("必须在getLayoutResource()中返回布局id");
            if (rootView == null) {
                rootView = inflater.inflate(R.layout.demo_activity, container, false);
            }
        } else {
            if (rootView == null) {
                rootView = inflater.inflate(getLayoutResource()[0], container, false);
            }
        }

        ButterKnife.bind(this, rootView);
        if (getLayoutResource().length >= 2) {
            if (getLayoutResource()[1] != 0) {
                initializeBaseView(rootView, getLayoutResource()[1]);
            }
            initializeAdvance();
        } else {
            initializeAdvance();
        }

        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };

        NetStateReceiver.registerObserver(mNetChangeObserver);
        return rootView;
    }

    /**
     * 监听到网络连接的状态
     *
     * @param type
     */
    protected abstract void onNetworkConnected(NetUtils.NetType type);

    /**
     * 监听到网络未连接的状态
     */
    protected abstract void onNetworkDisConnected();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
    }

    /**
     * 初始化导航栏，通用提醒等
     *
     * @param rootView
     * @param mode
     */
    protected void initializeBaseView(View rootView, int mode) {
        try {
            //toolbar设置
            znzToolBar = bindViewById(rootView, R.id.znzToolBar);
            znzToolBar.setToolMode(mode);
            ((AppCompatActivity) getActivity()).setSupportActionBar(znzToolBar.getToolbar());

            switch (mode) {
                case 1:
                case 3:
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    znzToolBar.getToolbar().setNavigationOnClickListener(v -> finish());
                    break;
                case 2:
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    znzToolBar.getToolbar().setNavigationOnClickListener(v -> finish());
                    znzToolBar.setOnSearchRightClickListener(v -> finish());
                    break;
            }
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

//            //设置沉浸式
//            StatusBarUtils.setTranslucentImageHeader(activity, 0, znzToolBar.getToolbar());

            //remind about
            znzRemind = ViewHolder.init(rootView, R.id.znzRemind);
            znzRemind.setOnNoWifiClickLinstener(v -> {
                onNetworkConnected(NetUtils.NetType.WIFI);
            });
        } catch (Exception e) {
//            e.printStackTrace();
        }

        try {
            //网络状态显示
            llNetworkStatus = bindViewById(rootView, R.id.llNetworkStatus);
            llNetworkStatus.setOnClickListener(v -> {
                mDataManager.openSettingWifi(activity);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载layout，fragment中需要返回int数组，int[0]是控件id，int[1]控制是否使用znzToolbar
     *
     * @return
     */
    protected abstract int[] getLayoutResource();


    /**
     * 初始化
     */
    protected abstract void initializeAdvance();


    /**
     * 设置标题名称
     */
    protected void setTitleName(String name) {
        znzToolBar.setTitleName(name);
    }

    /**
     * 隐藏左边返回按钮
     */
    protected void setNavLeftGone() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /**
     * 结束activity；
     */
    protected void finish() {
        getActivity().finish();
    }

    /**
     * activity跳转
     *
     * @param cls
     */
    public void gotoActivity(Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        this.startActivity(intent);
    }

    /**
     * activity跳转
     *
     * @param cls
     * @param bundle
     */
    public void gotoActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivity(intent);
    }

    /**
     * activity跳转
     *
     * @param cls
     * @param requestCode
     */
    public void gotoActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(activity, cls);
        this.startActivityForResult(intent, requestCode);
    }

    /**
     * activity跳转
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void gotoActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivityForResult(intent, requestCode);
    }

    /**
     * 隐藏界面
     */
    protected void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            // 这里可能会报空指针错误
        }
    }

    /**
     * 绑定控件
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT bindViewById(@IdRes int id) {
        return (VT) rootView.findViewById(id);
    }

    /**
     * 绑定控件
     *
     * @param view
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T bindViewById(View view, @IdRes int resId) {
        View childView = view.findViewById(resId);
        return (T) childView;
    }


    /**
     * 绑定控件
     *
     * @param activity
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T bindViewById(Activity activity, int resId) {
        View childView = activity.findViewById(resId);
        return (T) childView;
    }

    //-------------------进度dialog---------------------

    protected ZnzLodingDialog pd;

    /**
     * 显示dialog
     */
    protected void showPd() {
        pd = new ZnzLodingDialog(activity, R.style.progressDialog, true);
        pd.show();
    }

    /**
     * 显示dialog
     */
    protected void showPd(boolean canCancel) {
        pd = new ZnzLodingDialog(activity, R.style.progressDialog, canCancel);
        pd.show();
    }

    /**
     * 取消dialog
     */
    protected void hidePd() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }


    /**
     * 显示loding
     */
    protected void showLoding() {
        if (znzRemind != null) {
            znzRemind.setVisibility(View.VISIBLE);
            znzRemind.showLoding();
        }
    }

    /**
     * 隐藏loding
     */
    protected void hideLoding() {
        if (znzRemind != null) {
            znzRemind.setVisibility(View.GONE);
            znzRemind.hideLoding();
        }
    }

    /**
     * 显示没有数据提醒
     */
    protected void showNoData() {
        if (znzRemind != null) {
            znzRemind.setVisibility(View.VISIBLE);
            znzRemind.showNoData();
        }
    }

    /**
     * 显示没有数据提醒
     */
    protected void showNoData(String message) {
        if (znzRemind != null) {
            znzRemind.setVisibility(View.VISIBLE);
            znzRemind.showNoData(message);
        }
    }

    /**
     * 隐藏没有数据提醒
     */
    protected void hideNoData() {
        if (znzRemind != null) {
            znzRemind.setVisibility(View.GONE);
            znzRemind.hideNoData();
        }
    }

    @Override
    public void UIShowPd() {
        showPd();
    }

    @Override
    public void UIHidePd() {
        hidePd();
    }

    @Override
    public void UIShowLoding() {
        showLoding();
    }

    @Override
    public void UIHideLoding() {
        hideLoding();
    }
}
