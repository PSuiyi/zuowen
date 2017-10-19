package com.znz.zuowen.ui.mine;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
 * Date： 2017/9/30 2017
 * User： PSuiyi
 * Description：
 */

public class UpdatePhoneTwoAct extends BaseAppActivity<UserModel> {
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.etCode)
    EditTextWithDel etCode;
    @Bind(R.id.tvSendCode)
    TextView tvSendCode;
    @Bind(R.id.llSendCode)
    LinearLayout llSendCode;
    private String phone;
    private String imgCode;
    private CountDownTimer timer;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_update_phone_two, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
        if (getIntent().hasExtra("phone")) {
            phone = getIntent().getStringExtra("phone");
        }
        if (getIntent().hasExtra("imgCode")) {
            imgCode = getIntent().getStringExtra("imgCode");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("改绑手机号");
    }

    @Override
    protected void initializeView() {
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
    }

    /**
     * 处理按钮状态
     */
    private void handleBtnState() {
        if (StringUtil.isBlank(mDataManager.getValueFromView(etCode))) {
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
        Map<String, String> params = new HashMap<>();
        switch (view.getId()) {
            case R.id.llSendCode:
                params.put("phone", phone);
                String timeLong = TimeUtils.getNowTimeMills() + "";
                params.put("times", timeLong);
                params.put("type", "4");
                params.put("str", MD5Util.createSign("Haozuowenapp" + MD5Util.createSign(timeLong + phone)));
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
                break;
            case R.id.tvSubmit:
                if (StringUtil.isBlank(mDataManager.getValueFromView(etCode))) {
                    mDataManager.showToast("请输入验证码");
                    return;
                }
                params.put("phone", phone);
                params.put("code", mDataManager.getValueFromView(etCode));
                params.put("imgcode", imgCode);
                mModel.requestUpdatePhone(params, new ZnzHttpListener() {
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
