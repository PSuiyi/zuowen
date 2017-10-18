package com.znz.zuowen.ui.mine;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.model.ArticleModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/10/1 2017
 * User： PSuiyi
 * Description：
 */

public class PayWayAct extends BaseAppActivity<ArticleModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvMoney)
    TextView tvMoney;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.rbAli)
    RadioButton rbAli;
    @Bind(R.id.rbWechat)
    RadioButton rbWechat;
    @Bind(R.id.rbGroup)
    RadioGroup rbGroup;
    private String id;

    private int payWay = 1;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_pay_way, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("支付方式");
    }

    @Override
    protected void initializeView() {
        rbAli.setChecked(true);
        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbAli:
                        payWay = 1;
                        break;
                    case R.id.rbWechat:
                        payWay = 2;
                        break;
                }
            }
        });
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
        Map<String, String> params = new HashMap<>();
        params.put("class_id", id);
        if (payWay == 1) {
            mModel.requestBuyAli(params, new ZnzHttpListener() {
                @Override
                public void onSuccess(JSONObject responseOriginal) {
                    super.onSuccess(responseOriginal);
                }

                @Override
                public void onFail(String error) {
                    super.onFail(error);
                }
            });
        } else {
            mModel.requestBuyWechat(params, new ZnzHttpListener() {
                @Override
                public void onSuccess(JSONObject responseOriginal) {
                    super.onSuccess(responseOriginal);
                }

                @Override
                public void onFail(String error) {
                    super.onFail(error);
                }
            });
        }
    }
}
