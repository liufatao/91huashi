package com.huashi.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.adapter.OrderDetailAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.OrderStateModel;
import com.huashi.app.util.Httputil;
import com.huashi.app.util.Utils;
import com.huashi.app.view.MyListview;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/31.
 * 订单详情
 */
public class OrderDetailsActivity extends Activity implements View.OnClickListener {
    private OrderStateModel orderStateModel;
    private String userId;
    private Utils utils;
    private String orderId;
    private List<OrderStateModel.OrderBean.DetailsBean> modelList;
    private OrderDetailAdapter orderDetailAdapter;
    private ImageView imgBack;
    private TextView txtConsignee;
    private TextView txtPhone;
    private TextView txtAdder;
    private TextView txtDeliverytime;
    private TextView txtLogisticscompany;
    private TextView txtWaybillnumber;
    private TextView txtOrderstatus;
    private TextView txtOrderno;
    private TextView txtOrdertime;
    private MyListview lvOrderdetails;
    private TextView txtInvoice;
    private TextView txtAftersale;
    private TextView txtFreight;
    private TextView txtLeaveword;
    private TextView txt_count;
    private TextView txt_allpic;
    private SimpleDateFormat format;
    private String orderCode;
    private Double totalcount;
    private Button btnOne;
    private Button btnTwo;
    private DecimalFormat to = new DecimalFormat("0.00");
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        intoView();
        if (Httputil.isNetworkAvailable(this)) {
            querShopCartOrderinfo();
        }else {
            Toast.makeText(this,"网络不通畅",Toast.LENGTH_LONG).show();
        }
    }

    private void intoView(){
        utils = new Utils(this);
        userId = utils.getInfomation();
        orderId=getIntent().getStringExtra("orderId");
        orderCode=getIntent().getStringExtra("orderCode");
        totalcount=getIntent().getDoubleExtra("totalcount",-1);
        format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        txtConsignee = (TextView) findViewById(R.id.txt_consignee);
        txtPhone = (TextView) findViewById(R.id.txt_phone);
        txtAdder = (TextView) findViewById(R.id.txt_adder);
        txtDeliverytime = (TextView) findViewById(R.id.txt_deliverytime);
        txtLogisticscompany = (TextView) findViewById(R.id.txt_logisticscompany);
        txtWaybillnumber = (TextView) findViewById(R.id.txt_waybillnumber);
        txtOrderstatus = (TextView) findViewById(R.id.txt_orderstatus);
        txtOrderno = (TextView) findViewById(R.id.txt_orderno);
        txtOrdertime = (TextView) findViewById(R.id.txt_ordertime);
        lvOrderdetails = (MyListview) findViewById(R.id.lv_orderdetails);
        txtInvoice = (TextView) findViewById(R.id.txt_invoice);
        txtAftersale = (TextView) findViewById(R.id.txt_aftersale);
        txtFreight = (TextView) findViewById(R.id.txt_freight);
        txtLeaveword = (TextView) findViewById(R.id.txt_leaveword);
        txt_count= (TextView) findViewById(R.id.txt_count);
        txt_allpic = (TextView) findViewById(R.id.txt_allpic);
        btnOne = (Button) findViewById(R.id.btn_one);
        btnOne.setOnClickListener(this);
        btnTwo = (Button) findViewById(R.id.btn_two);
        btnTwo.setOnClickListener(this);
        dialog=new ProgressDialog(this);
        dialog.setMessage("数据提交中");

    }
    private void querShopCartOrderinfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERORDERBYID, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (TextUtils.isEmpty(s)) {
                    orderStateModel=ExampleApplication.getInstance().getGson().fromJson(s,OrderStateModel.class);
                    modelList=orderStateModel.getOrder().getDetails();
                    int status=orderStateModel.getOrder().getStatus();
                    orderDetailAdapter=new OrderDetailAdapter(OrderDetailsActivity.this,modelList,status);
                    orderDetailAdapter.setOnrefund(OrderDetailsActivity.this);
                    lvOrderdetails.setAdapter(orderDetailAdapter);
                    setintoData(orderStateModel);
                }

                Log.e("解析成功",orderStateModel.getOrder().getOrder_code());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("购物车订单查询失败", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("id", orderId);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    //购物车数据赋值
    private void setintoData(OrderStateModel orderStateModel) {
        txtConsignee.setText(orderStateModel.getOrder().getTake_address().getName());
        txtPhone.setText(orderStateModel.getOrder().getTake_address().getPhone());
        txtAdder.setText(orderStateModel.getOrder().getTake_address().getDistrict());
        txt_count.setText("总共"+orderStateModel.getOrder().getCount()+"件商品,总价：");
        txt_allpic.setText(String.format(getResources().getString(R.string.currentprice),to.format(Double.valueOf(orderStateModel.getOrder().getTotal_count()))));
        if (orderStateModel.getOrder().getDelivery_time()!=0) {
            txtDeliverytime.setText(format.format(orderStateModel.getOrder().getDelivery_time()));
        }
        if (orderStateModel.getOrder().getLogisticInfo()!=null) {
            txtLogisticscompany.setText(orderStateModel.getOrder().getLogisticInfo().getLogistic_name());//物流公司
        }
        if (orderStateModel.getOrder().getLogisticInfo()!=null) {
            txtWaybillnumber.setText(orderStateModel.getOrder().getLogisticInfo().getTracking_no());//运单编号
        }
        txtOrderno.setText(String.valueOf(orderStateModel.getOrder().getId()));
        txtOrdertime.setText(format.format(orderStateModel.getOrder().getCreate_time()));
        if(orderStateModel.getOrder().getIsBill()==Constant.ONE){
            txtInvoice.setText("发票:提供");
        }else {
            txtInvoice.setText("发票:否");
        }

            txtAftersale.setText("售后:否");//售后服务
        if (orderStateModel.getOrder().getLogistics_cost()>=0) {
            txtFreight.setText("运费:"+String.valueOf(orderStateModel.getOrder().getLogistics_cost()) + "");//运费
        }
        else {
            txtFreight.setText("运费:");
        }
        if (orderStateModel.getOrder().getBull_note()==null){
            txtLeaveword.setText("");
        }else {
            txtLeaveword.setText(String.valueOf(orderStateModel.getOrder().getBull_note()) );
        }

        switch (orderStateModel.getOrder().getStatus()){
            case -1:
                //订单失效
                txtOrderstatus.setText("订单失效");
                btnOne.setVisibility(View.GONE);
                btnTwo.setVisibility(View.GONE);
                break;
            case 0:
                //待付款
                txtOrderstatus.setText("待付款");
                btnOne.setText("取消订单");
                btnTwo.setText("去支付");
                break;
            case 1:
                //待付款
                txtOrderstatus.setText("待付款");
                btnOne.setText("取消订单");
                btnTwo.setText("去支付");
                break;
            case 2:
                //待发货
                txtOrderstatus.setText("待发货");
                btnOne.setVisibility(View.GONE);
                btnTwo.setText("退款");
                break;
            case 3:
                //待收货
                txtOrderstatus.setText("待收货");
                btnOne.setText("查看物流");
                btnTwo.setText("确认收货");
                break;

            case 5:
                //退款中
                txtOrderstatus.setText("退款中");
                btnOne.setVisibility(View.GONE);
                btnTwo.setVisibility(View.GONE);
                break;
            case 6:
                //退款成功
                txtOrderstatus.setText("退款成功");
                btnOne.setVisibility(View.GONE);
                btnTwo.setVisibility(View.GONE);
                break;
            case 7:
                //退款失败
                txtOrderstatus.setText("退款失败");
                btnOne.setText("联系客服");
                btnTwo.setText("继续退款");
                break;
            case 8:
                //交易成功
                txtOrderstatus.setText("待评论");
                btnOne.setText("查看物流");
                btnTwo.setText("评论");
                break;
            case 9:
                //换货中
                txtOrderstatus.setText("换货中");
                btnOne.setVisibility(View.GONE);
                btnTwo.setText("申请售后");
                break;
            case 10:
               //换货成功
                txtOrderstatus.setText("交易完成");
                btnOne.setVisibility(View.GONE);
                btnTwo.setVisibility(View.GONE);
                break;
            case 11:
                //完成评价
                txtOrderstatus.setText("交易完成");
                btnOne.setVisibility(View.GONE);
                btnTwo.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public void onClick(View v) {
           switch (v.getId()){
               case R.id.img_back:
                    finish();
                   break;
               case R.id.btn_one:
                   switch (orderStateModel.getOrder().getStatus()){
                       case 0:
                           //待付款
                           cancelOder();
                           break;
                       case 1:
                           //待付款
                           cancelOder();
                           break;
                       case 2:
                           //待发货

                           break;
                       case 3:
                           //待收货
                           Toast.makeText(OrderDetailsActivity.this,"查看物流",Toast.LENGTH_LONG).show();
                           Intent intent=new Intent(OrderDetailsActivity.this,LogisticsDetailActivity.class);
                           intent.putExtra("orderId",orderStateModel.getOrder().getId()+"");
                           startActivity(intent);
                           break;

                       case 5:
                           //退款中

                           break;
                       case 6:
                           //退款成功

                           break;
                       case 7:
                           //退款失败
                           //打电话
                           Intent intentphone = new Intent();
                           //系统默认的action，用来打开默认的电话界面
                           intentphone.setAction(Intent.ACTION_CALL);
                           //需要拨打的号码
                           intentphone.setData(Uri.parse("tel:" + "02389870155"));
                           startActivity(intentphone);
                           break;
                       case 8:
                           //交易成功
                           Intent intents=new Intent(OrderDetailsActivity.this,LogisticsDetailActivity.class);
                           intents.putExtra("orderId",orderStateModel.getOrder().getId()+"");
                           startActivity(intents);
                           break;
                       case 9:
                           //换货中

                           break;
                       case 10:
                           //完成评价

                           break;
                   }
                   break;
               case R.id.btn_two:
                   switch (orderStateModel.getOrder().getStatus()){
                       case 0:
                           //待付款
                           Intent intents=new Intent(OrderDetailsActivity.this,PayActivity.class);
                           intents.putExtra("totalcount",totalcount);
                           intents.putExtra("orderId",orderId);
                           intents.putExtra("orderCode",orderCode);
                           startActivity(intents);
                           break;
                       case 1:
                           //待付款
                           Intent intent=new Intent(OrderDetailsActivity.this,PayActivity.class);
                           intent.putExtra("totalcount",totalcount);
                           intent.putExtra("orderId",orderId);
                           intent.putExtra("orderCode",orderCode);
                           startActivity(intent);
                           break;
                       case 2:
                           //待发货
                           Intent intentrefund=new Intent(OrderDetailsActivity.this,Refund_Activity.class);
                           intentrefund.putExtra("allpic",Double.valueOf(orderStateModel.getOrder().getTotal_count()));
                           intentrefund.putExtra("orderId",String.valueOf(orderStateModel.getOrder().getId()));
                           startActivity(intentrefund);
                           break;
                       case 3:
                           //待收货
                      Toast.makeText(OrderDetailsActivity.this,"确认收货",Toast.LENGTH_LONG).show();
                           confirmReceipt();
                           break;

                       case 5:
                           //退款中

                           break;
                       case 6:
                           //退款成功

                           break;
                       case 7:
                           //退款失败
                           //继续退款
                           Intent tintent=new Intent(OrderDetailsActivity.this,Refund_Activity.class);
                           tintent.putExtra("allpic",Double.valueOf(orderStateModel.getOrder().getTotal_count()+""));
                           tintent.putExtra("orderId",orderStateModel.getOrder().getId());
                           startActivity(tintent);
                           Log.e("退款金额",orderStateModel.getOrder().getTotal_count()+"");
                           break;
                       case 8:
                           //交易成功
                           Intent intentcomment=new Intent(OrderDetailsActivity.this,CommentFocusActivity.class);
                           intentcomment.putExtra("orderId",orderStateModel.getOrder().getId()+"");
                           startActivity(intentcomment);
                           break;
                       case 9:
                           //换货中

                           break;
                       case 10:
                           //完成评价

                           break;
                   }
                   break;
           }
    }


    //取消订单
    private void cancelOder(){
        dialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.CANCELORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("取消订单成功",s);

                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(s);
                    int status=jsonObject.getInt("status");
                    String message=jsonObject.getString("message");
                    if (status== Constant.ONE){
                        finish();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(OrderDetailsActivity.this,message,Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                map.put("orderId",orderId+"");
                map.put("cancelReason","");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    /***
     * 确认收货
     */
    private void confirmReceipt(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.CONFIRMRECEIPT, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("确认收货成功",s);
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    int status=jsonObject.getInt("status");
                    String message=jsonObject.getString("message");
                    if (status== Constant.ONE){
                        finish();
                    }
                    Toast.makeText(OrderDetailsActivity.this,message,Toast.LENGTH_LONG).show();
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
                map.put("orderId",orderStateModel.getOrder().getId()+"");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }



}
