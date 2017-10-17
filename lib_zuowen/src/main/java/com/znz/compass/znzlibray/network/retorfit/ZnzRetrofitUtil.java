package com.znz.compass.znzlibray.network.retorfit;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.znz.compass.znzlibray.ZnzApplication;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.common.ZnzConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by panqiming on 2016/11/8.
 */

public class ZnzRetrofitUtil {
    private static Retrofit retrofit;
    private static DataManager mDataManager;
    private static ZnzRetrofitUtil INSTANCE;
    private static boolean WITH_COMMON_PARAMS;

    //    public static final String BASE_URL = "http://114.215.134.205/";
//    public static final String BASE_URL = "http://tyapi.znzkj.net/";//外网测试环境接口地址
    public static String BASE_URL;

    //获取单例
    public static ZnzRetrofitUtil getInstance() {
        return getInstance(true);
    }

    public static ZnzRetrofitUtil getInstance(boolean withCommonParams) {
        if (INSTANCE == null || withCommonParams != WITH_COMMON_PARAMS) {
            INSTANCE = new ZnzRetrofitUtil(withCommonParams);
        }
        return INSTANCE;
    }

    private ZnzRetrofitUtil(boolean withCommonParams) {
        if (retrofit == null || withCommonParams != WITH_COMMON_PARAMS) {
            WITH_COMMON_PARAMS = withCommonParams;

            mDataManager = DataManager.getInstance(ZnzApplication.getContext());
            BASE_URL = mDataManager.readTempData(ZnzConstants.SERVICE_IP);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            // Log信息拦截器
            builder.addInterceptor(new LoggingInterceptor());

//            File cacheFile = new File(ZnzConstants.FILE_DIR);
//            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
//            Interceptor cacheInterceptor = chain -> {
//                Request request = chain.request();
//                if (!NetUtil.isNetworkAvailable(ZnzApplication.getContext())) {
//                    request = request.newBuilder()
//                            .cacheControl(CacheControl.FORCE_CACHE)
//                            .build();
//                }
//                Response response = chain.proceed(request);
//                if (NetUtil.isNetworkAvailable(ZnzApplication.getContext())) {
//                    int maxAge = 0;
//                    // 有网络时 设置缓存超时时间0个小时
//                    response.newBuilder()
//                            .header("Cache-Control", "public, max-age=" + maxAge)
//                            .removeHeader("ZnzLog")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                            .build();
//                } else {
//                    // 无网络时，设置超时为4周
//                    int maxStale = 60 * 60 * 24 * 28;
//                    response.newBuilder()
//                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                            .removeHeader("nyn")
//                            .build();
//                }
//                return response;
//            };
//            builder.cache(cache).addInterceptor(cacheInterceptor);


            //公共参数
            Interceptor addQueryParameterInterceptor = chain -> {
                Request originalRequest = chain.request();
                Request request = null;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        .addQueryParameter("token", mDataManager.getAccessToken())
                        .build();

                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            };
//            builder.addInterceptor(addQueryParameterInterceptor);

            //设置头
            Interceptor headerInterceptor = chain -> {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                        .header("Accept", "application/json")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            };
//            builder.addInterceptor(headerInterceptor);

            //设置超时
            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);

            ClearableCookieJar cookieJar =
                    new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(ZnzApplication.getContext()));
            OkHttpClient okHttpClient = builder
                    .cookieJar(cookieJar)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }

    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }
}
