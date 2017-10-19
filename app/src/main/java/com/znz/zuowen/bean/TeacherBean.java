package com.znz.zuowen.bean;

import com.znz.compass.znzlibray.base.BaseZnzBean;

/**
 * Date： 2017/10/19 2017
 * User： PSuiyi
 * Description：
 */

public class TeacherBean extends BaseZnzBean {
    private String id;
    private String real_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }
}
