package com.znz.zuowen.ui.home.vote;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.VoteAdapter;
import com.znz.zuowen.base.BaseAppListFragment;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.model.ArticleModel;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class VoteListFragment extends BaseAppListFragment<ArticleModel, ArticleBean> {

    private String page;

    public static VoteListFragment newInstance(String page) {
        Bundle args = new Bundle();
        args.putString("page", page);
        VoteListFragment fragment = new VoteListFragment();
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
        adapter = new VoteAdapter(dataList);
        rvRefresh.setAdapter(adapter);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            ArticleBean bean = dataList.get(position);
            switch (view.getId()) {
                case R.id.tvVote:
                    Map<String, String> params = new HashMap<>();
                    params.put("id", bean.getId());
                    mModel.requestVoteVote(params, new ZnzHttpListener() {
                        @Override
                        public void onSuccess(JSONObject responseOriginal) {
                            super.onSuccess(responseOriginal);
                            if (!bean.getIs_vote().equals("1")) {
                                bean.setIs_vote("1");
                            } else {
                                bean.setIs_vote("0");
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFail(String error) {
                            super.onFail(error);
                        }
                    });
                    break;
            }
        });
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
                    return mModel.requestVoteList(params);
                case "初中组":
                    params.put("cate_type", "2");
                    return mModel.requestVoteList(params);
                case "高中组":
                    params.put("cate_type", "3");
                    return mModel.requestVoteList(params);
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
