package com.huashi.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.huashi.app.R;
import com.huashi.app.activity.MainActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class ViewPagerAdapter extends PagerAdapter {
    List<View> views;
    Activity activity;
    public ViewPagerAdapter( List<View> views, Activity activity){
        this.views=views;
        this.activity=activity;
    }
    //销毁arg1位置的界面
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(views.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public int getCount() {
        if (views != null)
        {
            return views.size();
        }

        return 0;
    }
    //初始化arg1位置的界面
    @Override
    public Object instantiateItem(View arg0, int arg1) {

        ((ViewPager) arg0).addView(views.get(arg1), 0);
        if (arg1 == views.size() - 1) {
            ImageView mStartWeiboImageButton = (ImageView) arg0
                    .findViewById(R.id.btn_gouhom);
            mStartWeiboImageButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 设置已经引导
                    setGuided();
                    goHome();

                }

            });
        }

        return views.get(arg1);
    }
    private void goHome() {
        // 跳转
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void setGuided() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("share", 0);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", false);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.commit();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public Parcelable saveState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
        // TODO Auto-generated method stub

    }
}
