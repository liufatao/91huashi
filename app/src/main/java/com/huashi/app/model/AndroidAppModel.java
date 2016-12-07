package com.huashi.app.model;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AndroidAppModel {

    public static final String APK_DOWNLOAD_URL = "filePath";
    public static final String APK_UPDATE_CONTENT = "upgradeLog";
    public static final String APK_VERSION_CODE = "versionCode";
    /**
     * id : 1
     * appNameCN : app中文名
     * appNameEN : app英文名
     * fileName : 1.1.2
     * fileSize : 216
     * filePath : http://119.84.99.187/imtt.dd.qq.com/16891/8CD6C4F1948FC4633C745E61E0B21028.apk?mkey=57ba4ac17f3a902b&f=cf87&c=0&fsname=com.tencent.news_5.0.7_507.apk&csr=4d5s&p=.apk
     * versionCode : 3
     * versionName : 1.1.2
     * upgradeLog : 你拍一我拍一，涛涛有根大鸡鸡
     * createTime : 1471590841000
     */

    private AppBean app;
    /**
     * app : {"id":1,"appNameCN":"app中文名","appNameEN":"app英文名","fileName":"1.1.2","fileSize":"216","filePath":"http://119.84.99.187/imtt.dd.qq.com/16891/8CD6C4F1948FC4633C745E61E0B21028.apk?mkey=57ba4ac17f3a902b&f=cf87&c=0&fsname=com.tencent.news_5.0.7_507.apk&csr=4d5s&p=.apk","versionCode":3,"versionName":"1.1.2","upgradeLog":"你拍一我拍一，涛涛有根大鸡鸡","createTime":1471590841000}
     * status : 1
     */

    private int status;

    public AppBean getApp() {
        return app;
    }

    public void setApp(AppBean app) {
        this.app = app;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class AppBean {
        private int id;
        private String appNameCN;
        private String appNameEN;
        private String fileName;
        private String fileSize;
        private String filePath;
        private int versionCode;
        private String versionName;
        private String upgradeLog;
        private long createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAppNameCN() {
            return appNameCN;
        }

        public void setAppNameCN(String appNameCN) {
            this.appNameCN = appNameCN;
        }

        public String getAppNameEN() {
            return appNameEN;
        }

        public void setAppNameEN(String appNameEN) {
            this.appNameEN = appNameEN;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getUpgradeLog() {
            return upgradeLog;
        }

        public void setUpgradeLog(String upgradeLog) {
            this.upgradeLog = upgradeLog;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
