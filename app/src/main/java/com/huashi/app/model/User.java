package com.huashi.app.model;

/**
 * Created by Administrator on 2016/7/26.
 */
public class User {


    /**
     * message : 登录成功
     * status : 1
     * user : {"id":178,"account":"2000000037","email":"ridehai@163.com","phone":"13193131327","nickname":"枫落","password":"","avatar":"f/4/0/9/f4095cb089977a273543cdb68bf9c5f5","motto":"农业重金属","gender":200,"hobby":null}
     */

    private String message;
    private int status;
    /**
     * id : 178
     * account : 2000000037
     * email : ridehai@163.com
     * phone : 13193131327
     * nickname : 枫落
     * password :
     * avatar : f/4/0/9/f4095cb089977a273543cdb68bf9c5f5
     * motto : 农业重金属
     * gender : 200
     * hobby : null
     */

    private UserBean user;

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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        private int id;
        private String account;
        private String email;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
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
