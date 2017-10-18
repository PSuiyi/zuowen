package com.znz.zuowen.event;

public interface EventTags {
    int REFRESH_MINE_INFO = 0x100;//刷新个人信息

    int GOTO_CART = 0x200;
    int GOTO_INFO = 0x201;//修改个人资料通知个人资料修改
    int GOTO_HOME = 0x203;
    int GOTO_ADDRESS_SELECT = 0x204;//选择收货地址
    int GOTO_NUM_EDIT = 0x205;//购物车输入物品数量

    int LIST_ARTICLE_FAV = 0x301;
    int LIST_ARTICLE_LIKE = 0x302;
    int LIST_ARTICLE_FAV_DELETE = 0x303;

    int PAY_WX_SUCCESS = 0x400;
    int PAY_WX_FAIL = 0x401;
    int PAY_WX_CANCEL = 0x402;
}
