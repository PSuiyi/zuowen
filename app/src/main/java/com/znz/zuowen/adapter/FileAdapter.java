package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.FileBean;
import com.znz.zuowen.utils.StaticValues;

import java.util.List;

public class FileAdapter extends BaseQuickAdapter<FileBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    public FileAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_file, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileBean bean) {
        switch (bean.getType()) {
            case StaticValues.FILE_ITEM_TYPE_DIRECTORY:
                helper.setImageResource(R.id.ivImage, R.mipmap.file);
                break;
            case StaticValues.FILE_ITEM_TYPE_FILE:
                helper.setImageResource(R.id.ivImage, R.mipmap.word);
                break;
        }

        helper.setText(R.id.tvName, bean.getName());
        helper.setText(R.id.tvPath, bean.getPath());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
