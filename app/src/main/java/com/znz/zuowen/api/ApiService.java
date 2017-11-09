package com.znz.zuowen.api;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    @POST("index.php")
    Observable<ResponseBody> post(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=checkversion&a=check_version")
    Observable<ResponseBody> getVersion(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=help&a=aboutus")
    Observable<ResponseBody> requestAboutUs(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=help&a=index")
    Observable<ResponseBody> requestHelp(@FieldMap Map<String, String> params);

    @Multipart
    @POST("?m=rest&c=common&a=upload")
    Observable<ResponseBody> requestUploadImage(@QueryMap Map<String, String> params, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=send_sms")
    Observable<ResponseBody> requestCode(@FieldMap Map<String, String> params);

    @GET("?m=rest&c=login&a=getimgcode")
    Observable<ResponseBody> requestCodeImg(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=resgister")
    Observable<ResponseBody> reuqestRegister(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=forgetpw_one")
    Observable<ResponseBody> reuqestPsdOne(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=forgetpw_two")
    Observable<ResponseBody> reuqestPsdTwo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=my&a=feedback")
    Observable<ResponseBody> reuqestFeedback(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=perfect_information")
    Observable<ResponseBody> reuqestCompleteInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=login")
    Observable<ResponseBody> requestLogin(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=my&a=view_class_hour")
    Observable<ResponseBody> requestClass(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=modify_pass")
    Observable<ResponseBody> requestUpdatePsd(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=my&a=index")
    Observable<ResponseBody> requestMineInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=my&a=modify_photo")
    Observable<ResponseBody> requestUpdateHeader(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=modify_username")
    Observable<ResponseBody> requestUpdateName(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=modify_phone")
    Observable<ResponseBody> requestUpdatePhone(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=login&a=logout")
    Observable<ResponseBody> requestLogout(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=my&a=mycollect")
    Observable<ResponseBody> requestFavList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=weekcomposition&a=index")
    Observable<ResponseBody> requestWeekList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=weekcomposition&a=myweeklist")
    Observable<ResponseBody> requestWeekMineList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=weekcomposition&a=detail")
    Observable<ResponseBody> requestWeekDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=weekcomposition&a=myweekdetail")
    Observable<ResponseBody> requestWeekMineDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=weekcomposition&a=do_buy")
    Observable<ResponseBody> requestWeekBuy(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=weekcomposition&a=first_upload")
    Observable<ResponseBody> requestArticleSubmitOne(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=weekcomposition&a=second_upload")
    Observable<ResponseBody> requestArticleSubmitTwo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=weekcomposition&a=select_teacher")
    Observable<ResponseBody> requestTeacherList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=information&a=index")
    Observable<ResponseBody> requestMessageList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=information&a=detail")
    Observable<ResponseBody> requestMessageDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=votecomposition&a=index")
    Observable<ResponseBody> requestVoteList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=votecomposition&a=getnums")
    Observable<ResponseBody> requestStageList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=famousteacher&a=index")
    Observable<ResponseBody> requestTeacherFamousList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=famousteacher&a=detail")
    Observable<ResponseBody> requestTeacherFamousDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=votecomposition&a=detail")
    Observable<ResponseBody> requestVoteDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=votecomposition&a=do_collect")
    Observable<ResponseBody> requestVoteFav(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=votecomposition&a=do_vote")
    Observable<ResponseBody> requestVoteVote(@FieldMap Map<String, String> params);

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
    @POST("?m=rest&c=classguidance&a=do_collect")
    Observable<ResponseBody> requestVideoFav(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=classguidance&a=do_buy")
    Observable<ResponseBody> requestVideoBuy(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=my&a=myclassguidance")
    Observable<ResponseBody> requestVideoMineList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=pay&a=alipay")
    Observable<ResponseBody> requestBuyAli(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=pay&a=wxpay")
    Observable<ResponseBody> requestBuyWechat(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("?m=rest&c=pay&a=getorderstatus")
    Observable<ResponseBody> requestBuyStatus(@FieldMap Map<String, String> params);
}
