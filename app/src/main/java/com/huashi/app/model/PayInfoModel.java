package com.huashi.app.model;

/**
 * Created by Administrator on 2016/9/7.
 */
public class PayInfoModel {
    /**
     * message : 查询成功
     * commodity : {"id":53,"nameCN":"红枣测试商品","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":1,"modelName":"480克","modelId":7,"aftersale":null}
     * status : 1
     */

    private String message;
    private String privateKey;
    /**
     * id : 53
     * nameCN : 红枣测试商品
     * status : 0
     * comment : null
     * cartType : 0
     * explainTxt : null
     * price : null
     * true_sell : 0.01
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

    private CommodityBean commodity;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
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
        private Object price;
        private String true_sell;
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
