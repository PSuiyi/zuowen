package com.znz.zuowen.api;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Date： 2017/2/22 2017
 * User： PSuiyi
 * Description：
 */

public interface ApiService {
    @FormUrlEncoded
    @POST(" ")
    Observable<ResponseBody> post(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("touyanshe_api/pay/getPreReqParamsModel")
    Observable<ResponseBody> getPreReqParamsModel(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("touyanshe_api/pay/getAliPayInfo")
    Observable<ResponseBody> getAliPayInfo(@FieldMap Map<String, String> params);

    @Multipart
    @POST("touyanshe_api/s/api/")
    Observable<ResponseBody> uploadImageSingle(@QueryMap Map<String, String> params, @Part MultipartBody.Part file);

    @Multipart
    @POST("touyanshe_api/s/api/")
    Observable<ResponseBody> uploadFilesWithParts(@QueryMap Map<String, String> params, @Part() List<MultipartBody.Part> parts);

}
