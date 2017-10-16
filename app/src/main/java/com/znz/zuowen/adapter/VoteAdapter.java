package com.znz.zuowen.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.ui.home.vote.VoteDetailAct;

import java.util.List;

import butterknife.Bind;

public class VoteAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.ivGood)
    ImageView ivGood;
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.ivImage)
    ImageView ivImage;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvTag1)
    TextView tvTag1;
    @Bind(R.id.tvTag2)
    TextView tvTag2;
    @Bind(R.id.tvCount)
    TextView tvCount;
    @Bind(R.id.tvVote)
    TextView tvVote;
    @Bind(R.id.ivIcon)
    ImageView ivIcon;
    @Bind(R.id.llVote)
    LinearLayout llVote;

    public VoteAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_vote, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean bean) {
        setOnItemClickListener(this);

        helper.setText(R.id.tvTitle, bean.getTitle());
        if (!StringUtil.isBlank(bean.getContent())) {
            helper.setVisible(R.id.tvContent, true);
            helper.setText(R.id.tvContent, bean.getContent());
        } else {
            helper.setVisible(R.id.tvContent, false);
        }
        helper.setText(R.id.tvTag1, bean.getStyle_type());
        helper.setText(R.id.tvTag2, bean.getCounts() + "字");
        helper.setText(R.id.tvCount, "票数：" + bean.getVote_count());
        if (!bean.getImgurl().isEmpty()) {
            helper.setVisible(R.id.ivImage, true);
            helper.loadRectImage(R.id.ivImage, bean.getImgurl().get(0).getUrl());
        } else {
            helper.setVisible(R.id.ivImage, false);
        }

        if (bean.getIs_vote().equals("1")) {
            tvVote.setText("已投票");
            mDataManager.setViewVisibility(ivIcon, false);
            llVote.setBackgroundResource(R.drawable.bg_vote_gray);
        } else {
            tvVote.setText("投票");
            mDataManager.setViewVisibility(ivIcon, true);
            llVote.setBackgroundResource(R.drawable.bg_vote);
        }
        helper.addOnClickListener(R.id.tvVote);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getId());
        gotoActivity(VoteDetailAct.class, bundle);
    }
}
