package com.znz.zuowen.ui.mine;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.model.UserModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Date： 2017/10/31 2017
 * User： PSuiyi
 * Description：
 */

public class FeedbackAct extends BaseAppActivity<UserModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_feedback, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("意见反馈");
    }

    @Override
    protected void initializeView() {
        mDataManager.toggleInputSoft();
    }

    @Override
    protected void loadDataFromServer() {

    }

    @OnClick(R.id.tvSubmit)
    public void onViewClicked() {
        if (StringUtil.isBlank(mDataManager.getValueFromView(etContent))) {
            mDataManager.showToast("请输入反馈内容");
            return;
        }
        if (StringUtil.isBlank(mDataManager.getValueFromView(etPhone))) {
            mDataManager.showToast("请输入联系方式");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("content", mDataManager.getValueFromView(etContent));
        params.put("contact", mDataManager.getValueFromView(etPhone));
        mModel.reuqestFeedback(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                mDataManager.showToast("提交成功");
                finish();
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }
}
