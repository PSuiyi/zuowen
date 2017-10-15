package com.znz.zuowen.ui.home.video;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.bean.VideoBean;
import com.znz.zuowen.model.ArticleModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class VideoDetailAct extends BaseAppActivity<ArticleModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.ivImage)
    HttpImageView ivImage;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvTeacher)
    TextView tvTeacher;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.tvContent)
    TextView tvContent;
    private String id;

    private VideoBean bean;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_video_detail, 1};
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
        setTitleName("微课详情");
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

    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mModel.requestVideoDetail(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                bean = JSONObject.parseObject(responseOriginal.getString("data"), VideoBean.class);
                ivImage.loadRectImage(bean.getImage());
                mDataManager.setValueToView(tvName, bean.getTitle());
                mDataManager.setValueToView(tvContent, bean.getContent());
                mDataManager.setValueToView(tvTeacher, bean.getTeacher_name());
                mDataManager.setValueToView(tvTime, bean.getAddtime());

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
}
