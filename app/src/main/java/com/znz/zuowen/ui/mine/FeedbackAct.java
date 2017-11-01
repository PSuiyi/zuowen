package com.znz.zuowen.ui.mine;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/10/31 2017
 * User： PSuiyi
 * Description：
 */

public class FeedbackAct extends BaseAppActivity {
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

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("意见反馈");
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

    @OnClick(R.id.tvSubmit)
    public void onViewClicked() {
        if (StringUtil.isBlank(mDataManager.getValueFromView(etContent))) {
            mDataManager.showToast("请输入反馈内容");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("content", mDataManager.getValueFromView(etContent));
        if (!StringUtil.isBlank(mDataManager.getValueFromView(etPhone))) {
            params.put("content", mDataManager.getValueFromView(etPhone));
        }
//        mModel.feedBack(params, new ZnzHttpListener() {
//            @Override
//            public void onSuccess(JSONObject responseOriginal) {
//                super.onSuccess(responseOriginal);
//                mDataManager.showToast("提交成功");
//                finish();
//            }
//
//            @Override
//            public void onFail(String error) {
//                super.onFail(error);
//            }
//        });
    }
}
