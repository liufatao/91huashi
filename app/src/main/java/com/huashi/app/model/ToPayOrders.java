package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class ToPayOrders {
    /**
     * message : 查询成功
     * status : 1
     * orderModels : [{"orderId":1024,"orderCode":"22","count":0,"totalCount":null,"userId":191,"orderType":0,"costType":0,"createTime":null,"payTime":null,"status":0,"logisticsCost":0,"deliveryTime":null,"deliveryUser":null,"takeAddressId":0,"receivingTime":null,"bullNote":null,"isBill":0,"cancelReason":null,"commodityModels":[{"id":53,"nameCN":"红枣测试商品53","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","trueSellDouble":0,"collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":1,"modelName":"480克","modelId":7,"aftersale":null},{"id":93,"nameCN":"12393","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.10","trueSellDouble":0,"collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":1,"modelName":"1","modelId":73,"aftersale":null}]},{"orderId":1023,"orderCode":"22","count":0,"totalCount":null,"userId":191,"orderType":0,"costType":0,"createTime":null,"payTime":null,"status":0,"logisticsCost":0,"deliveryTime":null,"deliveryUser":null,"takeAddressId":0,"receivingTime":null,"bullNote":null,"isBill":0,"cancelReason":null,"commodityModels":[{"id":53,"nameCN":"红枣测试商品53","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","trueSellDouble":0,"collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":1,"modelName":"480克","modelId":7,"aftersale":null},{"id":93,"nameCN":"12393","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.10","trueSellDouble":0,"collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":1,"modelName":"1","modelId":73,"aftersale":null}]}]
     */

    private String message;
    private int status;
    /**
     * orderId : 1024
     * orderCode : 22
     * count : 0
     * totalCount : null
     * userId : 191
     * orderType : 0
     * costType : 0
     * createTime : null
     * payTime : null
     * status : 0
     * logisticsCost : 0.0
     * deliveryTime : null
     * deliveryUser : null
     * takeAddressId : 0
     * receivingTime : null
     * bullNote : null
     * isBill : 0
     * cancelReason : null
     * commodityModels : [{"id":53,"nameCN":"红枣测试商品53","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","trueSellDouble":0,"collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":1,"modelName":"480克","modelId":7,"aftersale":null},{"id":93,"nameCN":"12393","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.10","trueSellDouble":0,"collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":1,"modelName":"1","modelId":73,"aftersale":null}]
     */

    private List<OrderModelsBean> orderModels;

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

    public List<OrderModelsBean> getOrderModels() {
        return orderModels;
    }

    public void setOrderModels(List<OrderModelsBean> orderModels) {
        this.orderModels = orderModels;
    }

    public static class OrderModelsBean {
        private int orderId;
        private String orderCode;
        private int count;
        private Object totalCount;
        private int userId;
        private int orderType;
        private int costType;
        private Object createTime;
        private Object payTime;
        private int status;
        private double logisticsCost;
        private Object deliveryTime;
        private Object deliveryUser;
        private int takeAddressId;
        private Object receivingTime;
        private Object bullNote;
        private int isBill;
        private Object cancelReason;
        /**
         * id : 53
         * nameCN : 红枣测试商品53
         * status : 0
         * comment : null
         * cartType : 0
         * explainTxt : null
         * price : null
         * true_sell : 0.01
         * trueSellDouble : 0.0
         * collectCount : 0
         * collectStatus : 0
         * isBill : 0
         * pictures : 7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b
         * picture : null
         * phone : 13193131327
         * introduction : null
         * introductionImages : null
         * commentCount : 0
         * commodityCommentModel : null
         * count : 1
         * modelName : 480克
         * modelId : 7
         * aftersale : null
         */

        private List<CommodityModelsBean> commodityModels;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Object getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Object totalCount) {
            this.totalCount = totalCount;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getCostType() {
            return costType;
        }

        public void setCostType(int costType) {
            this.costType = costType;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getPayTime() {
            return payTime;
        }

        public void setPayTime(Object payTime) {
            this.payTime = payTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getLogisticsCost() {
            return logisticsCost;
        }

        public void setLogisticsCost(double logisticsCost) {
            this.logisticsCost = logisticsCost;
        }

        public Object getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(Object deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public Object getDeliveryUser() {
            return deliveryUser;
        }

        public void setDeliveryUser(Object deliveryUser) {
            this.deliveryUser = deliveryUser;
        }

        public int getTakeAddressId() {
            return takeAddressId;
        }

        public void setTakeAddressId(int takeAddressId) {
            this.takeAddressId = takeAddressId;
        }

        public Object getReceivingTime() {
            return receivingTime;
        }

        public void setReceivingTime(Object receivingTime) {
            this.receivingTime = receivingTime;
        }

        public Object getBullNote() {
            return bullNote;
        }

        public void setBullNote(Object bullNote) {
            this.bullNote = bullNote;
        }

        public int getIsBill() {
            return isBill;
        }

        public void setIsBill(int isBill) {
            this.isBill = isBill;
        }

        public Object getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(Object cancelReason) {
            this.cancelReason = cancelReason;
        }

        public List<CommodityModelsBean> getCommodityModels() {
            return commodityModels;
        }

        public void setCommodityModels(List<CommodityModelsBean> commodityModels) {
            this.commodityModels = commodityModels;
        }

        public static class CommodityModelsBean {
            private int id;
            private String nameCN;
            private int status;
            private Object comment;
            private int cartType;
            private Object explainTxt;
            private Object price;
            private String true_sell;
            private double trueSellDouble;
            private int collectCount;
            private int collectStatus;
            private int isBill;
            private String pictures;
            private Object picture;
            private String phone;
            private Object introduction;
            private Object introductionImages;
            private int commentCount;
            private Object commodityCommentModel;
            private int count;
            private String modelName;
            private int modelId;
            private Object aftersale;

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

            public Object getPrice() {
                return price;
            }

            public void setPrice(Object price) {
                this.price = price;
            }

            public String getTrue_sell() {
                return true_sell;
            }

            public void setTrue_sell(String true_sell) {
                this.true_sell = true_sell;
            }

            public double getTrueSellDouble() {
                return trueSellDouble;
            }

            public void setTrueSellDouble(double trueSellDouble) {
                this.trueSellDouble = trueSellDouble;
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

            public Object getPicture() {
                return picture;
            }

            public void setPicture(Object picture) {
                this.picture = picture;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getIntroduction() {
                return introduction;
            }

            public void setIntroduction(Object introduction) {
                this.introduction = introduction;
            }

            public Object getIntroductionImages() {
                return introductionImages;
            }

            public void setIntroductionImages(Object introductionImages) {
                this.introductionImages = introductionImages;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }

            public Object getCommodityCommentModel() {
                return commodityCommentModel;
            }

            public void setCommodityCommentModel(Object commodityCommentModel) {
                this.commodityCommentModel = commodityCommentModel;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getModelName() {
                return modelName;
            }

            public void setModelName(String modelName) {
                this.modelName = modelName;
            }

            public int getModelId() {
                return modelId;
            }

            public void setModelId(int modelId) {
                this.modelId = modelId;
            }

            public Object getAftersale() {
                return aftersale;
            }

            public void setAftersale(Object aftersale) {
                this.aftersale = aftersale;
            }
        }
    }
}
