package com.huashi.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.huashi.app.model.RefundModel;
import com.huashi.app.util.Httputil;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Accept_Activity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView txtTime;
    private TextView txtReasons;
    private SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ", Locale.getDefault());
    private String userId;
    private String orderId;
    private RefundModel refundModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);
        intoView();
        getaccept();
        if (Httputil.isNetworkAvailable(this)) {
            getaccept();
        }else {
            Toast.makeText(this,"网络不通畅",Toast.LENGTH_LONG).show();
        }
    }
    private void intoView(){
        userId=getIntent().getStringExtra("uderId");
        orderId=getIntent().getStringExtra("orderId");
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtTime = (TextView) findViewById(R.id.txt_time);
        txtReasons = (TextView) findViewById(R.id.txt_reasons);


    }

    private void getaccept(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.GETREFUNDINFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                refundModel=ExampleApplication.getInstance().getGson().fromJson(s,RefundModel.class);
                txtReasons.setText(String.format(getResources().getString(R.string.refundreason),refundModel.getOrderModel().getCancel_reason()));
                txtTime.setText(String.format(getResources().getString(R.string.refundreason),formatter.format(refundModel.getOrderModel().getRefund_time())));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("退款状态请求成功",volleyError.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("id",orderId);
                map.put("userId",userId);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

}
