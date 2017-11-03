package com.znz.zuowen.ui.home.article;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ImageAdapter;
import com.znz.zuowen.base.BaseVideoActivity;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.bean.ArticleMineBean;
import com.znz.zuowen.event.EventRefresh;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.ArticleModel;
import com.znz.zuowen.ui.home.week.ArticleUploadAgainAct;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Date： 2017/10/9 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleDetailMineAct extends BaseVideoActivity<ArticleModel> {

    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvTeacher)
    TextView tvTeacher;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.llArticleTwo)
    LinearLayout llArticleTwo;
    @Bind(R.id.rvArticle1)
    RecyclerView rvArticle1;
    @Bind(R.id.tvComment1)
    TextView tvComment1;
    @Bind(R.id.rvArticle2)
    RecyclerView rvArticle2;
    @Bind(R.id.tvComment2)
    TextView tvComment2;
    @Bind(R.id.llComment1)
    LinearLayout llComment1;
    @Bind(R.id.llComment2)
    LinearLayout llComment2;
    @Bind(R.id.rvSubject)
    RecyclerView rvSubject;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    private String id;
    private ArticleBean bean;
    private ArticleMineBean mineBean;
    private String title;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_detail_mine, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");
        }
    }

    @Override
    protected void initializeNavigation() {
        if (!StringUtil.isBlank(title)) {
            setTitleName(title);
        } else {
            setTitleName("我的作文");
        }
    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mModel.requestWeekMineDetail(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                bean = JSONObject.parseObject(responseObject.getString("week_composition_info"), ArticleBean.class);
                tvContent.setText(Html.fromHtml(bean.getContent()));
                rvSubject.setLayoutManager(new LinearLayoutManager(activity));
                rvSubject.setHasFixedSize(true);
                rvSubject.setNestedScrollingEnabled(false);
                ImageAdapter imageAdapter = new ImageAdapter(bean.getImgurl());
                rvSubject.setAdapter(imageAdapter);

                mineBean = JSONObject.parseObject(responseObject.getString("my_info"), ArticleMineBean.class);
                mDataManager.setValueToView(tvName, "作文题目：" + mineBean.getTitle());
                if (!StringUtil.isBlank(title)) {
                    mDataManager.setValueToView(tvTeacher, "命题老师：" + mineBean.getTeacher_name());
                } else {
                    mDataManager.setValueToView(tvTeacher, "审批老师：" + mineBean.getTeacher_name());
                }
                mDataManager.setValueToView(tvTime, mineBean.getAddtime());
                if (!StringUtil.isBlank(mineBean.getFirst_teacher_reviews())) {
                    tvComment1.setText(Html.fromHtml(mineBean.getFirst_teacher_reviews()));
                    mDataManager.setViewVisibility(llComment1, true);
                } else {
                    mDataManager.setViewVisibility(llComment1, false);
                }

                rvArticle1.setLayoutManager(new LinearLayoutManager(activity));
                rvArticle1.setHasFixedSize(true);
                rvArticle1.setNestedScrollingEnabled(false);
                ImageAdapter imageAdapter1 = new ImageAdapter(mineBean.getFirst_upload());
                rvArticle1.setAdapter(imageAdapter1);

                if (mineBean.getSecond_status().equals("1") || mineBean.getSecond_status().equals("2")) {
                    llArticleTwo.setVisibility(View.VISIBLE);
                    if (!StringUtil.isBlank(mineBean.getSecond_teacher_reviews())) {
                        tvComment2.setText(Html.fromHtml(mineBean.getSecond_teacher_reviews()));
                        mDataManager.setViewVisibility(llComment2, true);
                    } else {
                        mDataManager.setViewVisibility(llComment2, false);
                    }

                    rvArticle2.setLayoutManager(new LinearLayoutManager(activity));
                    rvArticle2.setHasFixedSize(true);
                    rvArticle2.setNestedScrollingEnabled(false);
                    ImageAdapter imageAdapter2 = new ImageAdapter(mineBean.getSecond_upload());
                    rvArticle2.setAdapter(imageAdapter2);

                } else {
                    llArticleTwo.setVisibility(View.GONE);
                    if (mineBean.getFirst_status().equals("2")) {
                        tvSubmit.setVisibility(View.VISIBLE);
                    } else {
                        tvSubmit.setVisibility(View.GONE);
                    }
                }

                HttpImageView ivImage = new HttpImageView(activity);
                ivImage.loadRectImage("");

                String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
                gsyVideoOption.setThumbImageView(ivImage)
                        .setUrl(url)
                        .build(detailPlayer);
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }

    @OnClick(R.id.tvSubmit)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getId());
        gotoActivity(ArticleUploadAgainAct.class, bundle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventRefresh event) {
        if (event.getFlag() == EventTags.REFRESH_MINE_ARTICLE_DETAIL) {
            loadDataFromServer();
        }
    }
}
