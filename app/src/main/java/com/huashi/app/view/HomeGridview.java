package com.huashi.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/5/27.
 */
public class HomeGridview extends GridView {

    public HomeGridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HomeGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public HomeGridview(Context context) {
        super(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec,expandSpec);


    }
}
