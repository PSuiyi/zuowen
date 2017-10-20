package com.znz.zuowen.ui.common;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.common.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date： 2017/10/9 2017
 * User： PSuiyi
 * Description：
 */

public class AgreementAct extends BaseAppActivity {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvContent)
    TextView tvContent;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_agreement, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("支付条款");
    }

    @Override
    protected void initializeView() {
        tvContent.setText(Html.fromHtml(mDataManager.readTempData(Constants.ZHIFU_AGREEMENT)));
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
}
