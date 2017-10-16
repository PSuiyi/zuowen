package com.znz.zuowen.ui.fav;

import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.MultiAdapter;
import com.znz.zuowen.base.BaseAppListFragment;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.bean.MultiBean;
import com.znz.zuowen.common.Constants;
import com.znz.zuowen.model.ArticleModel;

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
}