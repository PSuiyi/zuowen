package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.VideoBean;

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
        mDataManager.setValueToView(tvContent, bean.getIntro());
        mDataManager.setValueToView(tvName, bean.getTitle());
        mDataManager.setValueToView(tvTeacher, bean.getTeacher_name());
        mDataManager.setValueToView(tvTime, bean.getAddtime());
        ivImage.loadRectImage(bean.getImage());

        if (bean.getIs_buy().equals("1")) {
            helper.setVisible(R.id.tvStatus, true);
        } else {
            helper.setVisible(R.id.tvStatus, false);
        }

        helper.addOnClickListener(R.id.llContainer);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
