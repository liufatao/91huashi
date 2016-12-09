package com.huashi.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.activity.CommodityDispayActivity;
import com.huashi.app.activity.ProductdetailsActivity;
import com.huashi.app.activity.SearchActivity;
import com.huashi.app.adapter.CommodityAdapter;
import com.huashi.app.adapter.RecommendAdapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.IndexAreaCommodityModel;
import com.huashi.app.model.IndexPublicityModel;
import com.huashi.app.model.IndexReDetailModel;
import com.huashi.app.util.Httputil;
import com.huashi.app.view.HorizontalListview;
import com.huashi.app.view.MyDialog;
import com.huashi.app.view.PullDownElasticImp;
import com.huashi.app.view.PullDownScrollview;
import com.huashi.app.view.SlideShowView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * 主页面
 */
public class Home_Fragment extends Fragment implements PullDownScrollview.RefreshListener, View.OnClickListener {
    private SlideShowView mSlideShowView;
    private PullDownScrollview pullDownScrollview;
    private MyDialog dialog;
    private RecommendAdapter recommendAdapter = null;
    private View home_view;
    private Activity activity;
    private ImageView imgbtn_search;
    private SimpleDateFormat format = null;
    private LinearLayout mGallery;
    private LayoutInflater mInflater;
    private RecommendAdapter adapter;
    private HorizontalListview horizontalListview;
    private ImageView img_giftbox, img_horticultre, img_petproducts, img_fishinggear, img_specialty;
    private ImageView img_facility, img_saping, img_manure, img_pesticide;
    private GridView grv_commodity;
    private CommodityAdapter commodityAdapter;
    private int staus = -1;
    private String img_url;
    private ArrayList<IndexReDetailModel> list;
    private ArrayList<IndexAreaCommodityModel> commoditylist;
    private IndexPublicityModel publicityModel;
    private List<String> imglist;
    private IndexReDetailModel indexReDetailModel;
    private IndexAreaCommodityModel commodityModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        home_view = inflater.inflate(R.layout.frment_home,container,false);

        return home_view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = this.getActivity();
        intoView();
        if (Httputil.isNetworkAvailable(activity)) {
            intoResource();
        }else {
            Toast.makeText(activity,R.string.networkexception,Toast.LENGTH_LONG).show();
        }

    }

    private void intoView() {
        mInflater = LayoutInflater.from(activity);
        horizontalListview = (HorizontalListview) home_view.findViewById(R.id.hzl_recomend);
        mSlideShowView = (SlideShowView) home_view.findViewById(R.id.sidview);
        grv_commodity = (GridView) home_view.findViewById(R.id.grv_commodity);
        imgbtn_search = (ImageView) home_view.findViewById(R.id.imgbtn_search);
        imgbtn_search.setOnClickListener(this);
        pullDownScrollview = (PullDownScrollview) home_view.findViewById(R.id.refresh_root);
        pullDownScrollview.setRefreshListener(this);
        //我的宝贝
        img_giftbox = (ImageView) home_view.findViewById(R.id.img_giftbox);
        img_giftbox.setOnClickListener(this);
        img_fishinggear = (ImageView) home_view.findViewById(R.id.img_fishinggear);
        img_fishinggear.setOnClickListener(this);
        img_horticultre = (ImageView) home_view.findViewById(R.id.img_horticultre);
        img_horticultre.setOnClickListener(this);
        img_petproducts = (ImageView) home_view.findViewById(R.id.img_petproducts);
        img_petproducts.setOnClickListener(this);
        img_specialty = (ImageView) home_view.findViewById(R.id.img_specialty);
        img_specialty.setOnClickListener(this);
        //集团采购
        img_facility = (ImageView) home_view.findViewById(R.id.img_facility);
        img_facility.setOnClickListener(this);
        img_saping = (ImageView) home_view.findViewById(R.id.img_saping);
        img_saping.setOnClickListener(this);
        img_manure = (ImageView) home_view.findViewById(R.id.img_manure);
        img_manure.setOnClickListener(this);
        img_pesticide = (ImageView) home_view.findViewById(R.id.img_pesticide);
        img_pesticide.setOnClickListener(this);
        pullDownScrollview.setPullDownElastic(new PullDownElasticImp(activity));
        dialog=new MyDialog(activity);
        dialog.setTitle("正在加载...");
        dialog.show();
    }

    /***
     * 网络请求资源
     */
    public void intoResource() {
        StringRequest request = new StringRequest(Request.Method.POST, RequestUrlsConfig.HOME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //返回值不为空
                if (!s.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        staus = jsonObject.getInt("status");//获得返回值
                        if (staus == Constant.ONE) {
                            //服务器响应成功轮播图片路径
                            JSONArray jsonArray = jsonObject.getJSONArray("publicitys");
                            imglist = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                publicityModel = new IndexPublicityModel();
                                JSONObject object = (JSONObject) jsonArray.opt(i);
                                img_url = object.getString("src");
                                publicityModel.setSrc(img_url);
                                imglist.add(publicityModel.getSrc());
                            }
                            mSlideShowView.setImageUris(imglist);
                            pullDownScrollview.setVisibility(View.VISIBLE);

                            //热卖推荐产品
                            JSONArray jsonArraydetails = jsonObject.getJSONArray("details");
                            list = new ArrayList<>();
                            for (int i = 0; i < jsonArraydetails.length(); i++) {
                                indexReDetailModel = new IndexReDetailModel();
                                JSONObject object = (JSONObject) jsonArraydetails.opt(i);
                                indexReDetailModel.setCommodityId(object.getInt("commodityId"));
                                indexReDetailModel.setName(object.getString("name"));
                                indexReDetailModel.setPicture(object.getString("picture"));
                                indexReDetailModel.setPrice(object.getString("price"));
                                list.add(indexReDetailModel);

                            }
                            adapter = new RecommendAdapter(activity, list);
                            horizontalListview.setAdapter(adapter);

                            recommend(list);

                            //猜你喜欢
                            JSONArray jsonArraycommoditys = jsonObject.getJSONArray("commoditys");
                            commoditylist = new ArrayList<>();
                            for (int i = 0; i < jsonArraycommoditys.length(); i++) {
                                commodityModel = new IndexAreaCommodityModel();
                                JSONObject object = (JSONObject) jsonArraycommoditys.opt(i);
                                commodityModel.setCommodityId(object.getLong("commodityId"));
                                commodityModel.setPicture(object.getString("picture"));
                                commodityModel.setNameCN(object.getString("nameCN"));
                                commodityModel.setPrice("￥" + object.getString("price"));
                                commoditylist.add(commodityModel);
                            }
                            commodityAdapter = new CommodityAdapter(activity, commoditylist);
                            grv_commodity.setAdapter(commodityAdapter);
                            intoCommodityData(commoditylist);
                            dialog.dismiss();
                        }else {
                            Toast.makeText(activity,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("aa", "post请求失败" + volleyError.toString());
                Toast.makeText(activity,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(request);


    }

    /***
     * 推荐商品
     *
     */
    private void recommend(final ArrayList<IndexReDetailModel> list) {


        horizontalListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String commodityid=String.valueOf(list.get(position).getCommodityId());
                Intent intent = new Intent(activity, ProductdetailsActivity.class);
                intent.putExtra("commodityid",commodityid);
                startActivity(intent);

            }
        });
    }


    /***
     * 添加主页商品
     */
    private void intoCommodityData(final ArrayList<IndexAreaCommodityModel> list) {



        grv_commodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentcommoditys=new Intent(activity,ProductdetailsActivity.class);
                intentcommoditys.putExtra("commodityid", list.get(position).getCommodityId()+"");
                startActivity(intentcommoditys);
            }
        });

    }


    /**
     * 写入刷新时间
     * 下拉刷新
     *
     */
    @Override
    public void onRefresh(PullDownScrollview view) {
        //获得当前毫秒数
        long time = System.currentTimeMillis();
        format = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());

        final String date = format.format(time);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                pullDownScrollview.finishRefresh("上次刷新时间:" + date);
                if (imglist == null) {
                    intoResource();
                }
                Toast.makeText(activity, "加载完毕", Toast.LENGTH_LONG).show();

            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_search:
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.img_giftbox:
                //鲜花礼盒
                Intent intentbox=new Intent(activity, CommodityDispayActivity.class);
                intentbox.putExtra("word","鲜花礼盒");
                startActivity(intentbox);
