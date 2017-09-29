package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.znz.compass.znzlibray.base.BaseZnzBean;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.ui.home.video.VideoDetailAct;

import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<BaseZnzBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    public VideoAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_video, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseZnzBean bean) {
        setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        gotoActivity(VideoDetailAct.class);
    }
}
