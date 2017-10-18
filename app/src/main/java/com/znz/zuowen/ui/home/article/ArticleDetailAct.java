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
import com.znz.zuowen.event.EventList;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.ArticleModel;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleDetailAct extends BaseAppActivity<ArticleModel> {
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
    @Bind(R.id.rvArticle)
    RecyclerView rvArticle;
    @Bind(R.id.tvComment)
    TextView tvComment;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    private ArticleBean bean;
    private String id;

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
        setTitleName("作文详情");
        znzToolBar.setNavRightImg(R.mipmap.icon_dianzanhui);
        znzToolBar.setNavRightImg2(R.mipmap.icon_shoucanghui);

        znzToolBar.setOnIconRightClickListener(v -> {
            Map<String, String> params = new HashMap<>();
            params.put("id", id);
            mModel.requestGoodLike(params, new ZnzHttpListener() {
                @Override
                public void onSuccess(JSONObject responseOriginal) {
                    super.onSuccess(responseOriginal);
                    if (!bean.getIs_like().equals("1")) {
                        znzToolBar.setNavRightImg(R.mipmap.icon_dianzan);
                        bean.setIs_like("1");
                        bean.setLike_count(StringUtil.getNumUP(bean.getLike_count()));
                    } else {
                        znzToolBar.setNavRightImg(R.mipmap.icon_dianzanhui);
                        bean.setIs_like("0");
                        bean.setLike_count(StringUtil.getNumDown(bean.getLike_count()));
                    }
                    EventBus.getDefault().post(new EventList(EventTags.LIST_ARTICLE_LIKE, bean));
                }

                @Override
                public void onFail(String error) {
                    super.onFail(error);
                }
            });
        });

        znzToolBar.setOnIconRightClickListener2(v -> {
            Map<String, String> params = new HashMap<>();
            params.put("id", id);
            mModel.requestGoodFav(params, new ZnzHttpListener() {
                @Override
                public void onSuccess(JSONObject responseOriginal) {
                    super.onSuccess(responseOriginal);
                    if (!bean.getIs_collect().equals("1")) {
                        znzToolBar.setNavRightImg2(R.mipmap.icon_shoucang);
                        bean.setIs_collect("1");
                        bean.setCollect_count(StringUtil.getNumUP(bean.getCollect_count()));
                    } else {
                        znzToolBar.setNavRightImg2(R.mipmap.icon_shoucanghui);
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
    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mModel.requestGoodDetail(params, new ZnzHttpListener() {
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
                mDataManager.setValueToView(tvComment, bean.getTeacher_reviews());

                if (bean.getIs_collect().equals("1")) {
                    znzToolBar.setNavRightImg2(R.mipmap.icon_shoucang);
                } else {
                    znzToolBar.setNavRightImg2(R.mipmap.icon_shoucanghui);
                }

                if (bean.getIs_like().equals("1")) {
                    znzToolBar.setNavRightImg(R.mipmap.icon_dianzan);
                } else {
                    znzToolBar.setNavRightImg(R.mipmap.icon_dianzanhui);
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
}
