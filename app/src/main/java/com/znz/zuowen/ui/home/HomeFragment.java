package com.znz.zuowen.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.znz.compass.znzlibray.views.advs.bean.AdvInfoBean;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.MultiAdapter;
import com.znz.zuowen.base.BaseAppListFragment;
import com.znz.zuowen.bean.MultiBean;
import com.znz.zuowen.common.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class HomeFragment extends BaseAppListFragment {

    @Bind(R.id.llNavLeft)
    LinearLayout llNavLeft;
    @Bind(R.id.llNavRight)
    LinearLayout llNavRight;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsBarLayout)
    CollapsingToolbarLayout collapsBarLayout;
    @Bind(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @Bind(R.id.rvCommonRefresh)
    RecyclerView rvCommonRefresh;
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private View header;
    private BGABanner mBanner;
    private List<AdvInfoBean> advList = new ArrayList<>();

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.frag_home};
    }

    @Override
    protected void initializeVariate() {
        dataList.add(new MultiBean(Constants.MultiType.Section, "国内面料"));
        dataList.add(new MultiBean(Constants.MultiType.Article));
        dataList.add(new MultiBean(Constants.MultiType.Article));
        dataList.add(new MultiBean(Constants.MultiType.Article));
        dataList.add(new MultiBean(Constants.MultiType.Article));
        dataList.add(new MultiBean(Constants.MultiType.Article));

        AdvInfoBean bean = new AdvInfoBean();
        bean.setName("ceshi");
        bean.setImg("https://gju1.alicdn.com/tps/i3/175880295535497075/TB2RSwHXMUc61BjSZFvXXXKfVXa_!!0-juitemmedia.jpg_460x460Q90.jpg_.webp");
        advList.add(bean);
        advList.add(bean);
        advList.add(bean);
        advList.add(bean);
    }

    @Override
    protected void initializeNavigation() {
        toolbar.setNavigationOnClickListener(v -> finish());
        collapsBarLayout.setTitle("");
        collapsBarLayout.setCollapsedTitleTextColor(mDataManager.getColor(R.color.white));
        collapsBarLayout.setExpandedTitleColor(mDataManager.getColor(R.color.white));
        collapsBarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
//            if (Math.abs(verticalOffset) >= (Math.abs(appBarLayout1.getTotalScrollRange()))) {
//                toolbar.setBackgroundColor(mDataManager.getColor(R.color.nav_backgroud));
//            } else {
//                toolbar.setBackgroundColor(mDataManager.getColor(R.color.trans));
//            }
        });

        llNavRight.setOnClickListener(v -> {

        });

        llNavLeft.setOnClickListener(v -> {

        });
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new MultiAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        header = View.inflate(activity, R.layout.header_home, null);
        adapter.addHeaderView(header);

        bindViewById(header, R.id.llMenu1).setOnClickListener(v -> {
        });
        bindViewById(header, R.id.llMenu2).setOnClickListener(v -> {
        });
        bindViewById(header, R.id.llMenu3).setOnClickListener(v -> {
        });
        bindViewById(header, R.id.llMenu4).setOnClickListener(v -> {
        });

        mBanner = (BGABanner) header.findViewById(R.id.banner);
        mBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(activity)
                        .load(model)
                        .placeholder(R.mipmap.default_image_rect)
                        .error(R.mipmap.default_image_rect)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });


        mBanner.setDelegate((banner, itemView, model, position) -> {
        });


        List<String> advUrls = new ArrayList<>();
        List<String> advTitles = new ArrayList<>();

        for (AdvInfoBean advInfoBean : advList) {
            advUrls.add(advInfoBean.getImg());
            advTitles.add(advInfoBean.getName());
        }

        mBanner.setData(advUrls, advTitles);
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected void onRefreshSuccess(String response) {

    }

    @Override
    protected void onRefreshFail(String error) {

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
