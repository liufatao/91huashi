package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.huashi.app.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22.
 * 申请退款
 */

public class Refund_Activity extends Activity implements View.OnClickListener {
    private ImageView imgBack;
    private TextView txtRefundprice;
    private EditText edReason;
    private Button btnSubmit;
    private double allpric;
    private String orderid;
    private Utils utils;
    private String userId;
    private DecimalFormat to = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_refund);
        intoView();
    }

    //初始化
    private void intoView() {
        utils = new Utils(this);
        allpric = getIntent().getDoubleExtra("allpic", -1);
        orderid = getIntent().getStringExtra("orderId");
        userId = utils.getInfomation();
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        txtRefundprice = (TextView) findViewById(R.id.txt_refundprice);
        edReason = (EditText) findViewById(R.id.ed_reason);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        txtRefundprice.setText(String.format(getResources().getString(R.string.currentprice),to.format(allpric)));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:

                Refund();
                break;
        }
    }

    //申请退款
    private void Refund() {
        final String cancelReason = edReason.getText().toString();
        Log.e("Refund_activity", userId + "用户id" + orderid + "订单id" + cancelReason + "申请内容");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.REFUNREQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)){
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int state = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");
                        if (state == Constant.ONE) {
                            Intent intent = new Intent(Refund_Activity.this, Accept_Activity.class);
                            intent.putExtra("uderId", userId);
                            intent.putExtra("orderId", orderid);
                            startActivity(intent);
                            finish();
                        }
                        Toast.makeText(Refund_Activity.this, message, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(Refund_Activity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Refund_Activity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("orderId", orderid);
                map.put("cancelReason", cancelReason);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.activity_close, 0);
    }
}
