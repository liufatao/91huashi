package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29.
 */
public class SmartIndustryModel {

    /**
     * message : 查询成功
     * status : 1
     * smartIndustrys : [{"id":47,"nameCN":"手机6代测试商品","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":48,"nameCN":"红枣测试商品","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":49,"nameCN":"淘真里","picUrl":"4/7/8/a/478a1a681d74b24cf978518bb90c8a4c"},{"id":50,"nameCN":"戴尔笔记本电脑","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":52,"nameCN":"大疆红枣测试商品","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":53,"nameCN":"红枣测试商品","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":54,"nameCN":"红枣测试商品","picUrl":"6/a/5/4/6a5417a2b3a51c89aecd8a19401f72f8"},{"id":55,"nameCN":"大疆养生大枣测试商品","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":56,"nameCN":"玉枣片测试商品","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":57,"nameCN":"经典礼盒测试商品","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":58,"nameCN":"玉枣精选装测试商品","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":59,"nameCN":"贝因美 金装爱＋幼儿配方奶粉3段 900克*2 1-3周岁宝宝适用 满1500再 送两个爬行垫","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":60,"nameCN":"大疆养生玉枣精选装420g","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":61,"nameCN":"大疆养生玉枣特级装600g","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":62,"nameCN":"大疆养生玉枣优选装960g","picUrl":"9/9/6/1/996101af14ea89f78a8491f1a2936e01"},{"id":63,"nameCN":"大疆玉枣片180g","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":64,"nameCN":"大疆枣花蜜","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":65,"nameCN":"大芸","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":66,"nameCN":"干果礼盒1","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":67,"nameCN":"干果礼盒2","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":68,"nameCN":"经典礼盒1360g/盒","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":69,"nameCN":"昆仑雪菊","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":70,"nameCN":"罗布麻茶","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":71,"nameCN":"大疆养生枣-罐装","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"},{"id":72,"nameCN":"天山奇豆","picUrl":"7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b"}]
     */

    private String message;
    private int status;
    /**
     * id : 47
     * nameCN : 手机6代测试商品
     * picUrl : 7/3/9/7/7397ed1392c7c32456b0a7a2c6d2d07b
     */

    private List<SmartIndustrysBean> smartIndustrys;

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

    public List<SmartIndustrysBean> getSmartIndustrys() {
        return smartIndustrys;
    }

    public void setSmartIndustrys(List<SmartIndustrysBean> smartIndustrys) {
        this.smartIndustrys = smartIndustrys;
    }

    public static class SmartIndustrysBean {
        private int id;
        private String nameCN;
        private String picUrl;

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

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
    }
}
