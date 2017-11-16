package com.znz.zuowen.bean;

import com.znz.compass.znzlibray.base.BaseZnzBean;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.common.Constants;

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
    private String cate_type;
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
    private String is_my_week;
    private String first_status;
    private String is_model;
    private String is_star;
    private String vote_num_id;
    private String example_show;
    private String example_comments;
    private String video_url;
    private String video_image;
    private List<TeacherBean> assign_teacher_id_info;
    private List<UrlBean> imgurl;

    public String getCate_type() {
        return cate_type;
    }

    public void setCate_type(String cate_type) {
        this.cate_type = cate_type;
    }

    public String getVideo_image() {
        return video_image;
    }

    public void setVideo_image(String video_image) {
        this.video_image = Constants.IMG_URL + video_image;
    }

    public String getVote_num_id() {
        return vote_num_id;
    }

    public void setVote_num_id(String vote_num_id) {
        this.vote_num_id = vote_num_id;
    }

    public String getIs_star() {
        return is_star;
    }

    public void setIs_star(String is_star) {
        this.is_star = is_star;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        if (!StringUtil.isBlank(video_url)) {
            this.video_url = Constants.IMG_URL + video_url;
        } else {
            this.video_url = video_url;
        }
    }

    public String getExample_show() {
        return example_show;
    }

    public void setExample_show(String example_show) {
        this.example_show = example_show;
    }

    public String getExample_comments() {
        return example_comments;
    }

    public void setExample_comments(String example_comments) {
        this.example_comments = example_comments;
    }

    public String getIs_model() {
        return is_model;
    }

    public void setIs_model(String is_model) {
        this.is_model = is_model;
    }

    public List<TeacherBean> getAssign_teacher_id_info() {
        return assign_teacher_id_info;
    }

    public void setAssign_teacher_id_info(List<TeacherBean> assign_teacher_id_info) {
        this.assign_teacher_id_info = assign_teacher_id_info;
    }

    public String getFirst_status() {
        return first_status;
    }

    public void setFirst_status(String first_status) {
        this.first_status = first_status;
    }

    public String getIs_my_week() {
        return is_my_week;
    }

    public void setIs_my_week(String is_my_week) {
        this.is_my_week = is_my_week;
    }

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


    @Override
    public boolean equals(Object obj) {
        // 如果为同一对象的不同引用,则相同
        if (this == obj) {
            return true;
        }
        // 如果传入的对象为空,则返回false
        if (obj == null) {
            return false;
        }

        // 如果两者属于不同的类型,不能相等
        if (getClass() != obj.getClass()) {
            return false;
        }

        // 类型相同, 比较内容是否相同
        return ((ArticleBean) obj).getId().equals(this.getId());
    }
}
