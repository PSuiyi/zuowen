package com.znz.zuowen.ui.home.article;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ArticleAdapter;
import com.znz.zuowen.adapter.ArticleMineAdapter;
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

public class ArticleListFragment extends BaseAppListFragment<ArticleModel, ArticleBean> {

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
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        if (!StringUtil.isBlank(page)) {
            switch (page) {
                case "我的收藏":
                    params.put("cate_type", "1");
                    return mModel.requestFavList(params);
                case "优秀作文":
                    return mModel.requestGoodList(params);
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
        if (!StringUtil.isBlank(page)) {
            switch (page) {
                case "优秀作文":
                    dataList.addAll(JSONArray.parseArray(responseJson.getString("list"), ArticleBean.class));
                    break;
                default:
                    dataList.addAll(JSONArray.parseArray(response, ArticleBean.class));
                    break;
            }
        } else {
            dataList.addAll(JSONArray.parseArray(response, ArticleBean.class));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }
}
