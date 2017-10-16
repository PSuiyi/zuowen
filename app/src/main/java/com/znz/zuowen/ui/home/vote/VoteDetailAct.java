package com.znz.zuowen.ui.home.vote;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ImageAdapter;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.model.ArticleModel;

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
    @Bind(R.id.wvHtml)
    WebView wvHtml;
    private String id;

    private ArticleBean bean;
    private WebSettings settings;

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
                    } else {
                        znzToolBar.setNavRightImg(R.mipmap.icon_shoucanghui);
                        bean.setIs_collect("0");
                    }
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

        settings = wvHtml.getSettings();
        settings.setSupportZoom(true);//是否支持缩放,默认为true
        settings.setBuiltInZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //版本号控制，使图片能够适配
        int currentapiVersion = Build.VERSION.SDK_INT;
        //版本号控制，使图片能够适配
        if (Build.VERSION.SDK_INT > 19) {
            settings.setUseWideViewPort(true);
        }

        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        wvHtml.setWebViewClient(new WebViewClient() {
            // 点击网页里面的链接还是在当前的webView内部跳转，不跳转外部浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            // 可以让webView处理https请求
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
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
                    wvHtml.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT > 19) {
                        settings.setTextZoom(260);
                    }
                    wvHtml.loadDataWithBaseURL(null, bean.getContent(), "text/html", "utf-8", null);
                } else {
                    rvArticle.setVisibility(View.VISIBLE);
                    wvHtml.setVisibility(View.GONE);
                    rvArticle.setLayoutManager(new LinearLayoutManager(activity));
                    rvArticle.setHasFixedSize(true);
                    rvArticle.setNestedScrollingEnabled(false);
                    ImageAdapter imageAdapter = new ImageAdapter(bean.getImgurl());
                    rvArticle.setAdapter(imageAdapter);
                }
                mDataManager.setValueToView(tvComment, bean.getTeacher_reviews());
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
    }
}
