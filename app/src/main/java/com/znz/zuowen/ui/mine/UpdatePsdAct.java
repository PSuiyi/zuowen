package com.znz.zuowen.ui.mine;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.znz.zuowen.ui.login.LoginAct;

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

public class UpdatePsdAct extends BaseAppActivity<UserModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.etPsdOld)
    EditTextWithDel etPsdOld;
    @Bind(R.id.etPsdNew)
    EditTextWithDel etPsdNew;
    @Bind(R.id.etPsdConfirm)
    EditTextWithDel etPsdConfirm;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_update_psd, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("更改登录密码");
    }

    @Override
    protected void initializeView() {
        etPsdOld.addTextChangedListener(new TextWatcher() {
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
        etPsdNew.addTextChangedListener(new TextWatcher() {
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
        etPsdConfirm.addTextChangedListener(new TextWatcher() {
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
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPsdOld))) {
            return;
        }
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPsdNew))) {
            return;
        }
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPsdConfirm))) {
            return;
        }
        if (!mDataManager.getValueFromView(etPsdNew).equals(mDataManager.getValueFromView(etPsdConfirm))) {
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

    @OnClick(R.id.tvSubmit)
    public void onViewClicked() {
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPsdOld))) {
            mDataManager.showToast("请输入旧密码");
            return;
        }
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPsdNew))) {
            mDataManager.showToast("请输入新密码");
            return;
        }
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPsdConfirm))) {
            mDataManager.showToast("请确认新密码");
            return;
        }
        if (!mDataManager.getValueFromView(etPsdNew).equals(mDataManager.getValueFromView(etPsdConfirm))) {
            mDataManager.showToast("两次输入的新密码不一致");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("old_pass", mDataManager.getValueFromView(etPsdOld));
        params.put("new_pass", mDataManager.getValueFromView(etPsdNew));
        params.put("re_new_pass", mDataManager.getValueFromView(etPsdConfirm));
        mModel.requestUpdatePsd(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                mDataManager.logout(activity, LoginAct.class);
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }
}
