package com.huashi.app.application;

import android.app.Application;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.huashi.app.service.HuashiService;

import cn.jpush.android.api.JPushInterface;

/**
 * For developer startup JPush SDK
 * <p>
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class ExampleApplication extends Application {
    private RequestQueue requestQueue;
    private static ExampleApplication sInstance;
    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        //启动server
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        this.startService(new Intent(this, HuashiService.class));
        sInstance = this;
    }

    public static synchronized ExampleApplication getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }
    public Gson getGson(){
        if (gson==null){
            gson=new Gson();
        }
        return gson;
    }

}
