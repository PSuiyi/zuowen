package com.znz.zuowen.ui.home.teacher;

import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.TeacherAdapter;
import com.znz.zuowen.base.BaseAppListActivity;
import com.znz.zuowen.bean.TeacherBean;
import com.znz.zuowen.model.ArticleModel;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/10/31 2017
 * User： PSuiyi
 * Description：
 */

public class TeacherListAct extends BaseAppListActivity<ArticleModel, TeacherBean> {
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
        setTitleName("名师榜");
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new TeacherAdapter(dataList);
        rvRefresh.setAdapter(adapter);
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        return mModel.requestTeacherFamousList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        dataList.addAll(JSONArray.parseArray(response, TeacherBean.class));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }
}
