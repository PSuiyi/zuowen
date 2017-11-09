package com.znz.zuowen.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.MessageBean;
import com.znz.zuowen.ui.home.message.MesageDetailAct;

import java.util.List;

import butterknife.Bind;

public class MessageAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvTime)
    TextView tvTime;

    public MessageAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_message, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean bean) {
        setOnItemClickListener(this);
        mDataManager.setValueToView(tvTitle, bean.getTitle());
        mDataManager.setValueToView(tvTime, bean.getAdd_time());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getInfo_id());
        gotoActivity(MesageDetailAct.class, bundle);
    }
}
