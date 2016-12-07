package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/26.
 */
public class FindPasswordActivity extends Activity implements View.OnClickListener{
    private ImageView mImgBack;
    private EditText mEdtPhnoenumber;
    private ImageView mImgMessage;
    private TextView mTxtCode;
    private EditText mEdtCode;
    private Button mBtnSubmit;
    private int time = 60;
    private boolean isSend = true;
    private String phonenumber;
    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpwd);
        intoView();
    }
    private void intoView(){

        mImgBack = (ImageView) findViewById(R.id.img_back);
        mImgBack.setOnClickListener(this);
        mEdtPhnoenumber = (EditText) findViewById(R.id.edt_phnoenumber);
        mTxtCode = (TextView) findViewById(R.id.txt_code);
        mTxtCode.setOnClickListener(this);
        mEdtCode = (EditText) findViewById(R.id.edt_code);
        mBtnSubmit= (Button) findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
       case  R.id.img_back:
           finish();
            break;
            case R.id.txt_code:
                //获取验证码
                if (isSend) {
                    getVerifyCodeAction();
                }

                break;
            case R.id.btn_submit:
                //提交数据
                mSubmit();
                break;
        }
    }


    /**
     * 获得手机验证码
     */
    private void getVerifyCodeAction() {
        phonenumber =mEdtPhnoenumber .getText().toString();

        if (StringUtils.isNullOrEmpty(phonenumber)) {
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
                                Toast.makeText(FindPasswordActivity.this, R.string.strcodewin, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.ABNORMAL:
                                Toast.makeText(FindPasswordActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.TWO:
                                Toast.makeText(FindPasswordActivity.this, R.string.strparametersabnormal, Toast.LENGTH_LONG).show();
                                break;
                            case  Constant.THREE:
                                Toast.makeText(FindPasswordActivity.this, R.string.strnull, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.FOUR:
                                Toast.makeText(FindPasswordActivity.this, R.string.strfomatwrong, Toast.LENGTH_LONG).show();
                                break;
                            case Constant.FIVE:
                                Toast.makeText(FindPasswordActivity.this, R.string.strfailure, Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(FindPasswordActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
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
                map.put("phone", phonenumber);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(request);


    }

    /**
     * 提交数据
     */
    private void mSubmit(){
        code=mEdtCode.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.FINDPASSWORDCHECK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
            Log.e("找回密码","成功了"+s);
                try {
                    JSONObject jsonObject=new JSONObject(s);
                   int status=jsonObject.getInt("status");
                   String message=jsonObject.getString("message");
                    switch (status){
                        case Constant.ONE:
                            Toast.makeText(FindPasswordActivity.this,message,Toast.LENGTH_LONG).show();
                            Intent findpwdintern=new Intent(FindPasswordActivity.this,FindPawdEndActivity.class);
                            findpwdintern.putExtra("phone",phonenumber);
                            startActivity(findpwdintern);
                            break;
                        case Constant.ABNORMAL:
                            Toast.makeText(FindPasswordActivity.this,message,Toast.LENGTH_LONG).show();
                            break;
                        case Constant.TWO:
                            Toast.makeText(FindPasswordActivity.this,message,Toast.LENGTH_LONG).show();
                            break;
                        case Constant.THREE:
                            Toast.makeText(FindPasswordActivity.this,message,Toast.LENGTH_LONG).show();
                            break;
                        case Constant.FOUR:
                            Toast.makeText(FindPasswordActivity.this,message,Toast.LENGTH_LONG).show();
                            break;
                        case Constant.FIVE:
                            Toast.makeText(FindPasswordActivity.this,message,Toast.LENGTH_LONG).show();
                            break;
                        case Constant.SIX:
                            Toast.makeText(FindPasswordActivity.this,message,Toast.LENGTH_LONG).show();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("找回密码","失败"+volleyError);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", phonenumber);
                map.put("verifyCode",code);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
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
            mTxtCode.setText(time + "秒后重发");
            handler.postDelayed(runnable, 1000);
            if (time <= 0) {
                handler.removeCallbacks(runnable);
                mTxtCode.setText(R.string.strregain);
                isSend = true;
                time = 60;
            }
        }
    };
}
