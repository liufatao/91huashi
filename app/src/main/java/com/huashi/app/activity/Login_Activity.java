package com.huashi.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.User;
import com.huashi.app.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/23.
 */
public class Login_Activity extends Activity implements OnClickListener {
    private ImageView img_back, img_qq, img_weibo, img_alipay;
    private TextView txt_registered, txt_over, txt_findpassword;
    private Button btn_login;
    private EditText edt_account, edt_password;
    private String APP_ID = Constant.TencentApp_ID;
    private String Scope = "all";
    private String TAG = "Login_Activity";
    private String nicknameString;
    private String openidString;
    private Utils utils;
    private User user;
    private String account;
    private String password;
    private ProgressDialog dialog;



    /**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
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
        dialog=new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("玩命加载中...");

    }



    public void intoResource() {
        dialog.show();
        account = edt_account.getText().toString();
        password = edt_password.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.LOGO_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("login", "请求成功" + s);
                user = ExampleApplication.getInstance().getGson().fromJson(s, User.class);
                Log.e("用户id",user.getStatus()+"");
                if (user.getStatus()==Constant.ONE) {
                    utils.clearData();
                    utils.saveUserinfo(user.getUser().getId()+"");
                    Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
                switch (user.getStatus()){
                    case Constant.ONE:
                        Toast.makeText(Login_Activity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;
                    case Constant.ABNORMAL:
                        Toast.makeText(Login_Activity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;
                    case Constant.TWO:
                        Toast.makeText(Login_Activity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;
                    case Constant.THREE:
                        Toast.makeText(Login_Activity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;
                    case Constant.FOUR:
                        Toast.makeText(Login_Activity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;
                    case Constant.FIVE:
                        Toast.makeText(Login_Activity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;
                    case Constant.SIX:
                        Toast.makeText(Login_Activity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;
                    case Constant.SEVEN:
                        Toast.makeText(Login_Activity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;


                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("login", "请求失败" + volleyError);
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
     *
     * @param v
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
             Intent findpwdintent=new Intent(this,FindPasswordActivity.class);
                startActivity(findpwdintent);
                break;
        }
    }

}
