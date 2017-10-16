package com.znz.zuowen.bean;

import com.znz.compass.znzlibray.base.BaseZnzBean;

/**
 * Date： 2017/10/16 2017
 * User： PSuiyi
 * Description：
 */

public class OptionBean extends BaseZnzBean {
    private String style_type;
    private String counts_id;
    private boolean isChecked;

    public String getStyle_type() {
        return style_type;
    }

    public void setStyle_type(String style_type) {
        this.style_type = style_type;
    }

    public String getCounts_id() {
        return counts_id;
    }

    public void setCounts_id(String counts_id) {
        this.counts_id = counts_id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
