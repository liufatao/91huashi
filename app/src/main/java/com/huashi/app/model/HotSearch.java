package com.huashi.app.model;

import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */

public class HotSearch {

    /**
     * message : 查询成功
     * status : 1
     * hotSearchs : [{"id":235,"name":"母婴","createTime":1435657943000},{"id":226,"name":"电话","createTime":1435654625000},{"id":220,"name":"小米","createTime":1431602908000},{"id":216,"name":"小涛涛","createTime":1431602673000},{"id":208,"name":"花王","createTime":1431082469000},{"id":207,"name":"三星","createTime":1431082248000}]
     */

    private String message;
    private int status;
    /**
     * id : 235
     * name : 母婴
     * createTime : 1435657943000
     */

    private List<HotSearchsBean> hotSearchs;

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

    public List<HotSearchsBean> getHotSearchs() {
        return hotSearchs;
    }

    public void setHotSearchs(List<HotSearchsBean> hotSearchs) {
        this.hotSearchs = hotSearchs;
    }

    public static class HotSearchsBean {
        private int id;
        private String name;
        private long createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
