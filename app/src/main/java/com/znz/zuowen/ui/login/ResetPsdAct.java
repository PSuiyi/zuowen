package com.znz.zuowen.ui.login;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.EditTextWithDel;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.model.UserModel;
import com.znz.zuowen.utils.PopupWindowManager;

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

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_reset_psd, 1};
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
                PopupWindowManager.getInstance(activity).showVerifyCode(view, (type, values) -> {

                });
                break;
            case R.id.tvSubmit:
//                if (StringUtil.isBlank(mDataManager.getValueFromView(etCode))) {
//                    mDataManager.showToast("请输入验证码");
//                    return;
//                }
//                if (StringUtil.isBlank(mDataManager.getValueFromView(etPsd))) {
//                    mDataManager.showToast("请输入密码");
//                    return;
//                }
//                Map<String, String> params = new HashMap<>();
//                params.put("phone", mDataManager.getValueFromView(etUserName));
//                params.put("code", mDataManager.getValueFromView(etCode));
//                params.put("passwd", mDataManager.getValueFromView(etPsd));
//                mModel.reuqestRegister(params, new ZnzHttpListener() {
//                    @Override
//                    public void onSuccess(JSONObject responseOriginal) {
//                        super.onSuccess(responseOriginal);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("id", responseObject.getString("uid"));
//                        bundle.putString("code", responseObject.getString("promo_code"));
//                        gotoActivity(CompleteInfoAct.class, bundle);
//                    }
//
//                    @Override
//                    public void onFail(String error) {
//                        super.onFail(error);
//                    }
//                });
                break;
        }
    }
}
