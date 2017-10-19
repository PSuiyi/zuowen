package com.znz.zuowen.ui.login;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.common.ZnzConstants;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.EditTextWithDel;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.bean.UserBean;
import com.znz.zuowen.model.CommonModel;
import com.znz.zuowen.model.UserModel;
import com.znz.zuowen.ui.TabHomeAct;
import com.znz.zuowen.utils.AppUtils;

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

public class LoginAct extends BaseAppActivity<UserModel> {
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
    @Bind(R.id.cbLook)
    CheckBox cbLook;

    private CommonModel commonModel;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_login};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
        commonModel = new CommonModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setSwipeBackEnable(false);
    }

    @Override
    protected void initializeView() {
        if (!StringUtil.isBlank(mDataManager.readTempData(ZnzConstants.ACCOUNT))) {
            etUserName.setText(mDataManager.readTempData(ZnzConstants.ACCOUNT));
        }

        etUserName.addTextChangedListener(new TextWatcher() {
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

        cbLook.setOnCheckedChangeListener((compoundButton, b) -> {//设置密码明文密文
            if (b) {
                etPsd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                etPsd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        etPsd.setOnFocusChangeListener((v, hasFocus) -> mDataManager.setViewVisibility(cbLook, hasFocus));
    }

    /**
     * 处理按钮状态
     */
    private void handleBtnState() {
        if (StringUtil.isBlank(mDataManager.getValueFromView(etUserName))) {
            return;
        }
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPsd))) {
            return;
        }
        tvLogin.setBackgroundResource(R.drawable.bg_btn_round);
    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        commonModel.requestVersion(params, new ZnzHttpListener() {
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
                if (StringUtil.isBlank(mDataManager.getValueFromView(etUserName))) {
                    mDataManager.showToast("请输入用户名");
                    return;
                }
                if (StringUtil.isBlank(mDataManager.getValueFromView(etPsd))) {
                    mDataManager.showToast("请输入密码");
                    return;
                }

                Map<String, String> params = new HashMap<>();
                params.put("phone", mDataManager.getValueFromView(etUserName));
                params.put("pass", mDataManager.getValueFromView(etPsd));
                mModel.requestLogin(params, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        UserBean userBean = JSON.parseObject(responseObject.getString("memberinfo"), UserBean.class);
                        AppUtils.getInstance(activity).saveUserData(userBean);
                        mDataManager.saveTempData(ZnzConstants.ACCESS_TOKEN, responseObject.getString("token"));
                        mDataManager.saveBooleanTempData(ZnzConstants.IS_LOGIN, true);
                        mDataManager.saveTempData(ZnzConstants.ACCOUNT, mDataManager.getValueFromView(etUserName));
                        gotoActivity(TabHomeAct.class);
                        finish();
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });

                break;
            case R.id.tvRegister:
                gotoActivity(RegisterAct.class);
                break;
            case R.id.tvForgetPsd:
                gotoActivity(ResetPsdOneAct.class);
                break;
        }
    }
}
