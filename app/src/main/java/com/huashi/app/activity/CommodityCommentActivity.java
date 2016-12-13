package com.huashi.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.R;
import com.huashi.app.adapter.CommodityCommentAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.CommodityCommentModel;
import com.huashi.app.util.Httputil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/3.
 * 商品评论
 */

public class CommodityCommentActivity extends Activity implements View.OnClickListener {
    private ImageView imgBack;
    private ListView lvCommity;
    private CommodityCommentAdapter commentAdapter;
    private CommodityCommentModel commentModel;
    private List<CommodityCommentModel> list;
    private String commodityid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commoditycomment);
        intoView();
        if (Httputil.isNetworkAvailable(this)) {
            intoData();
        } else {
            Toast.makeText(this, "网络不通畅", Toast.LENGTH_LONG).show();
        }
    }

    private void intoView() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        lvCommity = (ListView) findViewById(R.id.lv_commity);
    }

    private void intoData() {
        commodityid = getIntent().getStringExtra("commodityid");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYCOMMENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                commentModel = ExampleApplication.getInstance().getGson().fromJson(s, CommodityCommentModel.class);
                Log.e("请求成功", s);
                if (commentModel.getComments() != null) {
                    list = new ArrayList<>();
                    for (int i = 1; i <= commentModel.getComments().size(); i++) {
                        list.add(commentModel);
                    }
                    commentAdapter = new CommodityCommentAdapter(CommodityCommentActivity.this, list);
                    lvCommity.setAdapter(commentAdapter);
                } else {
                    Toast.makeText(CommodityCommentActivity.this, "暂无评论数据", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("请求失败", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("commodityId", commodityid);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

}
