package com.huashi.app.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by Administrator on 2016/12/1 0001.
 */

public class HuashiService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // 再次动态注册广播
        IntentFilter localIntentFilter = new IntentFilter("android.intent.action.USER_PRESENT");
        localIntentFilter.setPriority(Integer.MAX_VALUE);// 整形最大值
        MyService searchReceiver = new MyService();
        registerReceiver(searchReceiver, localIntentFilter);
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        Intent localIntent = new Intent();
        localIntent.setClass(this, HuashiService.class); // 销毁时重新启动Service
        this.startService(localIntent);
        super.onDestroy();

    }
}
