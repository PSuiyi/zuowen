package com.znz.compass.znzlibray.eventbus;

/**
 * Created by Xugang on 2016/4/22.
 */
public class BaseEvent {
    private int flag;

    public BaseEvent(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
