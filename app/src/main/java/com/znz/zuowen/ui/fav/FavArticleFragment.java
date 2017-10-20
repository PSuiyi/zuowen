package com.znz.zuowen.ui.fav;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.MultiAdapter;
import com.znz.zuowen.base.BaseAppListFragment;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.bean.MultiBean;
import com.znz.zuowen.common.Constants;
import com.znz.zuowen.event.EventList;
import com.znz.zuowen.event.EventRefresh;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.ArticleModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/10/15 2017
 * User： PSuiyi
 * Description：
 */

public class FavArticleFragment extends BaseAppListFragment<ArticleModel, MultiBean> {
    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
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
        adapter = new MultiAdapter(dataList);
        rvRefresh.setAdapter(adapter);
        ((MultiAdapter) adapter).setOnVoteClickLinstener(bean -> {
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
        });
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        params.put("cate_type", "1");
        return mModel.requestFavList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        for (ArticleBean articleBean : JSONArray.parseArray(response, ArticleBean.class)) {
            switch (articleBean.getMytype()) {
                case "1":
                    dataList.add(new MultiBean(Constants.MultiType.Article, articleBean));
                    break;
                case "3":
                    dataList.add(new MultiBean(Constants.MultiType.ArticleVote, articleBean));
                    break;
                default:
                    dataList.add(new MultiBean(Constants.MultiType.Article, articleBean));
                    break;
            }
        }
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
            resetRefresh();
        }
        if (event.getFlag() == EventTags.LIST_ARTICLE_VOTE) {
            for (MultiBean multiBean : dataList) {
                if (multiBean.getItemType() == Constants.MultiType.ArticleVote) {
                    if (multiBean.getArticleBean().equals(event.getBean())) {
                        multiBean.getArticleBean().setIs_vote(((ArticleBean) event.getBean()).getIs_vote());
                        multiBean.getArticleBean().setVote_count(((ArticleBean) event.getBean()).getVote_count());
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
        if (event.getFlag() == EventTags.LIST_ARTICLE_LIKE) {
            for (MultiBean multiBean : dataList) {
                if (multiBean.getItemType() == Constants.MultiType.Article) {
                    if (multiBean.getArticleBean().equals(event.getBean())) {
                        multiBean.getArticleBean().setLike_count(((ArticleBean) event.getBean()).getLike_count());
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventRefresh event) {
        if (event.getFlag() == EventTags.REFRESH_MINE_FAV) {
            resetRefresh();
        }
    }
}
