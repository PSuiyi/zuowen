package com.znz.zuowen.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ClassAdapter;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.bean.ClassBean;
import com.znz.zuowen.common.Constants;
import com.znz.zuowen.model.UserModel;
import com.znz.zuowen.ui.common.AgreementAct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/10/1 2017
 * User： PSuiyi
 * Description：
 */

public class MineClassAct extends BaseAppActivity<UserModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvAgreement)
    TextView tvAgreement;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.ivUserHeader)
    HttpImageView ivUserHeader;
    @Bind(R.id.tvNickName)
    TextView tvNickName;
    @Bind(R.id.tvVip)
    TextView tvVip;
    @Bind(R.id.tvClassOwn)
    TextView tvClassOwn;
    @Bind(R.id.tvIntro)
    TextView tvIntro;
    @Bind(R.id.rvClass)
    RecyclerView rvClass;
    @Bind(R.id.tvClassTotal)
    TextView tvClassTotal;

    private List<ClassBean> dataList = new ArrayList<>();

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_mine_class, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new UserModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("课时查询");
    }

    @Override
    protected void initializeView() {
        if (!StringUtil.isBlank(mDataManager.readTempData(Constants.User.NAME))) {
            mDataManager.setValueToView(tvNickName, mDataManager.readTempData(Constants.User.NAME));
        }
        ivUserHeader.loadHeaderImage(mDataManager.readTempData(Constants.User.HEADIMG));
        mDataManager.setValueToView(tvVip, "VIP" + mDataManager.readTempData(Constants.User.VIP_LEVEL));
    }

    @Override
    protected void loadDataFromServer() {

        Map<String, String> params = new HashMap<>();
        mModel.requestClass(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                mDataManager.setValueToView(tvClassOwn, responseObject.getString("my_class_hour") + "课时");
                mDataManager.setValueToView(tvClassTotal, responseObject.getString("total_money")
                        + "元（已赠送" + responseObject.getString("give_class_hour") + "课时）");

                rvClass.setLayoutManager(new LinearLayoutManager(activity));
                rvClass.setHasFixedSize(true);
                rvClass.setNestedScrollingEnabled(false);
                dataList.clear();
                dataList.addAll(JSONArray.parseArray(responseObject.getString("class_hour_list"), ClassBean.class));
                if (!dataList.isEmpty()) {
                    dataList.get(0).setChecked(true);
                }
                ClassAdapter adapter = new ClassAdapter(dataList);
                rvClass.setAdapter(adapter);
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tvAgreement, R.id.tvSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvAgreement:
                gotoActivity(AgreementAct.class);
                break;
            case R.id.tvSubmit:
                String id = null;
                for (ClassBean classBean : dataList) {
                    if (classBean.isChecked()) {
                        id = classBean.getId();
                    }
                }
                if (StringUtil.isBlank(id)) {
                    mDataManager.showToast("请选择充值课时数");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                gotoActivity(PayWayAct.class, bundle);
                break;
        }
    }
}
