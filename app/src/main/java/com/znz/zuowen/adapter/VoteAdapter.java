package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.znz.compass.znzlibray.base.BaseZnzBean;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.ui.home.vote.VoteDetailAct;

import java.util.List;

import butterknife.Bind;

public class VoteAdapter extends BaseQuickAdapter<BaseZnzBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.ivGood)
    ImageView ivGood;
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.ivImage)
    ImageView ivImage;

    public VoteAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_vote, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseZnzBean bean) {
        setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        gotoActivity(VoteDetailAct .class);
    }
}
