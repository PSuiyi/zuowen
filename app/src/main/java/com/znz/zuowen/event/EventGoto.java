package com.znz.zuowen.event;

import com.znz.compass.znzlibray.eventbus.BaseEvent;

/**
 * Date： 2017/1/18 2017
 * User： PSuiyi
 * Description：
 */

public class EventGoto<T> extends BaseEvent {
    private String value;
    private String type;
    private T bean;

    public EventGoto(int flag) {
        super(flag);
    }

    public EventGoto(int flag, String value) {
        super(flag);
        this.value = value;
    }
    public EventGoto(int flag, T bean) {
        super(flag);
        this.bean = bean;
    }

    public EventGoto(int flag, String value, String type) {
        super(flag);
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }
}
