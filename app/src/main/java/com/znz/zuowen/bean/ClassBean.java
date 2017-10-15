package com.znz.zuowen.bean;

import com.znz.compass.znzlibray.base.BaseZnzBean;

/**
 * Date： 2017/10/15 2017
 * User： PSuiyi
 * Description：
 */

public class ClassBean extends BaseZnzBean {

    /**
     * id : 1
     * name : 2课时
     * money : 10.00
     */

    private String id;
    private String name;
    private String money;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
