package com.znz.zuowen.common;


public class Constants {

    /**
     * SharePreference key
     */
    public interface User {
        String ID = "user_id";//用户id
        String MOBILE_STATE = "mobile_state";//手机绑定状态 1绑定 2未绑定
        String DATA_STATE = "data_state";//资料完善状态 1已完善 2未完善
        String MOBILE = "mobile";
        String HEADIMG = "avatar";//头像
        String NAME = "name";
        String EMAIL = "EMAIL";
        String USERNAME = "username";
        String IS_VIP = "IS_VIP";
        String PAY_TYPE = "pay_type";//支付的类型
        String ROLE_TYPE = "role_type";
        String NOTE = "note";
        String SEX = "sex";
        String ADDRESS = "address";
        String COMPANY_ID = "company_id";
        String QR_CODE = "qr_code";
        String AREA_ID = "area_id";
        String AREA_NAME = "area_name";
        String AUTH_TYPE = "auth_type";
        String key = "key";
        String Latitude = "Latitude";
        String Longitude = "Longitude";
        String NONCE = "nonce";
        String VERIFYSTATUS = "verifyStatus";//认证状态
        String BIRTHDAY = "birthday";
        String PROFESSIONAL = "professional";//职业
        String INTEREST = "interest";//兴趣爱好
        String CONSTELLATION = "constellation";//星座
        String SYNOPSIS = "synopsis";//个人简介
        String INCOME = "income";//收入
        String WEIGHT = "weight";//体重
        String HEIGHT = "height";//身高
        String FEEL = "feel";//身高
        String CARTNUM = "cartnum";//购物车数量
    }


    /**
     * 输入框输入类型
     */
    public interface EditInputType {
        int NORMAL = 0x00001;
        int PHONE = 0x00002;
        int MULTI = 0x00003;
        int NUMBER = 0x00004;
    }

    public interface BooleanValue {
    }

    public interface AppInfo {
        String CACHE_SIZE = "CACHE_SIZE";//缓存大小
        String SPLASH_IMG_URL = "SPLASH_IMG_URL";//闪屏广告地址
    }

    public interface MultiType {
        int HomeMenu = 0;
        int Section = 1;
        int HomeRecommend = 2;
        int HomeChongzhi = 3;
        int HomeAdv = 4;
        int HomeRecommendBanner = 5;
        int PayParent = 6;
        int PayChild = 7;
        int HomeModelBanner = 8;
        int FactoryParent = 9;
        int FactoryChild = 10;

    }

    public interface SearchType {
        String SEARCHTYPE = "SearchType";
    }

    public static final int BLUR_RADIUS = 25;
    public static final int SCORE_TIME_LIMINT = 5 * 60 * 1000;//评分倒计时间
    public static final int RECENT_LIVE_TIME_LIMINT = 10 * 60 * 1000;//最近直播时间
    public static final int ALIAS_SEQUENCE = 0x333;

    public static final String GENSEE_ACCOUNT = "hifi";//展示互动管理员账号
    public static final String LIVE_TIME_FORMAT = "yy/MM/dd HH:mm";//直播列表时间格式
}
