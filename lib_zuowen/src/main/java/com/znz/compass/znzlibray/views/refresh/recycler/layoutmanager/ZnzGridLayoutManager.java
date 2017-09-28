package com.znz.compass.znzlibray.views.refresh.recycler.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.znz.compass.znzlibray.base.BaseRecyclerAdapter;
import com.znz.compass.znzlibray.views.refresh.recycler.FooterSpanSizeLookup;


/**
 * Created by Stay on 5/3/16.
 * Powered by www.stay4it.com
 */
public class ZnzGridLayoutManager extends GridLayoutManager implements ILayoutManager {

    public ZnzGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ZnzGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public ZnzGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
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
        FooterSpanSizeLookup lookup = new FooterSpanSizeLookup(adapter, getSpanCount());
        setSpanSizeLookup(lookup);
    }
}
