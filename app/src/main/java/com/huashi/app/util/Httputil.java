package com.huashi.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by Administrator on 2016/4/6.
 * 网络请求工具类
 */
public class Httputil {

    /**
     * 判断网络是否连接
     * @param ctx
     * @return
     */
    public static boolean isNetworkAvailable(Context ctx) {

        try {
            ConnectivityManager connectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {

                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {

                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


}
