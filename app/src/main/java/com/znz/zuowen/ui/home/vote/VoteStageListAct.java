package com.znz.zuowen.ui.home.vote;

import android.support.v7.widget.RecyclerView;

import com.znz.zuowen.R;
import com.znz.zuowen.adapter.VoteStageAdapter;
import com.znz.zuowen.base.BaseAppListActivity;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.model.ArticleModel;


/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class VoteStageListAct extends BaseAppListActivity<ArticleModel, ArticleBean> {

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("佳作PK");
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new VoteStageAdapter(dataList);
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
