package com.huashi.app.model;

/**
 * Created by Administrator on 2016/8/8.
 */
public class UserAddressModel {


    /**
     * id : 187
     * userId : 191
     * name : 刘发涛
     * phone : 13220380556
     * province : 北京
     * city : 北京市内
     * district : 东城区
     * street : 铜锣湾区牛氓巷火拼街站街2号
     * status : 200
     * updateStatus : 0
     */

    private UserAddressBean userAddress;
    /**
     * userAddress : {"id":187,"userId":191,"name":"刘发涛","phone":"13220380556","province":"北京","city":"北京市内","district":"东城区","street":"铜锣湾区牛氓巷火拼街站街2号","status":200,"updateStatus":0}
     * message : 查询收货信息成功
     * status : 1
     */

    private String message;
    private int status;

    public UserAddressBean getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddressBean userAddress) {
        this.userAddress = userAddress;
    }

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
}
