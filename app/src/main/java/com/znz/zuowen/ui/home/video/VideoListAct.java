package com.znz.zuowen.ui.home.video;

import android.support.v4.app.Fragment;

import com.znz.compass.znzlibray.utils.FragmentUtil;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;

/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class VideoListAct extends BaseAppActivity {

    private Fragment fragment;
    private String page;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_activity_with_fragment, 1};
    }

    @Override
    protected void initializeVariate() {
        if (getIntent().hasExtra("page")) {
            page = getIntent().getStringExtra("page");
        }
    }

    @Override
    protected void initializeNavigation() {
        if (!StringUtil.isBlank(page)) {
            if (page.equals("已购买")) {
                setTitleName("已购买微课堂");
            } else {
                setTitleName("微课堂");
            }
        } else {
            setTitleName("微课指导");
        }
    }

    @Override
    protected void initializeView() {
        if (fragment == null) {
            if (!StringUtil.isBlank(page)) {
                fragment = new VideoListFragment().newInstance(page);
            } else {
                fragment = new VideoListFragment();
            }
        }
        FragmentUtil.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.container);
    }

    @Override
    protected void loadDataFromServer() {

    }
}
