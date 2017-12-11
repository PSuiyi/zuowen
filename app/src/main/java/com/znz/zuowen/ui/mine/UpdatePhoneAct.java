package com.znz.zuowen.ui.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.common.ZnzConstants;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.MD5Util;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.utils.TimeUtils;
import com.znz.compass.znzlibray.views.EditTextWithDel;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.common.Constants;
import com.znz.zuowen.model.UserModel;
import com.znz.zuowen.utils.PopupWindowManager;

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

public class UpdatePhoneAct extends BaseAppActivity<UserModel> {
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvPhone)
    TextView tvPhone;
    @Bind(R.id.etPhoneNew)
    EditTextWithDel etPhoneNew;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_verify_phone, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("改绑手机号");
    }

    @Override
    protected void initializeView() {
        if (!StringUtil.isBlank(mDataManager.readTempData(Constants.User.MOBILE))) {
            mDataManager.setValueToView(tvPhone, "当前手机号：" + mDataManager.readTempData(Constants.User.MOBILE));
        } else {
            tvPhone.setVisibility(View.GONE);
        }

        etPhoneNew.addTextChangedListener(new TextWatcher() {
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
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPhoneNew))) {
            return;
        }
        if (!StringUtil.isMobile(mDataManager.getValueFromView(etPhoneNew))) {
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
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPhoneNew))) {
            mDataManager.showToast("请输入手机号");
            return;
        }
        if (!StringUtil.isMobile(mDataManager.getValueFromView(etPhoneNew))) {
            mDataManager.showToast("请输入正确的手机号");
            return;
        }

        PopupWindowManager.getInstance(activity).showVerifyCode(tvSubmit,
                ZnzConstants.HTTP_URL + "index.php?m=rest&c=login&a=getimgcode&type=3",
                (type, values) -> {
                    Map<String, String> params = new HashMap<>();
                    params.put("phone", mDataManager.getValueFromView(etPhoneNew));
                    String timeLong = TimeUtils.getNowTimeMills() + "";
                    params.put("times", timeLong);
                    params.put("type", "4");
                    params.put("str", MD5Util.createSign("Haozuowenapp" + MD5Util.createSign(timeLong + mDataManager.getValueFromView(etPhoneNew))));
                    mModel.requestCode(params, new ZnzHttpListener() {
                        @Override
                        public void onSuccess(JSONObject responseOriginal) {
                            super.onSuccess(responseOriginal);
                            Bundle bundle = new Bundle();
                            bundle.putString("phone", mDataManager.getValueFromView(etPhoneNew));
                            bundle.putString("imgCode", values[0]);
                            gotoActivity(UpdatePhoneTwoAct.class, bundle);
                        }

                        @Override
                        public void onFail(String error) {
                            super.onFail(error);
                        }
                    });
                });
    }
}
