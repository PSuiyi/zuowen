package com.znz.zuowen.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSONObject;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.eventbus.BaseEvent;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.FragmentUtil;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.common.Constants;
import com.znz.zuowen.event.EventRefresh;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.UserModel;
import com.znz.zuowen.ui.fav.FavFragment;
import com.znz.zuowen.ui.home.HomeFragment;
import com.znz.zuowen.ui.login.LoginAct;
import com.znz.zuowen.ui.mine.MineFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


public class TabHomeAct extends BaseAppActivity<UserModel> {
    @Bind(R.id.main_container)
    LinearLayout mainContainer;
    @Bind(R.id.radioButton1)
    RadioButton radioButton1;
    @Bind(R.id.radioButton3)
    RadioButton radioButton3;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.radioButton2)
    RadioButton radioButton2;
    private HomeFragment homeFragment;
    private FavFragment favFragment;
    private MineFragment mineFragment;
    private FragmentUtil fragmentUtil;
    private FragmentManager fragmentManager;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_tab};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
        new RxPermissions(activity)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE)
                .subscribe(granted -> {
                    if (granted) {

                    } else {
                        // At least one permission is denied
                    }
                });
    }

    @Override
    protected void initializeNavigation() {
        setSwipeBackEnable(false);
    }

    @Override
    protected void initializeView() {
        fragmentUtil = new FragmentUtil();
        fragmentManager = getSupportFragmentManager();

        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        fragmentManager.beginTransaction().add(R.id.main_container, homeFragment).commit();
        fragmentUtil.mContent = homeFragment;
    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        mModel.requestAgreement(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                if (StringUtil.isBlank(responseObject.getString("infos"))) {
                    return;
                }
                mDataManager.saveTempData(Constants.ZHIFU_AGREEMENT, responseObject.getString("infos"));
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });

    }

    @OnClick({R.id.radioButton1, R.id.radioButton2, R.id.radioButton3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioButton1:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                fragmentUtil.switchContent(homeFragment, R.id.main_container, fragmentManager);
                break;
            case R.id.radioButton2:
                if (favFragment == null) {
                    favFragment = new FavFragment();
                }
                fragmentUtil.switchContent(favFragment, R.id.main_container, fragmentManager);
                EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_MINE_FAV));
                break;
            case R.id.radioButton3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
                fragmentUtil.switchContent(mineFragment, R.id.main_container, fragmentManager);
                break;
        }
    }

    private long mExitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            DataManager.getInstance(this).showToast(R.string.app_exit_again);
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEvent event) {
        if (event.getFlag() == 0x90000) {
            mDataManager.logout(activity, LoginAct.class);
        }
    }
}
