package com.huashi.app.Config;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/5/17.
 */
public class Constant {
    public static final String TencentApp_ID = "1105245755";

    public static final String DB_NAME = "huashi.db";
    public static final String HUASHI_TABLE = "history";
    public static final String CREAT_HISTORY = "create table " + HUASHI_TABLE
            + "(h_id integer primary key autoincrement, h_name text not null)";

    // 商户PID
    public static final String PARTNER = "2088221508684394";
    // 商户收款账号
    public static final String SELLER = "cqmrzhen@sina.com";
    //支付宝公钥
//    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    private static final int SDK_PAY_FLAG = 1;
    public static final String INTENT_ORDER_KEY = "intent_order";
    public static final int SHOPPING_INTENT_ORDER = 0;
    public static final String SERIAL_KEY = "com.91huashi";

    public static final SQLiteDatabase SQLITE = SQLiteDatabase
            .openOrCreateDatabase(
                    "data/data/com.huashi.app/files/huashi.db", null);
    public static final int ONE = 1;
    public static final int ABNORMAL = -1;
    public static final int TWO = -2;
    public static final int THREE = -3;
    public static final int FOUR = -4;
    public static final int FIVE = -5;
    public static final int SIX = -6;
    public static final int SEVEN = -7;
    public static final int EIGHT = -8;
    public static final int NINE = -9;
    public static final int TEN = -10;
    public static final int ELEVEN = -11;

/***
 * 微信支付
 */
   //appid 微信分配的公众账号ID
   public static final String APP_ID = "wx94fafab682f0fe08";
    //商户号 微信分配的公众账号ID
    public static final String MCH_ID = "1398805302";
    //  API密钥，在商户平台设置
//    public static final  String API_KEY= "KUocdtS5tFnPBxrcpRwNefIRaht9QYei";
}
