package com.huashi.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
 * 全部订单
 */
public class AllOrderActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rayHand;
    private ImageView imgBack;
    private ListView lvAllorder;
    private ToPayOrders toPayOrders;
    private ToPayOrdersAdapter payOrdersAdapter;
    private Utils utils;
    private String userId;
    private double totalcount;
    private TextView txt_orderhint;
    private List<ToPayOrders.OrderModelsBean> orderModelsBeanList;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allorder);
        intoView();
        if (Httputil.isNetworkAvailable(this)) {
            if (!TextUtils.isEmpty(userId)){
                getPayOrders();
            }else {
                Toast.makeText(this,"请先登录",Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this,"网络不通畅",Toast.LENGTH_LONG).show();
        }
    }

    private void intoView() {

        utils = new Utils(this);
        userId = utils.getInfomation();
        rayHand = (RelativeLayout) findViewById(R.id.ray_hand);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        lvAllorder = (ListView) findViewById(R.id.lv_allorder);
        txt_orderhint= (TextView) findViewById(R.id.txt_orderhint);
        dialog=new ProgressDialog(this);
        dialog.setMessage("数据提交中");
        lvAllorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("全部订单","订单id"+orderModelsBeanList.get(position).getOrderId());

                Intent intent=new Intent(AllOrderActivity.this,OrderDetailsActivity.class);
                intent.putExtra("orderId",orderModelsBeanList.get(position).getOrderId()+"");
                intent.putExtra("totalcount",Double.valueOf(orderModelsBeanList.get(position).getTotalCount()+""));
                intent.putExtra("orderCode",orderModelsBeanList.get(position).getOrderCode());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            //查看物流
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
                            Toast.makeText(AllOrderActivity.this, "退款" + position, Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(AllOrderActivity.this,Refund_Activity.class);
                            intent.putExtra("allpic",Double.valueOf(orderModelsBeanList.get(position).getTotalCount()+""));
                            intent.putExtra("orderId",orderModelsBeanList.get(position).getOrderId()+"");
                            startActivity(intent);
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
                            //打电话
                            Intent intentphone = new Intent();
                            //系统默认的action，用来打开默认的电话界面
                            intentphone.setAction(Intent.ACTION_CALL);
                            //需要拨打的号码
                            intentphone.setData(Uri.parse("tel:" + "02389870155"));
                            startActivity(intentphone);
                            break;
                        case 8:
                            Toast.makeText(AllOrderActivity.this, "评价" + position, Toast.LENGTH_LONG).show();
                            Intent intentt=new Intent(AllOrderActivity.this,CommentFocusActivity.class);
                            intentt.putExtra("orderId",orderModelsBeanList.get(position).getOrderId()+"");
                            startActivity(intentt);
                            break;
                        case 9:
                            break;
                        case 10:
                            break;

                    }
                }
                break;
           // 删除
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
                            Intent intent=new Intent(AllOrderActivity.this,LogisticsDetailActivity.class);
                            intent.putExtra("orderId",orderModelsBeanList.get(position).getOrderId()+"");
                            startActivity(intent);
                            break;
                        case 9:
                            break;
                        case 10:
                            break;

                    }
                }
                break;
            //提交
            case R.id.btn_estimate:
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    switch (orderModelsBeanList.get(position).getStatus()) {
                        case 0:
                            totalcount=Double.valueOf(orderModelsBeanList.get(position).getTotalCount()+"");
                            Intent intent=new Intent(AllOrderActivity.this,PayActivity.class);
                            intent.putExtra("totalcount",totalcount);
                            intent.putExtra("orderId",orderModelsBeanList.get(position).getOrderId());
                            intent.putExtra("orderCode",orderModelsBeanList.get(position).getOrderCode());
                            startActivity(intent);
                            break;
                        case 1:
                            Intent intents=new Intent(AllOrderActivity.this,PayActivity.class);
                            intents.putExtra("totalcount",Double.valueOf(orderModelsBeanList.get(position).getTotalCount()+""));
                            intents.putExtra("orderId",orderModelsBeanList.get(position).getOrderId());
                            intents.putExtra("orderCode",orderModelsBeanList.get(position).getOrderCode());
                            startActivity(intents);
                            break;
                        case 2:

                            break;
                        case 3:
                            Intent intentst=new Intent(AllOrderActivity.this,LogisticsDetailActivity.class);
                            intentst.putExtra("orderId",orderModelsBeanList.get(position).getOrderId()+"");
                            startActivity(intentst);
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            //继续退款
                            Intent tintent=new Intent(AllOrderActivity.this,Refund_Activity.class);
                            tintent.putExtra("allpic",Double.valueOf(orderModelsBeanList.get(position).getTotalCount()+""));
                            tintent.putExtra("orderId",orderModelsBeanList.get(position).getOrderId()+"");
                            startActivity(tintent);
                            break;
                        case 8:
                            //再次购买
                            Toast.makeText(this,"再次购买",Toast.LENGTH_LONG).show();
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
                if (!s.isEmpty()) {
                    toPayOrders = ExampleApplication.getInstance().getGson().fromJson(s, ToPayOrders.class);
                    if (!toPayOrders.getOrderModels().isEmpty() && toPayOrders.getStatus()==Constant.ONE) {
                        orderModelsBeanList = toPayOrders.getOrderModels();
                        payOrdersAdapter = new ToPayOrdersAdapter(AllOrderActivity.this, orderModelsBeanList);
                        payOrdersAdapter.setBtnone(AllOrderActivity.this);
                        payOrdersAdapter.setBtntwo(AllOrderActivity.this);
                        payOrdersAdapter.setBtnthree(AllOrderActivity.this);
                        lvAllorder.setAdapter(payOrdersAdapter);
                    }else {
                        Toast.makeText(AllOrderActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                        txt_orderhint.setVisibility(View.VISIBLE);
                    }

                }else {
                    Toast.makeText(AllOrderActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                    txt_orderhint.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                txt_orderhint.setVisibility(View.VISIBLE);
                Toast.makeText(AllOrderActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("status", 0+ "");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }


    //取消订单
    private void cancelOder(final int postion){
        dialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.CANCELORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("取消订单成功",s);
                Log.e("订单id",orderModelsBeanList.get(postion).getOrderId()+"");
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    int status=jsonObject.getInt("status");
                    String message=jsonObject.getString("message");
                    if (status== Constant.ONE){
                        getPayOrders();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(AllOrderActivity.this,message,Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("取消订单失败",volleyError.toString());
                dialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("userId",userId);
                map.put("orderId",orderModelsBeanList.get(postion).getOrderId()+"");
                map.put("cancelReason","");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }



    /***
     * 确认收货
     */
    private void confirmReceipt(final int postion){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.CONFIRMRECEIPT, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("确认收货成功",s);
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    int status=jsonObject.getInt("status");
                    String message=jsonObject.getString("message");
                    if (status== Constant.ONE){
                        getPayOrders();
                    }
                    Toast.makeText(AllOrderActivity.this,message,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("确认收货失败",volleyError.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("userId",userId);
                map.put("orderId",orderModelsBeanList.get(postion).getOrderId()+"");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }
}
