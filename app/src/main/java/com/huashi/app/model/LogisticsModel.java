package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */

public class LogisticsModel {

    /**
     * message : 查询物流信息成功
     * order : {"id":1169,"order_code":"000191147427190545139570","user_id":191,"count":3,"total_count":"0.12000000000000001","create_time":1474271541000,"status":8,"logistics_cost":10,"pay_time":1474509322000,"orderType":1,"costType":1,"delivery_time":1474595722000,"refund_time":null,"delivery_user":null,"take_address_id":32,"receiving_time":1474768522000,"bull_note":null,"cancel_reason":null,"isBill":0,"details":[{"id":1017,"order_id":1169,"commodity_id":53,"model_id":7,"model_name":"480克","price":0.01,"count":2,"name":"红枣测试商品53","pic_url":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","has_comment":0},{"id":1018,"order_id":1169,"commodity_id":93,"model_id":73,"model_name":"1","price":0.1,"count":1,"name":"12393","pic_url":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","has_comment":0}],"logisticInfo":null,"take_address":null}
     * status : 1
     * logisticsOrder : {"id":3,"logistic_id":108,"tracking_no":221055327438,"order_id":1169,"logistic_fee":10,"logistic_info":null,"createTime":1474595722000,"status":0,"logisticsCode":"STO","logisticsName":"申通快递","logisticsInfo":[{"accepttime":"2016-08-10 17:56:19","acceptstation":"【收件】【江苏昆山公司】的【太仓科捷】已收件,扫描员是【太仓科捷】","remark":null},{"accepttime":"2016-08-10 20:16:06","acceptstation":"【收件】【江苏昆山公司】的【江苏昆山公司】已收件,扫描员是【郭素华】","remark":null},{"accepttime":"2016-08-10 20:32:49","acceptstation":"【发件】快件在【江苏昆山公司】由【汪胜国】扫描发往【上海航空部】","remark":null},{"accepttime":"2016-08-10 20:32:49","acceptstation":"【装袋】【江苏昆山公司】已进行【装袋】扫描,袋号【900404365590】单号【221055327438】","remark":null},{"accepttime":"2016-08-10 20:44:47","acceptstation":"【发件】快件在【江苏昆山公司】由【冯金明】扫描发往【上海航空部】","remark":null},{"accepttime":"2016-08-11 00:25:32","acceptstation":"【发件】快件在【上海航空部】由【周开能】扫描发往【重庆航空部】","remark":null},{"accepttime":"2016-08-12 13:21:13","acceptstation":"【发件】快件在【重庆公司】由【一分区发件】扫描发往【重庆江北公司】","remark":null},{"accepttime":"2016-08-13 04:00:05","acceptstation":"【到件】快件到达【重庆江北公司】,上一站是【】,扫描员是【重庆江北公司1】","remark":null},{"accepttime":"2016-08-13 05:31:19","acceptstation":"【派件】【重庆江北公司】的【江北二部】正在派件,扫描员是【重庆江北公司1】","remark":null},{"accepttime":"2016-08-13 10:00:58","acceptstation":"【派件】【重庆江北公司】的【刘洪坤小区件】正在派件,扫描员是【刘洪坤】","remark":null},{"accepttime":"2016-08-13 16:40:59","acceptstation":"【签收】已签收,签收人是:【日日顺】","remark":null}]}
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
     * logistics_cost : 10.0
     * pay_time : 1474509322000
     * orderType : 1
     * costType : 1
     * delivery_time : 1474595722000
     * refund_time : null
     * delivery_user : null
     * take_address_id : 32
     * receiving_time : 1474768522000
     * bull_note : null
     * cancel_reason : null
     * isBill : 0
     * details : [{"id":1017,"order_id":1169,"commodity_id":53,"model_id":7,"model_name":"480克","price":0.01,"count":2,"name":"红枣测试商品53","pic_url":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","has_comment":0},{"id":1018,"order_id":1169,"commodity_id":93,"model_id":73,"model_name":"1","price":0.1,"count":1,"name":"12393","pic_url":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b","has_comment":0}]
     * logisticInfo : null
     * take_address : null
     */

