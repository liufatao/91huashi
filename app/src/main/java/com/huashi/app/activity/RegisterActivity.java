package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.util.StringUtils;
import com.huashi.app.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/24.
 */
public class RegisterActivity extends Activity implements OnClickListener {
    private ImageView img_back;
    private EditText edt_account, edt_code, edt_password, edt_confirm;
    private Button btn_register;
    private TextView txt_code;
    private int time = 60;
    private boolean isSend = true;
    private String userId;
    private Utils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        intoView();
    }

    private void intoView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        edt_account = (EditText) findViewById(R.id.edt_account);
        edt_code = (EditText) findViewById(R.id.edt_code);
        edt_confirm = (EditText) findViewById(R.id.edt_confirm);
        txt_code = (TextView) findViewById(R.id.txt_code);
        txt_code.setOnClickListener(this);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_confirm = (EditText) findViewById(R.id.edt_confirm);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        utils=new Utils(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                //返回
                finish();
                break;
            case R.id.txt_code:
                if (isSend) {
                    getVerifyCodeAction();
                }

                break;
            case R.id.btn_register:
                registerSubmit();
                break;

        }

    }

    /**
     * 提交注册资料
     */
    private void registerSubmit() {
        final String phone = edt_account.getText().toString();
        final String code = edt_code.getText().toString();
        final String pwd = edt_password.getText().toString();
        final String pwd1 = edt_confirm.getText().toString();
        if (!StringUtils.isNullOrEmpty(phone)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (!StringUtils.isPhoneNo(phone)){
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_LONG).show();
            return;
        }
        if (StringUtils.isNullOrEmpty(pwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (pwd == pwd1) {
            Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_LONG).show();

            return;
        }
        if (!StringUtils.isPhoneNo(phone)) {
            Toast.makeText(this, "电话号码不正确", Toast.LENGTH_LONG).show();
            return;
        }
        StringRequest request = new StringRequest(Request.Method.POST, RequestUrlsConfig.REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //返回值
                Log.e("RegisterActivity提交", "post请求成功" + s);

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(s);
                        int  message = jsonObject.getInt("status");
                        String ms = jsonObject.getString("message");

                        Log.e("提交",ms+jsonObject.getString("status")+message);
                        switch (message){
                            case Constant.ONE:
                                Toast.makeText(RegisterActivity.this, R.string.strregisteredsuccessfully, Toast.LENGTH_LONG).show();
                                userId=jsonObject.getString("userId");
                                utils.clearData();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                utils.saveUserinfo(userId);
                                finish();
                                Log.e("用户id",jsonObject.getString("userId"));
                                break;
                            case Constant.ABNORMAL:
                                Toast.makeText(RegisterActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.TWO:
                                Toast.makeText(RegisterActivity.this, R.string.strparametersabnormal, Toast.LENGTH_LONG).show();
                                break;
                            case  Constant.THREE:
                                Toast.makeText(RegisterActivity.this, R.string.strnull, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.FOUR:
                                Toast.makeText(RegisterActivity.this, R.string.strfomatwrong, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.FIVE:
                                Toast.makeText(RegisterActivity.this, R.string.strcodenull, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.SIX:
                                Toast.makeText(RegisterActivity.this, R.string.strpwdlenght, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.SEVEN:
                                Toast.makeText(RegisterActivity.this,  R.string.strpwdlenghts, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.EIGHT:
                                Toast.makeText(RegisterActivity.this, R.string.strpwdnotmatch, Toast.LENGTH_LONG).show();
                                edt_password.setText("");
                                edt_confirm.setText("");
                                edt_password.setFocusable(true);
                                edt_password.setFocusableInTouchMode(true);
                                edt_password.requestFocus();
                                break;
                            case Constant.NINE:
                                Toast.makeText(RegisterActivity.this, R.string.strrepeatedregistration, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.TEN:
                                Toast.makeText(RegisterActivity.this, R.string.strfailure, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.ELEVEN:
                                Toast.makeText(RegisterActivity.this, R.string.strregistrationfailed, Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(RegisterActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //请求失败
                Log.e("RegisterActivity", "post请求失败" + volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("verifyCode", code);
                map.put("firstPassword", pwd);
                map.put("secondPassword", pwd1);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(request);


    }



    /**
     * 获得手机验证码
     */
    private void getVerifyCodeAction() {
        final String phone = edt_account.getText().toString();
        if (StringUtils.isNullOrEmpty(phone)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
            return;
        }


        handler.removeCallbacks(runnable);
        Thread thread = new Thread(runnable);
        thread.start();
        isSend = false;
        //上传电话号码

        StringRequest request = new StringRequest(Request.Method.POST, RequestUrlsConfig.VERIFYCODE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //返回值
                Log.e("RegisterActivity", "post请求成功" + s);
                if (s != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int  message = jsonObject.getInt("status");

                        Log.e("status",message+"");
                        switch (message){
                            case Constant.ONE:
                                Toast.makeText(RegisterActivity.this, R.string.strcodewin, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.ABNORMAL:
                                Toast.makeText(RegisterActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.TWO:
                                Toast.makeText(RegisterActivity.this, R.string.strparametersabnormal, Toast.LENGTH_LONG).show();
                                break;
                            case  Constant.THREE:
                                Toast.makeText(RegisterActivity.this, R.string.strnull, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.FOUR:
                                Toast.makeText(RegisterActivity.this, R.string.strfomatwrong, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.FIVE:
                                Toast.makeText(RegisterActivity.this, R.string.strfailure, Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(RegisterActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                                break;
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //请求失败
                Log.e("RegisterActivity", "post请求失败" + volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(request);


    }

    /**
     * 获取验证码等候时间
     */
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            Message messagetime = new Message();

            time--;
            messagetime.arg1 = time;
            handler.sendMessage(messagetime);
        }
    };
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            txt_code.setText(time + "秒后重发");
            handler.postDelayed(runnable, 1000);
            if (time <= 0) {
                handler.removeCallbacks(runnable);
                txt_code.setText(R.string.strregain);
                isSend = true;
                time = 60;
            }
        }

        ;
    };
}
