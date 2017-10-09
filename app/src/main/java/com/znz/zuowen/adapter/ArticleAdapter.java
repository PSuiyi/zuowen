package com.znz.zuowen.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.znz.compass.znzlibray.base.BaseZnzBean;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.ui.home.article.ArticleDetailAct;
import com.znz.zuowen.ui.home.article.ArticleSubjectAct;

import java.util.List;

import butterknife.Bind;

public class ArticleAdapter extends BaseQuickAdapter<BaseZnzBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.ivImage)
    ImageView ivImage;
    @Bind(R.id.ivGood)
    ImageView ivGood;

    private String page;

    public ArticleAdapter(@Nullable List dataList) {
        super(R.layout.item_lv_article, dataList);
    }

    public void setPage(String page) {
        this.page = page;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseZnzBean bean) {
        setOnItemClickListener(this);
        if (mDataManager.getRandomBoolean()) {
            tvContent.setVisibility(View.VISIBLE);
            ivImage.setVisibility(View.GONE);
        } else {
            tvContent.setVisibility(View.GONE);
            ivImage.setVisibility(View.VISIBLE);
        }

        if (!StringUtil.isBlank(page)) {
            switch (page) {
                case "优秀作文":
                    ivGood.setVisibility(View.VISIBLE);
                    break;
                default:
                    ivGood.setVisibility(View.GONE);
                    break;
            }
        } else {
            ivGood.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!StringUtil.isBlank(page)) {
            switch (page) {
                case "优秀作文":
                    gotoActivity(ArticleDetailAct.class);
                    break;
                case "题目":
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
