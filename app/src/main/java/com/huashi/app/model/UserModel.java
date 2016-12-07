package com.huashi.app.model;

/**
 * Created by Administrator on 2016/7/25.
 */
public class UserModel {

    /**
     * status : 1
     * toEvaluationNum : 2
     * toDeliveryNum : 2
     * user : {"id":191,"account":"2000000038","email":null,"phone":"13220380556","nickname":"13220380556","password":"","avatar":"7/c/d/1/7cd1d0f2c0ad5f14398174574d51bb1d","motto":"向我开炮","gender":0,"hobby":null}
     * toBarterNum : 2
     * toRefundNum : 2
     * toPayNum : 179
     */

    private int status;
    private int toEvaluationNum;
    private int toDeliveryNum;
    /**
     * id : 191
     * account : 2000000038
     * email : null
     * phone : 13220380556
     * nickname : 13220380556
     * password :
     * avatar : 7/c/d/1/7cd1d0f2c0ad5f14398174574d51bb1d
     * motto : 向我开炮
     * gender : 0
     * hobby : null
     */

    private UserBean user;
    private int toBarterNum;
    private int toRefundNum;
    private int toPayNum;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getToEvaluationNum() {
        return toEvaluationNum;
    }

    public void setToEvaluationNum(int toEvaluationNum) {
        this.toEvaluationNum = toEvaluationNum;
    }

    public int getToDeliveryNum() {
        return toDeliveryNum;
    }

    public void setToDeliveryNum(int toDeliveryNum) {
        this.toDeliveryNum = toDeliveryNum;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getToBarterNum() {
        return toBarterNum;
    }

    public void setToBarterNum(int toBarterNum) {
        this.toBarterNum = toBarterNum;
    }

    public int getToRefundNum() {
        return toRefundNum;
    }

    public void setToRefundNum(int toRefundNum) {
        this.toRefundNum = toRefundNum;
    }

    public int getToPayNum() {
        return toPayNum;
    }

    public void setToPayNum(int toPayNum) {
        this.toPayNum = toPayNum;
    }

    public static class UserBean {
        private int id;
        private String account;
        private Object email;
        private String phone;
        private String nickname;
        private String password;
        private String avatar;
        private String motto;
        private int gender;
        private Object hobby;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getMotto() {
            return motto;
        }

        public void setMotto(String motto) {
            this.motto = motto;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public Object getHobby() {
            return hobby;
        }

        public void setHobby(Object hobby) {
            this.hobby = hobby;
        }
    }
}
