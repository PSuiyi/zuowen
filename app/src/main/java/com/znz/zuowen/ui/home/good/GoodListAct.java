package com.znz.zuowen.ui.home.good;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ArticleAdapter;
import com.znz.zuowen.base.BaseAppListActivity;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.model.ArticleModel;
import com.znz.zuowen.utils.PopupWindowManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class GoodListAct extends BaseAppListActivity<ArticleModel, ArticleBean> {

    @Bind(R.id.tvLeft)
    TextView tvLeft;
    @Bind(R.id.llLeft)
    LinearLayout llLeft;
    @Bind(R.id.tvRight)
    TextView tvRight;
    @Bind(R.id.llRight)
    LinearLayout llRight;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_good, 2};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        znzToolBar.setSearchHint("请输入作文题目");
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new ArticleAdapter(dataList);
        rvRefresh.setAdapter(adapter);
        ((ArticleAdapter) adapter).setPage("优秀作文");
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        return mModel.requestGoodList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        dataList.addAll(JSONArray.parseArray(responseJson.getString("list"), ArticleBean.class));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.llLeft, R.id.llRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llLeft:
                PopupWindowManager.getInstance(activity).showOptionLeft(view);
                break;
            case R.id.llRight:
                PopupWindowManager.getInstance(activity).showOptionRight(view);
                break;
        }
    }
}
