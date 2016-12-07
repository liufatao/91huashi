package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.huashi.app.R;
import com.huashi.app.adapter.ToPayOrdersAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.ToPayOrders;
import com.huashi.app.util.Httputil;
import com.huashi.app.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/3.
 * 待发货
 */
public class WaitshipmentActivity extends Activity implements View.OnClickListener {
    private ImageView imgBack;
    private ListView lvWaitshipment;
    private ToPayOrders toPayOrders;
    private ToPayOrdersAdapter payOrdersAdapter;
    private TextView txt_orderhint;
    private Utils utils;
    private String userId;
    private List<ToPayOrders.OrderModelsBean> orderModelsBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitshipments);
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
        lvWaitshipment = (ListView) findViewById(R.id.lv_waitshipment);
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
//                            Toast.makeText(WaitshipmentActivity.this, "没有奸情" + position, Toast.LENGTH_LONG).show();
                            break;
                        case 2:
//                            Toast.makeText(WaitshipmentActivity.this, "退款" + position, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(WaitshipmentActivity.this, Refund_Activity.class);
                            intent.putExtra("allpic", Double.valueOf(orderModelsBeanList.get(position).getTotalCount() + ""));
                            intent.putExtra("orderId", orderModelsBeanList.get(position).getOrderId() + "");
                            startActivity(intent);
                            break;
                        case 3:
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
                Log.e("待发货请求成功", s);
                if (!s.isEmpty()) {
                    toPayOrders = ExampleApplication.getInstance().getGson().fromJson(s, ToPayOrders.class);
                    Log.e("集合大小",toPayOrders.getOrderModels().size()+"");

                    if (toPayOrders.getOrderModels().size() >= 1) {
                        orderModelsBeanList = toPayOrders.getOrderModels();
                        payOrdersAdapter = new ToPayOrdersAdapter(WaitshipmentActivity.this, orderModelsBeanList);
                        payOrdersAdapter.setBtnone(WaitshipmentActivity.this);
                        payOrdersAdapter.setBtntwo(WaitshipmentActivity.this);
                        payOrdersAdapter.setBtnthree(WaitshipmentActivity.this);
                        lvWaitshipment.setAdapter(payOrdersAdapter);
                    } else {
                        txt_orderhint.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(WaitshipmentActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                    txt_orderhint.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("待付款请求失败", volleyError.toString());
                txt_orderhint.setVisibility(View.VISIBLE);
                Toast.makeText(WaitshipmentActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("status", 2 + "");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);

        lvWaitshipment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WaitshipmentActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderId", orderModelsBeanList.get(position).getOrderId() + "");
                startActivity(intent);
            }
        });
    }

}
