package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public class ShopCarModel {

    /**
     * message : 查询购物车信息详情成功
     * status : 1
     * shoppingCarts : [{"id":65,"userId":191,"commodityId":144,"modelId":16,"commodityNameCN":"大疆养生玉枣特级装600g","commodityPictures":null,"commodityPicture":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","modelName":"大疆养生玉枣精选装420g1","status":0,"price":148,"count":20,"sellRule":null},{"id":77,"userId":191,"commodityId":63,"modelId":19,"commodityNameCN":"大疆玉枣片180g","commodityPictures":null,"commodityPicture":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","modelName":"123","status":0,"price":48,"count":5,"sellRule":null}]
     */

    private String message;
    private int status;
    /**
     * id : 65
     * userId : 191
     * commodityId : 144
     * modelId : 16
     * commodityNameCN : 大疆养生玉枣特级装600g
     * commodityPictures : null
     * commodityPicture : 7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b
     * modelName : 大疆养生玉枣精选装420g1
     * status : 0
     * price : 148.0
     * count : 20
     * sellRule : null
     */

    private List<ShoppingCartsBean> shoppingCarts;

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

    public List<ShoppingCartsBean> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(List<ShoppingCartsBean> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }

    public static class ShoppingCartsBean {
        private int id;
        private int userId;
        private int commodityId;
        private int modelId;
        private String commodityNameCN;
        private Object commodityPictures;
        private String commodityPicture;
        private String modelName;
        private int status;
        private double price;
        private int count;
        private Object sellRule;

        public boolean isStatue() {
            return statue;
        }

        public boolean setStatue(boolean statue) {
            this.statue = statue;
            return statue;
        }

        private boolean statue;

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

        public int getModelId() {
            return modelId;
        }

        public void setModelId(int modelId) {
            this.modelId = modelId;
        }

        public String getCommodityNameCN() {
            return commodityNameCN;
        }

        public void setCommodityNameCN(String commodityNameCN) {
            this.commodityNameCN = commodityNameCN;
        }

        public Object getCommodityPictures() {
            return commodityPictures;
        }

        public void setCommodityPictures(Object commodityPictures) {
            this.commodityPictures = commodityPictures;
        }

        public String getCommodityPicture() {
            return commodityPicture;
        }

        public void setCommodityPicture(String commodityPicture) {
            this.commodityPicture = commodityPicture;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Object getSellRule() {
            return sellRule;
        }

        public void setSellRule(Object sellRule) {
            this.sellRule = sellRule;
        }
    }
}
