package com.znz.zuowen.model;

import android.content.Context;

import com.znz.compass.znzlibray.base_znz.BaseModel;
import com.znz.compass.znzlibray.base_znz.IView;
import com.znz.compass.znzlibray.network.retorfit.ZnzRetrofitUtil;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.zuowen.api.ApiService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Date： 2017/5/15 2017
 * User： PSuiyi
 * Description：
 */

public class CommonModel extends BaseModel {

    private ApiService apiService;

    public CommonModel(Context context, IView mView) {
        super(context, mView);
        apiService = ZnzRetrofitUtil.getInstance().createService(ApiService.class);
    }

    /**
     * 上传图片
     *
     * @return
     */
    public void uploadImage(String url, ZnzHttpListener znzHttpListener) {
        Map<String, String> params = new HashMap<>();
        params.put("requestCode", "93333");
        File file = new File(url);
    }


    //获取版本号
    public void requestVersion(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("code", "1");
        params.put("type", "1");
        request(apiService.getVersion(params), znzHttpListener);
    }
}
