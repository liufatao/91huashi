package com.huashi.app.activity;

import android.app.Activity;
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
import com.huashi.app.adapter.MyCommentAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.library.view.SwipeMenu;
import com.huashi.app.library.view.SwipeMenuCreator;
import com.huashi.app.library.view.SwipeMenuItem;
import com.huashi.app.model.MyCommentModel;
import com.huashi.app.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 我的评论
 * Created by Administrator on 2016/5/20.
 */
public class CommentActivity extends Activity implements View.OnClickListener {
    private List<MyCommentModel.CommodityCommentsBean> list;
    private MyCommentAdapter commentAdapter;
    private MyCommentModel myCommentModel;
    private ImageView img_back;
    private String commodityid;
    private ListView slv_comment;
    private Utils utils;
    private String userId;
    private TextView txt_commenttitle;
    private ProgressDialog dialog;
    private SwipeRefreshLayout swipe_layout;
    private int pageNumber = 0;
    private int maxPageNumber = 0;
    private PopupWindow popupWindow;
    private Button btnCommentCancel;
    private Button btnCommentDelete;
    private RelativeLayout ryPowin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        intoIntoView();


    }

    //初始化控件
    private void intoIntoView() {
        utils = new Utils(this);
        userId = utils.getInfomation();
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        slv_comment = (ListView) findViewById(R.id.slv_comment);
        txt_commenttitle = (TextView) findViewById(R.id.txt_commenttitle);
        dialog = new ProgressDialog(this);
        dialog.setMessage("数据提交中...");
        if (userId != null) {
            intoData();
        } else {
            Toast.makeText(this, "请先登录", Toast.LENGTH_LONG).show();
        }

        swipe_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipe_layout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置刷新时动画的颜色
        swipe_layout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//
                        swipe_layout.setRefreshing(false);
                    }
                }, 3000);
                intoData();

            }
        });
        //处理滑动冲突
        slv_comment.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                boolean enable = false;
                if (slv_comment != null && slv_comment.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = slv_comment.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = slv_comment.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipe_layout.setEnabled(enable);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
//        //上拉加载
//        swipe_layout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
//
//            @Override
//            public void onLoad() {
//                swipe_layout.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // 加载完后调用该方法
//                        swipe_layout.setLoading(false);
//                    }
//                }, 1500);
//                pageNumber+=1;
//                boolean b=true;
//              if (pageNumber==maxPageNumber){
//                  Toast.makeText(CommentActivity.this,"到底了",Toast.LENGTH_LONG).show();
//                  b=false;
//                  pageNumber=maxPageNumber;
//              }
//                if (b){
//                    pageData();
//                }
//
//            }
//        });

    }

    private void showPopWindon(View view) {
        View windon;
        LayoutInflater inflater = LayoutInflater.from(this);
        windon = inflater.inflate(R.layout.popwindow_comment_delete, null);
        popupWindow = new PopupWindow(windon, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
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

    //  //分页
//    private void pageData(){
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYUSERCOMMENTS, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                if (s!=null){
//                    myCommentModel=gson.fromJson(s,MyCommentModel.class);
//                }
//                list.addAll(myCommentModel.getCommodityComments());
//                commentAdapter.notifyDataSetChanged();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> map=new HashMap<>();
//                map.put("userId",userId);
//                map.put("pageNumber",String.valueOf(pageNumber));
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
    //初始化数据
    public void intoData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYUSERCOMMENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!s.isEmpty()) {
                    myCommentModel = ExampleApplication.getInstance().getGson().fromJson(s, MyCommentModel.class);
                    if (myCommentModel.getStatus()==Constant.ONE && !myCommentModel.getCommodityComments().isEmpty()){
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            maxPageNumber = jsonObject.getInt("maxPageNumber");
                            if (list != null) {
                                list.clear();
                            }
                            list = new ArrayList<>();
                            list = myCommentModel.getCommodityComments();
                            commentAdapter = new MyCommentAdapter(CommentActivity.this, list);
                            slv_comment.setAdapter(commentAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                            txt_commenttitle.setVisibility(View.VISIBLE);
                    }

                }else {
                    Toast.makeText(CommentActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                txt_commenttitle.setVisibility(View.GONE);
                Toast.makeText(CommentActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                if (pageNumber >= maxPageNumber) {
                    pageNumber = maxPageNumber;
                    map.put("pageNumber", String.valueOf(pageNumber));
                }
                Log.e("第一次", pageNumber + "");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
        slv_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String commontity = String.valueOf(list.get(position).getCommodityId());
                Intent intent = new Intent(CommentActivity.this, ProductdetailsActivity.class);
                intent.putExtra("commodityid", commontity);
                startActivity(intent);
            }
        });
        slv_comment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                commodityid=String.valueOf(list.get(position).getId());
                Log.e("商品名称",list.get(position).getName()+"商品id"+commodityid);
                showPopWindon(slv_comment);
                return true;
            }
        });

        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                createMenu(menu);
            }
        };

//        slv_comment.setMenuCreator(swipeMenuCreator);
//        slv_comment.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
//
//                switch (index) {
//                    case 0:
//                        Toast.makeText(CommentActivity.this, "删除评论", Toast.LENGTH_LONG).show();
//                        commentAdapter.notifyDataSetChanged();
//                        String Commentid=String.valueOf(list.get(position).getId());
//                        deleteComment(position,Commentid);
//                        break;
//                    case 1:
//
//                        break;
//                }
//            }
//        });

    }

    //删除评论
    private void deleteComment(final String commentId) {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.DELETECOMMODITYCOMMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("删除成功", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == Constant.ONE) {
                        intoData();
                        dialog.dismiss();
                        popupWindow.dismiss();
                    }
                    Toast.makeText(CommentActivity.this, message, Toast.LENGTH_LONG).show();
                    popupWindow.dismiss();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("删除失败", volleyError.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("commentId", commentId);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    //写入收藏
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                //返回
                finish();
                break;
            case R.id.btn_comment_cancel:
                popupWindow.dismiss();
                break;
            case R.id.btn_comment_delete:
                deleteComment(commodityid);
                break;
            case R.id.ry_powin:
                popupWindow.dismiss();
                break;
        }
    }
}
