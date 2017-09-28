package com.znz.compass.znzlibray.views.refresh.recycler.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.znz.compass.znzlibray.base.BaseRecyclerAdapter;


/**
 * Created by Stay on 5/3/16.
 * Powered by www.stay4it.com
 */
public class ZnzLinearLayoutManager extends LinearLayoutManager implements ILayoutManager {
    public ZnzLinearLayoutManager(Context context) {
        super(context);
    }

    public ZnzLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public ZnzLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return this;
    }

    @Override
    public int findLastVisiblePosition() {
        return findLastVisibleItemPosition();
    }

    @Override
    public void setZnzAdapter(BaseRecyclerAdapter adapter) {

    }
}
