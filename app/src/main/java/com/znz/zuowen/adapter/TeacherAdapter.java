package com.znz.zuowen.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.TeacherBean;
import com.znz.zuowen.ui.home.teacher.TeacherDetailAct;

import java.util.List;

import butterknife.Bind;

public class TeacherAdapter extends BaseQuickAdapter<TeacherBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.ivImage)
    HttpImageView ivImage;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvContent)
    TextView tvContent;

    public TeacherAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_teacher, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeacherBean bean) {
        setOnItemClickListener(this);
        ivImage.loadRectImage(bean.getImage());
        mDataManager.setValueToView(tvTitle, bean.getName());
        mDataManager.setValueToView(tvContent, bean.getIntro());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getId());
        gotoActivity(TeacherDetailAct.class, bundle);
    }
}
