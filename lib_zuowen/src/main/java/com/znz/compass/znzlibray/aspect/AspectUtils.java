package com.znz.compass.znzlibray.aspect;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;

/**
 * 反射获取context
 */

public class AspectUtils {

    /**
     * 获取全局的context，也就是Application Context
     *
     * @return 返回全局的Context，只适用于android 4.0以后
     */
    @TargetApi(14)
    public static Context getContext() {
        try {
            Application application = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null);
            return application.getApplicationContext();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
