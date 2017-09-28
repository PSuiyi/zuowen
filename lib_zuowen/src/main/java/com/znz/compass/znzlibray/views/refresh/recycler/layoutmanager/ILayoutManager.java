package com.znz.compass.znzlibray.views.refresh.recycler.layoutmanager;

import android.support.v7.widget.RecyclerView;

import com.znz.compass.znzlibray.base.BaseRecyclerAdapter;


/**
 * Created by Stay on 5/3/16.
 * Powered by www.stay4it.com
 */
public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();

    int findLastVisiblePosition();

    void setZnzAdapter(BaseRecyclerAdapter adapter);
}
