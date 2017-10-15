package com.znz.zuowen.ui.fav;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ViewPageAdapter;
import com.znz.zuowen.base.BaseAppFragment;
import com.znz.zuowen.ui.home.video.VideoListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class FavFragment extends BaseAppFragment {
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

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_tab_layout, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("我的收藏");
        setNavLeftGone();
    }

    @Override
    protected void initializeView() {
        tabNames.add("作文");
        tabNames.add("微课");

        fragmentList.add(new FavArticleFragment());
        fragmentList.add(new VideoListFragment().newInstance("我的收藏"));

        commonViewPager.setAdapter(new ViewPageAdapter(getChildFragmentManager(), tabNames, fragmentList));
        commonTabLayout.setupWithViewPager(commonViewPager);
        commonViewPager.setOffscreenPageLimit(fragmentList.size());
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
