package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29.
 */
public class IndustryModel {
    /**
     * message : 查询成功
     * status : 1
     * industrys : [{"id":201,"name":"花花世界"},{"id":202,"name":"宠物用品"},{"id":203,"name":"水族渔具"},{"id":204,"name":"家具园艺"},{"id":205,"name":"特产土艺"},{"id":206,"name":"园林机械"},{"id":207,"name":"花种草种"},{"id":208,"name":"绿化苗木"},{"id":209,"name":"农药肥料"},{"id":210,"name":"园艺资材"}]
     */

    private String message;
    private int status;

    public boolean isNameIsSelect() {
        return nameIsSelect;
    }

    public void setNameIsSelect(boolean nameIsSelect) {
        this.nameIsSelect = nameIsSelect;
    }

    /**
     * id : 201
     * name : 花花世界
     */
    private boolean nameIsSelect;// 商品属性是否选中
    private List<IndustrysBean> industrys;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<IndustrysBean> getIndustrys() {
        return industrys;
    }

    public void setIndustrys(List<IndustrysBean> industrys) {
        this.industrys = industrys;
    }

    public static class IndustrysBean {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
