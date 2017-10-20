package com.znz.zuowen.ui.home.week;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.upload_image.UploadImageLayout;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.event.EventRefresh;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.ArticleModel;
import com.znz.zuowen.model.CommonModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/10/9 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleUploadAgainAct extends BaseAppActivity<ArticleModel> {

    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.uploadImage)
    UploadImageLayout uploadImage;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    private String id;
    private CommonModel commonModel;
    private List<String> uploadUrls = new ArrayList<>();

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_upload_again, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
        commonModel = new CommonModel(activity, this);
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("上传作文");
    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void loadDataFromServer() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tvSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSubmit:
                if (uploadImage.getImageList().isEmpty()) {
                    mDataManager.showToast("请上传作文图片");
                    return;
                }
                showPd();

                uploadUrls.clear();
                for (String url : uploadImage.getImageList()) {
                    commonModel.requestUploadImage(url, new ZnzHttpListener() {
                        @Override
                        public void onSuccess(JSONObject responseOriginal) {
                            super.onSuccess(responseOriginal);
                            uploadUrls.add(responseObject.getString("url"));

                            if (uploadUrls.size() == uploadImage.getImageList().size()) {
                                Map<String, String> params = new HashMap<>();
                                params.put("id", id);
                                params.put("images", mDataManager.getValueBySeparator(uploadUrls, "|||"));
                                mModel.requestArticleSubmitTwo(params, new ZnzHttpListener() {
                                    @Override
                                    public void onSuccess(JSONObject responseOriginal) {
                                        super.onSuccess(responseOriginal);
                                        mDataManager.showToast("上传成功");
                                        hidePd();
                                        finish();
                                        EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_MINE_ARTICLE_DETAIL));
                                    }

                                    @Override
                                    public void onFail(String error) {
                                        super.onFail(error);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFail(String error) {
                            super.onFail(error);
                        }
                    });
                }
                break;
        }
    }
}
