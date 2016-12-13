package com.huashi.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.huashi.app.adapter.CollectionAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.library.view.SwipeMenu;
import com.huashi.app.library.view.SwipeMenuCreator;
import com.huashi.app.library.view.SwipeMenuItem;
import com.huashi.app.model.CollectionModel;
import com.huashi.app.util.Utils;
import com.huashi.app.view.city.RefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/1.
 * 我的收藏
 */
public class MyCollectionActivity extends Activity implements View.OnClickListener {
    private ListView svl_collection;
    private ImageView img_back;
    private List<CollectionModel.CommoditysBean> list;
    private CollectionModel collectionModel;
    private CollectionAdapter adapter;
    private Utils utils;
    private String userid;
    private int postion;
    private ProgressDialog dialog;
    private RefreshLayout swipe_layout;
    private PopupWindow popupWindow;
    private Button btnCommentCancel;
    private Button btnCommentDelete;
    private RelativeLayout ryPowin;
    private TextView txt_commenttitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollection);
        intoView();

    }

    private void intoView() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("玩命加载中...");
        txt_commenttitle = (TextView) findViewById(R.id.txt_commenttitle);
        svl_collection = (ListView) findViewById(R.id.slv_collection);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        utils = new Utils(this);
        userid = utils.getInfomation();
        if (!TextUtils.isEmpty(userid)) {
            intoData();
        } else {
            Toast.makeText(this, "请先登录", Toast.LENGTH_LONG).show();
        }

        swipe_layout = (RefreshLayout) findViewById(R.id.swipe_layout);
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
//                        List<String> newDatas = new ArrayList<String>();
//                        for (int i = 0; i <5; i++) {
//                            int index = i + 1;
//                            newDatas.add("new item" + index);
//                        }
//                        adapter.addItem(newDatas);
                        swipe_layout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
        //上拉加载
        swipe_layout.setOnLoadListener(new RefreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {
                swipe_layout.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 加载完后调用该方法
                        swipe_layout.setLoading(false);
                    }
                }, 1500);

            }
        });
        svl_collection.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                boolean enable = false;
                if (svl_collection != null && svl_collection.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = svl_collection.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = svl_collection.getChildAt(0).getTop() == 0;
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
        LayoutInflater inflater = LayoutInflater.from(this);
        windon = inflater.inflate(R.layout.popwindow_comment_delete, null,false);
        popupWindow = new PopupWindow(windon, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        btnCommentDelete = (Button) windon.findViewById(R.id.btn_comment_delete);
        btnCommentDelete.setOnClickListener(this);
        btnCommentCancel = (Button) windon.findViewById(R.id.btn_comment_cancel);
        btnCommentCancel.setOnClickListener(this);
        ryPowin = (RelativeLayout) windon.findViewById(R.id.ry_powin);
        ryPowin.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_comment_cancel:
                popupWindow.dismiss();
                break;
            case R.id.btn_comment_delete:
                deleteCollection();
                break;
            case R.id.ry_powin:
                popupWindow.dismiss();
                break;
        }

    }

    public void intoData() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYUSERCOLLECTCOMMOSIRYS, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!s.isEmpty()) {
                    list = new ArrayList<>();
                    collectionModel = ExampleApplication.getInstance().getGson().fromJson(s, CollectionModel.class);
                    if (collectionModel.getStatus() == Constant.ONE && !collectionModel.getCommoditys().isEmpty()) {
                        list = collectionModel.getCommoditys();
                        adapter = new CollectionAdapter(MyCollectionActivity.this, list);
                        svl_collection.setAdapter(adapter);
                    } else {
                        txt_commenttitle.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }

                } else {
                    Toast.makeText(MyCollectionActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                Toast.makeText(MyCollectionActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userid);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
        svl_collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MyCollectionActivity.this, "" + list.get(position).getId(), Toast.LENGTH_LONG).show();
                String commontity = String.valueOf(list.get(position).getId());
                Intent intent = new Intent(MyCollectionActivity.this, ProductdetailsActivity.class);
                intent.putExtra("commodityid", commontity);
                startActivity(intent);
            }
        });
        svl_collection.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                postion = position;
                showPopWindon(svl_collection);
                return true;
            }
        });
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                createMenu(menu);
            }
        };

//        svl_collection.setMenuCreator(swipeMenuCreator);
//        svl_collection.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
//
//                switch (index) {
//                    case 0:
//                        deleteCollection();
//                        postion=position;
//                        Toast.makeText(MyCollectionActivity.this, "删除"+position, Toast.LENGTH_LONG).show();
//
//                        break;
//                    case 1:
//
//                        break;
//                }
//            }
//        });

    }

    //取消收藏
    private void deleteCollection() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.CANCELCOLLECTCOMMODITY, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
             if (!TextUtils.isEmpty(s)){
                 JSONObject jsonObject ;
                 try {
                     jsonObject = new JSONObject(s);
                     int state = jsonObject.getInt("status");
                     String message = jsonObject.getString("message");
                     if (state == Constant.ONE) {
                         intoData();
                         dialog.dismiss();
                         popupWindow.dismiss();
                         Toast.makeText(MyCollectionActivity.this, message, Toast.LENGTH_LONG).show();
                     } else {
                         dialog.dismiss();
                         popupWindow.dismiss();
                         Toast.makeText(MyCollectionActivity.this, message, Toast.LENGTH_LONG).show();
                     }
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("取消收藏请求失败", volleyError.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userid);
                map.put("commodityId", String.valueOf(list.get(postion).getId()));
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    //写入侧滑图片
    private void createMenu(SwipeMenu menu) {
//        SwipeMenuItem item1 = new SwipeMenuItem(
//                getApplicationContext());
//        item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0xE0,
//                0x3F)));
//        item1.setWidth(dp2px(90));
//        item1.setIcon(R.mipmap.ic_action_important);
//        menu.addMenuItem(item1);
        SwipeMenuItem item2 = new SwipeMenuItem(
                getApplicationContext());
        item2.setBackground(new ColorDrawable(Color.rgb(0xF9,
                0x3F, 0x25)));
        item2.setWidth(dp2px(90));
        item2.setIcon(R.mipmap.ic_action_discard);
        menu.addMenuItem(item2);
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
