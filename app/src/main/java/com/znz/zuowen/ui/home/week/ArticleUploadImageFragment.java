package com.znz.zuowen.ui.home.week;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.upload_image.UploadImageLayout;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppFragment;
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
 * Date： 2017/11/3 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleUploadImageFragment extends BaseAppFragment<ArticleModel> {
    @Bind(R.id.uploadImage)
    UploadImageLayout uploadImage;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;

    private List<String> uploadUrls = new ArrayList<>();
    private List<String> uploadNames = new ArrayList<>();
    private CommonModel commonModel;
    private String id;
    private String type;

    public static ArticleUploadImageFragment newInstance(String type, String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("type", type);
        ArticleUploadImageFragment fragment = new ArticleUploadImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.frag_upload_image};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
        commonModel = new CommonModel(activity, this);

        if (getArguments() != null) {
            id = getArguments().getString("id");
            type = getArguments().getString("type");
        }
    }

    @Override
    protected void initializeNavigation() {

    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tvSubmit)
    public void onViewClicked() {
        if (type.equals("1")) {
            firstUpload();
        } else {
            secondUpload();
        }
    }

    private void firstUpload() {
        if (StringUtil.isBlank(ArticleUploadAct.teacher_id)) {
            mDataManager.showToast("请选择批改老师");
            return;
        }

        if (StringUtil.isBlank(ArticleUploadAct.title)) {
            mDataManager.showToast("请输入作文题目");
            return;
        }

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
                    uploadNames.add(responseObject.getString("file_name"));

                    if (uploadUrls.size() == uploadImage.getImageList().size()) {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", id);
                        params.put("teacher_id", ArticleUploadAct.teacher_id);
                        params.put("images", mDataManager.getValueBySeparator(uploadUrls, "|||"));
                        params.put("files_name", mDataManager.getValueBySeparator(uploadNames, "|||"));
                        params.put("title", ArticleUploadAct.title);
                        params.put("type", "1");
                        mModel.requestArticleSubmitOne(params, new ZnzHttpListener() {
                            @Override
                            public void onSuccess(JSONObject responseOriginal) {
                                super.onSuccess(responseOriginal);
                                mDataManager.showToast("上传成功");
                                hidePd();
                                finish();
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
    }


    private void secondUpload() {
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
                        params.put("files_name", mDataManager.getValueBySeparator(uploadNames, "|||"));
                        params.put("type", "1");
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
    }
}
