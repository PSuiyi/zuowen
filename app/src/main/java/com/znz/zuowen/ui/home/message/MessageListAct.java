package com.znz.zuowen.ui.home.message;

import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.MessageAdapter;
import com.znz.zuowen.base.BaseAppListActivity;
import com.znz.zuowen.bean.MessageBean;
import com.znz.zuowen.model.UserModel;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/10/31 2017
 * User： PSuiyi
 * Description：
 */

public class MessageListAct extends BaseAppListActivity<UserModel, MessageBean> {
    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("信息汇");
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new MessageAdapter(dataList);
        rvRefresh.setAdapter(adapter);
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        return mModel.requestMessageList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        dataList.addAll(JSONArray.parseArray(response, MessageBean.class));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }
}
