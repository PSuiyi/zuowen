package com.znz.zuowen.ui.login;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class ResetPsdAct extends BaseAppActivity<UserModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvSendCode)
    TextView tvSendCode;
    @Bind(R.id.llSendCode)
    LinearLayout llSendCode;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.etCode)
    EditTextWithDel etCode;
    @Bind(R.id.etPsd)
    EditTextWithDel etPsd;
    private String id;
    private String str;
    private String username;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_reset_psd, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
        if (getIntent().hasExtra("str")) {
            str = getIntent().getStringExtra("str");
        }
        if (getIntent().hasExtra("username")) {
            username = getIntent().getStringExtra("username");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("找回密码");
    }

    @Override
    protected void initializeView() {
        etCode.setText("123456");
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handleBtnState();
            }
        });
        etPsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handleBtnState();
            }
        });
    }

    /**
     * 处理按钮状态
     */
    private void handleBtnState() {
        if (StringUtil.isBlank(mDataManager.getValueFromView(etCode))) {
            return;
        }
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPsd))) {
            return;
        }
        tvSubmit.setBackgroundResource(R.drawable.bg_btn_round);
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

    @OnClick({R.id.llSendCode, R.id.tvSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSubmit:
                if (StringUtil.isBlank(mDataManager.getValueFromView(etCode))) {
                    mDataManager.showToast("请输入验证码");
                    return;
                }
                if (StringUtil.isBlank(mDataManager.getValueFromView(etPsd))) {
                    mDataManager.showToast("请输入密码");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("username", username);
                params.put("str", str);
                params.put("passwd", mDataManager.getValueFromView(etPsd));
                params.put("code", mDataManager.getValueFromView(etCode));
                mModel.reuqestPsdTwo(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        gotoActivity(LoginAct.class);
                        finish();
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });
                break;
        }
    }
}
