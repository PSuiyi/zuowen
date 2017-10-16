package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.ClassBean;

import java.util.List;

import butterknife.Bind;

public class ClassAdapter extends BaseQuickAdapter<ClassBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.cbClass)
    CheckBox cbClass;
    @Bind(R.id.tvCount)
    TextView tvCount;
    @Bind(R.id.tvMoney)
    TextView tvMoney;

    public ClassAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_class, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassBean bean) {
        setOnItemClickListener(this);
        cbClass.setChecked(bean.isChecked());
        mDataManager.setValueToView(tvCount, bean.getName());
        mDataManager.setValueToView(tvMoney, bean.getMoney());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        for (ClassBean classBean : mDataList) {
            classBean.setChecked(false);
        }
        bean.setChecked(true);
        notifyDataSetChanged();
    }
}
