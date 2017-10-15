package com.znz.zuowen.bean;

import com.znz.compass.znzlibray.base.BaseZnzBean;
import com.znz.zuowen.common.Constants;

/**
 * Date： 2017/10/13 2017
 * User： PSuiyi
 * Description：
 */

public class UrlBean extends BaseZnzBean {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = Constants.IMG_URL + url;
    }
}
