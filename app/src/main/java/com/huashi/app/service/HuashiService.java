package com.huashi.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import cn.jpush.android.service.PushService;


/**
 * Created by Administrator on 2016/12/1 0001.
 */

public class HuashiService extends Service {
    private int messageNotificationID=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //开启线程
        MessageThread thread = new MessageThread();
        thread.isRunning = true;
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // 再次动态注册广播
        this.startService(new Intent(this, PushService.class));
//        IntentFilter localIntentFilter = new IntentFilter("android.intent.action.USER_PRESENT");
//        localIntentFilter.setPriority(Integer.MAX_VALUE);// 整形最大值
//        MyService searchReceiver = new MyService();
//        registerReceiver(searchReceiver, localIntentFilter);
//        Intent intents = new  Intent();
//        sendBroadcast(intent);
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        Intent localIntent = new Intent();
        localIntent.setClass(this, HuashiService.class); // 销毁时重新启动Service
        this.startService(localIntent);
        Intent intent = new Intent("com.huashi.app.service.HuashiBroadcastReceiver");
        sendBroadcast(intent);
        stopForeground(true);
        super.onDestroy();

    }

    /***
     * 从服务端获取消息
     * @author zhanglei
     *
     */
    class MessageThread extends Thread{
        //运行状态
        public boolean isRunning = true;
        @Override
        public void run() {
            while(isRunning){
                try {
                    //休息10秒
                    Thread.sleep(1000);
                    if(getServerMessage().equals("yes")){
                        //设置消息内容和标题
//                        Log.e("花市服务","执行了"+messageNotificationID);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /***
     * 模拟了服务端的消息。实际应用中应该去服务器拿到message
     * @return
     */
    public String getServerMessage(){
        return "yes";
    }
}
