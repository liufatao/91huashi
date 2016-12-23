package com.huashi.app.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huashi.app.R;

/**
 * Created by Administrator on 206/12/20 0020.
 */

public class UnusualDialog extends Dialog implements View.OnClickListener {
    private TextView txt_unusual;
    private Button btn_load;
    private UnusualDialogListener dialogListener;
    public UnusualDialog(Context context, UnusualDialogListener unusualDialogListener) {
        super(context,R.style.MyDialogStyle);
        this.dialogListener=unusualDialogListener;
        into();
    }

    public UnusualDialog(Context context, int themeResId,UnusualDialogListener unusualDialogListener) {
        super(context,themeResId);
        into();
        this.dialogListener=unusualDialogListener;
    }


    public interface UnusualDialogListener{
        void onClick(View view);
    }
    private void into(){
        setContentView(R.layout.unusual_window);
        btn_load= (Button) findViewById(R.id.btn_load);
        txt_unusual= (TextView) findViewById(R.id.txt_unusual);
        btn_load.setOnClickListener(this);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (!TextUtils.isEmpty(title)){
            txt_unusual.setText(title);
        }
    }
    public void setBtn_loadTitle(String title){
        if (!TextUtils.isEmpty(title)){
            btn_load.setText(title);
        }
    }

    @Override
    public void onClick(View v) {
      dialogListener.onClick(v);
    }
}