//                Toast.makeText(activity, "鲜花礼盒", Toast.LENGTH_LONG).show();
                break;
            case R.id.img_fishinggear:
                //渔具
                Intent intentshinggear=new Intent(activity, CommodityDispayActivity.class);
                intentshinggear.putExtra("word","渔具");
                startActivity(intentshinggear);
//                Toast.makeText(activity, "鱼具", Toast.LENGTH_LONG).show();
                break;
            case R.id.img_horticultre:
                //绿化园艺
                Intent intenthorticultre=new Intent(activity, CommodityDispayActivity.class);
                intenthorticultre.putExtra("word","绿化园艺");
                startActivity(intenthorticultre);
//                Toast.makeText(activity, "绿化园艺", Toast.LENGTH_LONG).show();
                break;
            case R.id.img_petproducts:
                //宠物用品
                Intent intentpetproducts=new Intent(activity, CommodityDispayActivity.class);
                intentpetproducts.putExtra("word","宠物用品");
                startActivity(intentpetproducts);
//                Toast.makeText(activity, "宠物用品", Toast.LENGTH_LONG).show();
                break;
            case R.id.img_specialty:
                //特产
                Intent intentspecialty=new Intent(activity, CommodityDispayActivity.class);
                intentspecialty.putExtra("word","特产");
                startActivity(intentspecialty);
//                Toast.makeText(activity, "特产", Toast.LENGTH_LONG).show();
                break;
            case R.id.img_facility:
                //园林设备
                Intent intentfacility=new Intent(activity, CommodityDispayActivity.class);
                intentfacility.putExtra("word","园林设备");
                startActivity(intentfacility);
//                Toast.makeText(activity, "园林设备", Toast.LENGTH_LONG).show();
                break;
            case R.id.img_saping:
                //树苗
                Intent intentsaping=new Intent(activity, CommodityDispayActivity.class);
                intentsaping.putExtra("word","树苗");
                startActivity(intentsaping);
//                Toast.makeText(activity, "树苗", Toast.LENGTH_LONG).show();
                break;
            case R.id.img_manure:
                //营养元素
                Intent intentmanure=new Intent(activity, CommodityDispayActivity.class);
                intentmanure.putExtra("word","营养元素");
                startActivity(intentmanure);
//                Toast.makeText(activity, "营养元素", Toast.LENGTH_LONG).show();
                break;
            case R.id.img_pesticide:
                //辅助材料
                Intent intentpesticide=new Intent(activity, CommodityDispayActivity.class);
                intentpesticide.putExtra("word","辅助材料");
                startActivity(intentpesticide);
//                Toast.makeText(activity, "辅助材料", Toast.LENGTH_LONG).show();
                break;


        }
    }
}
