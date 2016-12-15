package com.huashi.app.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.huashi.app.model.PayInfoModel;
import com.huashi.app.pay.Alipay;
import com.huashi.app.pay.Unionpay;
import com.huashi.app.pay.WeiXinPay;
import com.huashi.app.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/31.
 * 支付界面
 */
public class PayActivity extends Activity implements View.OnClickListener{

    // 商户私钥，pkcs8格式
    private static String RSA_PRIVATE = null;
    private ImageView imgBack;
    private Button btnOk;
    private double totalcount;
    private TextView txtAllpic;
    private String orderId;
    private RadioButton rbPay;
    private RadioButton rbWeixinpay;
    private RadioButton rbUnionpay;
    private int isto = 0;
    private String userId;
    private Utils utils;
    private PayInfoModel payInfoModel;
    private String orderCode;
    private DecimalFormat to = new DecimalFormat("0.00");
    private final String serverMode = "01";
    private String API_KEY = null;
    private Alipay alipay;
    private WeiXinPay weiXinPay;
    private Unionpay unionpay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        totalcount = getIntent().getDoubleExtra("totalcount", -1);
        orderCode = getIntent().getStringExtra("orderCode");
        Log.e("订单code", orderCode);
        intoView();
        getInfo();
        getWeiXinInfo();
    }

    private void intoView() {
        utils = new Utils(this);
        weiXinPay=new WeiXinPay(this);
        unionpay=new Unionpay(this);
        alipay = new Alipay(this);
        userId = utils.getInfomation();
        orderId = getIntent().getStringExtra("orderId");
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        txtAllpic = (TextView) findViewById(R.id.txt_allpic);
        if (totalcount != -1) {
            txtAllpic.setText(String.format(getResources().getString(R.string.currentprice), to.format(totalcount)));
        }
        rbPay = (RadioButton) findViewById(R.id.rb_pay);
        rbUnionpay = (RadioButton) findViewById(R.id.rb_unionpay);
        rbPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isto = 0;
                }
            }
        });
        rbWeixinpay = (RadioButton) findViewById(R.id.rb_weixinpay);

        rbWeixinpay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isto = 1;
                }
            }
        });
        rbUnionpay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isto = 2;
                }
            }
        });


    }

    /**
     * 获得支付宝私钥
     */
    private void getInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.GETPRIVATEKEY, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)) {
                    payInfoModel = ExampleApplication.getInstance().getGson().fromJson(s, PayInfoModel.class);
                    if (payInfoModel != null) {
                        RSA_PRIVATE = payInfoModel.getPrivateKey();
                    }
                } else {
                    Toast.makeText(PayActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(PayActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", orderId);
                map.put("userId", userId);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    /**
     * 获得微信公钥
     */
    private void getWeiXinInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.WEIXINGETPRIVATEKEY, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        API_KEY = jsonObject.getString("privateKey");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(PayActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(PayActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", orderId);
                map.put("userId", userId);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_ok:
                switch (isto) {
                    case 0:
                        if (payInfoModel.getStatus() == Constant.ONE) {
                            alipay.aliPay(payInfoModel.getCommodity().getNameCN(), userId, payInfoModel.getCommodity().getModelName(), "0.01", orderCode,RSA_PRIVATE);
                        } else {
                            Toast.makeText(this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                        }

                        break;
                    case 1:
                        if (!TextUtils.isEmpty(API_KEY)){
                            weiXinPay.weixinPay(orderCode,totalcount,API_KEY);
                        }else {
                            Toast.makeText(this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                        }

                        break;
                    case 2:
                       unionpay.Unionpay();
                        break;
                }

                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 验签证书同后台验签证书
                    // 此处的verify，商户需送去商户后台做验签
                    boolean ret = verify(dataOrg, sign, serverMode);
                    if (ret) {
                        // 验证通过后，显示支付结果
                        msg = "支付成功！";
                    } else {
                        // 验证不通过后的处理
                        // 建议通过商户后台查询支付结果
                        msg = "支付失败！";
                    }
                } catch (JSONException e) {
                }
            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                msg = "支付成功！";
            }
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;

    }
}
