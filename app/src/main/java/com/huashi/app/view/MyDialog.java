package com.huashi.app.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.huashi.app.R;

/**
 * 自定义加载Dialog
 *
 */

public class MyDialog extends Dialog {
    private ImageView mImageView;
    private TextView mLoadingTv;
    private AnimationDrawable mAnimation;
    public MyDialog(Context context) {
        super(context,R.style.MyDialog);
        init();
        initData();
    }

    private void init(){
        setContentView(R.layout.progress_dialog);
        mLoadingTv = (TextView) findViewById(R.id.loadingTv);
        mImageView = (ImageView) findViewById(R.id.loadingIv);

    }
    private void initData() {

        mImageView.setBackgroundResource(R.drawable.frame);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) mImageView.getBackground();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();

            }
        });
    }



    @Override
    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)){
            mLoadingTv.setText(R.string.pull_to_refresh_footer_refreshing_label);
        }else {
            mLoadingTv.setText(title);
        }
    }
}
