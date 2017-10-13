package com.znz.zuowen.ui.login;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.views.EditTextWithDel;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.model.CommonModel;
import com.znz.zuowen.ui.TabHomeAct;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/9/26 2017
 * User： PSuiyi
 * Description：
 */

public class LoginAct extends BaseAppActivity<CommonModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.etUserName)
    EditTextWithDel etUserName;
    @Bind(R.id.etPsd)
    EditText etPsd;
    @Bind(R.id.tvLogin)
    TextView tvLogin;
    @Bind(R.id.tvRegister)
    TextView tvRegister;
    @Bind(R.id.tvForgetPsd)
    TextView tvForgetPsd;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_login};
    }

    @Override
    protected void initializeVariate() {
        mModel = new CommonModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {

    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        mModel.requestVersion(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
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

    @OnClick({R.id.tvLogin, R.id.tvRegister, R.id.tvForgetPsd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLogin:
                gotoActivity(TabHomeAct.class);
                finish();
                break;
            case R.id.tvRegister:
                gotoActivity(RegisterAct.class);
                break;
            case R.id.tvForgetPsd:
                gotoActivity(ResetPsdAct.class);
                break;
        }
    }
}
