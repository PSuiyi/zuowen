package com.znz.zuowen;

import com.bugtags.library.Bugtags;
import com.znz.compass.znzlibray.ZnzApplication;

/**
 * Date： 2017/9/26 2017
 * User： PSuiyi
 * Description：
 */

public class AppApplication extends ZnzApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化bugtags
        Bugtags.start("fb69161bb918e949874d67569ded4f67", this, Bugtags.BTGInvocationEventBubble);
    }
}
