package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.ClassBean;

import java.util.List;

import butterknife.Bind;

public class ClassAdapter extends BaseQuickAdapter<ClassBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.cbClass)
    CheckBox cbClass;

    public ClassAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_class, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassBean bean) {
        setOnItemClickListener(this);
        cbClass.setChecked(bean.isChecked());
        cbClass.setText(bean.getName() + " " + bean.getMoney() + "元");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
