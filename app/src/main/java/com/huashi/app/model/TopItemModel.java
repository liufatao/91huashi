package com.huashi.app.model;

/**
 * Created by Administrator on 2016/5/4.
 * 搜索页热门搜索模型类
 */
public class TopItemModel {
    public int getH_id() {
        return h_id;
    }

    public void setH_id(int h_id) {
        this.h_id = h_id;
    }

    public int h_id;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;
}
