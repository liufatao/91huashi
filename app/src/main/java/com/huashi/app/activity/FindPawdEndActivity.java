package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/26.
 * 重置密码
 */
public class FindPawdEndActivity extends Activity implements View.OnClickListener {
    private ImageView mImgBack;
    private EditText mEdtPassword;
    private EditText mEdtConfirm;
    private Button mBtnLogin;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpwdend);
        intoView();

    }

    private void intoView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mImgBack.setOnClickListener(this);
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
        mEdtConfirm = (EditText) findViewById(R.id.edt_confirm);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        phone = getIntent().getStringExtra("phone");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_login:
                mSubmit();

                break;
        }
    }

    private void mSubmit() {
        final String newPassword = mEdtPassword.getText().toString();
        final String oldPassword = mEdtConfirm.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.FINDPASSWORD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("修改密码", "请求成功" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String message = jsonObject.getString("message");
                    int status = jsonObject.getInt("status");
                    switch (status) {
                        case Constant.ONE:
                            Toast.makeText(FindPawdEndActivity.this, message, Toast.LENGTH_LONG).show();
                            Intent logointent = new Intent(FindPawdEndActivity.this, Login_Activity.class);
                            startActivity(logointent);
                            break;
                        case Constant.ABNORMAL:
                            Toast.makeText(FindPawdEndActivity.this, message, Toast.LENGTH_LONG).show();
                            break;
                        case Constant.TWO:
                            Toast.makeText(FindPawdEndActivity.this, message, Toast.LENGTH_LONG).show();
                            break;
                        case Constant.THREE:
                            Toast.makeText(FindPawdEndActivity.this, message, Toast.LENGTH_LONG).show();
                            break;
                        case Constant.FOUR:
                            Toast.makeText(FindPawdEndActivity.this, message, Toast.LENGTH_LONG).show();
                            break;
                        case Constant.FIVE:
                            Toast.makeText(FindPawdEndActivity.this, message, Toast.LENGTH_LONG).show();
                            break;
                        case Constant.SIX:
                            Toast.makeText(FindPawdEndActivity.this, message, Toast.LENGTH_LONG).show();
                            break;
                        case Constant.SEVEN:
                            Toast.makeText(FindPawdEndActivity.this, message, Toast.LENGTH_LONG).show();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("修改密码", "失败" + volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("newPassword", newPassword);
                map.put("reNewPassword", oldPassword);
                map.put("phone", phone);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }


}
