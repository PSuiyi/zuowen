package com.znz.compass.znzlibray.views.refresh.recycler;

import android.support.v7.widget.GridLayoutManager;

import com.znz.compass.znzlibray.base.BaseRecyclerAdapter;

public class FooterSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private BaseRecyclerAdapter adapter;
    private int spanCount;

    public FooterSpanSizeLookup(BaseRecyclerAdapter adapter, int spanCount) {
        this.adapter = adapter;
        this.spanCount = spanCount;
    }

    @Override
    public int getSpanSize(int position) {
        if (adapter.isLoadMoreFooter(position) || adapter.isSectionHeader(position)
                || adapter.isNoData() || adapter.isCustomeHeader(position)) {
            return spanCount;
        }
        return 1;
    }
}
