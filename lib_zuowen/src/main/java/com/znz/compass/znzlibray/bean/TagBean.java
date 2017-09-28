package com.znz.compass.znzlibray.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.znz.compass.znzlibray.base.BaseZnzBean;

/**
 * Date： 2017/5/26 2017
 * User： PSuiyi
 * Description：
 */
@DatabaseTable(tableName = "tagview")
public class TagBean extends BaseZnzBean {
    @DatabaseField
    private String id;
    @DatabaseField(id = true)
    private String title;
    @DatabaseField
    private String state;
    @DatabaseField
    private String type;

    private boolean isSelect;

    public TagBean() {
    }

    public TagBean(String title) {
        this.title = title;
    }

    public TagBean(String title, boolean isSelect) {
        this.title = title;
        this.isSelect = isSelect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
