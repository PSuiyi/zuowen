package com.znz.zuowen.ui.home.article;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ArticleAdapter;
import com.znz.zuowen.adapter.ArticleMineAdapter;
import com.znz.zuowen.base.BaseAppListFragment;


/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleListFragment extends BaseAppListFragment {

    private String page;

    public static ArticleListFragment newInstance(String page) {
        Bundle args = new Bundle();
        args.putString("page", page);
        ArticleListFragment fragment = new ArticleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout};
    }

    @Override
    protected void initializeVariate() {
        if (getArguments() != null) {
            page = getArguments().getString("page");
        }
    }

    @Override
    protected void initializeNavigation() {

    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        if (!StringUtil.isBlank(page)) {
            switch (page) {
                case "我的作文":
                    adapter = new ArticleMineAdapter(dataList);
                    break;
                default:
                    adapter = new ArticleAdapter(dataList);
                    ((ArticleAdapter) adapter).setPage(page);
                    break;
            }
        } else {
            adapter = new ArticleAdapter(dataList);
        }
        rvRefresh.setAdapter(adapter);
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
}
