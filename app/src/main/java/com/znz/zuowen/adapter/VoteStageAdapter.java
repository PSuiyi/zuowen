package com.znz.zuowen.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.StageBean;
import com.znz.zuowen.ui.home.vote.VoteAct;

import java.util.List;

import butterknife.Bind;

public class VoteStageAdapter extends BaseQuickAdapter<StageBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.tvTitle)
    TextView tvTitle;

    public VoteStageAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_vote_stage, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, StageBean bean) {
        setOnItemClickListener(this);
        mDataManager.setValueToView(tvTitle, bean.getName());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getId());
        gotoActivity(VoteAct.class, bundle);
    }
}
