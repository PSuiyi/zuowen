package com.znz.zuowen.event;

public interface EventTags {
    int REFRESH_MINE_INFO = 0x100;//刷新个人信息
    int REFRESH_MINE_FAV = 0x101;
    int REFRESH_MINE_ARTICLE_DETAIL = 0x103;

    int GOTO_FILE_UPLOAD = 0x200;

    int LIST_ARTICLE_FAV = 0x301;
    int LIST_ARTICLE_LIKE = 0x302;
    int LIST_ARTICLE_VOTE = 0x303;

    int PAY_WX_SUCCESS = 0x400;
    int PAY_WX_FAIL = 0x401;
    int PAY_WX_CANCEL = 0x402;
}
