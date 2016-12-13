package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.R;
import com.huashi.app.adapter.SearchAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.SearchModel;
import com.huashi.app.util.Httputil;
import com.huashi.app.view.PullToRefreshView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/3.
 * 搜索展示
 */
public class CommodityDispayActivity extends Activity implements View.OnClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    private TextView txt_recommend, txt_comprehensive, txt_price, txt_evaluation, txt_search;
    private EditText edi_search;
    private String word;
    private int searchType = 100;//搜索类型
    private ImageView img_back;
    private SearchAdapter searchAdapter;
    private SearchModel searchModel;
    private GridView gv_dispay;
    private List<SearchModel.CommoditysBean> list;
    private PullToRefreshView mPullToRefreshView;
    private int pageNumber = 0;
    private boolean isNo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commoditydispay);
        intoView();
        getinfo();
        if (Httputil.isNetworkAvailable(this)) {
            getinfo();
        } else {
            Toast.makeText(this, "网络不通畅", Toast.LENGTH_LONG).show();
        }
    }

    private void intoView() {
        word = getIntent().getStringExtra("word");
        txt_recommend = (TextView) findViewById(R.id.txt_recommend);
        txt_recommend.setOnClickListener(this);
        txt_search = (TextView) findViewById(R.id.txt_search);
        txt_search.setOnClickListener(this);
        txt_comprehensive = (TextView) findViewById(R.id.txt_comprehensive);
        txt_comprehensive.setOnClickListener(this);
        txt_price = (TextView) findViewById(R.id.txt_price);
        txt_price.setOnClickListener(this);
        txt_evaluation = (TextView) findViewById(R.id.txt_evaluation);
        txt_evaluation.setOnClickListener(this);
        edi_search = (EditText) findViewById(R.id.edi_search);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        gv_dispay = (GridView) findViewById(R.id.gv_dispay);
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        if (word != null) {
            edi_search.setText(word);
        }
        gv_dispay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CommodityDispayActivity.this, ProductdetailsActivity.class);
                intent.putExtra("commodityid", String.valueOf(list.get(position).getId()));
                startActivity(intent);
                Log.e("商品id", list.get(position).getId() + "");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_recommend:
                //推荐
                searchType = 200;
                getinfo();
                break;
            case R.id.txt_comprehensive:
                //综合
                searchType = 300;
                getinfo();
                break;
            case R.id.txt_price:
                //价格
                if (isNo) {
                    searchType = 400;
                    txt_price.setText("价格↑");
                } else {
                    txt_price.setText("价格↓");
                    searchType = 500;
                }
                isNo = !isNo;
                getinfo();
                break;
            case R.id.txt_evaluation:
                //评价
                searchType = 600;
                getinfo();
                break;
            case R.id.txt_search:
                getinfo();
                break;
            case R.id.img_back:
                finish();
                break;
        }

    }

    //搜索关键字
    private void getinfo() {
        if (word.isEmpty()) {
            Toast.makeText(CommodityDispayActivity.this, "搜索内容为空", Toast.LENGTH_LONG).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.SEARCHCOMMODITYDATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)) {
                    searchModel = ExampleApplication.getInstance().getGson().fromJson(s, SearchModel.class);
                    if (searchModel.getCommoditys().size() >= 1) {
                        list = searchModel.getCommoditys();
                        searchAdapter = new SearchAdapter(CommodityDispayActivity.this, list);
                        gv_dispay.setAdapter(searchAdapter);
                    } else {
                        Toast.makeText(CommodityDispayActivity.this, "没找到此商品", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(CommodityDispayActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("搜索失败", volleyError.toString());
                Toast.makeText(CommodityDispayActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("word", word);
                map.put("searchType", searchType + "");
                map.put("pageNumber", pageNumber + "");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    /**
     * 上拉加载
     *
     */
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getinfopage();
                mPullToRefreshView.onFooterRefreshComplete();
            }
        }, 1000);
        if (pageNumber >= searchModel.getMaxPageNum()) {
            pageNumber = searchModel.getMaxPageNum();
        } else {
            pageNumber += 1;
            getinfopage();
        }
    }

    /***
     * 下拉刷新
     *
     */
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshView.onHeaderRefreshComplete();
            }
        }, 1000);
    }


    //搜索关键字
    private void getinfopage() {
        if (word.isEmpty()) {
            Toast.makeText(CommodityDispayActivity.this, "搜索内容为空", Toast.LENGTH_LONG).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.SEARCHCOMMODITYDATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("搜索成功", s);
                if (!s.isEmpty()) {
                    searchModel = ExampleApplication.getInstance().getGson().fromJson(s, SearchModel.class);
                    if (searchModel.getCommoditys().size() >= 1) {
                        list.addAll(searchModel.getCommoditys());
                        searchAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(CommodityDispayActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("搜索失败", volleyError.toString());
                Toast.makeText(CommodityDispayActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("word", word);
                map.put("searchType", 100 + "");
                map.put("pageNumber", pageNumber + "");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

}
