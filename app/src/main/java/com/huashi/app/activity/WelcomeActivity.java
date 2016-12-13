package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.huashi.app.R;
import com.huashi.app.util.Httputil;

/**
 * Created by Administrator on 2016/5/9.
 * 欢迎页
 */
public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        firstRun();
    }
    //欢迎页面
    private void welcome(){
        final View view= View.inflate(this, R.layout.welcome_activity, null);
        setContentView(view);
        if(!Httputil.isNetworkAvailable(this)){
            Toast.makeText(this,"网络连接不通畅",Toast.LENGTH_LONG).show();
        }
        //渐变背景图片
        AlphaAnimation animation=new AlphaAnimation(0.3f,1f);
        animation.setDuration(2000);//设置持续时间
        view.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //开始时调用

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //完成时调用
                toMainActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //重复时调用
            }
        });




    }

    //从启动也跳转到主页
    private void  toMainActivity(){

        Intent intent=new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.overridePendingTransition(R.anim.activity_open,0);
        finish();
    }
    //跳转到引导页
    private void toGuideActivity(){
        Intent intent=new Intent(this,GuideActivity.class);
        startActivity(intent);

    }

    /**
     * 判断是否第一次运行
     */
    private void firstRun(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun)
        {
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            //第一次运行
            toGuideActivity();
            finish();

        } else
        {
            //非第一次运行
            welcome();
        }
    }
}
