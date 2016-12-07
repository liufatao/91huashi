package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class UserAddress {
    /**
     * message : 查询收货信息成功
     * status : 1
     * userAddresses : [{"id":32,"userId":128,"name":"小米","phone":"15620607243","province":"重庆市","city":"重庆市","district":"江北区","street":"北城天街海怡花园9-2","status":200},{"id":36,"userId":128,"name":"么么","phone":"15620607243","province":"内蒙古","city":"鄂尔多斯","district":"鄂托克前旗","street":"北京天安门","status":100},{"id":52,"userId":128,"name":"刘","phone":"13220380556","province":"江苏","city":"宿迁","district":"泗洪县","street":"大街上面","status":200},{"id":73,"userId":128,"name":"回来了","phone":"13220380556","province":"山西","city":"长治","district":"襄垣县","street":null,"status":200},{"id":108,"userId":128,"name":"方法","phone":"13220380556","province":"河北","city":"邯郸","district":"成安县","street":null,"status":200},{"id":120,"userId":128,"name":"韦小宝","phone":"13220380556","province":"河北","city":"石家庄","district":"桥东区","street":null,"status":200},{"id":121,"userId":128,"name":"阿里","phone":"13220380556","province":"内蒙古","city":"包头","district":"白云鄂博矿区(*)","street":"人民大会堂","status":200},{"id":122,"userId":128,"name":"健康房间打开","phone":"13220380556","province":"内蒙古","city":"包头","district":"石拐区","street":"当减肥可是对方尽快","status":200},{"id":124,"userId":128,"name":"小米","phone":"15620607243","province":"重庆","city":"重庆市内","district":"渝北区","street":"天竺路","status":200}]
     */

    private String message;
    private int status;
    /**
     * id : 32
     * userId : 128
     * name : 小米
     * phone : 15620607243
     * province : 重庆市
     * city : 重庆市
     * district : 江北区
     * street : 北城天街海怡花园9-2
     * status : 200
     */

    private List<UserAddressesBean> userAddresses;

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

    public List<UserAddressesBean> getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(List<UserAddressesBean> userAddresses) {
        this.userAddresses = userAddresses;
    }

    public static class UserAddressesBean {
        private int id;
        private int userId;
        private String name;
        private String phone;
        private String province;
        private String city;
        private String district;
        private String street;
        private int status;

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
    }
}
