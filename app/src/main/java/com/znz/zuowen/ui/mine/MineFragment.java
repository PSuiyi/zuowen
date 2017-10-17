package com.znz.zuowen.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.network_status.NetUtils;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.gallery.inter.IPhotoSelectCallback;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.ios.ActionSheetDialog.UIAlertDialog;
import com.znz.compass.znzlibray.views.row_view.ZnzRowDescription;
import com.znz.compass.znzlibray.views.row_view.ZnzRowGroupView;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppFragment;
import com.znz.zuowen.bean.UserBean;
import com.znz.zuowen.common.Constants;
import com.znz.zuowen.event.EventRefresh;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.CommonModel;
import com.znz.zuowen.model.UserModel;
import com.znz.zuowen.ui.common.AgreementAct;
import com.znz.zuowen.ui.common.EditValueAct;
import com.znz.zuowen.ui.home.article.ArticleListAct;
import com.znz.zuowen.ui.home.video.VideoListAct;
import com.znz.zuowen.ui.login.LoginAct;
import com.znz.zuowen.utils.AppUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/9/26 2017
 * User： PSuiyi
 * Description：
 */

public class MineFragment extends BaseAppFragment<UserModel> {
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
    @Bind(R.id.tvNickName)
    TextView tvNickName;

    private ArrayList<ZnzRowDescription> rowDescriptionList;
    private CommonModel commonModel;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.frag_mine, 4};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
        commonModel = new CommonModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setNavLeftGone();
    }

    @Override
    protected void initializeView() {
        if (!StringUtil.isBlank(mDataManager.readTempData(Constants.User.NAME))) {
            mDataManager.setValueToView(tvNickName, mDataManager.readTempData(Constants.User.NAME));
        }
        ivUserHeader.loadHeaderImage(mDataManager.readTempData(Constants.User.HEADIMG));

        rowDescriptionList = new ArrayList<>();
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("用户名")
                .withValue(mDataManager.readTempData(Constants.User.NAME))
                .withEnableArraw(true)
                .withOnClickListener(v -> {
                    gotoActivity(EditValueAct.class);
                })
                .build());
        rowDescriptionList.add(new ZnzRowDescription.Builder()
                .withTitle("手机号绑定")
                .withEnableArraw(true)
                .withValue(StringUtil.getSignPhone(mDataManager.readTempData(Constants.User.MOBILE)))
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
                .withTitle("已购买的微课")
                .withEnableArraw(true)
                .withOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("page", "已购买");
                    gotoActivity(VideoListAct.class, bundle);
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
                    gotoActivity(UpdatePsdAct.class);
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
                                if (NetUtils.isNetworkAvailable(activity)) {
                                    Map<String, String> params = new HashMap<>();
                                    mModel.requestLogout(params, new ZnzHttpListener() {
                                        @Override
                                        public void onSuccess(JSONObject responseOriginal) {
                                            super.onSuccess(responseOriginal);
                                            mDataManager.logout(activity, LoginAct.class);
                                        }

                                        @Override
                                        public void onFail(String error) {
                                            super.onFail(error);
                                        }
                                    });
                                } else {
                                    mDataManager.logout(activity, LoginAct.class);
                                }
                            })
                            .show();
                })
                .build());
        commonRowGroup.notifyDataChanged(rowDescriptionList);
    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        mModel.requestMineInfo(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                UserBean bean = JSONObject.parseObject(responseOriginal.getString("data"), UserBean.class);
                AppUtils.getInstance(activity).saveUserData(bean);
                mDataManager.setValueToView(tvNickName, bean.getUsername());
                ivUserHeader.loadHeaderImage(Constants.IMG_URL + bean.getPhoto());
                mDataManager.setValueToView(tvVip, "VIP" + bean.getVip());
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
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
                            commonModel.requestUploadImage(photoList.get(0), new ZnzHttpListener() {
                                @Override
                                public void onSuccess(JSONObject responseOriginal) {
                                    super.onSuccess(responseOriginal);
                                    String url = responseObject.getString("url");
                                    Map<String, String> params = new HashMap<>();
                                    params.put("url", url);
                                    mModel.requestUpdateHeader(params, new ZnzHttpListener() {
                                        @Override
                                        public void onSuccess(JSONObject responseOriginal) {
                                            super.onSuccess(responseOriginal);
                                            mDataManager.saveTempData(Constants.User.HEADIMG, Constants.IMG_URL + url);
                                        }

                                        @Override
                                        public void onFail(String error) {
                                            super.onFail(error);
                                        }
                                    });
                                }

                                @Override
                                public void onFail(String error) {
                                    super.onFail(error);
                                }
                            });
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventRefresh event) {
        if (event.getFlag() == EventTags.REFRESH_MINE_INFO) {
            if (!StringUtil.isBlank(mDataManager.readTempData(Constants.User.NAME))) {
                mDataManager.setValueToView(tvNickName, mDataManager.readTempData(Constants.User.NAME));
                rowDescriptionList.get(0).setValue(mDataManager.readTempData(Constants.User.NAME));
                commonRowGroup.notifyDataChanged(rowDescriptionList);
            }
        }
    }
}
