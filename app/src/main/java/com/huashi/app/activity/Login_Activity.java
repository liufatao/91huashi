package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.User;
import com.huashi.app.util.Utils;
import com.huashi.app.view.MyDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/23.
 * 登录页面
 */
public class Login_Activity extends Activity implements OnClickListener {
    private ImageView img_back, img_qq, img_weibo, img_alipay;
    private TextView txt_registered, txt_over, txt_findpassword;
    private Button btn_login;
    private EditText edt_account, edt_password;
    private Utils utils;
    private User user;
    private String account;
    private String password;
    private MyDialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        utils = new Utils(this);
        intoView();

    }

    private void intoView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txt_over = (TextView) findViewById(R.id.txt_over);
        txt_registered = (TextView) findViewById(R.id.txt_registered);
        txt_registered.setOnClickListener(this);
        edt_account = (EditText) findViewById(R.id.edt_account);
        edt_password = (EditText) findViewById(R.id.edt_password);
        txt_findpassword = (TextView) findViewById(R.id.txt_findpassword);
        txt_findpassword.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        dialog = new MyDialog(this);


    }


    public void intoResource() {
        dialog.show();
        account = edt_account.getText().toString();
        password = edt_password.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.LOGO_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                user = ExampleApplication.getInstance().getGson().fromJson(s, User.class);
                if (user.getStatus() == Constant.ONE) {
                    utils.clearData();
                    utils.saveUserinfo(user.getUser().getId() + "");
                    Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
                if (user.getStatus()==Constant.ONE) {
                    Toast.makeText(Login_Activity.this, user.getMessage(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }else {
                    Toast.makeText(Login_Activity.this, user.getMessage(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("account", account);
                map.put("password", password);
                return map;
            }
        };
        //加入消息队列
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);


    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                //返回
                finish();
                break;
            case R.id.txt_registered:
                Intent intentregistered = new Intent(this, RegisterActivity.class);
                startActivity(intentregistered);
                break;
            case R.id.btn_login:
                intoResource();
                break;
            case R.id.txt_findpassword:
                Intent findpwdintent = new Intent(this, FindPasswordActivity.class);
                startActivity(findpwdintent);
                break;
        }
    }

}
