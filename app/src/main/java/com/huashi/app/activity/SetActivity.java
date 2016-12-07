package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.huashi.app.R;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.util.DataCleanManger;
import com.huashi.app.util.Httputil;
import com.huashi.app.util.UpdateChecker;
import com.huashi.app.util.Utils;

/**
 * Created by Administrator on 2016/5/20.
 */
public class SetActivity extends Activity implements View.OnClickListener {
    private RelativeLayout ray_feedback, ray_empty, ray_versions, ray_about, ray_popwindow, ry_msgage;
    private Button btn_quit, btn_exit, btn_cancel, btn_msgconfirm, btn_msgcancel;
    private ImageView img_setback;
    private PopupWindow popupWindow, messageWindow;
    private Utils utils;
    private String version;
    private int code;
    private UpdateChecker updateChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        intoView();
    }

    private void intoView() {
        utils = new Utils(this);
        version = utils.getVersion();
        code = utils.getVersionCode();
        updateChecker = new UpdateChecker(this);
        ray_feedback = (RelativeLayout) findViewById(R.id.ray_feedback);
        ray_feedback.setOnClickListener(this);
        ray_empty = (RelativeLayout) findViewById(R.id.ray_empty);
        ray_empty.setOnClickListener(this);
        ray_versions = (RelativeLayout) findViewById(R.id.ray_versions);
        ray_versions.setOnClickListener(this);
        ray_about = (RelativeLayout) findViewById(R.id.ray_about);
        ray_about.setOnClickListener(this);
        btn_quit = (Button) findViewById(R.id.btn_quit);
        btn_quit.setOnClickListener(this);
        img_setback = (ImageView) findViewById(R.id.img_setback);
        img_setback.setOnClickListener(this);
    }


    private void showPopWindon(View view) {
        View windon;
        LayoutInflater inflater = LayoutInflater.from(this);
        windon = inflater.inflate(R.layout.popwindow_exit, null);
        popupWindow = new PopupWindow(windon, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        ray_popwindow = (RelativeLayout) windon.findViewById(R.id.ray_popwindow);
        btn_exit = (Button) windon.findViewById(R.id.btn_exit);
        btn_cancel = (Button) windon.findViewById(R.id.btn_cancel);
        ray_popwindow.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);


    }

    //清除缓存
    public void showMessageWindon(View view) {
        View windon;
        LayoutInflater inflater = LayoutInflater.from(this);
        windon = inflater.inflate(R.layout.popwindow_message, null);
        messageWindow = new PopupWindow(windon, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);

        messageWindow.setFocusable(true);
        messageWindow.setOutsideTouchable(false);
        messageWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        messageWindow.setBackgroundDrawable(new BitmapDrawable());
        messageWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        btn_msgconfirm = (Button) windon.findViewById(R.id.btn_msgconfirm);
        btn_msgconfirm.setOnClickListener(this);
        btn_msgcancel = (Button) windon.findViewById(R.id.btn_msgcancel);
        btn_msgcancel.setOnClickListener(this);
        ry_msgage = (RelativeLayout) windon.findViewById(R.id.rl_message);
        ry_msgage.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ray_feedback:
                //意见反馈
                Intent intentfeedback = new Intent(this, FeedBackActivity.class);
                startActivity(intentfeedback);
                break;
            case R.id.ray_empty:
                //清空缓存
                showMessageWindon(ray_empty);
                break;
            case R.id.ray_versions:
                //版本检测
                if (Httputil.isNetworkAvailable(this)) {
                    updateChecker.setCheckUrl(RequestUrlsConfig.CHECKNEWST);
                    updateChecker.checkForUpdates();
                } else {
                    Toast.makeText(this, R.string.networkexception, Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.ray_about:
                //关于我们
                Intent intentabout = new Intent(this, AboutActivity.class);
                startActivity(intentabout);
                break;
            case R.id.btn_quit:
                //退出当前账号
                showPopWindon(btn_quit);
                break;
            case R.id.img_setback:
                //返回
                finish();
                break;
            case R.id.btn_cancel:
                //取消退出当前账号
                popupWindow.dismiss();
                break;
            case R.id.btn_exit:
                //退出当前账号
                if (Httputil.isNetworkAvailable(this)){
                    utils.clearData();
                    Intent intent = new Intent(this, Login_Activity.class);
                    startActivity(intent);
                    popupWindow.dismiss();
                    finish();
                }


                break;
            case R.id.ray_popwindow:
                //点击外部消失
                popupWindow.dismiss();
                break;
            case R.id.btn_msgcancel:
                //缓存按钮取消
                messageWindow.dismiss();
                break;
            case R.id.btn_msgconfirm:
                //缓存按钮确定键
                DataCleanManger.cleanDatabases(this);
                DataCleanManger.cleanInternalCache(this);
                DataCleanManger.cleanSharedPreference(this);
                messageWindow.dismiss();
                Toast.makeText(this, "清除缓存成功", Toast.LENGTH_LONG).show();
                break;
            case R.id.rl_message:
                messageWindow.dismiss();
                break;
        }
    }

}
