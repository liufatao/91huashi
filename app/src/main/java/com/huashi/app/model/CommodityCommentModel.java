package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CommodityCommentModel {
    /**
     * message : 获取数据成功
     * status : 1
     * comments : [{"id":129,"userId":178,"content":"小涛涛吃棒棒","createTime":1456479981000,"createTimeMsg":null,"nickname":"枫落","avatar":"f/4/0/9/f4095cb089977a273543cdb68bf9c5f5","commodityId":144,"orderDetailId":0},{"id":133,"userId":144,"content":"Ss","createTime":1456726769000,"createTimeMsg":null,"nickname":"真先生","avatar":"1/e/0/c/1e0c1df55764743da6d8af06dcfda59c","commodityId":144,"orderDetailId":0},{"id":134,"userId":144,"content":"对方很纠结于付出\n","createTime":1456733418000,"createTimeMsg":null,"nickname":"真先生","avatar":"1/e/0/c/1e0c1df55764743da6d8af06dcfda59c","commodityId":144,"orderDetailId":0},{"id":135,"userId":144,"content":"对方很纠结于付出\n","createTime":1456733418000,"createTimeMsg":null,"nickname":"真先生","avatar":"1/e/0/c/1e0c1df55764743da6d8af06dcfda59c","commodityId":144,"orderDetailId":0},{"id":136,"userId":144,"content":"真丰富","createTime":1456733473000,"createTimeMsg":null,"nickname":"真先生","avatar":"1/e/0/c/1e0c1df55764743da6d8af06dcfda59c","commodityId":144,"orderDetailId":0}]
     */

    private String message;
    private int status;
    /**
     * id : 129
     * userId : 178
     * content : 小涛涛吃棒棒
     * createTime : 1456479981000
     * createTimeMsg : null
     * nickname : 枫落
     * avatar : f/4/0/9/f4095cb089977a273543cdb68bf9c5f5
     * commodityId : 144
     * orderDetailId : 0
     */

    private List<CommentsBean> comments;

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

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        private int id;
        private int userId;
        private String content;
        private long createTime;
        private Object createTimeMsg;
        private String nickname;
        private String avatar;
        private int commodityId;
        private int orderDetailId;

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

        public Object getCreateTimeMsg() {
            return createTimeMsg;
        }

        public void setCreateTimeMsg(Object createTimeMsg) {
            this.createTimeMsg = createTimeMsg;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public int getOrderDetailId() {
            return orderDetailId;
        }

        public void setOrderDetailId(int orderDetailId) {
            this.orderDetailId = orderDetailId;
        }
    }
}
