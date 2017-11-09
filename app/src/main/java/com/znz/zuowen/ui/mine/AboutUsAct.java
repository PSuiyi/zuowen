package com.znz.zuowen.ui.mine;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.model.CommonModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date： 2017/10/31 2017
 * User： PSuiyi
 * Description：
 */

public class AboutUsAct extends BaseAppActivity<CommonModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvVersion)
    TextView tvVersion;
    @Bind(R.id.tvContent)
    TextView tvContent;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_about_us, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new CommonModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("关于我们");
    }

    @Override
    protected void initializeView() {
        tvVersion.setText("特级教师改作文 v" + StringUtil.getVersionName(activity));
    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        mModel.requestAboutUs(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                mDataManager.setValueHtmlToTextView(tvContent, responseObject.getString("content"));
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
