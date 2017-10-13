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
}
