package com.znz.zuowen.ui.home.teacher;

import android.support.v7.widget.RecyclerView;

import com.znz.zuowen.R;
import com.znz.zuowen.adapter.TeacherAdapter;
import com.znz.zuowen.base.BaseAppListActivity;

/**
 * Date： 2017/10/31 2017
 * User： PSuiyi
 * Description：
 */

public class TeacherListAct extends BaseAppListActivity {
    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_activity_with_fragment, 1};
    }

    @Override
    protected void initializeVariate() {

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
    protected void onRefreshSuccess(String response) {

    }

    @Override
    protected void onRefreshFail(String error) {

    }
}
