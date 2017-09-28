package com.znz.compass.znzlibray.bean;


import com.znz.compass.znzlibray.base.BaseZnzBean;

/**
 * Created by Administrator on 2016/4/28.
 */
public class ShareBean extends BaseZnzBean {

    private String id;
    private String title;
    private String digest;
    private String content;
    private String url;
    private String imageUrl;
    private String objectType;
    private String objectId;
    private String thumb;
    private int thumbResId;

    public int getThumbResId() {
        return thumbResId;
    }

    public void setThumbResId(int thumbResId) {
        this.thumbResId = thumbResId;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
