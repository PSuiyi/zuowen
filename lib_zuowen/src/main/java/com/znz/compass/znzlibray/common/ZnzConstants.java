package com.znz.compass.znzlibray.common;

import android.os.Environment;

/**
 * 公共常量
 */
public class ZnzConstants {
    /**
     * ASE加密key
     */
    public static final String AES_KEY = "UITN25LMUQC436IM";
    /**
     * 应用文件存放目录
     */
    public static final String APP_DIR_NAME = "ruixi";
    // 图片目录
    public static final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/"
            + APP_DIR_NAME + "/image/";
    // 日志目录
    public static final String LOG_DIR = Environment.getExternalStorageDirectory() + "/"
            + APP_DIR_NAME + "/log/";
    // 文件目录
    public static final String FILE_DIR = Environment.getExternalStorageDirectory() + "/"
            + APP_DIR_NAME + "/file/";

    /**
     * 个人参数
     */
    public static final String IS_APP_OPEND = "is_app_opend"; // 用户是否是第一次打开app
    public static final String IS_LOGIN = "is_login"; // 是否登录
    public static final String ACCESS_TOKEN = "token_znz"; // 登录用户accessToken
    public static final String DEVICE_TOKEN = "device_token";
    public static final String ACCOUNT = "account"; // 用户名/手机号
    public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";

    /**
     * 公共参数
     */
    public static final String SERVICE_IP = "SERVICE_IP"; // SERVICE_IP
    public static final int LODING_TIME = 400; //loding滞留时间


    public static final boolean APP_DEBUG = true; //是否是测试环境

}
