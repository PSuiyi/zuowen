package com.znz.zuowen.bean;

import com.znz.compass.znzlibray.base.BaseZnzBean;
import com.znz.zuowen.common.Constants;

/**
 * Date： 2017/10/14 2017
 * User： PSuiyi
 * Description：
 */

public class VideoBean extends BaseZnzBean {

    /**
     * id : 10
     * title : 微课视频名称10
     * teacher_name : 老师名字10
     * image : upload/images/20170314/1489472485127980.jpg
     * video_url : upload/video/20170929/1506649325169760.mp4
     * intro : 我的理想十分奇特，但我十分喜欢。我的理想是当图书馆里的图书管理员
     * addtime : 2017/10/10
     * is_buy : 0
     */

    private String id;
    private String title;
    private String teacher_name;
    private String image;
    private String video_url;
    private String intro;
    private String content;
    private String addtime;
    private String is_buy;
    private String is_collect;

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = Constants.IMG_URL + image;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(String is_buy) {
        this.is_buy = is_buy;
    }
}
