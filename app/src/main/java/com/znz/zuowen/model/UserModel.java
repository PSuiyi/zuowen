package com.znz.zuowen.model;

import android.content.Context;

import com.znz.compass.znzlibray.base_znz.BaseModel;
import com.znz.compass.znzlibray.base_znz.IView;
import com.znz.compass.znzlibray.network.retorfit.ZnzRetrofitUtil;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.zuowen.api.ApiService;

import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

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

    //注册
    public void reuqestRegister(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "10000");
        request(apiService.post(params), znzHttpListener, BaseModel.LODING_PD);
    }

    //验证码
    public void requestCode(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("code", "1");
        params.put("type", "1");
        request(apiService.requestCode(params), znzHttpListener, LODING_PD);
    }


    //账号密码登录
    public void requestPassword(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "10001");
        request(apiService.post(params), znzHttpListener, LODING_PD);
    }

    //第三方登录
    public void requestLoginThird(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "00001");
        request(apiService.post(params), znzHttpListener, LODING_PD);
    }

    //用户详情
    public void requestMineInfo(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "10002");
        request(apiService.post(params), znzHttpListener);
    }

    //获取别名
    public void requestAlias(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "97000");
        request(apiService.post(params), znzHttpListener);
    }


    //完善资料
    public void requestInfo(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "10003");
        request(apiService.post(params), znzHttpListener);
    }

    //绑定手机号
    public void requestBind(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "10006");
        request(apiService.post(params), znzHttpListener, LODING_PD);
    }

    //查询手机号码是否绑定当前机构其他账号
    public void requestBindSelect(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "10007");
        request(apiService.post(params), znzHttpListener, LODING_LODING);
    }

    //获取用户偏好列表
    public void requestInterestParent(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "10009");
        request(apiService.post(params), znzHttpListener);
    }

    //修改用户偏好列表
    public void requestInterestParentUpdate(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "10010");
        request(apiService.post(params), znzHttpListener, LODING_PD);
    }

    //用户反馈
    public void requestUserFeedBack(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "91000");
        request(apiService.post(params), znzHttpListener, LODING_PD);
    }

    //清空用户账户明细
    public void requestMoneyDetailDelete(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "11001");
        request(apiService.post(params), znzHttpListener);
    }

    //查询用户余额接口
    public void requestQueryMoney(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "11002");
        request(apiService.post(params), znzHttpListener);
    }

    //查询用户账户明细
    public Observable<ResponseBody> requestMoneyDetail(Map<String, String> params) {
        params.put("requestCode", "11000");
        return apiService.post(params);
    }

    //分析师认证
    public void requestLiveApprove(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "10011");
        request(apiService.post(params), znzHttpListener);
    }

    //添加分组
    public void requestAddGroup(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "19000");
        request(apiService.post(params), znzHttpListener);
    }

    //查询用户分组列表
    public Observable<ResponseBody> requestUserGroupList(Map<String, String> params) {
        params.put("requestCode", "19001");
        return apiService.post(params);
    }

    //查询用户分组列表
    public void requestUserGroupList(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "19001");
        request(apiService.post(params), znzHttpListener);
    }

    //查询分析师列表
    public void requestAnalystList(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "10012");
        request(apiService.post(params), znzHttpListener, LODING_LODING);
    }

    //查询分析师列表
    public Observable<ResponseBody> requestAnalystList(Map<String, String> params) {
        params.put("requestCode", "10012");
        return apiService.post(params);
    }

    //查询行业列表或者用户行业列表接口
    public void requestIndustry(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "20002");
        request(apiService.post(params), znzHttpListener);
    }

    //查询系统字典
    public void requestDictionary(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "94000");
        request(apiService.post(params), znzHttpListener);
    }

    //查询系统字典
    public Observable<ResponseBody> requestDictionary(Map<String, String> params) {
        params.put("requestCode", "94000");
        return apiService.post(params);
    }

    //用户消息列表
    public Observable<ResponseBody> requestUserMessageList(Map<String, String> params) {
        params.put("requestCode", "18000");
        return apiService.post(params);
    }

    //清空用户消息记录
    public void requestUserMessageDelete(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "18001");
        request(apiService.post(params), znzHttpListener);
    }


    //查询分类，分组，默认分组用户列表
    public Observable<ResponseBody> requestUserList(Map<String, String> params) {
        params.put("requestCode", "19004");
        return apiService.post(params);
    }

    //查询分类，分组，默认分组用户列表
    public void requestUserList(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "19004");
        request(apiService.post(params), znzHttpListener);
    }

    //搜索所有人
    public void requestAllUserList(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "19007");
        request(apiService.post(params), znzHttpListener);
    }

    //批量修改用户分组
    public void requestUpdateUserListGroup(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "19005");
        request(apiService.post(params), znzHttpListener);
    }

    //查询我的收藏列表
    public Observable<ResponseBody> requestMineFavList(Map<String, String> params) {
        params.put("requestCode", "92003");
        return apiService.post(params);
    }

    //查询直播报名用户列表
    public Observable<ResponseBody> requestJoinUserList(Map<String, String> params) {
        params.put("requestCode", "81009");
        return apiService.post(params);
    }

    //查询机构会员等级列表
    public void requestVIPLevel(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "15000");
        request(apiService.post(params), znzHttpListener, LODING_PD);
    }

    //保存订单
    public void requestOrderAdd(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "70000");
        request(apiService.post(params), znzHttpListener, LODING_PD);
    }

    //获取微信支付参数
    public void requestPayWxParams(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "99800");
        request(apiService.post(params), znzHttpListener);
    }

    //获取支付宝支付参数
    public void requestPayAliParams(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "99900");
        request(apiService.post(params), znzHttpListener);
    }

    //修改订单状态
    public void requestChagePayStatus(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("requestCode", "70001");
        request(apiService.post(params), znzHttpListener);
    }

}
