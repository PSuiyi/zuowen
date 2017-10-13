package com.znz.zuowen.adapter;

import android.os.Bundle;
import android.view.View;

import com.znz.compass.znzlibray.views.recyclerview.BaseMultiItemQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.MultiBean;
import com.znz.zuowen.common.Constants;
import com.znz.zuowen.ui.home.article.ArticleDetailAct;
import com.znz.zuowen.ui.home.article.ArticleListAct;

import java.util.List;

/**
 * Date： 2017/9/4 2017
 * User： PSuiyi
 * Description：
 */

public class MultiAdapter extends BaseMultiItemQuickAdapter<MultiBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    public MultiAdapter(List dataList) {
        super(dataList);
        addItemType(Constants.MultiType.Section, R.layout.item_lv_section);
        addItemType(Constants.MultiType.Article, R.layout.item_lv_article);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiBean bean) {
        setOnItemClickListener(this);
        switch (bean.getItemType()) {
            case Constants.MultiType.Section:
                break;
            case Constants.MultiType.Article:
                helper.setText(R.id.tvTitle, bean.getArticleBean().getTitle());
                helper.setText(R.id.tvContent, bean.getArticleBean().getContent());
                helper.setText(R.id.tvTag1, bean.getArticleBean().getStyle_type());
                helper.setText(R.id.tvTag2, bean.getArticleBean().getCounts());
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        switch (bean.getItemType()) {
            case Constants.MultiType.Section:
                bundle.putString("page", "优秀作文");
                gotoActivity(ArticleListAct.class, bundle);
                break;
            case Constants.MultiType.Article:
                gotoActivity(ArticleDetailAct.class);
                break;

        }
    }

}
