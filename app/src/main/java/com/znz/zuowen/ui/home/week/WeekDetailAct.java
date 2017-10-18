package com.znz.zuowen.ui.home.week;

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
import com.znz.compass.znzlibray.views.ios.ActionSheetDialog.UIAlertDialog;
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

public class WeekDetailAct extends BaseAppActivity<ArticleModel> {
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
    @Bind(R.id.rvArticle)
    RecyclerView rvArticle;
    private String id;
    private ArticleBean bean;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_subject, 1};
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
        setTitleName("作文要求");
    }

    @Override
    protected void initializeView() {
    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mModel.requestWeekDetail(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                bean = JSONObject.parseObject(responseOriginal.getString("data"), ArticleBean.class);
                mDataManager.setValueToView(tvName, bean.getTitle());
                mDataManager.setValueToView(tvTeacher, "命题老师：" + bean.getTeacher_name());
                tvContent.setText(Html.fromHtml(bean.getContent()));
                mDataManager.setValueToView(tvTime, bean.getAddtime());
                if (!bean.getImgurl().isEmpty()) {
                    rvArticle.setVisibility(View.VISIBLE);
                    rvArticle.setLayoutManager(new LinearLayoutManager(activity));
                    rvArticle.setHasFixedSize(true);
                    rvArticle.setNestedScrollingEnabled(false);
                    ImageAdapter imageAdapter = new ImageAdapter(bean.getImgurl());
                    rvArticle.setAdapter(imageAdapter);
                } else {
                    rvArticle.setVisibility(View.GONE);
                }

                if (!StringUtil.isBlank(bean.getIs_my_week())) {
                    if (bean.getIs_my_week().equals("1")) {
                        tvSubmit.setText("再次挑战");
                    }
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
        if (!StringUtil.isBlank(bean.getIs_my_week())) {
            if (bean.getIs_my_week().equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                gotoActivity(ArticleUploadAct.class, bundle);
            } else {
                buyArticle();
            }
        } else {
            buyArticle();
        }
    }

    private void buyArticle() {
        new UIAlertDialog(activity)
                .builder()
                .setMsg("确定花费5个课时挑战该作文？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v2 -> {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", id);
                    mModel.requestWeekBuy(params, new ZnzHttpListener() {
                        @Override
                        public void onSuccess(JSONObject responseOriginal) {
                            super.onSuccess(responseOriginal);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", id);
                            gotoActivity(ArticleUploadAct.class, bundle);
                        }

                        @Override
                        public void onFail(String error) {
                            super.onFail(error);
                        }
                    });
                })
                .show();
    }
}
