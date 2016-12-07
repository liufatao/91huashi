package com.huashi.app.model;

/**
 * Created by Administrator on 2016/6/24.
 */
public class CommentModel {
    private int img;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getCommoditytitle() {
        return commoditytitle;
    }

    public void setCommoditytitle(String commoditytitle) {
        this.commoditytitle = commoditytitle;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String commoditytitle;
    private String comment;
    private String date;
}
