package com.znz.compass.znzlibray.base_znz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bugtags.library.Bugtags;
import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.network_status.NetChangeObserver;
import com.znz.compass.znzlibray.network_status.NetStateReceiver;
import com.znz.compass.znzlibray.network_status.NetUtils;
import com.znz.compass.znzlibray.utils.ActivityStackManager;
import com.znz.compass.znzlibray.utils.ViewHolder;
import com.znz.compass.znzlibray.utils.ZnzLog;
import com.znz.compass.znzlibray.views.CoordinatorLayoutWithLoading;
import com.znz.compass.znzlibray.views.ZnzLodingDialog;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.swipeback.SwipeBackActivity;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * activity基类
 */
public abstract class BaseZnzActivity extends SwipeBackActivity implements IView {
    protected String mPageName = "BaseZnzActivity";
    protected String TAG = "BaseZnzActivity";

    protected DataManager mDataManager;
    protected Context context;
    protected Activity activity;

    protected ZnzToolBar znzToolBar;
    protected ZnzRemind znzRemind;
    protected CoordinatorLayoutWithLoading znzCoordinator;
    protected LinearLayout llNetworkStatus;
    protected boolean isNetworReConnect;
    protected int currentToolbarMode;

    /**
     * network status
     */
    protected NetChangeObserver mNetChangeObserver = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //参数初始化
        context = this;
        activity = this;
        mPageName = this.getClass().getName();
        TAG = this.getClass().getSimpleName();
        mDataManager = DataManager.getInstance(this);

        ZnzLog.i(mPageName);
        ZnzLog.i(TAG);

        //本地控件初始化
        ActivityStackManager.getInstance().addActivity(new WeakReference<>(this));
//        PushAgent.getInstance(context).onAppStart();//统计app启动数

        if (getLayoutResource().length == 0) {
            mDataManager.showToast("必须在getLayoutResource()中返回布局id");
            ZnzLog.i("You must return a right contentView layout resource Id");
            setContentView(R.layout.demo_activity);
        } else {
            setContentView(getLayoutResource()[0]);
        }
        if (getLayoutResource().length >= 2) {
            if (getLayoutResource()[1] != 0) {
                initializeBaseView(getLayoutResource()[1]);
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
                isNetworReConnect = true;
            }

            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
                isNetworReConnect = false;
            }
        };

        NetStateReceiver.registerObserver(mNetChangeObserver);
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
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //第三方初始化
        ButterKnife.bind(activity);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        //设置字体大小不随系统变化
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 初始化nav中的控件等
     *
     * @param mode
     */
    protected void initializeBaseView(int mode) {
        try {
            //toolbar设置
            znzToolBar = bindViewById(this, R.id.znzToolBar);
            znzToolBar.setToolMode(mode);
            setSupportActionBar(znzToolBar.getToolbar());
            currentToolbarMode = mode;

            switch (mode) {
                case 1:
                case 3://普通模式
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    znzToolBar.getToolbar().setNavigationOnClickListener(v -> onBackPressed());
                    break;
                case 2://搜索模式
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    znzToolBar.getToolbar().setNavigationOnClickListener(v -> onBackPressed());
                    znzToolBar.setOnSearchRightClickListener(v -> onBackPressed());
                    break;
                case 4://透明模式
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    znzToolBar.getToolbar().setNavigationOnClickListener(v -> onBackPressed());
                    break;
            }

            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            //设置沉浸式
//            StatusBarUtils.setTranslucentImageHeader(this, 0, znzToolBar.getToolbar());
//            znzToolBar.setOnNavLeftClickListener(v -> finish());

            //remind about
            znzRemind = ViewHolder.init(this, R.id.znzRemind);
            znzRemind.setOnNoWifiClickLinstener(v -> {
                onNetworkConnected(NetUtils.NetType.WIFI);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //折叠布局，带loding
            znzCoordinator = bindViewById(this, R.id.znzCoordinator);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //网络状态显示
            llNetworkStatus = bindViewById(this, R.id.llNetworkStatus);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    //-------------------进度dialog---------------------

    protected ZnzLodingDialog pd;

    /**
     * 显示dialog
     */
    protected void showPd() {
        pd = new ZnzLodingDialog(activity, R.style.progressDialog, true);
        if (!pd.isShowing()) {
            pd.show();
        }
    }

    /**
     * 显示dialog
     */
    protected void showPd(boolean canCancel) {
        pd = new ZnzLodingDialog(activity, R.style.progressDialog, canCancel);
        if (!pd.isShowing()) {
            pd.show();
        }
    }

    /**
     * 取消dialog
     */
    protected void hidePd() {
        try {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        if (znzCoordinator != null) {
            znzCoordinator.showLoading();
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
        if (znzCoordinator != null) {
            znzCoordinator.showContent();
        }
    }


    /**
     * 显示没有数据提醒
     */
    protected void showNoData() {
        znzRemind.setVisibility(View.VISIBLE);
        znzRemind.showNoData();
    }

    /**
     * 显示没有数据提醒
     */
    protected void showNoData(String message) {
        znzRemind.setVisibility(View.VISIBLE);
        znzRemind.showNoData(message);
    }

    /**
     * 隐藏没有数据提醒
     */
    protected void hideNoData() {
        znzRemind.setVisibility(View.GONE);
        znzRemind.hideNoData();
    }

    /**
     * activity跳转
     *
     * @param cls
     */
    public void gotoActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        this.startActivity(intent);
    }

    /**
     * activity跳转并且清除之前的activit栈
     *
     * @param cls
     */
    public void gotoActivityWithClearStack(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }

    /**
     * activity跳转无跳转效果
     *
     * @param cls
     */
    public void gotoActivityWithoutPendingTransition(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        this.startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    /**
     * activity跳转
     *
     * @param cls
     * @param bundle
     */
    public void gotoActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
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
        Intent intent = new Intent(this, cls);
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
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivityForResult(intent, requestCode);
    }


    /**
     * 绑定控件
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT bindViewById(@IdRes int id) {
        return (VT) findViewById(id);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityStackManager.getInstance().removeActivity(new WeakReference<>(this));
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bugtags.onPause(this);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
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