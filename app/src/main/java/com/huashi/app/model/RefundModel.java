package com.huashi.app.model;

/**
 * Created by Administrator on 2016/9/22.
 */

public class RefundModel {

    /**
     * message : 查询成功
     * status : 1
     * orderModel : {"id":1159,"order_code":"000009147418972115676995","user_id":191,"count":1,"total_count":"0.01","create_time":1474189356000,"status":5,"logistics_cost":10,"pay_time":null,"orderType":1,"costType":1,"delivery_time":null,"refund_time":null,"delivery_user":null,"take_address_id":32,"receiving_time":null,"bull_note":"","cancel_reason":"了了了了了","isBill":0,"details":null,"logisticInfo":null,"take_address":null}
     */

    private String message;
    private int status;
    /**
     * id : 1159
     * order_code : 000009147418972115676995
     * user_id : 191
     * count : 1
     * total_count : 0.01
     * create_time : 1474189356000
     * status : 5
     * logistics_cost : 10.0
     * pay_time : null
     * orderType : 1
     * costType : 1
     * delivery_time : null
     * refund_time : null
     * delivery_user : null
     * take_address_id : 32
     * receiving_time : null
     * bull_note :
     * cancel_reason : 了了了了了
     * isBill : 0
     * details : null
     * logisticInfo : null
     * take_address : null
     */

    private OrderModelBean orderModel;

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

    public OrderModelBean getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModelBean orderModel) {
        this.orderModel = orderModel;
    }

    public static class OrderModelBean {
        private int id;
        private String order_code;
        private int user_id;
        private int count;
        private String total_count;
        private long create_time;
        private int status;
        private double logistics_cost;
        private Object pay_time;
        private int orderType;
        private int costType;
        private Object delivery_time;
        private Object refund_time;
        private Object delivery_user;
        private int take_address_id;
        private Object receiving_time;
        private String bull_note;
        private String cancel_reason;
        private int isBill;
        private Object details;
        private Object logisticInfo;
        private Object take_address;

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

        public Object getPay_time() {
            return pay_time;
        }

        public void setPay_time(Object pay_time) {
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

        public Object getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(Object delivery_time) {
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

        public Object getReceiving_time() {
            return receiving_time;
        }

        public void setReceiving_time(Object receiving_time) {
            this.receiving_time = receiving_time;
        }

        public String getBull_note() {
            return bull_note;
        }

        public void setBull_note(String bull_note) {
            this.bull_note = bull_note;
        }

        public String getCancel_reason() {
            return cancel_reason;
        }

        public void setCancel_reason(String cancel_reason) {
            this.cancel_reason = cancel_reason;
        }

        public int getIsBill() {
            return isBill;
        }

        public void setIsBill(int isBill) {
            this.isBill = isBill;
        }

        public Object getDetails() {
            return details;
        }

        public void setDetails(Object details) {
            this.details = details;
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
    }
}
