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

public class ArticleModel extends BaseModel {

    private ApiService apiService;

    public ArticleModel(Context context, IView mView) {
        super(context, mView);
        apiService = ZnzRetrofitUtil.getInstance().createService(ApiService.class);
    }


    //获取版本号
    public void requestVersion(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        request(apiService.getVersion(params), znzHttpListener);
    }

    //获取首页数据
    public Observable<ResponseBody> requestHomeList(Map<String, String> params) {
        params.put("code", "1");
        params.put("type", "1");
        params.put("token", mDataManager.getAccessToken());
        return apiService.requestHomeList(params);
    }

    public Observable<ResponseBody> requestFavList(Map<String, String> params) {
        params.put("code", "1");
        params.put("type", "1");
        params.put("token", mDataManager.getAccessToken());
        return apiService.requestFavList(params);
    }

    public Observable<ResponseBody> requestWeekList(Map<String, String> params) {
        params.put("code", "1");
        params.put("type", "1");
        params.put("token", mDataManager.getAccessToken());
        return apiService.requestWeekList(params);
    }

    public Observable<ResponseBody> requestVoteList(Map<String, String> params) {
        params.put("code", "1");
        params.put("type", "1");
        params.put("token", mDataManager.getAccessToken());
        return apiService.requestVoteList(params);
    }

    public void requestVoteDetail(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("code", "1");
        params.put("type", "1");
        params.put("token", mDataManager.getAccessToken());
        request(apiService.requestVoteDetail(params), znzHttpListener, LODING_LODING);
    }

    public Observable<ResponseBody> requestGoodList(Map<String, String> params) {
        params.put("code", "1");
        params.put("type", "1");
        params.put("token", mDataManager.getAccessToken());
        return apiService.requestGoodList(params);
    }

    //获取微课列表
    public Observable<ResponseBody> requestVideoList(Map<String, String> params) {
        params.put("code", "1");
        params.put("type", "1");
        params.put("token", mDataManager.getAccessToken());
        return apiService.requestVideoList(params);
    }

    public void requestVideoDetail(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("code", "1");
        params.put("type", "1");
        params.put("token", mDataManager.getAccessToken());
        request(apiService.requestVideoDetail(params), znzHttpListener, LODING_LODING);
    }
}
