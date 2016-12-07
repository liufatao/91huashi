package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class CommodityModel {

    /**
     * message : 查询成功
     * commodity : {"id":144,"nameCN":"大疆养生玉枣特级装600g","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":"85.00","true_sell":"85.00","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"","picture":[{"name":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","type":"jgp","width":350,"height":350}],"phone":"13193131327","introduction":"","introductionImages":[{"name":"test/detail2","type":"jgp","width":350,"height":350},{"name":"test/detail1","type":"jgp","width":350,"height":350}],"commentCount":5,"commodityCommentModel":{"id":0,"userId":178,"content":"小涛涛吃棒棒","createTime":1456479981000,"createTimeMsg":null,"nickname":"枫落","avatar":"f/4/0/9/f4095cb089977a273543cdb68bf9c5f5","commodityId":0,"orderDetailId":0},"count":0,"aftersale":null}
     * status : 1
     */

    private String message;
    /**
     * id : 144
     * nameCN : 大疆养生玉枣特级装600g
     * status : 0
     * comment : null
     * cartType : 0
     * explainTxt : null
     * price : 85.00
     * true_sell : 85.00
     * collectCount : 0
     * collectStatus : 0
     * isBill : 0
     * pictures :
     * picture : [{"name":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","type":"jgp","width":350,"height":350}]
     * phone : 13193131327
     * introduction :
     * introductionImages : [{"name":"test/detail2","type":"jgp","width":350,"height":350},{"name":"test/detail1","type":"jgp","width":350,"height":350}]
     * commentCount : 5
     * commodityCommentModel : {"id":0,"userId":178,"content":"小涛涛吃棒棒","createTime":1456479981000,"createTimeMsg":null,"nickname":"枫落","avatar":"f/4/0/9/f4095cb089977a273543cdb68bf9c5f5","commodityId":0,"orderDetailId":0}
     * count : 0
     * aftersale : null
     */

    private CommodityBean commodity;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommodityBean getCommodity() {
        return commodity;
    }

    public void setCommodity(CommodityBean commodity) {
        this.commodity = commodity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class CommodityBean {
        private int id;
        private String nameCN;
        private int status;
        private Object comment;
        private int cartType;
        private Object explainTxt;
        private String price;
        private String true_sell;
        private int collectCount;
        private int collectStatus;
        private int isBill;
        private String pictures;
        private String phone;
        private String introduction;
        private int commentCount;
        /**
         * id : 0
         * userId : 178
         * content : 小涛涛吃棒棒
         * createTime : 1456479981000
         * createTimeMsg : null
         * nickname : 枫落
         * avatar : f/4/0/9/f4095cb089977a273543cdb68bf9c5f5
         * commodityId : 0
         * orderDetailId : 0
         */

        private CommodityCommentModelBean commodityCommentModel;
        private int count;
        private Object aftersale;
        /**
         * name : 7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b
         * type : jgp
         * width : 350
         * height : 350
         */

        private List<PictureBean> picture;
        /**
         * name : test/detail2
         * type : jgp
         * width : 350
         * height : 350
         */

        private List<IntroductionImagesBean> introductionImages;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNameCN() {
            return nameCN;
        }

        public void setNameCN(String nameCN) {
            this.nameCN = nameCN;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getComment() {
            return comment;
        }

        public void setComment(Object comment) {
            this.comment = comment;
        }

        public int getCartType() {
            return cartType;
        }

        public void setCartType(int cartType) {
            this.cartType = cartType;
        }

        public Object getExplainTxt() {
            return explainTxt;
        }

        public void setExplainTxt(Object explainTxt) {
            this.explainTxt = explainTxt;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTrue_sell() {
            return true_sell;
        }

        public void setTrue_sell(String true_sell) {
            this.true_sell = true_sell;
        }

        public int getCollectCount() {
            return collectCount;
        }

        public void setCollectCount(int collectCount) {
            this.collectCount = collectCount;
        }

        public int getCollectStatus() {
            return collectStatus;
        }

        public void setCollectStatus(int collectStatus) {
            this.collectStatus = collectStatus;
        }

        public int getIsBill() {
            return isBill;
        }

        public void setIsBill(int isBill) {
            this.isBill = isBill;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public CommodityCommentModelBean getCommodityCommentModel() {
            return commodityCommentModel;
        }

        public void setCommodityCommentModel(CommodityCommentModelBean commodityCommentModel) {
            this.commodityCommentModel = commodityCommentModel;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Object getAftersale() {
            return aftersale;
        }

        public void setAftersale(Object aftersale) {
            this.aftersale = aftersale;
        }

        public List<PictureBean> getPicture() {
            return picture;
        }

        public void setPicture(List<PictureBean> picture) {
            this.picture = picture;
        }

        public List<IntroductionImagesBean> getIntroductionImages() {
            return introductionImages;
        }

        public void setIntroductionImages(List<IntroductionImagesBean> introductionImages) {
            this.introductionImages = introductionImages;
        }

        public static class CommodityCommentModelBean {
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

        public static class PictureBean {
            private String name;
            private String type;
            private int width;
            private int height;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }

        public static class IntroductionImagesBean {
            private String name;
            private String type;
            private int width;
            private int height;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}
