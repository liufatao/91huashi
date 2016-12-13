package com.huashi.app.activity;

import android.app.Activity;
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
import com.huashi.app.R;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/23.
 * 个性签名
 */
public class PersonalizedActivity extends Activity implements View.OnClickListener {
    private TextView txt_over;
    private ImageView img_back;
    private EditText edt_personalized;
    private String motto, updatamotto, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalized);
        motto = getIntent().getStringExtra("motto");
        userId = getIntent().getStringExtra("userId");
        intoView();
    }

    private void intoView() {
        txt_over = (TextView) findViewById(R.id.txt_over);
        txt_over.setOnClickListener(this);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        edt_personalized = (EditText) findViewById(R.id.edt_personzlized);
        if (motto != null) {
            edt_personalized.setText(motto);
        }
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
                mSubmit();

                break;
        }
    }

    private void mSubmit() {
        updatamotto = edt_personalized.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.UPDATEMOTTO, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("请求成功", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    Toast.makeText(PersonalizedActivity.this, message, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("请求失败", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                Log.e("个性签名", updatamotto);
                map.put("motto", updatamotto);
                map.put("userId", userId);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

}
