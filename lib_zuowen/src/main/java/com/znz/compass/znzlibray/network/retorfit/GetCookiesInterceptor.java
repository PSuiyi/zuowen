package com.znz.compass.znzlibray.network.retorfit;

import android.content.Context;

import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.common.ZnzConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import rx.Observable;

public class GetCookiesInterceptor implements Interceptor {
    private Context context;

    public GetCookiesInterceptor(Context context) {
        super();
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.from(originalResponse.headers("Set-Cookie"))
                    .map(s -> {
                        String[] cookieArray = s.split(";");
                        return cookieArray[0];
                    })
                    .subscribe(cookie -> cookieBuffer.append(cookie).append(";"));
            DataManager.getInstance(context).saveTempData(ZnzConstants.COOKIE, cookieBuffer.toString());
        }
        return originalResponse;
    }
}