package com.znz.zuowen.event;

import com.znz.compass.znzlibray.eventbus.BaseEvent;

/**
 * Date： 2017/1/18 2017
 * User： PSuiyi
 * Description：
 */

public class EventList<T> extends BaseEvent {
    private String value;
    private T bean;

    public EventList(int flag) {
        super(flag);
    }


    public EventList(int flag, T bean) {
        super(flag);
        this.bean = bean;
    }

    public EventList(int flag, String value) {
        super(flag);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }
}
