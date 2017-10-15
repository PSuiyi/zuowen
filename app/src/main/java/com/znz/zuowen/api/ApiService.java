package com.znz.zuowen.api;


import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Date： 2017/2/22 2017
 * User： PSuiyi
 * Description：
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("index.php")
    Observable<ResponseBody> post(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=checkversion&a=check_version")
    Observable<ResponseBody> getVersion(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=send_sms")
    Observable<ResponseBody> requestCode(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=getimgcode")
    Observable<ResponseBody> requestCodeImg(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=resgister")
    Observable<ResponseBody> reuqestRegister(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=perfect_information")
    Observable<ResponseBody> reuqestCompleteInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=login")
    Observable<ResponseBody> requestLogin(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=my&a=mycollect")
    Observable<ResponseBody> requestFavList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=weekcomposition&a=index")
    Observable<ResponseBody> requestWeekList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=weekcomposition&a=detail")
    Observable<ResponseBody> requestWeekDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=votecomposition&a=index")
    Observable<ResponseBody> requestVoteList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=votecomposition&a=detail")
    Observable<ResponseBody> requestVoteDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=votecomposition&a=do_collect")
    Observable<ResponseBody> requestVoteFav(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=goodcomposition&a=index")
    Observable<ResponseBody> requestGoodList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=goodcomposition&a=detail")
    Observable<ResponseBody> requestGoodDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=goodcomposition&a=do_collect")
    Observable<ResponseBody> requestGoodFav(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=goodcomposition&a=do_like")
    Observable<ResponseBody> requestGoodLike(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=index&a=index")
    Observable<ResponseBody> requestHomeList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=classguidance&a=index")
    Observable<ResponseBody> requestVideoList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=classguidance&a=detail")
    Observable<ResponseBody> requestVideoDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=classguidance&a=do_buy")
    Observable<ResponseBody> requestVideoBuy(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=my&a=myclassguidance")
    Observable<ResponseBody> requestVideoMineList(@FieldMap Map<String, String> params);


}
