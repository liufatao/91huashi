package com.huashi.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.R;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.util.StringUtils;
import com.huashi.app.util.Utils;
import com.huashi.app.view.city.AddressData;
import com.huashi.app.view.city.MyAlertDialog;
import com.huashi.app.view.city.OnWheelChangedListener;
import com.huashi.app.view.city.adapter.AbstractWheelTextAdapter;
import com.huashi.app.view.city.adapter.ArrayWheelAdapter;
import com.huashi.app.view.city.view.WheelView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/27.
 * 添加新地址
 */
public class NewAdderActivity extends Activity implements View.OnClickListener {
    private ImageView imgBack;
    private EditText etConsigneename;
    private EditText etConsigneenphone;
    private RelativeLayout ryArea;
    private EditText etDetaileadder;
    private CheckBox cnDefauta;
    private String cityTxt;
    private TextView txtAdder, txtSubmit;
    private String str_province, str_city, str_district;
    private String userId;
    private Utils utils;
    private int status = 200;
    private ProgressDialog dialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newadder);
        intoView();
    }

    private void intoView() {
        utils = new Utils(this);
        userId = utils.getInfomation();
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        etConsigneename = (EditText) findViewById(R.id.et_consigneename);
        etConsigneenphone = (EditText) findViewById(R.id.et_consigneenphone);
        ryArea = (RelativeLayout) findViewById(R.id.ry_area);
        ryArea.setOnClickListener(this);
        etDetaileadder = (EditText) findViewById(R.id.et_detaileadder);
        cnDefauta = (CheckBox) findViewById(R.id.cn_defauta);
        cnDefauta.setOnClickListener(this);
        txtAdder = (TextView) findViewById(R.id.txt_adder);
        txtSubmit = (TextView) findViewById(R.id.txt_submit);
        txtSubmit.setOnClickListener(this);
        dialogs = new ProgressDialog(this);
        dialogs.setMessage("数据提交中...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ry_area:

                // TODO Auto-generated method stub
                View view = dialogm();
                final MyAlertDialog dialog = new MyAlertDialog(
                        this).builder().setTitle("城市选择")

                        .setView(view).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                dialog.setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(cityTxt);
                        Log.e("NewAdderActivity", cityTxt);
                        txtAdder.setText(cityTxt);
                    }
                });
                dialog.show();


                break;
            case R.id.txt_submit:

                userAdder();
                break;
            case R.id.cn_defauta:
                status = 100;
                break;
        }
    }

    /***
     * 添加收货地址
     *
     * @return
     */
    private void userAdder() {
        final String name = etConsigneename.getText().toString();
        final String phone = etConsigneenphone.getText().toString();
        final String street = etDetaileadder.getText().toString();
        if (StringUtils.isNullOrEmpty(name)) {
            Toast.makeText(NewAdderActivity.this, "收件人不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (!StringUtils.isPhoneNo(phone)) {
            Toast.makeText(this, "电话号码不正确", Toast.LENGTH_LONG).show();
            etConsigneenphone.getText().clear();
            return;
        }
        if (StringUtils.isNullOrEmpty(phone)) {
            Toast.makeText(NewAdderActivity.this, "联系电话不能为空", Toast.LENGTH_LONG).show();
            etConsigneenphone.getText().clear();
            return;
        }
        if (StringUtils.isNullOrEmpty(str_province)) {
            Toast.makeText(NewAdderActivity.this, "省份不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (StringUtils.isNullOrEmpty(str_city)) {
            Toast.makeText(NewAdderActivity.this, "城市不能为空", Toast.LENGTH_LONG).show();
            return;

        }
        if (StringUtils.isNullOrEmpty(str_district)) {
            Toast.makeText(NewAdderActivity.this, "街道地址不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (street.isEmpty() || street.length() >= 10) {
            Toast.makeText(NewAdderActivity.this, "详细地址不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        Log.e("上传地址信息", name + phone + street);
        dialogs.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.INSERTUSERADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("添加地址请求成功", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {
                        Toast.makeText(NewAdderActivity.this, "地址添加成功", Toast.LENGTH_LONG).show();
                        dialogs.dismiss();
                        finish();
                    } else {
                        Toast.makeText(NewAdderActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("添加地址请求失败", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                map.put("name", name);
                map.put("phone", phone);
                map.put("province", str_province);
                map.put("city", str_city);
                map.put("district", str_district);
                map.put("street", street);
                map.put("status", String.valueOf(status));
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);

    }


    private View dialogm() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.wheelcity_cities_layout, null,false);
        final WheelView country = (WheelView) contentView
                .findViewById(R.id.wheelcity_country);
        country.setVisibleItems(3);
        country.setViewAdapter(new CountryAdapter(this));

        final String cities[][] = AddressData.CITIES;
        final String ccities[][][] = AddressData.COUNTIES;
        final WheelView city = (WheelView) contentView
                .findViewById(R.id.wheelcity_city);
        city.setVisibleItems(0);

        // 地区选择
        final WheelView ccity = (WheelView) contentView
                .findViewById(R.id.wheelcity_ccity);
        ccity.setVisibleItems(0);// 不限城市

        country.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCities(city, cities, newValue);
                cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                str_province = AddressData.PROVINCES[country.getCurrentItem()];
                str_city = AddressData.CITIES[country.getCurrentItem()][city.getCurrentItem()];
                str_district = AddressData.COUNTIES[country.getCurrentItem()][city.getCurrentItem()][ccity.getCurrentItem()];
            }
        });

        city.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updatecCities(ccity, ccities, country.getCurrentItem(),
                        newValue);
                cityTxt = AddressData.PROVINCES[country.getCurrentItem()]

                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]

                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                str_province = AddressData.PROVINCES[country.getCurrentItem()];
                str_city = AddressData.CITIES[country.getCurrentItem()][city.getCurrentItem()];
                str_district = AddressData.COUNTIES[country.getCurrentItem()][city.getCurrentItem()][ccity.getCurrentItem()];
            }
        });

        ccity.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                str_province = AddressData.PROVINCES[country.getCurrentItem()];
                str_city = AddressData.CITIES[country.getCurrentItem()][city.getCurrentItem()];
                str_district = AddressData.COUNTIES[country.getCurrentItem()][city.getCurrentItem()][ccity.getCurrentItem()];

            }

        });

        country.setCurrentItem(1);// 默认设置北京
        city.setCurrentItem(1);
        ccity.setCurrentItem(1);
        return contentView;
    }

    /**
     * Adapter for countries
     */
    private class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
        private String countries[] = AddressData.PROVINCES;

        /**
         * Constructor
         */
        protected CountryAdapter(Context context) {
            super(context, R.layout.wheelcity_country_layout, NO_RESOURCE);
            setItemTextResource(R.id.wheelcity_country_name);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return countries.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return countries[index];
        }
    }

    /**
     * Updates the city wheel
     */
    private void updateCities(WheelView city, String cities[][], int index) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<>(this,
                cities[index]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    /**
     * Updates the ccity wheel
     */
    private void updatecCities(WheelView city, String ccities[][][], int index,
                               int index2) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
                ccities[index][index2]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

}
