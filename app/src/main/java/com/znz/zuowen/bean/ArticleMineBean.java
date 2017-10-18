package com.znz.zuowen.bean;

import com.znz.compass.znzlibray.base.BaseZnzBean;

import java.util.List;

/**
 * Date： 2017/10/18 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleMineBean extends BaseZnzBean {

    /**
     * itemid : 8
     * title : 我的作文题目
     * teacher_name : 朱老师
     * first_upload : ["upload/wx4260e9a3e7ecc058/3213153773575651.jpg","upload/wx4260e9a3e7ecc058/3210872121434641.jpg","upload/wx4260e9a3e7ecc058/32868554847261.jpg","upload/wx4260e9a3e7ecc058/326372207520701.jpg","upload/wx4260e9a3e7ecc058/324441389481.jpg"]
     * addtime : 2017/10/17
     * first_teacher_reviews :
     * first_status : 1
     * second_upload : []
     * second_teacher_reviews :
     * second_status : 0
     */

    private String itemid;
    private String title;
    private String teacher_name;
    private String addtime;
    private String first_teacher_reviews;
    private String first_status;
    private String second_teacher_reviews;
    private String second_status;
    private List<UrlBean> first_upload;
    private List<UrlBean> second_upload;

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
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

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getFirst_teacher_reviews() {
        return first_teacher_reviews;
    }

    public void setFirst_teacher_reviews(String first_teacher_reviews) {
        this.first_teacher_reviews = first_teacher_reviews;
    }

    public String getFirst_status() {
        return first_status;
    }

    public void setFirst_status(String first_status) {
        this.first_status = first_status;
    }

    public String getSecond_teacher_reviews() {
        return second_teacher_reviews;
    }

    public void setSecond_teacher_reviews(String second_teacher_reviews) {
        this.second_teacher_reviews = second_teacher_reviews;
    }

    public String getSecond_status() {
        return second_status;
    }

    public void setSecond_status(String second_status) {
        this.second_status = second_status;
    }

    public List<UrlBean> getFirst_upload() {
        return first_upload;
    }

    public void setFirst_upload(List<UrlBean> first_upload) {
        this.first_upload = first_upload;
    }

    public List<UrlBean> getSecond_upload() {
        return second_upload;
    }

    public void setSecond_upload(List<UrlBean> second_upload) {
        this.second_upload = second_upload;
    }
}
