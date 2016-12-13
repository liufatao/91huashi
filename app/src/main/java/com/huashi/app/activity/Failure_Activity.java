package com.huashi.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huashi.app.R;

/**
 * Created by Administrator on 2016/6/1.
 * 支付失败
 */
public class Failure_Activity extends Activity {
    private RelativeLayout mRayTop;
    private ImageView mImgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);
        initView();
    }

    private void initView() {
        mRayTop = (RelativeLayout) findViewById(R.id.ray_top);
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
