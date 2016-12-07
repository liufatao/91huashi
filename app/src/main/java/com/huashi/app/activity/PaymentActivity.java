package com.huashi.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import com.huashi.app.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/15.
 */
public class PaymentActivity extends Activity implements View.OnClickListener {

    private RelativeLayout ryTop;
    private ImageView imgBack;
    private RelativeLayout ryPayment;
    private TextView txtPayment;
    private RadioButton rbPay;
    private RadioButton rbPayfor;
    private TextView txtIndistribution;
    private RadioButton rbFastdelivery;
    private RadioButton rbHomedelivery;
    private RadioGroup rgPay;
    private RadioGroup rgLogistics;
    private Button btn_ok;
    private int costType=-1;//付款方式
    private int orderType=-1;//配送方式
    private String userId;
    private Utils utils;
    private String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        intoView();
    }
    private void  intoView(){
        utils=new Utils(this);
        userId=utils.getInfomation();
        orderId=getIntent().getStringExtra("orderId");
        costType=getIntent().getIntExtra("costType",-1);
        orderType=getIntent().getIntExtra("orderType",-1);
        Log.e("刚传",costType+"支付"+orderType+"配送");
        ryTop = (RelativeLayout) findViewById(R.id.ry_top);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        ryPayment = (RelativeLayout) findViewById(R.id.ry_payment);
        txtPayment = (TextView) findViewById(R.id.txt_payment);
        rbPay = (RadioButton) findViewById(R.id.rb_pay);
        rbPayfor = (RadioButton) findViewById(R.id.rb_payfor);
        txtIndistribution = (TextView) findViewById(R.id.txt_indistribution);
        rbFastdelivery = (RadioButton) findViewById(R.id.rb_fastdelivery);
        rbHomedelivery = (RadioButton) findViewById(R.id.rb_homedelivery);
        btn_ok= (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        rgPay = (RadioGroup) findViewById(R.id.rg_pay);
        rgLogistics = (RadioGroup) findViewById(R.id.rg_logistics);
        rgPay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==rbPay.getId()){
                    //选择在线支付
                    costType=1;
                }
                if (checkedId==rbPayfor.getId()){
                    //选择货到付款
                    costType=2;
                }
            }
        });

        //物流配送
        rgLogistics.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==rbHomedelivery.getId()){
                    //选择送货上门
                    orderType=2;
                }
                if (checkedId==rbFastdelivery.getId()){
                    //快速配送
                    orderType=1;
                }
            }
        });

        if (orderType==Constant.ONE){
            orderType=1;
            rbFastdelivery.setChecked(true);
        }else {
            orderType=2;
            rbHomedelivery.setChecked(true);
        }
        if(costType==Constant.ONE){
            costType=1;
            rbPay.setChecked(true);
        }else {
            costType=2;
            rbPayfor.setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_pay:
                //在线支付
                break;
            case R.id.rb_payfor:
                //货到付款
                break;
            case R.id.rb_fastdelivery:
                //快速配送
                break;
            case R.id.rb_homedelivery:
                //送货上门
                break;
            case R.id.btn_ok:
                submitDistribution();
                break;
        }
    }
    //上传配送方式
    private void submitDistribution(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.UPDATECOSTTYPE, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("修改配送方式成功", s);
                if (s != null && s != " ") {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");
                        if (status == Constant.ONE) {
                            finish();
                        }
                        Toast.makeText(PaymentActivity.this, message, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(PaymentActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("修改配送方式失败",volleyError.toString());
                Toast.makeText(PaymentActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("costType", String.valueOf(costType));
                map.put("orderType",String.valueOf(orderType));
                map.put("userId",userId);
                map.put("id",orderId);
                Log.e("配送参数",costType+"支付方式"+orderType+"配送方式");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }
}
