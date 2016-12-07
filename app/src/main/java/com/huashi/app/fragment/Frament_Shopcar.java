package com.huashi.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.huashi.app.activity.ConfirmOrderActivity;
import com.huashi.app.activity.ProductdetailsActivity;
import com.huashi.app.adapter.ShopCarAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.library.view.SwipeMenu;
import com.huashi.app.library.view.SwipeMenuCreator;
import com.huashi.app.library.view.SwipeMenuItem;
import com.huashi.app.model.ShopCarModel;
import com.huashi.app.util.Httputil;
import com.huashi.app.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/10.
 */
public class Frament_Shopcar extends Fragment implements View.OnClickListener {
    private View view;
    private TextView txt_compile, txt_num, txt_shopcartmessag, txt_total;
    private Button btn_pay;
    private CheckBox cb_allfocus, cb_caritem;
    private ListView slv_shopcar;
    private ShopCarAdapter shopCarAdapter;
    private ShopCarModel shopCarModel;
    private SwipeRefreshLayout swipe_layout;
    private List<ShopCarModel.ShoppingCartsBean> list;
    private List<ShopCarModel> shopCarModelList;
    private Activity activity;
    private View shopcar;
    private PopupWindow popupWindow;
    private Button btnShopcartDelete;
    private Button btnShopcartCollect;
    private Button btnShopcartCancel;
    private RelativeLayout rl_popwindow;
    private String userId;
    private ProgressDialog dialog;
    private int logonck;
    private int m_commodityid;
    private double totalPrice = 0; // 商品总价
    private DecimalFormat to = new DecimalFormat("0.00");
    private String json = null;//购物车上传的商品数据
    private int pageNumber = 0;
    private int maxPageNumber = 0;
    private boolean isb = false;//标识是结算还是删除
    private String shoppingcartDetails;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frment_shopcar, null);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        intoView();
        if (Httputil.isNetworkAvailable(activity)) {
            getData();
        } else {
            Toast.makeText(activity, "网络异常", Toast.LENGTH_LONG).show();
        }
    }

    //初始化控件
    private void intoView() {
        userId = new Utils(activity).getInfomation();
        txt_compile = (TextView) view.findViewById(R.id.txt_compile);
        txt_compile.setOnClickListener(this);
        shopCarModelList = new ArrayList<>();
        swipe_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        swipe_layout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置刷新时动画的颜色
        swipe_layout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
//        swipe_layout.setProgressViewOffset(false, 0, (int) TypedValue
//                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
//                        .getDisplayMetrics()));
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        swipe_layout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

////        //上拉加载
////        swipe_layout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
////
////            @Override
////            public void onLoad() {
////                swipe_layout.postDelayed(new Runnable() {
////
////                    @Override
////                    public void run() {
////                        // 加载完后调用该方法
////                        swipe_layout.setLoading(false);
////                    }
////                }, 1500);
////                pageNumber+=1;
////                boolean b=true;
////                if (pageNumber>=maxPageNumber){
////                    pageNumber=maxPageNumber;
////                    Toast.makeText(activity,"没有了",Toast.LENGTH_LONG).show();
////                  b=false;
////                }
////                if (b){
////                    getData();
//////                    list.addAll(shopCarModel.getShoppingCarts());
//////                    shopCarAdapter.notifyDataSetChanged();
////                }
//
//
//            }
//        });


        btn_pay = (Button) view.findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(this);
        txt_total = (TextView) view.findViewById(R.id.txt_total);
        slv_shopcar = (ListView) view.findViewById(R.id.slv_shopcar);
        txt_shopcartmessag = (TextView) view.findViewById(R.id.txt_shopcartmessag);
        txt_shopcartmessag.setOnClickListener(this);

        dialog = new ProgressDialog(activity);
        dialog.setTitle("提示");
        dialog.setMessage("数据加载中");
        list = new ArrayList<>();

        shopcar = LayoutInflater.from(activity).inflate(R.layout.shopcar_item, null);
        cb_caritem = (CheckBox) shopcar.findViewById(R.id.cb_caritem);
        cb_caritem.setOnClickListener(this);
        txt_num = (TextView) shopcar.findViewById(R.id.txt_num);
        if (userId == null) {
            txt_shopcartmessag.setText("未登录，请先登录");
        }
        //解决Listview 与SwipeRefreshLayout滑动冲突
        slv_shopcar.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                boolean enable = false;
                if (slv_shopcar != null && slv_shopcar.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = slv_shopcar.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = slv_shopcar.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipe_layout.setEnabled(enable);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void showPopWindon(View view) {
        View windon;
        LayoutInflater inflater = LayoutInflater.from(activity);
        windon = inflater.inflate(R.layout.popwindow_shopcart, null);
        popupWindow = new PopupWindow(windon, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        btnShopcartDelete = (Button) windon.findViewById(R.id.btn_shopcart_delete);
        btnShopcartDelete.setOnClickListener(this);
        btnShopcartCollect = (Button) windon.findViewById(R.id.btn_shopcart_collect);
        btnShopcartCollect.setOnClickListener(this);
        btnShopcartCancel = (Button) windon.findViewById(R.id.btn_shopcart_cancel);
        btnShopcartCancel.setOnClickListener(this);
        rl_popwindow = (RelativeLayout) windon.findViewById(R.id.ry_powin);
        rl_popwindow.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {

            case R.id.txt_plus:
                //添加商品数量
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    int position = (Integer) tag;
                    int coun = list.get(position).getCount();
                    String shopid = String.valueOf(list.get(position).getId());
                    coun += 1;
                    Log.e("购物车count", coun + "");
                    calculate();
                    updataShoppingCartDetails(shopid, coun);
                    //更改集合的数据

                }
                break;
            case R.id.txt_munus:
                //删减商品数量
                // 获取 Adapter 中设置的 Tag
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    int coun = list.get(position).getCount();
                    String shopid = String.valueOf(list.get(position).getId());
                    coun -= 1;
                    if (coun <= 1) {
                        coun = 1;
                        Toast.makeText(activity, "商品数量不能小于1", Toast.LENGTH_LONG).show();
                    }
                    calculate();
                    Log.e("购物车count", coun + "");
                    updataShoppingCartDetails(shopid, coun);
                }
                break;
            case R.id.cb_caritem:
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    Log.e("标识", position + "");
                    if (list.get(position).isStatue() == false) {
                        list.get(position).setStatue(true);
                    } else {
                        list.get(position).setStatue(false);
                    }

                    calculate();
                    batchDelete();


                }
                break;


            case R.id.txt_compile:
                //编辑
                if (txt_compile.getText().equals("编辑")) {
                    txt_compile.setText("完成");
                    btn_pay.setText("删除");
                    isb = true;
                } else {
                    txt_compile.setText("编辑");
                    btn_pay.setText("去结算");
                    isb = false;
                }
                break;
            case R.id.btn_pay:
                //支付
                isPay();
                break;
            case R.id.btn_shopcart_collect:
                //购物车添加收藏
                insertCollectCommodity(m_commodityid);
                break;
            case R.id.btn_shopcart_cancel:
                //取消
                popupWindow.dismiss();
                break;
            case R.id.btn_shopcart_delete:
                //删除购物车商品
                deleteShopCartCommodity(String.valueOf(logonck));
                break;
            case R.id.ry_powin:
                popupWindow.dismiss();
                break;
        }
    }

    /**
     * 统计操作
     * 1.先清空全局计数器<
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        JSONObject jsonObject = null;
        JSONArray jsonArray = new JSONArray();
        totalPrice = 0.00;
        for (int j = 0; j < list.size(); j++) {
            Log.e("shopid", list.get(j).getId() + "");
            Log.e("statue", list.get(j).isStatue() + "");
            if (list.get(j).isStatue()) {
                totalPrice += list.get(j).getPrice() * list.get(j).getCount();
                //将数据用户选择的商品数据写入jsonObject
                jsonObject = new JSONObject();
                try {
                    jsonObject.put("commodity_id", list.get(j).getCommodityId());
                    jsonObject.put("shoppingcar_id", list.get(j).getId());
                    jsonObject.put("model_id", list.get(j).getModelId());
                    jsonObject.put("price", list.get(j).getPrice());
                    jsonObject.put("count", list.get(j).getCount());
                    jsonObject.put("order_id", 0);
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("选择的", "选中的" + j);
            } else {
                Log.e("没有选择", "没有选中");
            }

        }

        json = jsonArray.toString();
        txt_total.setText(to.format(totalPrice));
//        shopCarAdapter.notifyDataSetChanged();
    }

    //批量删除json数据
    private void batchDelete() {
        JSONObject jsonObject = null;
        JSONArray jsonArray = new JSONArray();
        for (int j = 0; j < list.size(); j++) {
            Log.e("shopid", list.get(j).getId() + "");
            Log.e("statue", list.get(j).isStatue() + "");
            if (list.get(j).isStatue()) {
                //将数据用户选择的商品数据写入jsonObject
                jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", list.get(j).getId());
                    jsonObject.put("userId", userId);
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("选择的", "选中的" + j);
            } else {
                Log.e("没有选择", "没有选中");
            }

        }
        shoppingcartDetails = jsonArray.toString();
    }


    //批量删除
    private void submitBatchDelete() {
        Log.e("删除的json数据", shoppingcartDetails);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.DELETESHOPPINGCARTBYID, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("批量删除成功", s.toString());
                getData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("批量删除失败", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("shoppingcartDetails", shoppingcartDetails);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }


    //初始化购物车数据
    private void getData() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYCARTDETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("购物车请求成功", s);
                if (!s.isEmpty()) {
                    shopCarModel = ExampleApplication.getInstance().getGson().fromJson(s, ShopCarModel.class);
                    if (!shopCarModel.getShoppingCarts().isEmpty() && shopCarModel.getStatus() == Constant.ONE) {
                        intoData(shopCarModel);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
//                            maxPageNumber=jsonObject.getInt("maxPageNumber");
//                            Log.e("总页数",maxPageNumber+"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();

                    } else {
                        txt_shopcartmessag.setVisibility(View.VISIBLE);
                    }

                }else {
                    txt_shopcartmessag.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("购物车请求失败", volleyError.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("pageNumber", String.valueOf(pageNumber));
                Log.e("页码", pageNumber + "");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);

    }

    //    //初始化数据数据
    private void intoData(final ShopCarModel shopCarModel) {

        list.clear();
        list = shopCarModel.getShoppingCarts();
        Log.e("集合大小", list.size() + "");
        if (!list.isEmpty()) {
            shopCarAdapter = new ShopCarAdapter(activity, list, slv_shopcar);
            slv_shopcar.setAdapter(shopCarAdapter);
            shopCarAdapter.setOnAddNum(this);
            shopCarAdapter.setOnSubNum(this);
            shopCarAdapter.setOnCheck(this);
        }

        dialog.dismiss();
        slv_shopcar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String commodityId = String.valueOf(list.get(position).getCommodityId());
                Intent intent = new Intent(activity, ProductdetailsActivity.class);
                intent.putExtra("commodityid", commodityId);
                startActivity(intent);

            }
        });
        slv_shopcar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopWindon(slv_shopcar);
                logonck = list.get(position).getId();
                m_commodityid = list.get(position).getCommodityId();
                return true;
            }
        });

        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                createMenu(menu);
            }
        };
//        slv_shopcar.setMenuCreator(swipeMenuCreator);
//        slv_shopcar.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
//
//                switch (index) {
//                    case 0:
//                        dialog.show();
//                        int commodityid = list.get(position).getCommodityId();
//                        insertCollectCommodity(commodityid);
//                        break;
//                    case 1:
//                        dialog.show();
//                        int id = list.get(position).getId();
//                        deleteShopCartCommodity(String.valueOf(id));
//
//                        break;
//                }
//            }
//        });


    }


    //删除购物车商品
    private void deleteShopCartCommodity(final String shopcarid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.DELETESHOPPINGCART, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("购物车删除商品成功", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int state = jsonObject.getInt("status");
                    Log.e("status", state + "");
                    if (state == Constant.ONE) {
                        getData();
                        dialog.dismiss();
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("购物车删除商品失败", volleyError.toString());
                Toast.makeText(activity, "删除失败", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("id", shopcarid);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);

    }

    //购物车收藏
    private void insertCollectCommodity(final int commodityid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.INSERTCOLLECTCOMMODITY, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!s.isEmpty()) {
                    Log.e("收藏购物车成功", s);
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");
                        if (status == Constant.ONE) {
                            Toast.makeText(activity, "收藏成功", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            popupWindow.dismiss();
                        } else {
                            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                            popupWindow.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(activity, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("收藏购物车失败", volleyError.toString());
                Toast.makeText(activity, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("commodityId", String.valueOf(commodityid));
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    //修改购物商品数量
    private void updataShoppingCartDetails(final String shopid, final int count) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.UPDATESHOPINGCARTDETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!s.isEmpty()) {
                    Log.e("修改成功", s);
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int status = jsonObject.getInt("status");
                        if (status == Constant.ONE) {
                            getData();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(activity, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("修改失败", volleyError.toString());
                Toast.makeText(activity, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", shopid);
                map.put("userId", userId);
                map.put("count", count + "");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }


    //写入侧滑图片
    private void createMenu(SwipeMenu menu) {
        SwipeMenuItem item1 = new SwipeMenuItem(
                getActivity());
        item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0xE0,
                0x3F)));
        item1.setWidth(dp2px(90));
        item1.setIcon(R.mipmap.ic_action_important);
        menu.addMenuItem(item1);
        SwipeMenuItem item2 = new SwipeMenuItem(
                getActivity());
        item2.setBackground(new ColorDrawable(Color.rgb(0xF9,
                0x3F, 0x25)));
        item2.setWidth(dp2px(90));
        item2.setIcon(R.mipmap.ic_action_discard);
        menu.addMenuItem(item2);
    }

    //判断是支付还是删除
    private void isPay() {
        if (btn_pay.getText().equals("去结算")) {
            if (Httputil.isNetworkAvailable(activity)) {
                if (totalPrice == 0) {
                    Toast.makeText(activity, "请选择要支付的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                submitShopCart();
            } else {
                Toast.makeText(activity, R.string.networkexception, Toast.LENGTH_LONG).show();
            }

        } else {
            submitBatchDelete();
            Log.e("判断是", isb + "");
        }
    }

    //提交数据
    private void submitShopCart() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.SHOPPINGCARTOORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("上传成功", s);
                if (!s.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int state = jsonObject.getInt("status");
                        int orderId = jsonObject.getInt("order_id");
                        Log.e("订单id", orderId + "" + state);
                        if (state == Constant.ONE) {
                            dialog.dismiss();
                            Log.e("订单id", orderId + "");
                            Intent intentorder = new Intent(activity, ConfirmOrderActivity.class);
                            intentorder.putExtra("orderId", String.valueOf(orderId));
                            intentorder.putExtra("totalcount", String.valueOf(totalPrice));
                            startActivity(intentorder);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(activity, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("上传失败", volleyError.toString());
                Toast.makeText(activity, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                if (!json.isEmpty()) {
                    map.put("orderDetails", json);
                }
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
