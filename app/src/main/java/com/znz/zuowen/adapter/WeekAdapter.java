package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.ui.home.article.ArticleSubjectAct;

import java.util.List;

import butterknife.Bind;

public class WeekAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.ivImage)
    ImageView ivImage;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvTeacher)
    TextView tvTeacher;

    private String page;

    public WeekAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_week, dataList);
    }

    public void setPage(String page) {
        this.page = page;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean bean) {
        setOnItemClickListener(this);

        helper.setText(R.id.tvTitle, bean.getTitle());
        if (!StringUtil.isBlank(bean.getContent())) {
            helper.setVisible(R.id.tvContent, true);
            helper.setText(R.id.tvContent, bean.getContent());
        } else {
            helper.setVisible(R.id.tvContent, false);
        }
        helper.setText(R.id.tvTeacher, "命题老师：" + bean.getTeacher_name());
        if (!bean.getImgurl().isEmpty()) {
            helper.setVisible(R.id.ivImage, true);
            helper.loadRectImage(R.id.ivImage, bean.getImgurl().get(0).getUrl());
        } else {
            helper.setVisible(R.id.ivImage, false);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        gotoActivity(ArticleSubjectAct.class);
    }
}
