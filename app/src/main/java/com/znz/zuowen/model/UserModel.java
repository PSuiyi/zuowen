package com.znz.zuowen.model;

import android.content.Context;

import com.znz.compass.znzlibray.base_znz.BaseModel;
import com.znz.compass.znzlibray.base_znz.IView;
import com.znz.compass.znzlibray.network.retorfit.ZnzRetrofitUtil;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.zuowen.api.ApiService;

import java.util.Map;

/**
 * Date： 2017/5/15 2017
 * User： PSuiyi
 * Description：
 */

public class UserModel extends BaseModel {
    private ApiService apiService;
    private Context context;

    public UserModel(Context context, IView mView) {
        super(context, mView);
        apiService = ZnzRetrofitUtil.getInstance().createService(ApiService.class);
        this.context = context;
    }

    //验证码
    public void requestCode(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("code", "1");
        params.put("type", "1");
        request(apiService.requestCode(params), znzHttpListener, LODING_PD);
    }

    //验证码
    public void requestCodeImg(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("code", "1");
        params.put("type", "1");
        request(apiService.requestCodeImg(params), znzHttpListener);
    }

    //注册
    public void reuqestRegister(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("type", "1");
        request(apiService.reuqestRegister(params), znzHttpListener, BaseModel.LODING_PD);
    }

    //注册
    public void reuqestCompleteInfo(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("code", "1");
        params.put("type", "1");
        request(apiService.reuqestCompleteInfo(params), znzHttpListener, BaseModel.LODING_PD);
    }

    //账号密码登录
    public void requestLogin(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("code", "1");
        params.put("type", "1");
        request(apiService.requestLogin(params), znzHttpListener, LODING_PD);
    }
}
