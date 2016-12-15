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
 * 待评论
 */
public class WaitCommentActivity extends Activity implements View.OnClickListener {
    private ImageView imgBack;
    private ListView lvWaitcomment;
    private ToPayOrders toPayOrders;
    private ToPayOrdersAdapter payOrdersAdapter;
    private Utils utils;
    private String userId;
    private TextView txt_orderhint;
    private List<ToPayOrders.OrderModelsBean> orderModelsBeanList;
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitcomment);
        intoView();
        if (Httputil.isNetworkAvailable(this)) {
            if (!TextUtils.isEmpty(userId)) {
                getPayOrders();
            } else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "网络不通畅", Toast.LENGTH_LONG).show();
        }
    }

    private void intoView() {
        utils = new Utils(this);
        userId = utils.getInfomation();
        dialog = new MyDialog(this);
        dialog.setTitle(R.string.pull_to_refresh_footer_refreshing_label);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        lvWaitcomment = (ListView) findViewById(R.id.lv_waitcomment);
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
                            break;

                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            Toast.makeText(WaitCommentActivity.this, "评价" + position, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(WaitCommentActivity.this, CommentFocusActivity.class);
                            intent.putExtra("orderId", orderModelsBeanList.get(position).getOrderId() + "");
                            startActivity(intent);
                            break;
                        case 9:
                            break;
                        case 10:
                            Toast.makeText(WaitCommentActivity.this, "评价" + position, Toast.LENGTH_LONG).show();
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

                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            Intent intent = new Intent(WaitCommentActivity.this, LogisticsDetailActivity.class);
                            intent.putExtra("orderId", orderModelsBeanList.get(position).getOrderId() + "");
                            startActivity(intent);
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
                            break;

                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            Toast.makeText(WaitCommentActivity.this, "再次购买" + position, Toast.LENGTH_LONG).show();
                            break;
                        case 9:
                            break;
                        case 10:
                            Toast.makeText(WaitCommentActivity.this, "再次购买" + position, Toast.LENGTH_LONG).show();
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
                        payOrdersAdapter = new ToPayOrdersAdapter(WaitCommentActivity.this, orderModelsBeanList);
                        payOrdersAdapter.setBtnone(WaitCommentActivity.this);
                        payOrdersAdapter.setBtntwo(WaitCommentActivity.this);
                        payOrdersAdapter.setBtnthree(WaitCommentActivity.this);
                        lvWaitcomment.setAdapter(payOrdersAdapter);
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
                    Toast.makeText(WaitCommentActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                    txt_orderhint.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                Toast.makeText(WaitCommentActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                txt_orderhint.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("status", 5 + "");
                return map;
            }
        };
        lvWaitcomment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WaitCommentActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderId", orderModelsBeanList.get(position).getOrderId() + "");
                startActivity(intent);
            }
        });
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getPayOrders();
    }

}
