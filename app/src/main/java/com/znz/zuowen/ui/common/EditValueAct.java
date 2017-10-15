package com.znz.zuowen.ui.common;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.EditTextWithDel;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.common.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/10/10 2017
 * User： PSuiyi
 * Description：
 */

public class EditValueAct extends BaseAppActivity {
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
    }
}
