package com.znz.zuowen.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.ui.home.article.ArticleDetailMineAct;

import java.util.List;

import butterknife.Bind;

public class ArticleMineAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvTeacher)
    TextView tvTeacher;
    @Bind(R.id.tvTime)
    TextView tvTime;

    public ArticleMineAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_article_mine, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean bean) {
        setOnItemClickListener(this);

        mDataManager.setValueToView(tvName, bean.getTitle());
        mDataManager.setValueToView(tvTeacher, bean.getTeacher_name());
        mDataManager.setValueToView(tvTime, bean.getAddtime());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getId());
        gotoActivity(ArticleDetailMineAct.class, bundle);
    }
}
