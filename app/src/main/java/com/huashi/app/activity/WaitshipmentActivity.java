package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.huashi.app.R;
import com.huashi.app.adapter.ToPayOrdersAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.ToPayOrders;
import com.huashi.app.util.Httputil;
import com.huashi.app.util.Utils;
import com.huashi.app.view.MyDialog;

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
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitshipments);
        intoView();

    }

    private void intoView() {
        utils = new Utils(this);
        userId = utils.getInfomation();
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        lvWaitshipment = (ListView) findViewById(R.id.lv_waitshipment);
        txt_orderhint = (TextView) findViewById(R.id.txt_orderhint);
        dialog=new MyDialog(this);
        dialog.setTitle(R.string.pull_to_refresh_footer_refreshing_label);
        if (Httputil.isNetworkAvailable(this) && !TextUtils.isEmpty(userId)) {
            getPayOrders();
        }
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
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYTOPAYORDERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!s.isEmpty()) {
                    toPayOrders = ExampleApplication.getInstance().getGson().fromJson(s, ToPayOrders.class);
                    if (toPayOrders.getOrderModels().size() >= 1) {
                        orderModelsBeanList = toPayOrders.getOrderModels();
                        payOrdersAdapter = new ToPayOrdersAdapter(WaitshipmentActivity.this, orderModelsBeanList);
                        payOrdersAdapter.setBtnone(WaitshipmentActivity.this);
                        payOrdersAdapter.setBtntwo(WaitshipmentActivity.this);
                        payOrdersAdapter.setBtnthree(WaitshipmentActivity.this);
                        lvWaitshipment.setAdapter(payOrdersAdapter);
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
                    Toast.makeText(WaitshipmentActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                    txt_orderhint.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
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
