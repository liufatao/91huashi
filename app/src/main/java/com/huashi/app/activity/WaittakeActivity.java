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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/3.
 * 待收货
 */
public class WaittakeActivity extends Activity implements View.OnClickListener {
    private ImageView imgBack;
    private ListView lvWaittake;
    private ToPayOrders toPayOrders;
    private ToPayOrdersAdapter payOrdersAdapter;
    private Utils utils;
    private String userId;
    private TextView txt_orderhint;
    private List<ToPayOrders.OrderModelsBean> orderModelsBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waittake);
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
        lvWaittake = (ListView) findViewById(R.id.lv_waittake);
        txt_orderhint = (TextView) findViewById(R.id.txt_orderhint);

    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_logistics:
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    switch (orderModelsBeanList.get(position).getStatus()) {
                        case 1:
                            break;
                        case 2:

                            break;
                        case 3:
                            confirmReceipt(position);
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

            case R.id.btn_estimate:
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    switch (orderModelsBeanList.get(position).getStatus()) {
                        case 1:
                            break;
                        case 2:

                            break;
                        case 3:
                            Intent intent = new Intent(WaittakeActivity.this, LogisticsDetailActivity.class);
                            intent.putExtra("orderId", orderModelsBeanList.get(position).getOrderId() + "");
                            startActivity(intent);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYTOPAYORDERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("待付款请求成功", s);
                if (!TextUtils.isEmpty(s)) {
                    toPayOrders = ExampleApplication.getInstance().getGson().fromJson(s, ToPayOrders.class);
                    if (toPayOrders.getOrderModels().size() >= 1) {
                        orderModelsBeanList = toPayOrders.getOrderModels();
                        payOrdersAdapter = new ToPayOrdersAdapter(WaittakeActivity.this, orderModelsBeanList);
                        payOrdersAdapter.setBtnone(WaittakeActivity.this);
                        payOrdersAdapter.setBtntwo(WaittakeActivity.this);
                        payOrdersAdapter.setBtnthree(WaittakeActivity.this);
                        lvWaittake.setAdapter(payOrdersAdapter);
                    } else {
                        txt_orderhint.setVisibility(View.VISIBLE);

                    }


                } else {
                    Toast.makeText(WaittakeActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                    txt_orderhint.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("待付款请求失败", volleyError.toString());
                txt_orderhint.setVisibility(View.VISIBLE);
                Toast.makeText(WaittakeActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("status", 3 + "");
                return map;
            }
        };

        lvWaittake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WaittakeActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderId", orderModelsBeanList.get(position).getOrderId() + "");
                startActivity(intent);
            }
        });
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    /***
     * 确认收货
     */
    private void confirmReceipt(final int postion) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.CONFIRMRECEIPT, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");
                        if (status == Constant.ONE) {
                            getPayOrders();
                        }
                        Toast.makeText(WaittakeActivity.this, message, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("确认收货失败", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("orderId", orderModelsBeanList.get(postion).getOrderId() + "");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getPayOrders();
    }

}
