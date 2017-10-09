package com.znz.zuowen.ui.mine;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.gallery.inter.IPhotoSelectCallback;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.ios.ActionSheetDialog.UIAlertDialog;
import com.znz.compass.znzlibray.views.row_view.ZnzRowDescription;
import com.znz.compass.znzlibray.views.row_view.ZnzRowGroupView;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppFragment;

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
                    gotoActivity(MineArticleAct.class);
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
                    new UIAlertDialog(activity)
                            .builder()
                            .setMsg("是否确定退出登录账号")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", v2 -> {

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

    @OnClick(R.id.ivUserHeader)
    public void onViewClicked() {

        new RxPermissions(activity)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(granted -> {
                    if (granted) {
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
                    } else {
                        new AlertDialog.Builder(activity)
                                .setTitle("权限申请")
                                .setMessage("该操作需要相机权限，请在设置中打开该应用的相机权限")
                                .setCancelable(false)
                                .setNegativeButton("取消", null)
                                .setPositiveButton("去设置", (dialog, which) -> {
                                    mDataManager.openSettingPermissions(activity);
                                })
                                .show();
                    }
                });
    }
}
