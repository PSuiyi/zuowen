package com.znz.zuowen.ui.home.week;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.EditTextWithDel;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.bean.OptionBean;
import com.znz.zuowen.model.ArticleModel;
import com.znz.zuowen.utils.PopupWindowManager;

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

public class ArticleUploadAct extends BaseAppActivity<ArticleModel> {
    @Bind(R.id.llAdd)
    LinearLayout llAdd;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvTeacher)
    TextView tvTeacher;
    @Bind(R.id.llSelect)
    LinearLayout llSelect;
    @Bind(R.id.etTitle)
    EditTextWithDel etTitle;

    private List<OptionBean> teacherList = new ArrayList<>();
    private String id;
    private String teacher_id;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_upload, 1};
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
        setTitleName("上传作文");
    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params2 = new HashMap<>();
        mModel.requestTeacherList(params2, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                teacherList.clear();
                teacherList.addAll(JSONArray.parseArray(responseOriginal.getString("data"), OptionBean.class));
                if (!teacherList.isEmpty()) {
                    teacherList.get(0).setChecked(true);
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

    @OnClick({R.id.llAdd, R.id.tvSubmit, R.id.llSelect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSubmit:
                if (StringUtil.isBlank(teacher_id)) {
                    mDataManager.showToast("请选择老师");
                    return;
                }
                if (StringUtil.isBlank(mDataManager.getValueFromView(etTitle))) {
                    mDataManager.showToast("请输入作文题目");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("teacher_id", teacher_id);
                params.put("title", mDataManager.getValueFromView(etTitle));
                mModel.requestArticleSubmitOne(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        finish();
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });
                break;
            case R.id.llSelect:
                PopupWindowManager.getInstance(activity).showSelectTeacher(view, teacherList, (type, values) -> {
                    tvTeacher.setText(values[1]);
                    teacher_id = values[0];
                });
                break;
        }
    }
}
