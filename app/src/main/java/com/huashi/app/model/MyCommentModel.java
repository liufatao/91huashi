package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class MyCommentModel {
    /**
     * message : 查询成功
     * commodityComments : [{"id":143,"userId":191,"commodityId":49,"orderId":622,"content":"挺好","createTime":1457332171000,"picUrl":"4/7/8/a/478a1a681d74b24cf978518bb90c8a4c","name":"淘真里"},{"id":142,"userId":191,"commodityId":54,"orderId":621,"content":"很好","createTime":1457314794000,"picUrl":"6/a/5/4/6a5417a2b3a51c89aecd8a19401f72f8","name":"红枣测试商品"},{"id":139,"userId":191,"commodityId":82,"orderId":811,"content":"真丰富","createTime":1456733493000,"picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","name":"尊贵礼盒1200g/盒"}]
     * status : 1
     */

    private String message;
    private int status;
    /**
     * id : 143
     * userId : 191
     * commodityId : 49
     * orderId : 622
     * content : 挺好
     * createTime : 1457332171000
     * picUrl : 4/7/8/a/478a1a681d74b24cf978518bb90c8a4c
     * name : 淘真里
     */

    private List<CommodityCommentsBean> commodityComments;

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

    public List<CommodityCommentsBean> getCommodityComments() {
        return commodityComments;
    }

    public void setCommodityComments(List<CommodityCommentsBean> commodityComments) {
        this.commodityComments = commodityComments;
    }

    public static class CommodityCommentsBean {
        private int id;
        private int userId;
        private int commodityId;
        private int orderId;
        private String content;
        private long createTime;
        private String picUrl;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
