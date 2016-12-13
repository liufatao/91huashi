package com.huashi.app.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.adapter.Logistics_adapter;
import com.huashi.app.api.HuashiApi;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.LogisticsModel;
import com.huashi.app.util.Httputil;

import java.util.HashMap;
import java.util.Map;

public class LogisticsDetailActivity extends Activity {
    private ImageView imgBack;
    private ImageView imgCommodity;
    private TextView txtCommodityname;
    private TextView txtWaybillnumber;
    private TextView txtLogisticscompany;
    private ListView lvLogistics;
    private LogisticsModel logisticsModel;
    private Logistics_adapter logisticsAdapter;
    private String orderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_detail);
        intoView();
        getInfo();
        if (Httputil.isNetworkAvailable(this)) {
            getInfo();
        } else {
            Toast.makeText(this, "网络不通畅", Toast.LENGTH_LONG).show();
        }
    }

    private void intoView() {
        orderid = getIntent().getStringExtra("orderId");
        Log.e("物流订单号", orderid);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgCommodity = (ImageView) findViewById(R.id.img_commodity);
        txtCommodityname = (TextView) findViewById(R.id.txt_commodityname);
        txtWaybillnumber = (TextView) findViewById(R.id.txt_waybillnumber);
        txtLogisticscompany = (TextView) findViewById(R.id.txt_logisticscompany);
        lvLogistics = (ListView) findViewById(R.id.lv_logistics);

    }

    private void getInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.LOGISTIC, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("物流信息请求成功", s);
                logisticsModel = ExampleApplication.getInstance().getGson().fromJson(s, LogisticsModel.class);
                if (logisticsModel.getStatus() == Constant.ONE) {
                    setinfo(logisticsModel);
                    logisticsAdapter = new Logistics_adapter(LogisticsDetailActivity.this, logisticsModel.getLogisticsOrder().getLogisticsInfo());
                    lvLogistics.setAdapter(logisticsAdapter);
                } else {
                    Toast.makeText(LogisticsDetailActivity.this, "没有物流信息", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("物流信息请求失败", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("order_id", orderid);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    private void setinfo(LogisticsModel detailsBean) {

        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgCommodity = (ImageView) findViewById(R.id.img_commodity);
        //    ImageLoader加载网络图片

        ImageLoader imageLoader = new ImageLoader(ExampleApplication.getInstance().getRequestQueue(), new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imgCommodity,
                R.mipmap.load, R.mipmap.load);
        imageLoader.get(HuashiApi.PICTUREURL + detailsBean.getOrder().getDetails().get(0).getPic_url() + "!m.jpg", listener);
        txtCommodityname.setText(detailsBean.getOrder().getDetails().get(0).getName());
        txtWaybillnumber.setText(String.format(getString(R.string.waybillnumber),detailsBean.getLogisticsOrder().getTracking_no()));
        txtLogisticscompany.setText(String.format(getString(R.string.logisticscompany),detailsBean.getLogisticsOrder().getLogisticsName()) );
    }

}
