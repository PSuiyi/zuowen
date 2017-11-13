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
    private String files_name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = Constants.IMG_URL + url;
    }

    public String getFiles_name() {
        return files_name;
    }

    public void setFiles_name(String files_name) {
        this.files_name = files_name;
    }
}
