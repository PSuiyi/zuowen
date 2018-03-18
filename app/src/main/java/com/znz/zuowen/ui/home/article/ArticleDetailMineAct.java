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
import com.znz.zuowen.common.Constants;
import com.znz.zuowen.event.EventRefresh;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.ArticleModel;
import com.znz.zuowen.ui.common.WebViewActivity;
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
    @Bind(R.id.tvFanwenContent)
    TextView tvFanwenContent;
    @Bind(R.id.tvFanwenComment)
    TextView tvFanwenComment;
    @Bind(R.id.rvArticle)
    RecyclerView rvArticle;
    @Bind(R.id.tvFileName1)
    TextView tvFileName1;
    @Bind(R.id.tvFileTime1)
    TextView tvFileTime1;
    @Bind(R.id.llFile1)
    LinearLayout llFile1;
    @Bind(R.id.tvFileName2)
    TextView tvFileName2;
    @Bind(R.id.tvFileTime2)
    TextView tvFileTime2;
    @Bind(R.id.llFile2)
    LinearLayout llFile2;
    @Bind(R.id.rvReply1)
    RecyclerView rvReply1;
    @Bind(R.id.tvReplyFileName1)
    TextView tvReplyFileName1;
    @Bind(R.id.tvReplyFileTime1)
    TextView tvReplyFileTime1;
    @Bind(R.id.llReplyFile1)
    LinearLayout llReplyFile1;
    @Bind(R.id.rvReply2)
    RecyclerView rvReply2;
    @Bind(R.id.tvReplyFileName2)
    TextView tvReplyFileName2;
    @Bind(R.id.tvReplyFileTime2)
    TextView tvReplyFileTime2;
    @Bind(R.id.llReplyFile2)
    LinearLayout llReplyFile2;
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

                if (!StringUtil.isBlank(mineBean.getSecond_upload_time())) {
                    mDataManager.setValueToView(tvTime, mineBean.getSecond_upload_time());
                } else {
                    mDataManager.setValueToView(tvTime, mineBean.getFirst_upload_time());
                }

                if (!StringUtil.isBlank(mineBean.getFirst_teacher_reviews())) {
                    tvComment1.setText(Html.fromHtml(mineBean.getFirst_teacher_reviews()));
                    mDataManager.setViewVisibility(llComment1, true);
                    if (!StringUtil.isBlank(mineBean.getFirst_teacher_doc())) {
                        mDataManager.setViewVisibility(llReplyFile1, true);
                        llReplyFile1.setOnClickListener(v -> {
                            Bundle bundle = new Bundle();
                            bundle.putString("title", "第一次批改回复");
                            bundle.putString("url", "https://view.officeapps.live.com/op/view.aspx?src=" + Constants.IMG_URL + mineBean.getFirst_teacher_doc());
                            gotoActivity(WebViewActivity.class, bundle);
                        });
                    } else {
                        if (!mineBean.getFirst_teacher_upload().isEmpty()) {
                            mDataManager.setViewVisibility(rvReply1, true);
                            rvReply1.setLayoutManager(new LinearLayoutManager(activity));
                            rvReply1.setHasFixedSize(true);
                            rvReply1.setNestedScrollingEnabled(false);
                            ImageAdapter imageReply = new ImageAdapter(mineBean.getFirst_teacher_upload());
                            rvReply1.setAdapter(imageReply);
                        }
                    }
                } else {
                    mDataManager.setViewVisibility(llComment1, false);
                }

                //处理第一次上传
                if (mineBean.getFirst_type().equals("2")) {
                    llFile1.setVisibility(View.VISIBLE);
                    mDataManager.setValueToView(tvFileName1, mineBean.getFirst_upload().get(0).getFiles_name());
                    mDataManager.setValueToView(tvFileTime1, mineBean.getFirst_upload_time());
                } else {
                    rvArticle1.setLayoutManager(new LinearLayoutManager(activity));
                    rvArticle1.setHasFixedSize(true);
                    rvArticle1.setNestedScrollingEnabled(false);
                    ImageAdapter imageAdapter1 = new ImageAdapter(mineBean.getFirst_upload());
                    rvArticle1.setAdapter(imageAdapter1);
                }


                //处理第二次上传
                if (mineBean.getSecond_status().equals("1") || mineBean.getSecond_status().equals("2")) {
                    llArticleTwo.setVisibility(View.VISIBLE);
                    if (!StringUtil.isBlank(mineBean.getSecond_teacher_reviews())) {
                        tvComment2.setText(Html.fromHtml(mineBean.getSecond_teacher_reviews()));
                        mDataManager.setViewVisibility(llComment2, true);
                        if (!StringUtil.isBlank(mineBean.getSecond_teacher_doc())) {
                            mDataManager.setViewVisibility(llReplyFile2, true);
                            llReplyFile2.setOnClickListener(v -> {
                                Bundle bundle = new Bundle();
                                bundle.putString("title", "第一次批改回复");
                                bundle.putString("url", "https://view.officeapps.live.com/op/view.aspx?src=" + Constants.IMG_URL + mineBean.getSecond_teacher_doc());
                                gotoActivity(WebViewActivity.class, bundle);
                            });
                        } else {
                            if (!mineBean.getSecond_teacher_upload().isEmpty()) {
                                mDataManager.setViewVisibility(rvReply2, true);
                                rvReply2.setLayoutManager(new LinearLayoutManager(activity));
                                rvReply2.setHasFixedSize(true);
                                rvReply2.setNestedScrollingEnabled(false);
                                ImageAdapter imageReply = new ImageAdapter(mineBean.getSecond_teacher_upload());
                                rvReply2.setAdapter(imageReply);
                            }
                        }
                    } else {
                        mDataManager.setViewVisibility(llComment2, false);
                    }

                    if (mineBean.getSecond_type().equals("2")) {
                        llFile2.setVisibility(View.VISIBLE);
                        mDataManager.setValueToView(tvFileName2, mineBean.getSecond_upload().get(0).getFiles_name());
                        mDataManager.setValueToView(tvFileTime2, mineBean.getSecond_upload_time());
                    } else {
                        rvArticle2.setLayoutManager(new LinearLayoutManager(activity));
                        rvArticle2.setHasFixedSize(true);
                        rvArticle2.setNestedScrollingEnabled(false);
                        ImageAdapter imageAdapter2 = new ImageAdapter(mineBean.getSecond_upload());
                        rvArticle2.setAdapter(imageAdapter2);
                    }
                } else {
                    llArticleTwo.setVisibility(View.GONE);
                    if (mineBean.getFirst_status().equals("2")) {
                        tvSubmit.setVisibility(View.VISIBLE);
                    } else {
                        tvSubmit.setVisibility(View.GONE);
                    }
                }

                //范文
                mDataManager.setValueHtmlToTextView(tvFanwenContent, bean.getExample_show());
                mDataManager.setValueHtmlToTextView(tvFanwenComment, bean.getExample_comments());

                //视频
                if (!StringUtil.isBlank(bean.getVideo_url())) {
                    detailPlayer.setVisibility(View.VISIBLE);
                    HttpImageView ivImage = new HttpImageView(activity);
                    ivImage.loadRectImage(bean.getVideo_image());

                    gsyVideoOption.setThumbImageView(ivImage)
                            .setUrl(bean.getVideo_url())
                            .build(detailPlayer);
                } else {
                    detailPlayer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
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
            finish();
        }

        if (event.getFlag() == EventTags.REFRESH_ARTICLE) {
            finish();
        }
    }

    @OnClick({R.id.llFile1, R.id.tvSubmit, R.id.llFile2})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.llFile1:
                bundle.putString("title", mineBean.getFirst_upload().get(0).getFiles_name());
                bundle.putString("url", "https://view.officeapps.live.com/op/view.aspx?src=" + mineBean.getFirst_upload().get(0).getUrl());
                gotoActivity(WebViewActivity.class, bundle);
                break;
            case R.id.llFile2:
                bundle.putString("title", mineBean.getSecond_upload().get(0).getFiles_name());
                bundle.putString("url", "https://view.officeapps.live.com/op/view.aspx?src=" + mineBean.getSecond_upload().get(0).getUrl());
                gotoActivity(WebViewActivity.class, bundle);
                break;
            case R.id.tvSubmit:
                bundle.putString("id", bean.getId());
                gotoActivity(ArticleUploadAgainAct.class, bundle);
                break;
        }
    }
}
