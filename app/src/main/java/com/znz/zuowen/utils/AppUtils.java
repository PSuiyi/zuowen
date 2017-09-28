package com.znz.zuowen.utils;

import android.content.Context;

import com.znz.compass.znzlibray.common.DataManager;
import com.znz.zuowen.bean.UserBean;
import com.znz.zuowen.common.Constants;

/**
 * Date： 2017/5/15 2017
 * User： PSuiyi
 * Description：
 */

public class AppUtils {

    private DataManager mDataManager;
    private static AppUtils instance;
    private Context context;

    public static AppUtils getInstance(Context context) {
        if (instance == null) {
            instance = new AppUtils(context.getApplicationContext());

        }
        return instance;
    }

    private AppUtils(Context context) {
        mDataManager = DataManager.getInstance(context.getApplicationContext());
    }

    /**
     * 保存用户信息
     *
     * @param bean
     */
    public void saveUserData(UserBean bean) {
        mDataManager.saveTempData(Constants.User.ID, bean.getUserId());
        mDataManager.saveTempData(Constants.User.MOBILE, bean.getMobile());
        mDataManager.saveTempData(Constants.User.NAME, bean.getNickName());
        mDataManager.saveTempData(Constants.User.HEADIMG, bean.getHeadImg());
    }
}