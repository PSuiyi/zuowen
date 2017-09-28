package com.znz.zuowen.bean;

import com.znz.compass.znzlibray.base.BaseZnzBean;

/**
 * Date： 2017/5/15 2017
 * User： PSuiyi
 * Description：
 */

public class UserBean extends BaseZnzBean {


    /**
     * userId : 3
     * account : jury09
     * nickName : 咸鱼
     * headImg : /public/upload/default/default.png
     * mobile : 18502110377
     * corpId : 21
     * corpName : 门店名称
     * corpType : 2
     */

    private String userId;
    private String account;
    private String nickName;
    private String headImg;
    private String mobile;
    private String corpId;
    private String corpName;
    private String corpType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpType() {
        return corpType;
    }

    public void setCorpType(String corpType) {
        this.corpType = corpType;
    }
}
