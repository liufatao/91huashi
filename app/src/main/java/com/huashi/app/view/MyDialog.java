package com.huashi.app.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.huashi.app.R;

/**
 * 自定义加载Dialog
 *
 */

public class MyDialog extends Dialog {
    private TextView txt_title;
    public MyDialog(Context context) {
        super(context,R.style.MyDialogStyle);
        init();
    }

    private void init(){
        setContentView(R.layout.dialog);
        txt_title= (TextView) findViewById(R.id.txt_title);
    }

    @Override
    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)){
            txt_title.setText("正在加载...");
        }else {
            txt_title.setText(title);
        }
    }
}
