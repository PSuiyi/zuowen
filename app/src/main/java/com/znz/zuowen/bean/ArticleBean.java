package com.znz.zuowen.bean;

import com.znz.compass.znzlibray.base.BaseZnzBean;

import java.util.List;

/**
 * Date： 2017/10/13 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleBean extends BaseZnzBean {

    /**
     * id : 4
     * author : 小小2
     * title : 我的理想3
     * type : 2
     * style_type : 议论文
     * counts : 4343
     * content :
     * imgurl : [{"url":"upload/wx4260e9a3e7ecc058/3213153773575651.jpg"},{"url":"upload/wx4260e9a3e7ecc058/3210872121434641.jpg"},{"url":"upload/wx4260e9a3e7ecc058/32868554847261.jpg"},{"url":"upload/wx4260e9a3e7ecc058/326372207520701.jpg"},{"url":"upload/wx4260e9a3e7ecc058/324441389481.jpg"},{"url":"upload/wx4260e9a3e7ecc058/321763826821671.jpg"}]
     */

    private String id;
    private String author;
    private String title;
    private String teacher_name;
    private String type;
    private String style_type;
    private String counts;
    private String content;
    private String vote_count;
    private String is_vote;
    private String is_collect;
    private String is_like;
    private String addtime;
    private String collect_count;
    private String like_count;
    private String teacher_reviews;
    private String mytype;
    private List<UrlBean> imgurl;

    public String getMytype() {
        return mytype;
    }

    public void setMytype(String mytype) {
        this.mytype = mytype;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getTeacher_reviews() {
        return teacher_reviews;
    }

    public void setTeacher_reviews(String teacher_reviews) {
        this.teacher_reviews = teacher_reviews;
    }

    public String getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(String collect_count) {
        this.collect_count = collect_count;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getIs_vote() {
        return is_vote;
    }

    public void setIs_vote(String is_vote) {
        this.is_vote = is_vote;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStyle_type() {
        return style_type;
    }

    public void setStyle_type(String style_type) {
        this.style_type = style_type;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<UrlBean> getImgurl() {
        return imgurl;
    }

    public void setImgurl(List<UrlBean> imgurl) {
        this.imgurl = imgurl;
    }
}
