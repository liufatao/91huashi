package com.huashi.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/12/15 0015.
 * 自定义Scrollview用于隐藏搜索栏
 * author liufatao
 */

public class MyScrollview extends ScrollView {
    private ScrollViewListener scrollViewListener = null;
    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     * @param l 当前x坐标
     * @param t 当前y坐标
     * @param oldl 前一步x坐标
     * @param oldt 前一步y坐标
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
       super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener!=null){
            scrollViewListener.onScrollChanged(this,l,t,oldl,oldt);
        }
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    //定义接口
    public interface ScrollViewListener{
        void onScrollChanged(View scrollView, int x, int y, int oldx, int oldy);
    }

    public void setScrollViewListener(ScrollViewListener listener)
    {
        scrollViewListener=listener;
    }
}
