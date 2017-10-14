package com.znz.zuowen.ui.home.week;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.WeekAdapter;
import com.znz.zuowen.base.BaseAppListFragment;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.model.ArticleModel;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class WeekListFragment extends BaseAppListFragment<ArticleModel, ArticleBean> {

    private String page;

    public static WeekListFragment newInstance(String page) {
        Bundle args = new Bundle();
        args.putString("page", page);
        WeekListFragment fragment = new WeekListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
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
        adapter = new WeekAdapter(dataList);
        rvRefresh.setAdapter(adapter);
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        if (!StringUtil.isBlank(page)) {
            switch (page) {
                case "小学组":
                    params.put("cate_type", "1");
                    return mModel.requestWeekList(params);
                case "初中组":
                    params.put("cate_type", "2");
                    return mModel.requestWeekList(params);
                case "高中组":
                    params.put("cate_type", "3");
                    return mModel.requestWeekList(params);
                default:
                    return mModel.requestHomeList(params);
            }
        } else {
            return mModel.requestHomeList(params);
        }
    }

    @Override
    protected void onRefreshSuccess(String response) {
        dataList.addAll(JSONArray.parseArray(response, ArticleBean.class));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }
}
