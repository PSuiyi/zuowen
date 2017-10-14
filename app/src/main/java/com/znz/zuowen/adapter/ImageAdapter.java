package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.UrlBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class ImageAdapter extends BaseQuickAdapter<UrlBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.ivImage)
    HttpImageView ivImage;

    public ImageAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_image, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, UrlBean bean) {
        setOnItemClickListener(this);
        ivImage.loadHaoImage(bean.getUrl());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<String> urls = new ArrayList<>();
        for (UrlBean urlBean : mDataList) {
            urls.add(urlBean.getUrl());
        }
        mDataManager.showImagePreviewMulti(mContext, urls, position, view);
    }
}
