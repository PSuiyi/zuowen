package com.znz.zuowen.ui.login;


import android.os.Bundle;
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

public class ResetPsdOneAct extends BaseAppActivity<UserModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.etPhone)
    EditTextWithDel etPhone;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_reset_psd_one, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("找回密码");
    }

    @Override
    protected void initializeView() {
        etPhone.setText("18020130334");
        etPhone.addTextChangedListener(new TextWatcher() {
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
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPhone))) {
            return;
        }
        if (!StringUtil.isMobile(mDataManager.getValueFromView(etPhone))) {
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

    @OnClick({R.id.tvSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSubmit:
                if (StringUtil.isBlank(mDataManager.getValueFromView(etPhone))) {
                    mDataManager.showToast("请输入手机号");
                    return;
                }
                if (!StringUtil.isMobile(mDataManager.getValueFromView(etPhone))) {
                    mDataManager.showToast("请输入正确的手机号");
                    return;
                }

                PopupWindowManager.getInstance(activity).showVerifyCode(view,
                        "http://hao.magick.ltd/index.php?m=rest&c=login&a=getimgcode&type=2",
                        (type, values) -> {
                            Map<String, String> params = new HashMap<>();
                            params.put("phone", mDataManager.getValueFromView(etPhone));
                            params.put("imgcode", values[0]);
                            mModel.reuqestPsdOne(params, new ZnzHttpListener() {
                                @Override
                                public void onSuccess(JSONObject responseOriginal) {
                                    super.onSuccess(responseOriginal);

                                    Map<String, String> params = new HashMap<>();
                                    params.put("phone", mDataManager.getValueFromView(etPhone));
                                    String timeLong = TimeUtils.getNowTimeMills() + "";
                                    params.put("times", timeLong);
                                    params.put("type", "2");
                                    params.put("str", MD5Util.createSign("Haozuowenapp" + MD5Util.createSign(timeLong + mDataManager.getValueFromView(etPhone))));
                                    mModel.requestCode(params, new ZnzHttpListener() {
                                        @Override
                                        public void onSuccess(JSONObject responseOriginal) {
                                            super.onSuccess(responseOriginal);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("id", responseObject.getString("id"));
                                            bundle.putString("username", responseObject.getString("username"));
                                            bundle.putString("str", responseObject.getString("str"));
                                            gotoActivity(ResetPsdTwoAct.class, bundle);
                                        }

                                        @Override
                                        public void onFail(String error) {
                                            super.onFail(error);
                                        }
                                    });
                                }

                                @Override
                                public void onFail(String error) {
                                    super.onFail(error);
                                }
                            });
                        });
                break;
        }
    }
}
