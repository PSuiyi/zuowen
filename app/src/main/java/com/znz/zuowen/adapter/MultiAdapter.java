package com.znz.zuowen.adapter;

import android.os.Bundle;
import android.view.View;

import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.recyclerview.BaseMultiItemQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.compass.znzlibray.views.recyclerview.BaseViewHolder;
import com.znz.zuowen.R;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.bean.MultiBean;
import com.znz.zuowen.common.Constants;
import com.znz.zuowen.ui.home.vote.VoteDetailAct;

import java.util.List;

/**
 * Date： 2017/9/4 2017
 * User： PSuiyi
 * Description：
 */

public class MultiAdapter extends BaseMultiItemQuickAdapter<MultiBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    private OnVoteClickLinstener onVoteClickLinstener;

    public MultiAdapter(List dataList) {
        super(dataList);
        addItemType(Constants.MultiType.Section, R.layout.item_lv_section);
        addItemType(Constants.MultiType.WeekStar, R.layout.item_lv_home);
        addItemType(Constants.MultiType.ArticleVote, R.layout.item_lv_vote);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiBean bean) {
        setOnItemClickListener(this);
        switch (bean.getItemType()) {
            case Constants.MultiType.Section:
                break;
            case Constants.MultiType.WeekStar:
                helper.setText(R.id.tvTitle, bean.getArticleBean().getTitle());
                if (!StringUtil.isBlank(bean.getArticleBean().getContent())) {
                    helper.setVisible(R.id.tvContent, true);
                    helper.setText(R.id.tvContent, bean.getArticleBean().getContent());
                } else {
                    helper.setVisible(R.id.tvContent, false);
                }
                helper.setText(R.id.tvType, bean.getArticleBean().getAuthor());
                helper.setText(R.id.tvTag1, bean.getArticleBean().getStyle_type());
                helper.setText(R.id.tvTag2, bean.getArticleBean().getCounts() + "字");
                if (!bean.getArticleBean().getImgurl().isEmpty()) {
                    helper.setVisible(R.id.ivImage, true);
                    helper.loadRectImage(R.id.ivImage, bean.getArticleBean().getImgurl().get(0).getUrl());
                } else {
                    helper.setVisible(R.id.ivImage, false);
                }
                break;
            case Constants.MultiType.ArticleVote:
                helper.setText(R.id.tvTitle, bean.getArticleBean().getTitle());
                if (!StringUtil.isBlank(bean.getArticleBean().getContent())) {
                    helper.setVisible(R.id.tvContent, true);
                    helper.setText(R.id.tvContent, bean.getArticleBean().getContent());
                } else {
                    helper.setVisible(R.id.tvContent, false);
                }
                helper.setText(R.id.tvTag1, bean.getArticleBean().getStyle_type());
                helper.setText(R.id.tvTag2, bean.getArticleBean().getCounts() + "字");
                helper.setText(R.id.tvCount, "票数：" + bean.getArticleBean().getVote_count());
                if (!bean.getArticleBean().getImgurl().isEmpty()) {
                    helper.setVisible(R.id.ivImage, true);
                    helper.loadRectImage(R.id.ivImage, bean.getArticleBean().getImgurl().get(0).getUrl());
                } else {
                    helper.setVisible(R.id.ivImage, false);
                }

                if (bean.getArticleBean().getIs_vote().equals("1")) {
                    helper.setText(R.id.tvVote, "已投票");
                    helper.setVisible(R.id.ivIcon, false);
                    helper.setBackgroundRes(R.id.llVote, R.drawable.bg_vote_gray);
                } else {
                    helper.setText(R.id.tvVote, "投票");
                    helper.setVisible(R.id.ivIcon, true);
                    helper.setBackgroundRes(R.id.llVote, R.drawable.bg_vote);
                }

                if (bean.getArticleBean().getIs_star().equals("1")) {
                    helper.setVisible(R.id.ivTag, true);
                } else {
                    helper.setVisible(R.id.ivTag, false);
                }

                helper.setOnClickListener(R.id.tvVote, v -> {
                    if (bean.getArticleBean().getIs_vote().equals("0")) {
                        if (onVoteClickLinstener != null) {
                            onVoteClickLinstener.onVoteClick(bean.getArticleBean());
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        switch (bean.getItemType()) {
            case Constants.MultiType.Section:
//                gotoActivity(GoodListAct.class);
                break;
            case Constants.MultiType.WeekStar:
                bundle.putString("id", bean.getArticleBean().getId());
                gotoActivity(VoteDetailAct.class, bundle);
                break;
            case Constants.MultiType.ArticleVote:
                bundle.putString("id", bean.getArticleBean().getId());
                gotoActivity(VoteDetailAct.class, bundle);
                break;

        }
    }

    public void setOnVoteClickLinstener(OnVoteClickLinstener onVoteClickLinstener) {
        this.onVoteClickLinstener = onVoteClickLinstener;
    }

    public interface OnVoteClickLinstener {
        void onVoteClick(ArticleBean bean);
    }
}
