package com.huashi.app.util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.huashi.app.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/5/16.
 */

public class Utils {
    Context context;
    long mTaskid;
    DownloadManager downloadManager;


    public Utils(Context context){
        this.context=context;
        String path=Environment.getExternalStorageState() ;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String TAG="Utils";
    public static Bitmap getbitmap(String imageUri) {
        Log.v(TAG, "getbitmap:" + imageUri);
       // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

            Log.v(TAG, "image download finished." + imageUri);
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "getbitmap bmp fail---");
            return null;
        }
        return bitmap;
    }

 //从SharedPreferences获取数据
    public String getInfomation(){
        SharedPreferences preferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String userid=preferences.getString("userid", null);
        return userid;
    }
    //用户登录成功后记录登录信息方法
    public void saveUserinfo( String userid){
        SharedPreferences preferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("userid", userid);
        editor.commit();
    }


    //清除SharedPreferences值
    public void clearData(){
        SharedPreferences preferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }


    /**
     2  * 获取版本号
     3  * @return 当前应用的版本号
     4  */
     public String getVersion() {
            try {
                    PackageManager manager = context.getPackageManager();
                    PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                   String version = info.versionName;
                return version;
                } catch (Exception e) {
                     e.printStackTrace();
                    return null;
            }
         }

    public int getVersionCode() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }



    //将图片转换为数组

    public byte[]getImageBytes(Bitmap bmp){

        if(bmp==null)return null;

        ByteArrayOutputStream baos =new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);

        byte[] imageBytes = baos.toByteArray();

        return imageBytes;

    }




}
