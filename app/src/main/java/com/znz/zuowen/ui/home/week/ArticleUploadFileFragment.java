package com.znz.zuowen.ui.home.week;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.utils.TimeUtils;
import com.znz.compass.znzlibray.views.ios.ActionSheetDialog.UIAlertDialog;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppFragment;
import com.znz.zuowen.event.EventGoto;
import com.znz.zuowen.event.EventRefresh;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.ArticleModel;
import com.znz.zuowen.model.CommonModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/11/3 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleUploadFileFragment extends BaseAppFragment<ArticleModel> {

    @Bind(R.id.llUploadWord)
    LinearLayout llUploadWord;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.tvFileName)
    TextView tvFileName;
    @Bind(R.id.tvFileTime)
    TextView tvFileTime;
    @Bind(R.id.ivDelete)
    ImageView ivDelete;
    @Bind(R.id.llFile)
    LinearLayout llFile;

    private String id;
    private String type;
    private String currentFilePath;
    private String currentUploadUrl;
    private String currentUploadName;
    private CommonModel commonModel;

    public static ArticleUploadFileFragment newInstance(String type, String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("type", type);
        ArticleUploadFileFragment fragment = new ArticleUploadFileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.frag_upload_file};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
        commonModel = new CommonModel(activity, this);
        if (getArguments() != null) {
            type = getArguments().getString("type");
            id = getArguments().getString("id");
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

    @OnClick({R.id.llUploadWord, R.id.tvSubmit, R.id.ivDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llUploadWord:
                gotoActivity(FileListAct.class);
                break;
            case R.id.ivDelete:
                new UIAlertDialog(activity)
                        .builder()
                        .setMsg("是否删除该文档")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", v2 -> {
                            llFile.setVisibility(View.GONE);
                            currentFilePath = null;
                        })
                        .show();
                break;
            case R.id.tvSubmit:
                if (type.equals("1")) {
                    firstUpload();
                } else {
                    secondUpload();
                }
                break;
        }
    }


    private void firstUpload() {
        if (StringUtil.isBlank(ArticleUploadAct.title)) {
            mDataManager.showToast("请输入作文题目");
            return;
        }

        if (StringUtil.isBlank(currentFilePath)) {
            mDataManager.showToast("请选择要上传的文档");
            return;
        }

        commonModel.requestUploadFile(currentFilePath, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                currentUploadUrl = responseObject.getString("url");
                currentUploadName = responseObject.getString("file_name");

                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                if (!StringUtil.isBlank(ArticleUploadAct.teacher_id)) {
                    params.put("teacher_id", ArticleUploadAct.teacher_id);
                }
                params.put("images", currentUploadUrl);
                params.put("files_name", currentUploadName);
                params.put("title", ArticleUploadAct.title);
                params.put("type", "2");
                mModel.requestArticleSubmitOne(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        mDataManager.showToast("上传成功");
                        hidePd();
                        EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_ARTICLE));
                        finish();
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }

    private void secondUpload() {
        if (StringUtil.isBlank(currentFilePath)) {
            mDataManager.showToast("请选择要上传的文档");
            return;
        }

        showPd();
        commonModel.requestUploadFile(currentFilePath, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                currentUploadUrl = responseObject.getString("url");
                currentUploadName = responseObject.getString("file_name");

                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("images", currentUploadUrl);
                params.put("files_name", currentUploadName);
                params.put("type", "2");
                mModel.requestArticleSubmitTwo(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        mDataManager.showToast("上传成功");
                        hidePd();
                        finish();
                        EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_ARTICLE));
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });
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
    public void onMessageEvent(EventGoto event) {
        if (event.getFlag() == EventTags.GOTO_FILE_UPLOAD) {
            currentFilePath = event.getValue();

            llFile.setVisibility(View.VISIBLE);
            tvFileName.setText(event.getValue());
            tvFileTime.setText(TimeUtils.getNowTimeString());
        }
    }
}
