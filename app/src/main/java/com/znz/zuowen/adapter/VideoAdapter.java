package com.znz.zuowen.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.VideoBean;
import com.znz.zuowen.ui.home.video.VideoDetailAct;

import java.util.List;

import butterknife.Bind;

public class VideoAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.ivImage)
    HttpImageView ivImage;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvTeacher)
    TextView tvTeacher;
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.tvTime)
    TextView tvTime;

    public VideoAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_video, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean bean) {
        setOnItemClickListener(this);
        mDataManager.setValueToView(tvContent, bean.getIntro());
        mDataManager.setValueToView(tvName, bean.getTitle());
        mDataManager.setValueToView(tvTeacher, bean.getTeacher_name());
        ivImage.loadHaoImage(bean.getImage());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getId());
        gotoActivity(VideoDetailAct.class, bundle);
    }
}
