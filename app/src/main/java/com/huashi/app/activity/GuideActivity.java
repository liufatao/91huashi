package com.huashi.app.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.huashi.app.R;
import com.huashi.app.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 * 引导页
 */
public class GuideActivity extends Activity {
    private ViewPager viewpager;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;

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
        setContentView(R.layout.activity_guide);
        intoView();
    }

    //引导图片资源
    private static final int[] pics = {R.mipmap.load,
            R.mipmap.load, R.mipmap.load,};

    private void intoView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();

        views.add(inflater.inflate(R.layout.guide_new_one, null,false));
        views.add(inflater.inflate(R.layout.guide_new_two, null,false));
        views.add(inflater.inflate(R.layout.guide_new_three, null,false));
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        //初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, GuideActivity.this);
        viewpager.setAdapter(vpAdapter);

    }

    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }

        viewpager.setCurrentItem(position);
    }
}
