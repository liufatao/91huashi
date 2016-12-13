package com.huashi.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.util.Httputil;
import com.huashi.app.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/20.
 * 意见反馈
 */
public class FeedBackActivity extends Activity implements View.OnClickListener {
    private ImageView img_back;
    private TextView txt_over;
    private RequestQueue requestQueue;
    private ProgressDialog dialog;
    private Utils utils;
    private String userId;
    private EditText edt_idea;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        intoView();
    }

    private void intoView() {
        utils = new Utils(this);
        userId = utils.getInfomation();
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txt_over = (TextView) findViewById(R.id.txt_over);
        txt_over.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
        edt_idea = (EditText) findViewById(R.id.edt_idea);
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("数据提交中");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                //返回
                finish();
                break;
            case R.id.txt_over:
                if (Httputil.isNetworkAvailable(this)) {
                    submitOffer();
                } else {
                    Toast.makeText(this, R.string.networkexception, Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    //反馈建议
    private void submitOffer() {
        dialog.show();
        content = edt_idea.getText().toString();
        if (content.isEmpty()) {
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.INSERTFEEDBACK, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("反馈意见成功", s);
                if (!s.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");
                        if (status == Constant.ONE) {
                            dialog.dismiss();
                        }
                        Toast.makeText(FeedBackActivity.this, message, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(FeedBackActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("反馈意见失败", volleyError.toString());
                dialog.dismiss();
                Toast.makeText(FeedBackActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("content", content);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}
