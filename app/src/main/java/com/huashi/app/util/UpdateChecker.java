package com.huashi.app.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huashi.app.R;
import com.huashi.app.model.AndroidAppModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by Administrator on 2016/7/5.
 */
public class UpdateChecker {
    private static final String TAG = "UpdateChecker";
    private Context mContext;
    //检查版本信息的线程
    private Thread mThread;
    //版本对比地址
    private String mCheckUrl;
    //下载apk的对话框
    private Gson gson;
    private AndroidAppModel androidAppModel;
    private File apkFile;
    public void setCheckUrl(String url) {
        mCheckUrl = url;
    }

    public UpdateChecker(Context context) {
        mContext = context;
        // instantiate it within the onCreate method

    }
//检查更新
    public void checkForUpdates() {
        if(mCheckUrl == null) {




            //throw new Exception("checkUrl can not be null");
            return;
        }
        final Handler handler = new Handler(){
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    androidAppModel = (AndroidAppModel) msg.obj;
                    try{
                        int versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
                        Log.e("版本号","服务器版本"+androidAppModel+"本地版本"+versionCode);
                        if (androidAppModel.getApp().getVersionCode()> versionCode) {
                            showUpdateDialog();
                        } else {
                            Toast.makeText(mContext, R.string.strversions, Toast.LENGTH_SHORT).show();
                        }
                    }catch (PackageManager.NameNotFoundException ignored) {
                        //
                    }
                }
            }
        };

        mThread = new Thread() {
            @Override
            public void run() {
                //if (isNetworkAvailable(mContext)) {
                Message msg = new Message();
                String json = sendPost();
                Log.i("jianghejie","json = "+json);
                if(json!=null){
                    gson=new Gson();
                    androidAppModel=gson.fromJson(json,AndroidAppModel.class);
                    msg.what = 1;
                    msg.obj = androidAppModel;
                    handler.sendMessage(msg);
                }else{
                    Log.e(TAG, "can't get app update json");
                }
            }
        };
        mThread.start();
    }
    protected String sendPost() {
        HttpURLConnection uRLConnection = null;
        InputStream is = null;
        BufferedReader buffer = null;
        String result = null;
        try {
            URL url = new URL(mCheckUrl);
            uRLConnection = (HttpURLConnection) url.openConnection();
            uRLConnection.setDoInput(true);
            uRLConnection.setDoOutput(true);
            uRLConnection.setRequestMethod("POST");
            uRLConnection.setUseCaches(false);
            uRLConnection.setConnectTimeout(10 * 1000);
            uRLConnection.setReadTimeout(10 * 1000);
            uRLConnection.setInstanceFollowRedirects(false);
            uRLConnection.setRequestProperty("Connection", "Keep-Alive");

            uRLConnection.setRequestProperty("Charset", "UTF-8");
            uRLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            uRLConnection.setRequestProperty("Content-Type", "application/json");
            uRLConnection.connect();
            is = uRLConnection.getInputStream();
            String content_encode = uRLConnection.getContentEncoding();
            if (null != content_encode && !"".equals(content_encode) && content_encode.equals("gzip")) {
                is = new GZIPInputStream(is);
            }
            buffer = new BufferedReader(new InputStreamReader(is));
            StringBuilder strBuilder = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                strBuilder.append(line);
            }
            result = strBuilder.toString();
        } catch (Exception e) {
            Log.e(TAG, "http post error", e);
        } finally {
            if(buffer!=null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(uRLConnection!=null){
                uRLConnection.disconnect();
            }
        }
        return result;
    }


    public void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //builder.setIcon(R.drawable.icon);
        builder.setTitle("有新版本");
        builder.setMessage(androidAppModel.getApp().getUpgradeLog());
        builder.setPositiveButton("下载",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        downLoadApk();
                    }
                });
        builder.setNegativeButton("忽略",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
        builder.show();
    }

    public void downLoadApk() {
//        http://jcodecraeer.com/uploads/soft/android/CodeBox.apk
        String apkUrl = androidAppModel.getApp().getFilePath();
        Toast.makeText(mContext,"版本更新中...",Toast.LENGTH_LONG).show();
        if (!canDownloadState()) {
            Toast.makeText(mContext, "下载服务不用,请您启用", Toast.LENGTH_SHORT).show();
            showDownloadSetting();
            return;
        }

        ApkUpdateUtils.download(mContext, apkUrl, mContext.getResources().getString(R.string.app_name));

    }

    private boolean canDownloadState() {
        try {
            int state = mContext.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean intentAvailable(Intent intent) {
        PackageManager packageManager = mContext.getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void showDownloadSetting() {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (intentAvailable(intent)) {
            mContext.startActivity(intent);
        }
    }

}
