package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.huashi.app.adapter.CommentFocusAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.OrderStateModel;
import com.huashi.app.util.Httputil;
import com.huashi.app.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentFocusActivity extends Activity {
    private ImageView imgBack;
    private ListView lvComment;
    private OrderStateModel orderStateModel;
    private List<OrderStateModel.OrderBean.DetailsBean> modelList;
    private CommentFocusAdapter commentFocusAdapter;
    private Utils utils;
    private String userId;
    private String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_focus);
        intoView();
        if (Httputil.isNetworkAvailable(this)) {
            getComtityInfo();
        }else {
            Toast.makeText(this,"网络不通畅",Toast.LENGTH_LONG).show();
        }
    }
    private void intoView(){
        utils=new Utils(CommentFocusActivity.this);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });
        lvComment = (ListView) findViewById(R.id.lv_comment);
        userId=utils.getInfomation();
        orderId=getIntent().getStringExtra("orderId");

    }
    private void getComtityInfo(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERORDERBYID, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("评价中心请求成功",s.toString());
                orderStateModel= ExampleApplication.getInstance().getGson().fromJson(s,OrderStateModel.class);
                modelList=orderStateModel.getOrder().getDetails();
                commentFocusAdapter=new CommentFocusAdapter(CommentFocusActivity.this,modelList);
                lvComment.setAdapter(commentFocusAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("评价中心请求失败",volleyError.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("userId",userId);
                map.put("id",orderId);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
        lvComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("订单id",modelList.get(position).getOrder_id()+"商品id"+modelList.get(position).getCommodity_id());
                Intent intent=new Intent(CommentFocusActivity.this,EvaluateActivity.class);
                intent.putExtra("orderId",modelList.get(position).getOrder_id()+"");
                intent.putExtra("commodityId",modelList.get(position).getCommodity_id()+"");
                intent.putExtra("id",modelList.get(position).getId()+"");
                if (modelList.get(position).getHas_comment()==1){
                    return;
                }else {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getComtityInfo();
    }
}
