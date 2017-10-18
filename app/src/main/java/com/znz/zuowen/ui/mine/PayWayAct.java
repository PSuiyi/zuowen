package com.znz.zuowen.ui.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.ZnzLog;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzpay.alipay.AliPayUtil;
import com.znz.compass.znzpay.wxpay.WXPayUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.event.EventPay;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.ArticleModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
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
    private String currentOrder;

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

    @OnClick(R.id.tvSubmit)
    public void onViewClicked() {
        Map<String, String> params = new HashMap<>();
        params.put("class_id", id);
        if (payWay == 1) {
            mModel.requestBuyAli(params, new ZnzHttpListener() {
                @Override
                public void onSuccess(JSONObject responseOriginal) {
                    super.onSuccess(responseOriginal);
                    AliPayUtil.getInstance(activity).startAliPay(responseObject.getString("str"));

                    currentOrder = responseObject.getString("ordersn");
                    ZnzLog.e("currentOrder---->" + currentOrder);
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
                    Map<String, String> params = new HashMap<>();
                    params.put("appid", responseObject.getString("appid"));
                    params.put("partnerid", responseObject.getString("partnerid"));
                    params.put("prepayid", responseObject.getString("prepayid"));
                    params.put("packageStr", responseObject.getString("package"));
                    params.put("nonceStr", responseObject.getString("noncestr"));
                    params.put("timeStamp", responseObject.getString("timestamp"));
                    params.put("paySign", responseObject.getString("sign"));
                    WXPayUtil.getInstance(activity).startWXPay(params);

                    currentOrder = responseObject.getString("ordersn");
                    ZnzLog.e("currentOrder---->" + currentOrder);
                }

                @Override
                public void onFail(String error) {
                    super.onFail(error);
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.register(this);
        IntentFilter aliFilter = new IntentFilter();
        aliFilter.addAction(AliPayUtil.ALIPAY_ACTION);
        context.registerReceiver(receiver, aliFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventPay event) {
        if (event.getFlag() == EventTags.PAY_WX_SUCCESS) {
            gotoActivity(PaySuccessAct.class);
        }
    }

    /**
     * 支付宝状态监听
     */
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra(AliPayUtil.ALIPAY_STATUS);
            switch (status) {
                case "成功":
                    gotoActivity(PaySuccessAct.class);
                    break;
                case "取消":
                    break;
                case "失败":
                    break;
            }
        }
    };
}
