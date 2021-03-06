package com.znz.zuowen.ui.home.vote;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ViewPageAdapter;
import com.znz.zuowen.base.BaseAppActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Date： 2017/9/28 2017
 * User： PSuiyi
 * Description：
 */

public class VoteAct extends BaseAppActivity {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.commonTabLayout)
    TabLayout commonTabLayout;
    @Bind(R.id.commonViewPager)
    ViewPager commonViewPager;

    private List<String> tabNames = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private String id;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_tab_layout, 1};
    }

    @Override
    protected void initializeVariate() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("佳作PK");
    }

    @Override
    protected void initializeView() {
        tabNames.add("小学组");
        tabNames.add("初中组");
        tabNames.add("高中组");

        fragmentList.add(new VoteListFragment().newInstance("小学组", id));
        fragmentList.add(new VoteListFragment().newInstance("初中组", id));
        fragmentList.add(new VoteListFragment().newInstance("高中组", id));

        commonViewPager.setAdapter(new ViewPageAdapter(getSupportFragmentManager(), tabNames, fragmentList));
        commonTabLayout.setupWithViewPager(commonViewPager);
        commonViewPager.setOffscreenPageLimit(fragmentList.size());
    }

    @Override
    protected void loadDataFromServer() {

    }
}
