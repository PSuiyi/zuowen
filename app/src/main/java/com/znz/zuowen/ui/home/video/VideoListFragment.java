package com.znz.zuowen.ui.home.video;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.VideoAdapter;
import com.znz.zuowen.base.BaseAppListFragment;
import com.znz.zuowen.bean.VideoBean;
import com.znz.zuowen.model.ArticleModel;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class VideoListFragment extends BaseAppListFragment<ArticleModel, VideoBean> {

    private String page;

    public static VideoListFragment newInstance(String page) {
        Bundle args = new Bundle();
        args.putString("page", page);
        VideoListFragment fragment = new VideoListFragment();
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
        adapter = new VideoAdapter(dataList);
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
                    params.put("cate_type", "2");
                    return mModel.requestFavList(params);
                case "已购买":
                    return mModel.requestVideoMineList(params);
                default:
                    return mModel.requestVideoList(params);
            }
        } else {
            return mModel.requestVideoList(params);
        }
    }

    @Override
    protected void onRefreshSuccess(String response) {
        dataList.addAll(JSONArray.parseArray(response, VideoBean.class));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }
}
