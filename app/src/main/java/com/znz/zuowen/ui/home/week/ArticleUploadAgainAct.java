package com.znz.zuowen.ui.home.week;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ViewPageAdapter;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.model.ArticleModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Date： 2017/10/9 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleUploadAgainAct extends BaseAppActivity<ArticleModel> {
    @Bind(R.id.commonTabLayout)
    TabLayout commonTabLayout;
    @Bind(R.id.commonViewPager)
    ViewPager commonViewPager;

    private List<String> tabNames = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private String id;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_upload_again, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("上传作文");
    }

    @Override
    protected void initializeView() {
        tabNames.add("上传图片");
        tabNames.add("上传文档");

        fragmentList.add(ArticleUploadImageFragment.newInstance("2", id));
        fragmentList.add(ArticleUploadFileFragment.newInstance("2", id));

        commonViewPager.setAdapter(new ViewPageAdapter(getSupportFragmentManager(), tabNames, fragmentList));
        commonTabLayout.setupWithViewPager(commonViewPager);
        commonViewPager.setOffscreenPageLimit(fragmentList.size());
    }

    @Override
    protected void loadDataFromServer() {
    }
}
