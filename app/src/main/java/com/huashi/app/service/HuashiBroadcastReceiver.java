package com.huashi.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class HuashiBroadcastReceiver extends BroadcastReceiver {
    private static final String ACTION = "android.intent.action.USER_PRESENT";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("花市广播","进入广播了");
        if (intent.getAction().equals(ACTION)){
            Intent i= new Intent(Intent.ACTION_RUN);
            i.setClass(context, HuashiService.class);
            context.startService(i);
        }
    }
}
