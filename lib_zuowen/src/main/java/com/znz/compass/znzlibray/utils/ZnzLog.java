package com.znz.compass.znzlibray.utils;

import android.content.pm.ApplicationInfo;

import com.socks.library.KLog;
import com.znz.compass.znzlibray.ZnzApplication;

/**
 * logcat工具类
 */
public class ZnzLog {
    /**
     * 打印log.information
     *
     * @param TAG 打印的类名
     * @param msg 打印的信息
     */
    public static void i(String TAG, String msg) {
        if (isApkDebugable())
            KLog.i(TAG, msg);
    }

    /**
     * 打印log.information
     *
     * @param msg 打印的信息
     */
    public static void i(String msg) {
        if (isApkDebugable())
            KLog.i(msg);
    }

    /**
     * 打印log.debug
     *
     * @param TAG 打印的类名
     * @param msg 打印的信息
     */
    public static void d(String TAG, String msg) {
        if (isApkDebugable())
            KLog.d(TAG, msg);
    }

    /**
     * 打印log.debug
     *
     * @param msg 打印的信息
     */
    public static void d(String msg) {
        if (isApkDebugable())
            KLog.d(msg);
    }

    /**
     * 打印log.error
     *
     * @param TAG 打印的类名
     * @param msg 打印的信息
     */
    public static void e(String TAG, String msg) {
        if (isApkDebugable())
            KLog.e(TAG, msg);
    }

    /**
     * 打印log.error
     *
     * @param msg 打印的信息
     */
    public static void e(String msg) {
        if (isApkDebugable())
            KLog.e(msg);
    }

    /**
     * 打印json
     *
     * @param TAG 打印的类名
     * @param msg 打印的信息
     */
    public static void json(String TAG, String msg) {
        if (isApkDebugable())
            KLog.e(TAG, msg);
    }

    /**
     * 打印json
     *
     * @param msg 打印的信息
     */
    public static void json(String msg) {
        if (isApkDebugable()) {
            KLog.json(msg);
        }
    }


    public static boolean isApkDebugable() {
        try {
            ApplicationInfo info = ZnzApplication.getContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }
}
