package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.huashi.app.adapter.OrderCommodityAdapter;
import com.huashi.app.adapter.ShopCartOrderCommodityAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.OrderInfoModel;
import com.huashi.app.model.ShopCarOrderinfoModel;
import com.huashi.app.util.Utils;
import com.huashi.app.view.MyDialog;
import com.huashi.app.view.MyListview;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/31.
 * 确认订单
 */
public class ConfirmOrderActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rayHand;
    private ImageView imgBack;
    private LinearLayout layInfo;
    private TextView txtConsignee;
    private TextView txtPhone;
    private TextView txtAdder;
    private MyListview lvOrderdetails;
    private LinearLayout lyAftersales;
    private LinearLayout lyLeaveword;
    private Button btnCancel;
    private Button btnOk;
    private OrderInfoModel orderInfoModel;
    private String userId;
    private int modelId;
    private Utils utils;
    private TextView txtInvoice;
    private TextView txtAftersale;
    private TextView txtFreight;
    private TextView txt_Allpic;
    private TextView txt_patternpayment;
    private TextView txt_logistics;
    private EditText txt_bullNote;
    private String orderId;
    private double totalcount;
    private int allcount;
    private TextView txt_count;
    private OrderCommodityAdapter orderCommodityAdapter;
    private ShopCartOrderCommodityAdapter shopCartOrderCommodityAdapter;
    private List<OrderInfoModel.OrderModelBean.CommodityModelsBean> list;
    private List<ShopCarOrderinfoModel.CommoditysBean> modelList;
    private String commodityid;
    private int count;
    private String price;
    private double logisticsCost;
    private String bullNote;
    private int addid;
    private RelativeLayout ry_delivery;
    private ShopCarOrderinfoModel shopCarOrderinfoModel;
    private DecimalFormat to = new DecimalFormat("0.00");
    private int costType=0;
    private int orderType=0;
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspectorder);
        intoView();
    }

    private void intoView() {
        utils = new Utils(this);
        modelList = new ArrayList<>();
        dialog=new MyDialog(ConfirmOrderActivity.this);
        dialog.setTitle(R.string.pull_to_refresh_footer_refreshing_label);
        modelId = getIntent().getIntExtra("modelId", -1);
        orderId = getIntent().getStringExtra("orderId");
        totalcount = Double.valueOf(getIntent().getStringExtra("totalcount"));
        commodityid = getIntent().getStringExtra("commodityid");
        rayHand = (RelativeLayout) findViewById(R.id.ray_hand);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        layInfo = (LinearLayout) findViewById(R.id.lay_info);
        layInfo.setOnClickListener(this);
        txtConsignee = (TextView) findViewById(R.id.txt_consignee);
        txtPhone = (TextView) findViewById(R.id.txt_phone);
        txtAdder = (TextView) findViewById(R.id.txt_adder);
        txt_bullNote = (EditText) findViewById(R.id.txt_bullNote);
        lvOrderdetails = (MyListview) findViewById(R.id.lv_orderdetails);
        lyAftersales = (LinearLayout) findViewById(R.id.ly_aftersales);
        lyLeaveword = (LinearLayout) findViewById(R.id.ly_leaveword);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        txtInvoice = (TextView) findViewById(R.id.txt_invoice);
        txtAftersale = (TextView) findViewById(R.id.txt_aftersale);
        txtFreight = (TextView) findViewById(R.id.txt_freight);
        txt_Allpic = (TextView) findViewById(R.id.txt_allpic);
        txt_count = (TextView) findViewById(R.id.txt_count);
        ry_delivery = (RelativeLayout) findViewById(R.id.ry_delivery);
        ry_delivery.setOnClickListener(this);
        txt_patternpayment= (TextView) findViewById(R.id.txt_patternpayment);
        txt_logistics= (TextView) findViewById(R.id.txt_logistics);
        userId = utils.getInfomation();
        if (orderId !=null) {
            querOrderinfo();
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_info:
                //选择地址
                Intent intentadder = new Intent(this, MyAdderActivity.class);
                startActivity(intentadder);
                break;
            case R.id.img_back:
                //退出当前
                finish();
                break;
            case R.id.btn_ok:
                //去支付
                if (modelId != -1) {
                    submitOrderinfo();
                } else {
                    sumitShopCartData();
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.ry_delivery:
                //选择配送方式
                Intent intentpayment = new Intent(this, PaymentActivity.class);
                intentpayment.putExtra("orderId",orderId);
                intentpayment.putExtra("orderType",orderType);
                intentpayment.putExtra("costType",costType);
                startActivity(intentpayment);
                Log.e("传过去",orderType+"配送"+costType+"支付");
                break;
        }
    }

    //查询订单信息
    private void querOrderinfo() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.ORDERQUERORDERDETAILBYUSERID, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                list = new ArrayList<>();
                if (!TextUtils.isEmpty(s)) {
                    orderInfoModel = ExampleApplication.getInstance().getGson().fromJson(s, OrderInfoModel.class);
                    list=orderInfoModel.getOrderModel().getCommodityModels();
                    orderCommodityAdapter = new OrderCommodityAdapter(ConfirmOrderActivity.this, list);
                    lvOrderdetails.setAdapter(orderCommodityAdapter);
                    setData(orderInfoModel);
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                }else {
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                    Toast.makeText(ConfirmOrderActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                Toast.makeText(ConfirmOrderActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
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

//    private void querShopCartOrderinfo() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYCOMMODITYINFOBYID, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Log.e("购物车订单查询成功", s.toString());
//                if (s != null) {
//                    shopCarOrderinfoModel = gson.fromJson(s, ShopCarOrderinfoModel.class);
//                    Log.e("OrderInfoModel", shopCarOrderinfoModel.getCommoditys().get(0).getNameCN());
//                    setShopCartData(shopCarOrderinfoModel);
////                    Log.e("购物车数jsoN数据",setShopCartJsonData());
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.e("购物车订单查询失败", volleyError.toString());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("userId", userId);
//                map.put("id", orderId);
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    //提交订单
    private void submitOrderinfo() {
        bullNote = txt_bullNote.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.INSERTORDERDETSIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)){
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        String message=jsonObject.getString("message");
                        int status=jsonObject.getInt("status");
//                    double totalcount=jsonObject.getDouble("totalcount");
                        if (status==Constant.ONE){
                            Intent intentpay=new Intent(ConfirmOrderActivity.this,PayActivity.class);
                            intentpay.putExtra("totalcount",totalcount);
                            intentpay.putExtra("orderId",orderId);
                            intentpay.putExtra("orderCode",orderInfoModel.getOrderModel().getOrderCode());

                            startActivity(intentpay);
                        }else {
                            Toast.makeText(ConfirmOrderActivity.this,message,Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(ConfirmOrderActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ConfirmOrderActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", orderId);
                map.put("userId", userId);
                map.put("takeAddressId", String.valueOf(addid));
                map.put("totalcount", String.valueOf(totalcount));
                map.put("count", String.valueOf(allcount));
                map.put("logisticsCost", String.valueOf(logisticsCost));
                map.put("bullNote", bullNote);
                map.put("costType",costType+"");
                map.put("orderType",orderType+"");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }



    //提交购物车数据
    private void sumitShopCartData() {
        bullNote = txt_bullNote.getText().toString().trim();
       StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.SHOPPINGCARTOORDERDETSILS, new Response.Listener<String>() {
           @Override
           public void onResponse(String s) {
               if (!TextUtils.isEmpty(s)) {
                   try {
                       JSONObject jsonObject = new JSONObject(s);
                       String message = jsonObject.getString("message");
                       int status = jsonObject.getInt("status");
//                   double totalcount=jsonObject.getDouble("totalcount");
                       if (status == Constant.ONE) {
                           Intent intentpay = new Intent(ConfirmOrderActivity.this, PayActivity.class);
                           intentpay.putExtra("totalcount", totalcount);
                           intentpay.putExtra("orderId", orderId);
                           startActivity(intentpay);
                       } else {
                           Toast.makeText(ConfirmOrderActivity.this, message, Toast.LENGTH_LONG).show();
                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }else {
                   Toast.makeText(ConfirmOrderActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError volleyError) {
               Toast.makeText(ConfirmOrderActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> map=new HashMap<>();
               map.put("id", orderId);
               map.put("userId", userId);
               map.put("order_id", orderId);
               map.put("logisticsCost",shopCarOrderinfoModel.getLogisticsCost()+"");
               map.put("takeAddressId",String.valueOf(addid));
               map.put("bullNote", bullNote);
               map.put("costType",costType+"");
               map.put("orderType",orderType+"");
               return map;
           }
       };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    //商品详情数据赋值
    private void setData(OrderInfoModel orderInfoModel) {

        allcount = orderInfoModel.getOrderModel().getCommodityModels().get(0).getCount();
        price = orderInfoModel.getOrderModel().getCommodityModels().get(0).getTrue_sell();
        count = orderInfoModel.getOrderModel().getCount();
        logisticsCost = orderInfoModel.getOrderModel().getLogisticsCost();
        costType=orderInfoModel.getOrderModel().getCostType();
        orderType=orderInfoModel.getOrderModel().getOrderType();
       if (costType==Constant.ONE){
           txt_patternpayment.setText("在线支付");
       }else {
           txt_patternpayment.setText("货到付款");
       }
        if (orderType==Constant.ONE){
            txt_logistics.setText("快速配送");
        }else {
            txt_logistics.setText("送货上门");
        }



        OrderInfoModel.UserAddressBean adder = orderInfoModel.getUserAddress();
        txtConsignee.setText(adder.getName());
        txtPhone.setText(adder.getPhone());
        txtAdder.setText(adder.getProvince() + adder.getCity() + adder.getDistrict() + adder.getStreet());
        if (orderInfoModel.getOrderModel().getIsBill() == Constant.ONE) {
            txtInvoice.setText("发票：有");
        } else {
            txtInvoice.setText("发票：无");
        }if (orderInfoModel.getOrderModel().getCommodityModels().get(0).getAftersale() != null) {
            txtAftersale.setText("售后服务：" + orderInfoModel.getOrderModel().getCommodityModels().get(0).getAftersale() + "");
        }
        txtFreight.setText("运费：" + orderInfoModel.getOrderModel().getLogisticsCost() + "");
        txt_Allpic.setText(String.format(getResources().getString(R.string.currentprice),to.format(totalcount)) );
        txt_count.setText("总共" + orderInfoModel.getOrderModel().getCount() + "件商品,总价：");
        addid = orderInfoModel.getUserAddress().getId();


    }
//    //购物车数据赋值
//    private void setShopCartData(ShopCarOrderinfoModel shopCarOrderinfoModel) {
//
//        modelList = shopCarOrderinfoModel.getCommoditys();
//        shopCartOrderCommodityAdapter = new ShopCartOrderCommodityAdapter(ConfirmOrderActivity.this, modelList);
//        lvOrderdetails.setAdapter(shopCartOrderCommodityAdapter);
//        allcount = shopCarOrderinfoModel.getCommodityNum();
//        logisticsCost = shopCarOrderinfoModel.getLogisticsCost();
//        orderType=shopCarOrderinfoModel.getOrderType();
//        costType=shopCarOrderinfoModel.getCostType();
//
//        if (orderType==Constant.ONE){
//            txt_logistics.setText("快速配送");
//
//        }else {
//            txt_logistics.setText("送货上门");
//
//        }
//        if (costType==Constant.ONE){
//            txt_patternpayment.setText("在线支付");
//        }else {
//            txt_patternpayment.setText("货到付款");
//        }
//
//        ShopCarOrderinfoModel.UserAddressBean adder = shopCarOrderinfoModel.getUserAddress();
//        txtConsignee.setText(adder.getName());
//        txtPhone.setText(adder.getPhone());
//        txtAdder.setText(adder.getProvince() + adder.getCity() + adder.getDistrict() + adder.getStreet());
//
//        if (shopCarOrderinfoModel.getCommoditys().get(0).getIsBill() == Constant.ONE) {
//            txtInvoice.setText("发票：有");
//        } else {
//            txtInvoice.setText("发票：无");
//        }
//        if (shopCarOrderinfoModel.getCommoditys().get(0).getAftersale() != null) {
//            txtAftersale.setText("售后服务：" + shopCarOrderinfoModel.getCommoditys().get(0).getAftersale() + "");
//        }
//        txtFreight.setText("运费：" + shopCarOrderinfoModel.getLogisticsCost() + "");
//        txt_count.setText("共" + shopCarOrderinfoModel.getCommoditys().size() + "件商品，合计：");
//        Log.e("集合大小", shopCarOrderinfoModel.getCommoditys().size() + "");
//        txt_Allpic.setText("￥" + to.format(totalcount));
//        addid = shopCarOrderinfoModel.getUserAddress().getId();
//    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (orderId!=null) {
            querOrderinfo();
        }
    }
}
