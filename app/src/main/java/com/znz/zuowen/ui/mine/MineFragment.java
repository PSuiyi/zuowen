package com.znz.zuowen.ui.mine;

import android.widget.LinearLayout;

import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.row_view.ZnzRowDescription;
import com.znz.compass.znzlibray.views.row_view.ZnzRowGroupView;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Date： 2017/9/26 2017
 * User： PSuiyi
 * Description：
 */

public class MineFragment extends BaseAppFragment {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.commonRowGroup)
    ZnzRowGroupView commonRowGroup;
    @Bind(R.id.ivUserHeader)
    HttpImageView ivUserHeader;

    private ArrayList<ZnzRowDescription> rowDescriptionList;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.frag_mine, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("我的");
        setNavLeftGone();
    }

    @Override
    protected void initializeView() {
        rowDescriptionList = new ArrayList<>();
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("用户名")
                .withValue("张三")
                .withEnableArraw(true)
                .withOnClickListener(v -> {
                })
                .build());
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("手机号绑定")
                .withEnableArraw(true)
                .withOnClickListener(v -> {
                })
                .build());
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("查询课时")
                .withEnableArraw(true)
                .withOnClickListener(v -> {
                })
                .build());
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("更改登录密码")
                .withEnableArraw(true)
                .withEnableLongLine(true)
                .withOnClickListener(v -> {
                })
                .build());
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("退出登录账号")
                .withEnableArraw(true)
                .withOnClickListener(v -> {
                })
                .build());
        commonRowGroup.notifyDataChanged(rowDescriptionList);
    }

    @Override
    protected void loadDataFromServer() {

    }
}
