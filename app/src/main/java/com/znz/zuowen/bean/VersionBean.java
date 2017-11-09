package com.znz.zuowen.bean;

import com.znz.compass.znzlibray.base.BaseZnzBean;

/**
 * Date： 2017/11/9 2017
 * User： PSuiyi
 * Description：
 */

public class VersionBean extends BaseZnzBean {
    private String id;
    private String version;
    private String code;
    private String url;
    private String version_desc;
    private String is_update;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion_desc() {
        return version_desc;
    }

    public void setVersion_desc(String version_desc) {
        this.version_desc = version_desc;
    }

    public String getIs_update() {
        return is_update;
    }

    public void setIs_update(String is_update) {
        this.is_update = is_update;
    }
}
