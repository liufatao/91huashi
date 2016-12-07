package com.huashi.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EvaluateActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText etContent;
    private Button btnSubmit;
    private Utils utils;
    private String userId;
    private String commentId;
    private String orderId;
    private String id;
    String content=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        intView();
    }
    private void intView(){
        utils=new Utils(this);
        userId=utils.getInfomation();
        commentId=getIntent().getStringExtra("commodityId");
        orderId=getIntent().getStringExtra("orderId");
        id=getIntent().getStringExtra("id");
        imgBack = (ImageView) findViewById(R.id.img_back);
        etContent = (EditText) findViewById(R.id.et_content);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSubmit();
            }
        });

    }
    //提交评价内容
    private void getSubmit(){
        content=etContent.getText().toString();
        Log.e("上传的内容","用户"+userId+"订单"+orderId+"商品"+commentId+"内容"+content);
        if (content==null){
            Toast.makeText(EvaluateActivity.this,"评论为空",Toast.LENGTH_LONG).show();
            return;
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.INSERTCOMMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("评论成功",s.toString());
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    int status=jsonObject.getInt("status");
                    String message=jsonObject.getString("message");
                    if (status== Constant.ONE){
                        finish();
                    }
                    Toast.makeText(EvaluateActivity.this,message,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("评论失败",volleyError.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("userId",userId);
                map.put("orderId",orderId);
                map.put("commodityId",commentId);
                map.put("content",content);
                map.put("id",id);
                return map;

            }
        };
    }
}
