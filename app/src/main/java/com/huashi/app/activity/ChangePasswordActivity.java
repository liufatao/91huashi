package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.huashi.app.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/23.
 * 修改密码页
 */
public class ChangePasswordActivity extends Activity implements View.OnClickListener {
    private ImageView img_back;
    private TextView txt_over;
    private EditText edt_nowpassword, edt_newpassword, edt_verifypassword;
    private String userId;
    private String oldPassword, newPassrord, verifypassword;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        userId = getIntent().getStringExtra("userId");
        intoView();
    }

    private void intoView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txt_over = (TextView) findViewById(R.id.txt_over);
        txt_over.setOnClickListener(this);
        edt_newpassword = (EditText) findViewById(R.id.edt_newpassword);
        edt_nowpassword = (EditText) findViewById(R.id.edt_nowpassword);
        edt_verifypassword = (EditText) findViewById(R.id.edt_verifypassword);
        utils = new Utils(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                //返回
                finish();
                break;
            case R.id.txt_over:
                //提交
                mSbmit();
                break;
        }
    }

    public void mSbmit() {
        newPassrord = edt_newpassword.getText().toString();
        oldPassword = edt_nowpassword.getText().toString();
        verifypassword = edt_verifypassword.getText().toString();
        if (!newPassrord.isEmpty() && !oldPassword.isEmpty() && !verifypassword.isEmpty()) {
            Toast.makeText(ChangePasswordActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.UPDDTEPASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("改密码", "post请求成功" + s);
                if (!s.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String message = jsonObject.getString("message");
                        int status = jsonObject.getInt("status");
                        if (status == Constant.ONE) {
                            utils.clearData();
                            Intent loginintent = new Intent(ChangePasswordActivity.this, Login_Activity.class);
                            startActivity(loginintent);
                        }
                        Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("改密码", "post请求失败" + volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("currentPassword", oldPassword);
                map.put("firstPassword", newPassrord);
                map.put("secondPassword", verifypassword);
                map.put("userId", userId);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

}
