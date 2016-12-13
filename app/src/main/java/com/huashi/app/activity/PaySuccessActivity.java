package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.R;
import com.huashi.app.adapter.PayRecommoendAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.RecommendCommtity;
import com.huashi.app.util.Httputil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 * 支付成功
 */
public class PaySuccessActivity extends Activity implements View.OnClickListener {
    private PayRecommoendAdapter payRecommoendAdapter;
    private ImageView imgBack;
    private Button btnBuy;
    private GridView gvPayrecommend;
    private String orderId;
    private RecommendCommtity commtity;
    private List<RecommendCommtity.CommoditysBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paysuccess);
        intoView();
        getCommtityInfo();
        if (Httputil.isNetworkAvailable(this)) {
            getCommtityInfo();
        } else {
            Toast.makeText(this, "网络不通畅", Toast.LENGTH_LONG).show();
        }
    }

    private void intoView() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        btnBuy = (Button) findViewById(R.id.btn_buy);
        btnBuy.setOnClickListener(this);
        gvPayrecommend = (GridView) findViewById(R.id.gv_payrecommend);
        list = new ArrayList<>();
        gvPayrecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PaySuccessActivity.this, ProductdetailsActivity.class);
                Log.e("商品id", list.get(position).getId() + "");
                intent.putExtra("commodityid", String.valueOf(list.get(position).getId()));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_buy:
                startActivity(new Intent(PaySuccessActivity.this, MainActivity.class));
                break;
        }
    }

    private void getCommtityInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYREXOMMWNSCOMMODITIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("推荐商品请求成功", s);
                if (s != null) {
                    commtity = ExampleApplication.getInstance().getGson().fromJson(s, RecommendCommtity.class);
                }
                Log.e("图片", commtity.getCommoditys().get(0).getPictures());
                list = commtity.getCommoditys();
                payRecommoendAdapter = new PayRecommoendAdapter(PaySuccessActivity.this, list);
                gvPayrecommend.setAdapter(payRecommoendAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("推荐商品请求失败", volleyError.toString());
            }
        });
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

}
