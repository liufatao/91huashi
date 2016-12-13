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
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.UserAddressModel;
import com.huashi.app.util.Httputil;
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
 * Created by Administrator on 2016/8/8.
 * 新增地址
 */
public class CompileAdderActivity extends Activity implements View.OnClickListener {
    private ImageView imgBack;
    private TextView txtSubmit;
    private EditText etConsigneename;
    private EditText etConsigneenphone;
    private RelativeLayout ryArea;
    private TextView txtAddertitle;
    private TextView txtAdder;
    private EditText etDetaileadder;
    private CheckBox cnDefauta;
    private TextView txt_title;
    private String cityTxt;
    private String username, phonenumber, str_province, str_city, str_district, street;
    private String addid;
    private String userid;
    private Utils utils;
    private UserAddressModel addressModel;
    private int STATUS_ONE_HUNDRED = 100;
    private int STATUS_TWO_HUNDRED = 200;
    private String status;
    private ProgressDialog dialog, progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newadder);
        intoView();
        if (Httputil.isNetworkAvailable(this)) {
            intoData();
        } else {
            Toast.makeText(this, "网络不通畅", Toast.LENGTH_LONG).show();
        }
    }

    private void intoView() {
        addid = getIntent().getStringExtra("addid");
        utils = new Utils(this);
        userid = utils.getInfomation();
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        txtSubmit = (TextView) findViewById(R.id.txt_submit);
        txtSubmit.setOnClickListener(this);
        etConsigneename = (EditText) findViewById(R.id.et_consigneename);
        etConsigneenphone = (EditText) findViewById(R.id.et_consigneenphone);
        ryArea = (RelativeLayout) findViewById(R.id.ry_area);
        ryArea.setOnClickListener(this);
        txtAddertitle = (TextView) findViewById(R.id.txt_addertitle);
        txtAdder = (TextView) findViewById(R.id.txt_adder);
        etDetaileadder = (EditText) findViewById(R.id.et_detaileadder);
        cnDefauta = (CheckBox) findViewById(R.id.cn_defauta);
        cnDefauta.setOnClickListener(this);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText("修改用户地址");


    }

    //修改地址
    private void UpdateUserAdder() {
        progressDialog = ProgressDialog.show(CompileAdderActivity.this, "", "数据提交中...");
        username = etConsigneename.getText().toString().trim();
        phonenumber = etConsigneenphone.getText().toString().trim();
        street = etDetaileadder.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.UPDATEUSERADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("请求成功", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == Constant.ONE) {
                        Toast.makeText(CompileAdderActivity.this, message, Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(CompileAdderActivity.this, message, Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                map.put("userId", userid);
                map.put("id", addid);
                map.put("name", username);
                map.put("phone", phonenumber);
                map.put("province", str_province);
                map.put("city", str_city);
                map.put("district", str_district);
                map.put("street", street);
                map.put("status", status);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    //初始化数据
    private void intoData() {
        dialog = ProgressDialog.show(CompileAdderActivity.this, "提示", "玩命加载中...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYUSERADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("初始化数据成功", s);

                if (s != null) {
                    addressModel = ExampleApplication.getInstance().getGson().fromJson(s, UserAddressModel.class);
                    Log.e("addressModel", addressModel.getUserAddress().getProvince());
                    if (addressModel.getUserAddress() != null) {

                        Valuation(addressModel);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("初始化数据失败", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userid);
                map.put("id", addid);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    //赋值
    private void Valuation(UserAddressModel addressModel) {
        UserAddressModel.UserAddressBean model = addressModel.getUserAddress();
        etConsigneename.setText(model.getName());
        etConsigneenphone.setText(model.getPhone());
        txtAdder.setText(model.getProvince() + model.getCity() + model.getDistrict());
        etDetaileadder.setText(model.getStreet());
        if (model.getStatus() == STATUS_ONE_HUNDRED) {
            cnDefauta.setChecked(true);
            status = String.valueOf(STATUS_ONE_HUNDRED);
        } else {
            cnDefauta.setChecked(false);
            status = String.valueOf(STATUS_TWO_HUNDRED);
        }
        str_province = model.getProvince();
        str_city = model.getCity();
        str_district = model.getDistrict();
        dialog.dismiss();

    }

    //选择城市
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
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<>(this,
                ccities[index][index2]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_submit:
                //提交
                UpdateUserAdder();
                break;
            case R.id.cn_defauta:
                //是否默认地址
                if (cnDefauta.isChecked()) {
                    status = String.valueOf(STATUS_ONE_HUNDRED);
                    Toast.makeText(CompileAdderActivity.this, "true", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CompileAdderActivity.this, "fales", Toast.LENGTH_LONG).show();
                    status = String.valueOf(STATUS_TWO_HUNDRED);
                }
                break;
            case R.id.ry_area:
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
        }
    }

}
