package com.znz.zuowen.ui.home.video;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.ios.ActionSheetDialog.UIAlertDialog;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.VideoAdapter;
import com.znz.zuowen.base.BaseAppListFragment;
import com.znz.zuowen.bean.VideoBean;
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
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            VideoBean bean = dataList.get(position);
            switch (view.getId()) {
                case R.id.llContainer:
                    if (bean.getIs_buy().equals("1")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", bean.getId());
                        gotoActivity(VideoDetailAct.class, bundle);
                    } else {
                        new UIAlertDialog(activity)
                                .builder()
                                .setMsg("确定花费1个课时购买该微课？")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", v2 -> {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id", bean.getId());
                                    mModel.requestVideoBuy(params, new ZnzHttpListener() {
                                        @Override
                                        public void onSuccess(JSONObject responseOriginal) {
                                            super.onSuccess(responseOriginal);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("id", bean.getId());
                                            gotoActivity(VideoDetailAct.class, bundle);
                                            bean.setIs_buy("1");
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onFail(String error) {
                                            super.onFail(error);
                                        }
                                    });
                                })
                                .show();
                    }

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
