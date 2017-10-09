package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.znz.compass.znzlibray.base.BaseZnzBean;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.ui.home.article.ArticleDetailMineAct;

import java.util.List;

public class ArticleMineAdapter extends BaseQuickAdapter<BaseZnzBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    public ArticleMineAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_article_mine, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseZnzBean bean) {
        setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        gotoActivity(ArticleDetailMineAct.class);
    }
}
