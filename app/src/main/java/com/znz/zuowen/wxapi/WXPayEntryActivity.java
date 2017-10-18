package com.znz.zuowen.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzpay.common.PayKeys;
import com.znz.zuowen.event.EventPay;
import com.znz.zuowen.event.EventTags;

import org.greenrobot.eventbus.EventBus;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;
    private DataManager mDataManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, PayKeys.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        mDataManager = DataManager.getInstance(this);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0:
                    mDataManager.showToast("支付成功");
                    EventBus.getDefault().post(new EventPay(EventTags.PAY_WX_SUCCESS));
                    break;
                case -1:
                    mDataManager.showToast("支付失败");
                    EventBus.getDefault().post(new EventPay(EventTags.PAY_WX_FAIL));
                    break;
                case -2:
                    mDataManager.showToast("支付取消");
                    EventBus.getDefault().post(new EventPay(EventTags.PAY_WX_CANCEL));
                    break;
            }
            finish();
        }
    }
}