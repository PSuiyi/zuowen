package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.UrlBean;
import com.znz.zuowen.ui.home.teacher.TeacherDetailAct;

import java.util.List;

public class MessageAdapter extends BaseQuickAdapter<UrlBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    public MessageAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_teacher, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, UrlBean bean) {
        setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        gotoActivity(TeacherDetailAct.class);
    }
}
