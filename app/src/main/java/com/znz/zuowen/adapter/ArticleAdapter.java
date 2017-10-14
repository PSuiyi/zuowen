package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.ui.home.article.ArticleDetailAct;
import com.znz.zuowen.ui.home.article.ArticleSubjectAct;

import java.util.List;

import butterknife.Bind;

public class ArticleAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.ivImage)
    ImageView ivImage;
    @Bind(R.id.ivGood)
    ImageView ivGood;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvTag1)
    TextView tvTag1;
    @Bind(R.id.tvTag2)
    TextView tvTag2;
    @Bind(R.id.tvFavCount)
    TextView tvFavCount;
    @Bind(R.id.tvLikeCount)
    TextView tvLikeCount;
    @Bind(R.id.llCount)
    LinearLayout llCount;

    private String page;

    public ArticleAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_article, dataList);
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
        helper.setText(R.id.tvTag1, bean.getStyle_type());
        helper.setText(R.id.tvTag2, bean.getCounts());
        if (!bean.getImgurl().isEmpty()) {
            helper.setVisible(R.id.ivImage, true);
            helper.loadRectImage(R.id.ivImage, bean.getImgurl().get(0).getUrl());
        } else {
            helper.setVisible(R.id.ivImage, false);
        }

        if (!StringUtil.isBlank(page)) {
            switch (page) {
                case "优秀作文":
                    ivGood.setVisibility(View.VISIBLE);
                    llCount.setVisibility(View.VISIBLE);

                    helper.setText(R.id.tvFavCount, bean.getCollect_count());
                    helper.setText(R.id.tvLikeCount, bean.getLike_count());
                    break;
                default:
                    ivGood.setVisibility(View.GONE);
                    llCount.setVisibility(View.GONE);
                    break;
            }
        } else {
            ivGood.setVisibility(View.GONE);
            llCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!StringUtil.isBlank(page)) {
            switch (page) {
                case "优秀作文":
                    gotoActivity(ArticleDetailAct.class);
                    break;
                case "小学组":
                case "初中组":
                case "高中组":
                    gotoActivity(ArticleSubjectAct.class);
                    break;
                default:
                    gotoActivity(ArticleDetailAct.class);
                    break;
            }
        } else {
            gotoActivity(ArticleDetailAct.class);
        }
    }
}
