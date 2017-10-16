package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.OptionBean;

import java.util.List;

import butterknife.Bind;

public class OptionAdapter extends BaseQuickAdapter<OptionBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.ivCheck)
    ImageView ivCheck;
    private String type;

    public OptionAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_option, dataList);
    }

    public OptionAdapter(@Nullable List dataList, String type) {
        super(R.layout.item_lv_option, dataList);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, OptionBean bean) {
        setOnItemClickListener(this);
        switch (type) {
            case "老师":
                break;
            case "体裁":
                mDataManager.setValueToView(tvName, bean.getStyle_type());
                break;
            case "字数":
                mDataManager.setValueToView(tvName, bean.getCounts_id());
                break;
        }
        mDataManager.setViewVisibility(ivCheck, bean.isChecked());
        if (bean.isChecked()) {
            tvName.setTextColor(mDataManager.getColor(R.color.text_color));
        } else {
            tvName.setTextColor(mDataManager.getColor(R.color.text_gray));
        }

        helper.addOnClickListener(R.id.llContainer);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
