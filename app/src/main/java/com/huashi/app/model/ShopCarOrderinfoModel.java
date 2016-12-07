package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class ShopCarOrderinfoModel {

    /**
     * message : 查询成功
     * userAddress : {"id":32,"userId":191,"name":"小米","phone":"15620607243","province":"重庆市","city":"重庆市","district":"江北区","street":"北城天街海怡花园9-2","status":100,"updateStatus":0}
     * commoditys : [{"id":54,"nameCN":"红枣测试商品","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"6/a/5/4/6a5417a2b3a51c89aecd8a19401f72f8","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":2,"modelName":"1200克","modelId":8,"aftersale":null},{"id":53,"nameCN":"红枣测试商品","status":0,"comment":null,"cartType":0,"explainTxt":null,"price":null,"true_sell":"0.01","collectCount":0,"collectStatus":0,"isBill":0,"pictures":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","picture":null,"phone":"13193131327","introduction":null,"introductionImages":null,"commentCount":0,"commodityCommentModel":null,"count":2,"modelName":"480克","modelId":7,"aftersale":null}]
     * status : 1
     * commodityNum : 1
     * logisticsCost : 10.0
     */

    private String message;
    /**
     * id : 32
     * userId : 191
     * name : 小米
     * phone : 15620607243
     * province : 重庆市
     * city : 重庆市
     * district : 江北区
     * street : 北城天街海怡花园9-2
     * status : 100
     * updateStatus : 0
     */

    private UserAddressBean userAddress;
    private int status;
    private int commodityNum;
    private double logisticsCost;
    private int costType;
    private int orderType;

    public int getCostType() {
        return costType;
    }

    public void setCostType(int costType) {
        this.costType = costType;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    /**
     * id : 54
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
     * pictures : 6/a/5/4/6a5417a2b3a51c89aecd8a19401f72f8
     * picture : null
     * phone : 13193131327
     * introduction : null
     * introductionImages : null
     * commentCount : 0
     * commodityCommentModel : null
     * count : 2
     * modelName : 1200克
     * modelId : 8
     * aftersale : null
     */

    private List<CommoditysBean> commoditys;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserAddressBean getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddressBean userAddress) {
        this.userAddress = userAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(int commodityNum) {
        this.commodityNum = commodityNum;
    }

    public double getLogisticsCost() {
        return logisticsCost;
    }

    public void setLogisticsCost(double logisticsCost) {
        this.logisticsCost = logisticsCost;
    }

    public List<CommoditysBean> getCommoditys() {
        return commoditys;
    }

    public void setCommoditys(List<CommoditysBean> commoditys) {
        this.commoditys = commoditys;
    }

    public static class UserAddressBean {
        private int id;
        private int userId;
        private String name;
        private String phone;
        private String province;
        private String city;
        private String district;
        private String street;
        private int status;
        private int updateStatus;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUpdateStatus() {
            return updateStatus;
        }

        public void setUpdateStatus(int updateStatus) {
            this.updateStatus = updateStatus;
        }
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
