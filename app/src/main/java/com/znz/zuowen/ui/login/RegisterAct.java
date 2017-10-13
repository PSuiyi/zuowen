package com.znz.zuowen.ui.login;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.MD5Util;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.utils.TimeUtils;
import com.znz.compass.znzlibray.views.EditTextWithDel;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.model.UserModel;
import com.znz.zuowen.utils.PopupWindowManager;

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

public class RegisterAct extends BaseAppActivity<UserModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.etUserName)
    EditTextWithDel etUserName;
    @Bind(R.id.llSendCode)
    LinearLayout llSendCode;
    @Bind(R.id.etPsd)
    EditText etPsd;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.etCode)
    EditTextWithDel etCode;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_register, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("注册");
    }

    @Override
    protected void initializeView() {
        etUserName.setText("18020130334");
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
            case R.id.llSendCode:
                if (StringUtil.isBlank(mDataManager.getValueFromView(etUserName))) {
                    mDataManager.showToast("请输入手机号");
                    return;
                }

                PopupWindowManager.getInstance(activity).showVerifyCode(view, (type, values) -> {
                    Map<String, String> params = new HashMap<>();
                    params.put("type", "1");
                    params.put("phone", mDataManager.getValueFromView(etUserName));
                    String timeLong = TimeUtils.getNowTimeMills() + "";
                    params.put("times", timeLong);
                    params.put("code", "1");
                    params.put("type", "1");
                    params.put("str", MD5Util.createSign("Haozuowenapp" + MD5Util.createSign("timeLong" + mDataManager.getValueFromView(etUserName))));
                    mModel.requestCode(params, new ZnzHttpListener() {
                        @Override
                        public void onSuccess(JSONObject responseOriginal) {
                            super.onSuccess(responseOriginal);
                        }

                        @Override
                        public void onFail(String error) {
                            super.onFail(error);
                        }
                    });
                });
                break;
            case R.id.tvSubmit:
                gotoActivity(CompleteInfoAct.class);
                break;
        }
    }
}
