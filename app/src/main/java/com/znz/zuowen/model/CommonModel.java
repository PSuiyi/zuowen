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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Date： 2017/5/15 2017
 * User： PSuiyi
 * Description：
 */

public class CommonModel extends BaseModel {

    private ApiService apiService;

    public CommonModel(Context context, IView mView) {
        super(context, mView);
        apiService = ZnzRetrofitUtil.getInstance(false).createService(ApiService.class);
    }


    //获取版本号
    public void requestVersion(Map<String, String> params, ZnzHttpListener znzHttpListener) {
        params.put("code", "1");
        params.put("type", "1");
        request(apiService.getVersion(params), znzHttpListener);
    }

    //上传图片
    public void requestUploadImage(String url, ZnzHttpListener znzHttpListener) {
        Map<String, String> params = new HashMap<>();
        params.put("code", "1");
        params.put("type", "1");
        File file = new File(url);
        Luban.get(context)
                .load(file)
                .asObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> throwable.printStackTrace())
                .onErrorResumeNext(throwable -> Observable.empty())
                .subscribe(file1 -> {
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file1);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), requestBody);
                    request(apiService.requestUploadImage(params, body), znzHttpListener);
                });
    }
}