    private OrderBean order;
    private int status;
    /**
     * id : 3
     * logistic_id : 108
     * tracking_no : 221055327438
     * order_id : 1169
     * logistic_fee : 10.0
     * logistic_info : null
     * createTime : 1474595722000
     * status : 0
     * logisticsCode : STO
     * logisticsName : 申通快递
     * logisticsInfo : [{"accepttime":"2016-08-10 17:56:19","acceptstation":"【收件】【江苏昆山公司】的【太仓科捷】已收件,扫描员是【太仓科捷】","remark":null},{"accepttime":"2016-08-10 20:16:06","acceptstation":"【收件】【江苏昆山公司】的【江苏昆山公司】已收件,扫描员是【郭素华】","remark":null},{"accepttime":"2016-08-10 20:32:49","acceptstation":"【发件】快件在【江苏昆山公司】由【汪胜国】扫描发往【上海航空部】","remark":null},{"accepttime":"2016-08-10 20:32:49","acceptstation":"【装袋】【江苏昆山公司】已进行【装袋】扫描,袋号【900404365590】单号【221055327438】","remark":null},{"accepttime":"2016-08-10 20:44:47","acceptstation":"【发件】快件在【江苏昆山公司】由【冯金明】扫描发往【上海航空部】","remark":null},{"accepttime":"2016-08-11 00:25:32","acceptstation":"【发件】快件在【上海航空部】由【周开能】扫描发往【重庆航空部】","remark":null},{"accepttime":"2016-08-12 13:21:13","acceptstation":"【发件】快件在【重庆公司】由【一分区发件】扫描发往【重庆江北公司】","remark":null},{"accepttime":"2016-08-13 04:00:05","acceptstation":"【到件】快件到达【重庆江北公司】,上一站是【】,扫描员是【重庆江北公司1】","remark":null},{"accepttime":"2016-08-13 05:31:19","acceptstation":"【派件】【重庆江北公司】的【江北二部】正在派件,扫描员是【重庆江北公司1】","remark":null},{"accepttime":"2016-08-13 10:00:58","acceptstation":"【派件】【重庆江北公司】的【刘洪坤小区件】正在派件,扫描员是【刘洪坤】","remark":null},{"accepttime":"2016-08-13 16:40:59","acceptstation":"【签收】已签收,签收人是:【日日顺】","remark":null}]
     */

    private LogisticsOrderBean logisticsOrder;

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

    public LogisticsOrderBean getLogisticsOrder() {
        return logisticsOrder;
    }

    public void setLogisticsOrder(LogisticsOrderBean logisticsOrder) {
        this.logisticsOrder = logisticsOrder;
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
        private Object logisticInfo;
        private Object take_address;
        /**
         * id : 1017
         * order_id : 1169
         * commodity_id : 53
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

        public Object getLogisticInfo() {
            return logisticInfo;
        }

        public void setLogisticInfo(Object logisticInfo) {
            this.logisticInfo = logisticInfo;
        }

        public Object getTake_address() {
            return take_address;
        }

        public void setTake_address(Object take_address) {
            this.take_address = take_address;
        }

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
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

    public static class LogisticsOrderBean {
        private int id;
        private int logistic_id;
        private long tracking_no;
        private int order_id;
        private double logistic_fee;
        private Object logistic_info;
        private long createTime;
        private int status;
        private String logisticsCode;
        private String logisticsName;
        /**
         * accepttime : 2016-08-10 17:56:19
         * acceptstation : 【收件】【江苏昆山公司】的【太仓科捷】已收件,扫描员是【太仓科捷】
         * remark : null
         */

        private List<LogisticsInfoBean> logisticsInfo;

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

        public long getTracking_no() {
            return tracking_no;
        }

        public void setTracking_no(long tracking_no) {
            this.tracking_no = tracking_no;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
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

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getLogisticsCode() {
            return logisticsCode;
        }

        public void setLogisticsCode(String logisticsCode) {
            this.logisticsCode = logisticsCode;
        }

        public String getLogisticsName() {
            return logisticsName;
        }

        public void setLogisticsName(String logisticsName) {
            this.logisticsName = logisticsName;
        }

        public List<LogisticsInfoBean> getLogisticsInfo() {
            return logisticsInfo;
        }

        public void setLogisticsInfo(List<LogisticsInfoBean> logisticsInfo) {
            this.logisticsInfo = logisticsInfo;
        }

        public static class LogisticsInfoBean {
            private String accepttime;
            private String acceptstation;
            private Object remark;

            public String getAccepttime() {
                return accepttime;
            }

            public void setAccepttime(String accepttime) {
                this.accepttime = accepttime;
            }

            public String getAcceptstation() {
                return acceptstation;
            }

            public void setAcceptstation(String acceptstation) {
                this.acceptstation = acceptstation;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }
        }
    }
}
