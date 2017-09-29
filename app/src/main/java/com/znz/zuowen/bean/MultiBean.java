package com.znz.zuowen.bean;

import com.znz.compass.znzlibray.base.BaseZnzBean;
import com.znz.compass.znzlibray.views.recyclerview.entity.MultiItemEntity;

/**
 * Date： 2017/9/4 2017
 * User： PSuiyi
 * Description：
 */

public class MultiBean extends BaseZnzBean implements MultiItemEntity {
    private int itemType;
    private String section;

    public MultiBean() {
    }

    public MultiBean(int itemType) {
        this.itemType = itemType;
    }

    public MultiBean(int itemType, String section) {
        this.itemType = itemType;
        this.section = section;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
