package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.adapter.ToPayOrdersAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.ToPayOrders;
import com.huashi.app.util.Httputil;
import com.huashi.app.util.Utils;
import com.huashi.app.view.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/3.
 * 待付款
 */
public class WaitpaymentActivity extends Activity implements View.OnClickListener {
    private ImageView imgBack;
    private ListView lvWaitpayment;
    private ToPayOrders toPayOrders;
    private ToPayOrdersAdapter payOrdersAdapter;
    private Utils utils;
    private String userId;
    private double totalcount;
    private TextView txt_orderhint;
    private List<ToPayOrders.OrderModelsBean> orderModelsBeanList;
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitpayment);
        intoView();
        if (Httputil.isNetworkAvailable(this)) {
            getPayOrders();
        } else {
            Toast.makeText(this, "网络不通畅", Toast.LENGTH_LONG).show();
        }
    }

    private void intoView() {
        utils = new Utils(this);
        userId = utils.getInfomation();
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        lvWaitpayment = (ListView) findViewById(R.id.lv_waitpayment);
        dialog = new MyDialog(this);
        dialog.setTitle(R.string.pull_to_refresh_footer_refreshing_label);
        txt_orderhint = (TextView) findViewById(R.id.txt_orderhint);

    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_estimate:
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    switch (orderModelsBeanList.get(position).getStatus()) {
                        case 0:
                            totalcount = Double.valueOf(orderModelsBeanList.get(position).getTotalCount() + "");
                            Intent intent = new Intent(WaitpaymentActivity.this, PayActivity.class);
                            intent.putExtra("totalcount", totalcount);
                            intent.putExtra("orderId", orderModelsBeanList.get(position).getOrderId() + "");
                            Log.e("待支付订单", orderModelsBeanList.get(position).getOrderId() + "");
                            intent.putExtra("orderCode", orderModelsBeanList.get(position).getOrderCode());
                            startActivity(intent);
                            break;
                        case 1:
                            Intent intents = new Intent(WaitpaymentActivity.this, PayActivity.class);
                            intents.putExtra("totalcount", Double.valueOf(orderModelsBeanList.get(position).getTotalCount() + ""));
                            intents.putExtra("orderId", orderModelsBeanList.get(position).getOrderId() + "");
                            intents.putExtra("orderCode", orderModelsBeanList.get(position).getOrderCode());
                            startActivity(intents);
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            break;
                        case 9:
                            break;
                        case 10:
                            break;


                    }
                }
                break;
            case R.id.btn_logistics:

                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    switch (orderModelsBeanList.get(position).getStatus()) {
                        case 0:
                            cancelOder(position);
                            break;
                        case 1:
                            cancelOder(position);
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            break;
                        case 9:
                            break;
                        case 10:
                            break;

                    }
                }
                break;
            case R.id.btn_deleteorder:
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    switch (orderModelsBeanList.get(position).getStatus()) {
                        case 1:
                            break;
                        case 2:

                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            break;
                        case 9:
                            break;
                        case 10:
                            break;

                    }
                }
                break;
        }
    }

    private void getPayOrders() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYTOPAYORDERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)) {
                    toPayOrders = ExampleApplication.getInstance().getGson().fromJson(s, ToPayOrders.class);
                    if (toPayOrders.getOrderModels().size() >= 1) {
                        orderModelsBeanList = toPayOrders.getOrderModels();
                        payOrdersAdapter = new ToPayOrdersAdapter(WaitpaymentActivity.this, orderModelsBeanList);
                        payOrdersAdapter.setBtnone(WaitpaymentActivity.this);
                        payOrdersAdapter.setBtntwo(WaitpaymentActivity.this);
                        payOrdersAdapter.setBtnthree(WaitpaymentActivity.this);
                        lvWaitpayment.setAdapter(payOrdersAdapter);
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    } else {
                        txt_orderhint.setVisibility(View.VISIBLE);
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }

                } else {
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                    Toast.makeText(WaitpaymentActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                    txt_orderhint.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                Toast.makeText(WaitpaymentActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                txt_orderhint.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("status", 1 + "");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);


        lvWaitpayment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WaitpaymentActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderId", orderModelsBeanList.get(position).getOrderId() + "");
                intent.putExtra("totalcount", Double.valueOf(orderModelsBeanList.get(position).getTotalCount() + ""));
                intent.putExtra("orderCode", orderModelsBeanList.get(position).getOrderCode());
                startActivity(intent);
            }
        });
    }

    //取消订单
    private void cancelOder(final int postion) {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.CANCELORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("取消订单成功", s);
                Log.e("订单id", orderModelsBeanList.get(postion).getOrderId() + "");
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == Constant.ONE) {
                        getPayOrders();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(WaitpaymentActivity.this, message, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("取消订单失败", volleyError.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("orderId", orderModelsBeanList.get(postion).getOrderId() + "");
                map.put("cancelReason", "");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (userId != null) {
            getPayOrders();
        }
    }

}
