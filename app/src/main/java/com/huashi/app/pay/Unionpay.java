package com.huashi.app.pay;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.R;
import com.huashi.app.application.ExampleApplication;
import com.unionpay.UPPayAssistEx;

/**
 * Created by Administrator on 2016/12/14 0014.
 * 银联支付类
 */

public class Unionpay {
    private String tn;
    private final String serverMode = "01";
    private Activity activity;
   public Unionpay(Activity activity){
       this.activity=activity;
   }
    /***
     * 银联支付
     */
    public void Unionpay() {
        StringRequest StringRequest = new StringRequest(Request.Method.POST, "http://101.231.204.84:8091/sim/getacptn", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)) {
                    tn = s;
                    UPPayAssistEx.startPay(activity, null, null, tn, serverMode);
                } else {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(activity, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        });
        ExampleApplication.getInstance().getRequestQueue().add(StringRequest);
    }
}
