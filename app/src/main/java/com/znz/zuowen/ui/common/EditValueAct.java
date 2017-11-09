package com.znz.zuowen.ui.common;

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
import com.znz.zuowen.common.Constants;
import com.znz.zuowen.event.EventRefresh;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.UserModel;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/10/10 2017
 * User： PSuiyi
 * Description：
 */

public class EditValueAct extends BaseAppActivity<UserModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.etValue)
    EditTextWithDel etValue;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_edit_value, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("用户名修改");
    }

    @Override
    protected void initializeView() {
        if (!StringUtil.isBlank(mDataManager.readTempData(Constants.User.NAME))) {
            mDataManager.setValueToView(etValue, mDataManager.readTempData(Constants.User.NAME));
        }

        mDataManager.toggleInputSoft();

        handleBtnState();

        etValue.addTextChangedListener(new TextWatcher() {
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
        if (StringUtil.isBlank(mDataManager.getValueFromView(etValue))) {
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
        if (StringUtil.isBlank(mDataManager.getValueFromView(etValue))) {
            mDataManager.showToast("请输入用户名");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", mDataManager.getValueFromView(etValue));
        mModel.requestUpdateName(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                mDataManager.saveTempData(Constants.User.NAME, mDataManager.getValueFromView(etValue));
                EventBus.getDefault().post(new EventRefresh(EventTags.REFRESH_MINE_INFO));
                finish();
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }
}
