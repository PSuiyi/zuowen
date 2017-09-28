package com.znz.compass.znzlibray.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Package_Name： com.znz.compass.znzlibray.utils
 * Project_Name： jibu_doctor
 * Date： 2016/12/21 2016
 * User： PSuiyi
 * Description：
 */

public class EventManager {
    public static void register(Object object) {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }

    public static void unregister(Object object) {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }
}
