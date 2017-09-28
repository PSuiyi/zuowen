package com.znz.zuowen.event;

/**
 * Created by xxc on 2017/2/14.
 */

public interface EventTags {
    int REFRESH_ADD_ADDRESS = 0x100;//新增收货地址通知地址列表刷新
    int REFRESH_CART_NUM = 0x101;
    int REFRESH_ADD_CART = 0x102;//加入购物车通知购物车列表刷新
    int REFRESH_MINE_INFO = 0x103;//个人信息刷新
    int REFRESH_GOODS_NUM = 0x104;//输入商品数量通知刷新

    int GOTO_CART = 0x200;
    int GOTO_INFO = 0x201;//修改个人资料通知个人资料修改
    int GOTO_HOME = 0x203;
    int GOTO_ADDRESS_SELECT = 0x204;//选择收货地址
    int GOTO_NUM_EDIT = 0x205;//购物车输入物品数量
}
