package com.znz.zuowen.ui.login;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.EditTextWithDel;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.model.UserModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/9/30 2017
 * User： PSuiyi
 * Description：
 */

public class CompleteInfoAct extends BaseAppActivity<UserModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.etUserName)
    EditTextWithDel etUserName;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.etRecommendUser)
    EditTextWithDel etRecommendUser;
    private String id;
    private String code;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_complete_info, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
        if (getIntent().hasExtra("code")) {
            code = getIntent().getStringExtra("code");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("完善信息");
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

    @OnClick(R.id.tvSubmit)
    public void onViewClicked() {
        if (StringUtil.isBlank(mDataManager.getValueFromView(etUserName))) {
            mDataManager.showToast("请输入用户名");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("promo_code", code);
        params.put("username", mDataManager.getValueFromView(etUserName));
        if (!StringUtil.isBlank(mDataManager.getValueFromView(etRecommendUser))) {
            params.put("recommendedusername", mDataManager.getValueFromView(etRecommendUser));
        }
        mModel.reuqestCompleteInfo(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                gotoActivity(LoginAct.class);
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }
}
