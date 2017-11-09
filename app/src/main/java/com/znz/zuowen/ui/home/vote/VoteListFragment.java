package com.znz.zuowen.ui.home.vote;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.VoteAdapter;
import com.znz.zuowen.base.BaseAppListFragment;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.event.EventList;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.ArticleModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private String id;

    public static VoteListFragment newInstance(String page,String id) {
        Bundle args = new Bundle();
        args.putString("page", page);
        args.putString("id", id);
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
            id = getArguments().getString("id");
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
                    if (bean.getIs_vote().equals("0")) {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", bean.getId());
                        mModel.requestVoteVote(params, new ZnzHttpListener() {
                            @Override
                            public void onSuccess(JSONObject responseOriginal) {
                                super.onSuccess(responseOriginal);
                                if (!bean.getIs_vote().equals("1")) {
                                    bean.setIs_vote("1");
                                    bean.setVote_count(StringUtil.getNumUP(bean.getVote_count()));
                                } else {
                                    bean.setIs_vote("0");
                                    bean.setVote_count(StringUtil.getNumDown(bean.getVote_count()));
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFail(String error) {
                                super.onFail(error);
                            }
                        });
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
            params.put("vote_num_id", id);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventList event) {
        if (event.getFlag() == EventTags.LIST_ARTICLE_FAV) {
            int position = dataList.indexOf(event.getBean());
            dataList.get(position).setCollect_count(((ArticleBean) event.getBean()).getCollect_count());
            adapter.notifyDataSetChanged();
        }
        if (event.getFlag() == EventTags.LIST_ARTICLE_LIKE) {
            int position = dataList.indexOf(event.getBean());
            dataList.get(position).setLike_count(((ArticleBean) event.getBean()).getLike_count());
            adapter.notifyDataSetChanged();
        }
        if (event.getFlag() == EventTags.LIST_ARTICLE_VOTE) {
            int position = dataList.indexOf(event.getBean());
            dataList.get(position).setIs_vote(((ArticleBean) event.getBean()).getIs_vote());
            dataList.get(position).setVote_count(((ArticleBean) event.getBean()).getVote_count());
            adapter.notifyDataSetChanged();
        }
    }
}
