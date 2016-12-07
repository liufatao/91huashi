package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8.
 */
public class RecommendCommtity {
    /**
     * message : 查询成功
     * commoditys : [{"id":47,"nameCN":"手机6代测试商品","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":0,"modelName":"型号1","modelId":1,"aftersale":null},{"id":48,"nameCN":"红枣测试商品","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"9.00","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":0,"modelName":"型号1","modelId":2,"aftersale":null},{"id":49,"nameCN":"淘真里","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"4/7/8/a/478a1a681d74b24cf978518bb90c8a4c","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":0,"modelName":"型号1","modelId":3,"aftersale":null},{"id":50,"nameCN":"戴尔笔记本电脑","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":0,"modelName":"型号1","modelId":4,"aftersale":null},{"id":52,"nameCN":"大疆红枣测试商品","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"9.00","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":0,"modelName":"型号1","modelId":5,"aftersale":null},{"id":52,"nameCN":"大疆红枣测试商品","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":0,"modelName":"灰枣","modelId":6,"aftersale":null},{"id":53,"nameCN":"红枣测试商品","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":0,"modelName":"480克","modelId":7,"aftersale":null},{"id":54,"nameCN":"红枣测试商品","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"6/a/5/4/6a5417a2b3a51c89aecd8a19401f72f8","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":0,"modelName":"1200克","modelId":8,"aftersale":null},{"id":55,"nameCN":"大疆养生大枣测试商品","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":0,"modelName":"480克","modelId":9,"aftersale":null},{"id":56,"nameCN":"玉枣片测试商品","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":0,"modelName":"180克","modelId":10,"aftersale":null}]
     * status : 1
     */

    private String message;
    private int status;
    /**
     * id : 47
     * nameCN : 手机6代测试商品
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
     * count : 0
     * modelName : 型号1
     * modelId : 1
     * aftersale : null
     */

    private List<CommoditysBean> commoditys;

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

    public List<CommoditysBean> getCommoditys() {
        return commoditys;
    }

    public void setCommoditys(List<CommoditysBean> commoditys) {
        this.commoditys = commoditys;
    }

    public static class CommoditysBean {
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
