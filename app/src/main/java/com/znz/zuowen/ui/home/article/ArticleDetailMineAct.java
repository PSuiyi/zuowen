package com.znz.zuowen.ui.home.article;

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
import com.znz.zuowen.bean.ArticleMineBean;
import com.znz.zuowen.model.ArticleModel;
import com.znz.zuowen.ui.home.week.ArticleUploadAgainAct;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/10/9 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleDetailMineAct extends BaseAppActivity<ArticleModel> {

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
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("我的作文");
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
                mDataManager.setValueToView(tvTeacher, "审批老师：" + mineBean.getTeacher_name());
                mDataManager.setValueToView(tvTime, mineBean.getAddtime());
                if (!StringUtil.isBlank(mineBean.getFirst_teacher_reviews())) {
                    mDataManager.setValueToView(tvComment1, mineBean.getFirst_teacher_reviews());
                    mDataManager.setViewVisibility(llComment1, true);
                } else {
                    mDataManager.setViewVisibility(llComment1, false);
                }

                rvArticle1.setLayoutManager(new LinearLayoutManager(activity));
                rvArticle1.setHasFixedSize(true);
                rvArticle1.setNestedScrollingEnabled(false);
                ImageAdapter imageAdapter1 = new ImageAdapter(mineBean.getFirst_upload());
                rvArticle1.setAdapter(imageAdapter1);

                if (mineBean.getSecond_status().equals("1")) {
                    llArticleTwo.setVisibility(View.VISIBLE);
                    if (!StringUtil.isBlank(mineBean.getSecond_teacher_reviews())) {
                        mDataManager.setValueToView(tvComment2, mineBean.getSecond_teacher_reviews());
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
                    tvSubmit.setVisibility(View.VISIBLE);
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
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getId());
        gotoActivity(ArticleUploadAgainAct.class, bundle);
    }
}
