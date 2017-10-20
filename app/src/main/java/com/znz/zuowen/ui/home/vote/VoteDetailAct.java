package com.znz.zuowen.ui.home.vote;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ImageAdapter;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.event.EventList;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.ArticleModel;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class VoteDetailAct extends BaseAppActivity<ArticleModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvTeacher)
    TextView tvTeacher;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.tvComment)
    TextView tvComment;
    @Bind(R.id.rvArticle)
    RecyclerView rvArticle;
    private String id;

    private ArticleBean bean;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_detail, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("作文投票");
        znzToolBar.setNavRightImg(R.mipmap.icon_shoucanghui);
        znzToolBar.setOnNavRightClickListener(v -> {
            Map<String, String> params = new HashMap<>();
            params.put("id", id);
            mModel.requestVoteFav(params, new ZnzHttpListener() {
                @Override
                public void onSuccess(JSONObject responseOriginal) {
                    super.onSuccess(responseOriginal);
                    if (!bean.getIs_collect().equals("1")) {
                        znzToolBar.setNavRightImg(R.mipmap.icon_shoucang);
                        bean.setIs_collect("1");
                        bean.setCollect_count(StringUtil.getNumUP(bean.getCollect_count()));
                    } else {
                        znzToolBar.setNavRightImg(R.mipmap.icon_shoucanghui);
                        bean.setIs_collect("0");
                        bean.setCollect_count(StringUtil.getNumDown(bean.getCollect_count()));
                    }
                    EventBus.getDefault().post(new EventList(EventTags.LIST_ARTICLE_FAV, bean));
                }

                @Override
                public void onFail(String error) {
                    super.onFail(error);
                }
            });
        });
    }

    @Override
    protected void initializeView() {
        tvSubmit.setVisibility(View.VISIBLE);
    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mModel.requestVoteDetail(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                bean = JSONObject.parseObject(responseOriginal.getString("data"), ArticleBean.class);
                mDataManager.setValueToView(tvName, bean.getTitle());
                mDataManager.setValueToView(tvTeacher, bean.getAuthor());
                if (bean.getImgurl().isEmpty()) {
                    rvArticle.setVisibility(View.GONE);
                    tvContent.setVisibility(View.VISIBLE);
                    tvContent.setText(Html.fromHtml(bean.getContent()));
                } else {
                    rvArticle.setVisibility(View.VISIBLE);
                    tvContent.setVisibility(View.GONE);
                    rvArticle.setLayoutManager(new LinearLayoutManager(activity));
                    rvArticle.setHasFixedSize(true);
                    rvArticle.setNestedScrollingEnabled(false);
                    ImageAdapter imageAdapter = new ImageAdapter(bean.getImgurl());
                    rvArticle.setAdapter(imageAdapter);
                }
                tvComment.setText(Html.fromHtml(bean.getTeacher_reviews()));
                if (bean.getIs_vote().equals("1")) {
                    tvSubmit.setText("已投票(" + bean.getVote_count() + ")");
                    tvSubmit.setBackgroundResource(R.drawable.bg_btn_round_no);
                } else {
                    tvSubmit.setText("投票(" + bean.getVote_count() + ")");
                }

                if (bean.getIs_collect().equals("1")) {
                    znzToolBar.setNavRightImg(R.mipmap.icon_shoucang);
                } else {
                    znzToolBar.setNavRightImg(R.mipmap.icon_shoucanghui);
                }
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tvSubmit)
    public void onViewClicked() {
        if (bean.getIs_vote().equals("0")) {
            Map<String, String> params = new HashMap<>();
            params.put("id", bean.getId());
            mModel.requestVoteVote(params, new ZnzHttpListener() {
                @Override
                public void onSuccess(JSONObject responseOriginal) {
                    super.onSuccess(responseOriginal);
                    if (!bean.getIs_vote().equals("1")) {
                        bean.setIs_vote("1");
                    } else {
                        bean.setIs_vote("0");
                    }
                    if (bean.getIs_vote().equals("1")) {
                        tvSubmit.setText("已投票(" + StringUtil.getNumUP(bean.getVote_count()) + ")");
                        tvSubmit.setBackgroundResource(R.drawable.bg_btn_round_no);
                        bean.setVote_count(StringUtil.getNumUP(bean.getVote_count()));
                    } else {
                        tvSubmit.setText("投票(" + bean.getVote_count() + ")");
                    }
                    EventBus.getDefault().post(new EventList(EventTags.LIST_ARTICLE_VOTE, bean));
                }

                @Override
                public void onFail(String error) {
                    super.onFail(error);
                }
            });
        }
    }
}
