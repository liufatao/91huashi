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
import com.huashi.app.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/23.
 * 修改昵称页
 */
public class NicknameActivity extends Activity implements View.OnClickListener {
    private ImageView img_back;
    private TextView txt_over;
    private EditText edt_nickname;
    private String nickname;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        intoView();
    }

    private void intoView() {
        utils = new Utils(this);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txt_over = (TextView) findViewById(R.id.txt_over);
        txt_over.setOnClickListener(this);
        edt_nickname = (EditText) findViewById(R.id.edt_nickname);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                //返回
                finish();
                break;
            case R.id.txt_over:
                submitNickname();
                break;
        }
    }

    private void submitNickname() {
        nickname = edt_nickname.getText().toString().trim();
        if (nickname.equals("")) {
            Toast.makeText(NicknameActivity.this, "昵称不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.UPDATENICKNAME, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("昵称修改", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String message = jsonObject.getString("message");
                    Toast.makeText(NicknameActivity.this, message, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", utils.getInfomation());
                map.put("nickname", nickname);
                return map;
            }
        };

        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

}
