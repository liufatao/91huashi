package com.huashi.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.huashi.app.adapter.AdderAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.UserAddress;
import com.huashi.app.util.Utils;
import com.huashi.app.view.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/6/1.
 * 地址管理
 */
public class MyAdderActivity extends Activity implements View.OnClickListener {
    private ImageView img_back, img_add;
    private ImageView imgBack;
    private ListView lv_adder;
    private AdderAdapter adderAdapter;
    private Utils utils;
    private String userId;
    private UserAddress userAddress;
    private List<UserAddress.UserAddressesBean> list;
    private int id = -1;
    private int updateStatus = 0;
    private ProgressDialog dialog;
    private PopupWindow delectwindow;
    private RelativeLayout rlMessage;
    private Button btnMsgconfirm;
    private Button btnMsgcancel;
    private TextView txt_delete, txt_compile;
    private String addid;
    private int addposition;
    private MyDialog mydialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myadder);
        intoView();


    }


    private void intoView() {
        utils = new Utils(this);
        userId = utils.getInfomation();
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        img_add = (ImageView) findViewById(R.id.img_add);
        img_add.setOnClickListener(this);
        lv_adder = (ListView) findViewById(R.id.lv_adder);
        dialog = new ProgressDialog(this);
        dialog.setMessage("数据提交中....");
        View view = LayoutInflater.from(this).inflate(R.layout.item_adder, null, false);
        txt_delete = (TextView) view.findViewById(R.id.txt_delete);
        txt_delete.setOnClickListener(this);
        txt_compile = (TextView) view.findViewById(R.id.txt_compile);
        txt_compile.setOnClickListener(this);
        mydialog = new MyDialog(this);
        mydialog.setTitle(R.string.pull_to_refresh_footer_refreshing_label);
        if (userId != null) {
            intoData();
        } else {
            Intent intentlogo = new Intent(this, Login_Activity.class);
            startActivity(intentlogo);
        }
    }

    //删除地址
    private void showdelectaddwindow(View v) {
        View widow;
        LayoutInflater inflater = LayoutInflater.from(this);
        widow = inflater.inflate(R.layout.popwindow_deleteadder, null, false);
        delectwindow = new PopupWindow(widow, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);

        delectwindow.setFocusable(true);
        delectwindow.setOutsideTouchable(false);
        delectwindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        delectwindow.setBackgroundDrawable(new ColorDrawable());
        delectwindow.showAtLocation(widow, Gravity.CENTER, 0, 0);


        rlMessage = (RelativeLayout) widow.findViewById(R.id.rl_message);
        rlMessage.setOnClickListener(this);
        btnMsgconfirm = (Button) widow.findViewById(R.id.btn_msgconfirm);
        btnMsgconfirm.setOnClickListener(this);
        btnMsgcancel = (Button) widow.findViewById(R.id.btn_msgcancel);
        btnMsgcancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_add:
                //添加收货地址
                Intent intentnewadder = new Intent(this, NewAdderActivity.class);
                startActivity(intentnewadder);
                break;
            case R.id.txt_compile:
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    addid = String.valueOf(list.get(position).getId());
                    Intent intentadd = new Intent(MyAdderActivity.this, CompileAdderActivity.class);
                    intentadd.putExtra("addid", addid);
                    startActivity(intentadd);

                }
                break;
            case R.id.txt_delete:
                if (tag != null && tag instanceof Integer) {
                    addposition = (Integer) tag;
                    showdelectaddwindow(txt_delete);
                    addid = String.valueOf(list.get(addposition).getId());
                }
                break;
            case R.id.cb_add:
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    id = list.get(position).getId();
                    Log.e("地址id", id + "" + addposition);
                    updateStatus = 1;
                    intoData();
                    dialog.dismiss();
                }
                break;
            case R.id.btn_msgcancel:
                delectwindow.dismiss();
                break;
            case R.id.rl_message:
                delectwindow.dismiss();
                break;
            case R.id.btn_msgconfirm:
                deleteAdder();
                break;
        }

    }

    //数据初始化
    private void intoData() {
        mydialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYUSERADDRESSES, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!s.isEmpty()) {
                    userAddress = ExampleApplication.getInstance().getGson().fromJson(s, UserAddress.class);
                    if (userAddress.getStatus() == Constant.ONE && !userAddress.getUserAddresses().isEmpty()) {
                        list = new ArrayList<>();
                        list = userAddress.getUserAddresses();
                        adderAdapter = new AdderAdapter(MyAdderActivity.this, list);
                        adderAdapter.setOnChenk(MyAdderActivity.this);
                        adderAdapter.setOnDelete(MyAdderActivity.this);
                        adderAdapter.setOnEdit(MyAdderActivity.this);
                        lv_adder.setAdapter(adderAdapter);
                        if (mydialog.isShowing()) {
                            mydialog.dismiss();
                        }
                    } else {
                        if (mydialog.isShowing()) {
                            mydialog.dismiss();
                        }
                        Toast.makeText(MyAdderActivity.this, userAddress.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    if (mydialog.isShowing()) {
                        mydialog.dismiss();
                    }
                    Toast.makeText(MyAdderActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (mydialog.isShowing()) {
                    mydialog.dismiss();
                }
                Toast.makeText(MyAdderActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("id", String.valueOf(id));
                map.put("updateStatus", String.valueOf(updateStatus));
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    //删除地址
    private void deleteAdder() {
        mydialog.show();
        delectwindow.dismiss();
        StringRequest deleteRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.DELETEUSERADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("删除地址成功", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == Constant.ONE) {
                        Toast.makeText(MyAdderActivity.this, message, Toast.LENGTH_LONG).show();
                        list.remove(addposition);
                        adderAdapter.notifyDataSetChanged();
                        if (mydialog.isShowing()) {
                            mydialog.dismiss();
                        }


                    } else {
                        Toast.makeText(MyAdderActivity.this, message, Toast.LENGTH_LONG).show();
                        if (mydialog.isShowing()) {
                            mydialog.dismiss();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (mydialog.isShowing()) {
                    mydialog.dismiss();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", addid);
                map.put("userId", userId);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(deleteRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intoData();
    }

}
