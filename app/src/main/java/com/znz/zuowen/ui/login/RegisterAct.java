package com.znz.zuowen.ui.login;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
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
    @Bind(R.id.tvSendCode)
    TextView tvSendCode;
    private CountDownTimer timer;

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
        if (StringUtil.isBlank(mDataManager.getValueFromView(etUserName))) {
            return;
        }
        if (!StringUtil.isMobile(mDataManager.getValueFromView(etUserName))) {
            return;
        }
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
            case R.id.llSendCode:
                if (StringUtil.isBlank(mDataManager.getValueFromView(etUserName))) {
                    mDataManager.showToast("请输入手机号");
                    return;
                }
                if (!StringUtil.isMobile(mDataManager.getValueFromView(etUserName))) {
                    mDataManager.showToast("请输入正确的手机号");
                    return;
                }

                llSendCode.setClickable(false);
                timer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long l) {
                        tvSendCode.setText(l / 1000 + "s");
                    }

                    @Override
                    public void onFinish() {
                        tvSendCode.setText("重新发送");
                        llSendCode.setClickable(true);
                    }
                }.start();

                Map<String, String> params = new HashMap<>();
                params.put("phone", mDataManager.getValueFromView(etUserName));
                String timeLong = TimeUtils.getNowTimeMills() + "";
                params.put("times", timeLong);
                params.put("str", MD5Util.createSign("Haozuowenapp" + MD5Util.createSign(timeLong + mDataManager.getValueFromView(etUserName))));
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
                break;
            case R.id.tvSubmit:
                if (StringUtil.isBlank(mDataManager.getValueFromView(etUserName))) {
                    mDataManager.showToast("请输入手机号");
                    return;
                }
                if (!StringUtil.isMobile(mDataManager.getValueFromView(etUserName))) {
                    mDataManager.showToast("请输入正确的手机号");
                    return;
                }
                if (StringUtil.isBlank(mDataManager.getValueFromView(etCode))) {
                    mDataManager.showToast("请输入验证码");
                    return;
                }
                if (StringUtil.isBlank(mDataManager.getValueFromView(etPsd))) {
                    mDataManager.showToast("请输入密码");
                    return;
                }
                Map<String, String> params2 = new HashMap<>();
                params2.put("phone", mDataManager.getValueFromView(etUserName));
                params2.put("code", mDataManager.getValueFromView(etCode));
                params2.put("passwd", mDataManager.getValueFromView(etPsd));
                mModel.reuqestRegister(params2, new ZnzHttpListener() {
                    @Override
                    public void onSuccess(JSONObject responseOriginal) {
                        super.onSuccess(responseOriginal);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", responseObject.getString("uid"));
                        bundle.putString("code", responseObject.getString("promo_code"));
                        gotoActivity(CompleteInfoAct.class, bundle);
                    }

                    @Override
                    public void onFail(String error) {
                        super.onFail(error);
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
