package com.znz.zuowen.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.gallery.inter.IPhotoSelectCallback;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.ios.ActionSheetDialog.UIAlertDialog;
import com.znz.compass.znzlibray.views.row_view.ZnzRowDescription;
import com.znz.compass.znzlibray.views.row_view.ZnzRowGroupView;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppFragment;
import com.znz.zuowen.ui.common.AgreementAct;
import com.znz.zuowen.ui.home.article.ArticleListAct;
import com.znz.zuowen.ui.login.LoginAct;
import com.znz.zuowen.ui.login.ResetPsdAct;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.tvVip)
    TextView tvVip;

    private ArrayList<ZnzRowDescription> rowDescriptionList;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.frag_mine, 4};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
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
                    gotoActivity(UpdatePhoneAct.class);
                })
                .build());
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("查询课时")
                .withEnableArraw(true)
                .withOnClickListener(v -> {
                    gotoActivity(MineClassAct.class);
                })
                .build());
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("我的作文")
                .withEnableArraw(true)
                .withOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("page", "我的作文");
                    gotoActivity(ArticleListAct.class, bundle);
                })
                .build());
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("更改登录密码")
                .withEnableArraw(true)
                .withEnableLongLine(true)
                .withOnClickListener(v -> {
                    gotoActivity(ResetPsdAct.class);
                })
                .build());
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("退出登录账号")
                .withEnableArraw(true)
                .withOnClickListener(v -> {
                    new UIAlertDialog(activity)
                            .builder()
                            .setMsg("是否确定退出登录账号")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", v2 -> {
                                mDataManager.logout(activity, LoginAct.class);
                            })
                            .show();
                })
                .build());
        commonRowGroup.notifyDataChanged(rowDescriptionList);
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ivUserHeader, R.id.tvVip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivUserHeader:
                mDataManager.openPhotoSelectSingle(activity, new IPhotoSelectCallback() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(List<String> photoList) {
                        if (!photoList.isEmpty()) {
                            ivUserHeader.loadHeaderImage(photoList.get(0));
                        }
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onError() {

                    }
                }, true);
                break;
            case R.id.tvVip:
                gotoActivity(AgreementAct.class);
                break;
        }
    }
}
