package com.znz.zuowen.ui.home.video;

import android.support.v7.widget.RecyclerView;

import com.znz.zuowen.R;
import com.znz.zuowen.adapter.VideoAdapter;
import com.znz.zuowen.base.BaseAppListFragment;


/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class VideoListFragment extends BaseAppListFragment {
    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout};
    }

    @Override
    protected void initializeVariate() {

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
    protected void onRefreshSuccess(String response) {

    }

    @Override
    protected void onRefreshFail(String error) {

    }
}
