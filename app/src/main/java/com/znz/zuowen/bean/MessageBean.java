package com.znz.zuowen.bean;

import com.znz.compass.znzlibray.base.BaseZnzBean;

/**
 * Date： 2017/11/9 2017
 * User： PSuiyi
 * Description：
 */

public class MessageBean extends BaseZnzBean {

    /**
     * info_id : 1
     * title : 标题信息小题1标题信息小题1标题信息小题1标题信息小题1标题信息小题1
     * add_time : 2017/10/12
     * detail : <p>					</p><p>再次测试</p><p>再次测试</p><p>再次测试</p><p>再次测试</p><p>1、支付条款测试，测试第一条。</p><p>2、测试第二条</p><p>3、测试第三条</p><p>VIP等级说明：</p><p>VIP0提交作文，VIP1及以上可以提交作文并选择老师</p><p> 					</p><p>  再次测试</p><p><br/></p><p> 					</p><p> 					</p>
     */

    private String info_id;
    private String title;
    private String add_time;
    private String detail;

    public String getInfo_id() {
        return info_id;
    }

    public void setInfo_id(String info_id) {
        this.info_id = info_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
