package com.znz.zuowen.ui.mine;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date： 2017/10/31 2017
 * User： PSuiyi
 * Description：
 */

public class AboutUsAct extends BaseAppActivity {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvVersion)
    TextView tvVersion;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_about_us, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("关于我们");
    }

    @Override
    protected void initializeView() {
        tvVersion.setText("好作文V" + StringUtil.getVersionName(activity));
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
