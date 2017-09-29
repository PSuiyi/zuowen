package com.znz.zuowen.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.utils.FragmentUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.ui.fav.FavFragment;
import com.znz.zuowen.ui.home.HomeFragment;
import com.znz.zuowen.ui.mine.MineFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TabHomeAct extends BaseAppActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
