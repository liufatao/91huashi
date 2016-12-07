package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public class OrderStateModel {

    /**
     * message : 查询订单信息成功
     * order : {"id":1169,"order_code":"000191147427190545139570","user_id":191,"count":3,"total_count":"0.12000000000000001","create_time":1474271541000,"status":8,"logistics_cost":0,"pay_time":1474509322000,"orderType":1,"costType":1,"delivery_time":1474595722000,"refund_time":null,"delivery_user":null,"take_address_id":0,"receiving_time":1474768522000,"bull_note":null,"cancel_reason":null,"isBill":0,"details":[{"id":1017,"order_id":1169,"commodity_id":0,"model_id":7,"model_name":"480克","price":0.01,"count":2,"name":"红枣测试商品53","pic_url":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","has_comment":0},{"id":1018,"order_id":1169,"commodity_id":0,"model_id":73,"model_name":"1","price":0.1,"count":1,"name":"12393","pic_url":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","has_comment":0}],"logisticInfo":{"id":3,"logistic_id":108,"logistic_name":"申通快递","logistic_code":"STO","tracking_no":"221055327438 ","order_id":1169,"createTime":1474595722000,"logistic_fee":10,"logistic_info":null},"take_address":{"id":32,"userId":191,"name":"小米","phone":"15620607243","province":"重庆市","city":"重庆市","district":"江北区","street":"北城天街海怡花园9-2","status":100,"updateStatus":0}}
     * status : 1
     */

    private String message;
    /**
     * id : 1169
     * order_code : 000191147427190545139570
     * user_id : 191
     * count : 3
     * total_count : 0.12000000000000001
     * create_time : 1474271541000
     * status : 8
     * logistics_cost : 0.0
     * pay_time : 1474509322000
     * orderType : 1
     * costType : 1
     * delivery_time : 1474595722000
     * refund_time : null
     * delivery_user : null
     * take_address_id : 0
     * receiving_time : 1474768522000
     * bull_note : null
     * cancel_reason : null
     * isBill : 0
     * details : [{"id":1017,"order_id":1169,"commodity_id":0,"model_id":7,"model_name":"480克","price":0.01,"count":2,"name":"红枣测试商品53","pic_url":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","has_comment":0},{"id":1018,"order_id":1169,"commodity_id":0,"model_id":73,"model_name":"1","price":0.1,"count":1,"name":"12393","pic_url":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","has_comment":0}]
     * logisticInfo : {"id":3,"logistic_id":108,"logistic_name":"申通快递","logistic_code":"STO","tracking_no":"221055327438 ","order_id":1169,"createTime":1474595722000,"logistic_fee":10,"logistic_info":null}
     * take_address : {"id":32,"userId":191,"name":"小米","phone":"15620607243","province":"重庆市","city":"重庆市","district":"江北区","street":"北城天街海怡花园9-2","status":100,"updateStatus":0}
     */

    private OrderBean order;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class OrderBean {
        private int id;
        private String order_code;
        private int user_id;
        private int count;
        private String total_count;
        private long create_time;
        private int status;
        private double logistics_cost;
        private long pay_time;
        private int orderType;
        private int costType;
        private long delivery_time;
        private Object refund_time;
        private Object delivery_user;
        private int take_address_id;
        private long receiving_time;
        private Object bull_note;
        private Object cancel_reason;
        private int isBill;
        /**
         * id : 3
         * logistic_id : 108
         * logistic_name : 申通快递
         * logistic_code : STO
         * tracking_no : 221055327438
         * order_id : 1169
         * createTime : 1474595722000
         * logistic_fee : 10.0
         * logistic_info : null
         */

        private LogisticInfoBean logisticInfo;
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

        private TakeAddressBean take_address;
        /**
         * id : 1017
         * order_id : 1169
         * commodity_id : 0
         * model_id : 7
         * model_name : 480克
         * price : 0.01
         * count : 2
         * name : 红枣测试商品53
         * pic_url : 7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b
         * has_comment : 0
         */

        private List<DetailsBean> details;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTotal_count() {
            return total_count;
        }

        public void setTotal_count(String total_count) {
            this.total_count = total_count;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getLogistics_cost() {
            return logistics_cost;
        }

        public void setLogistics_cost(double logistics_cost) {
            this.logistics_cost = logistics_cost;
        }

        public long getPay_time() {
            return pay_time;
        }

        public void setPay_time(long pay_time) {
            this.pay_time = pay_time;
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

        public long getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(long delivery_time) {
            this.delivery_time = delivery_time;
        }

        public Object getRefund_time() {
            return refund_time;
        }

        public void setRefund_time(Object refund_time) {
            this.refund_time = refund_time;
        }

        public Object getDelivery_user() {
            return delivery_user;
        }

        public void setDelivery_user(Object delivery_user) {
            this.delivery_user = delivery_user;
        }

        public int getTake_address_id() {
            return take_address_id;
        }

        public void setTake_address_id(int take_address_id) {
            this.take_address_id = take_address_id;
        }

        public long getReceiving_time() {
            return receiving_time;
        }

        public void setReceiving_time(long receiving_time) {
            this.receiving_time = receiving_time;
        }

        public Object getBull_note() {
            return bull_note;
        }

        public void setBull_note(Object bull_note) {
            this.bull_note = bull_note;
        }

        public Object getCancel_reason() {
            return cancel_reason;
        }

        public void setCancel_reason(Object cancel_reason) {
            this.cancel_reason = cancel_reason;
        }

        public int getIsBill() {
            return isBill;
        }

        public void setIsBill(int isBill) {
            this.isBill = isBill;
        }

        public LogisticInfoBean getLogisticInfo() {
            return logisticInfo;
        }

        public void setLogisticInfo(LogisticInfoBean logisticInfo) {
            this.logisticInfo = logisticInfo;
        }

        public TakeAddressBean getTake_address() {
            return take_address;
        }

        public void setTake_address(TakeAddressBean take_address) {
            this.take_address = take_address;
        }

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
        }

        public static class LogisticInfoBean {
            private int id;
            private int logistic_id;
            private String logistic_name;
            private String logistic_code;
            private String tracking_no;
            private int order_id;
            private long createTime;
            private double logistic_fee;
            private Object logistic_info;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLogistic_id() {
                return logistic_id;
            }

            public void setLogistic_id(int logistic_id) {
                this.logistic_id = logistic_id;
            }

            public String getLogistic_name() {
                return logistic_name;
            }

            public void setLogistic_name(String logistic_name) {
                this.logistic_name = logistic_name;
            }

            public String getLogistic_code() {
                return logistic_code;
            }

            public void setLogistic_code(String logistic_code) {
                this.logistic_code = logistic_code;
            }

            public String getTracking_no() {
                return tracking_no;
            }

            public void setTracking_no(String tracking_no) {
                this.tracking_no = tracking_no;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public double getLogistic_fee() {
                return logistic_fee;
            }

            public void setLogistic_fee(double logistic_fee) {
                this.logistic_fee = logistic_fee;
            }

            public Object getLogistic_info() {
                return logistic_info;
            }

            public void setLogistic_info(Object logistic_info) {
                this.logistic_info = logistic_info;
            }
        }

        public static class TakeAddressBean {
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

        public static class DetailsBean {
            private int id;
            private int order_id;
            private int commodity_id;
            private int model_id;
            private String model_name;
            private double price;
            private int count;
            private String name;
            private String pic_url;
            private int has_comment;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getCommodity_id() {
                return commodity_id;
            }

            public void setCommodity_id(int commodity_id) {
                this.commodity_id = commodity_id;
            }

            public int getModel_id() {
                return model_id;
            }

            public void setModel_id(int model_id) {
                this.model_id = model_id;
            }

            public String getModel_name() {
                return model_name;
            }

            public void setModel_name(String model_name) {
                this.model_name = model_name;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPic_url() {
                return pic_url;
            }

            public void setPic_url(String pic_url) {
                this.pic_url = pic_url;
            }

            public int getHas_comment() {
                return has_comment;
            }

            public void setHas_comment(int has_comment) {
                this.has_comment = has_comment;
            }
        }
    }
}
