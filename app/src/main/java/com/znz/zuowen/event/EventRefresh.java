package com.znz.zuowen.event;

import com.znz.compass.znzlibray.eventbus.BaseEvent;

/**
 * Date： 2017/1/18 2017
 * User： PSuiyi
 * Description：
 */

public class EventRefresh<T> extends BaseEvent {
    private String value;
    private String type;
    private String id;
    private T bean;

    public EventRefresh(int flag) {
        super(flag);
    }


    public EventRefresh(int flag, T bean) {
        super(flag);
        this.bean = bean;
    }

    public EventRefresh(int flag, T bean, String value) {
        super(flag);
        this.bean = bean;
        this.value = value;
    }

    public EventRefresh(int flag, String value) {
        super(flag);
        this.value = value;
    }

    public EventRefresh(int flag, String value, String type) {
        super(flag);
        this.value = value;
        this.type = type;
    }

    public EventRefresh(int flag, String value, String type, String id) {
        super(flag);
        this.value = value;
        this.type = type;
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
