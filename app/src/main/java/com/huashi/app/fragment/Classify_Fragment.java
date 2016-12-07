package com.huashi.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.activity.ProductdetailsActivity;
import com.huashi.app.activity.SearchActivity;
import com.huashi.app.adapter.ClassAdapter;
import com.huashi.app.adapter.ClassCommodityAdapater;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.IndustryModel;
import com.huashi.app.model.SmartIndustryModel;
import com.huashi.app.util.Httputil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2016/5/10.
 */
public class Classify_Fragment extends Fragment {
    private ArrayList<IndustryModel> list;
    private View class_view;
    private ListView list_classify;
    private GridView gv_classitem;
    private LinearLayout lay_hand;
    private ClassAdapter classAdapter;
    private Activity activity;
    private IndustryModel industryModel;
    private SmartIndustryModel smartIndustryModel;
    private List<SmartIndustryModel> smartIndustryModelList;
    private ClassCommodityAdapater commodityAdapater;
    private TextView txt_load;
    private ImageView imgbtn_search;
    private SwipeRefreshLayout swipe_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        class_view = inflater.inflate(R.layout.frment_classify, null);
        return class_view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        intoView();
        if (Httputil.isNetworkAvailable(activity)) {
            intoData();
        } else {
            Toast.makeText(activity, "网络不通畅", Toast.LENGTH_LONG).show();
        }
    }

    private void intoView() {
        gv_classitem = (GridView) class_view.findViewById(R.id.gv_classitem);
        list_classify = (ListView) class_view.findViewById(R.id.list_classify);

        txt_load = (TextView) class_view.findViewById(R.id.txt_load);
        imgbtn_search = (ImageView) class_view.findViewById(R.id.imgbtn_search);
        imgbtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
            }
        });
        swipe_layout = (SwipeRefreshLayout) class_view.findViewById(R.id.swipe_layout);
        swipe_layout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipe_layout.isRefreshing();
        //设置刷新时动画的颜色
        swipe_layout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);


        list_classify.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                boolean enable = false;
                if(list_classify != null && list_classify.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = list_classify.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = list_classify.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipe_layout.setEnabled(enable);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        gv_classitem.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                boolean enable = false;
                if(gv_classitem != null && gv_classitem.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = gv_classitem.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = gv_classitem.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipe_layout.setEnabled(enable);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void intoData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.INDUSTRYQUERY, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!s.isEmpty()) {
                    list = new ArrayList<>();
                    industryModel = ExampleApplication.getInstance().getGson().fromJson(s, IndustryModel.class);
                    if (industryModel.getStatus() == Constant.ONE && !industryModel.getIndustrys().isEmpty()) {
                        for (int i = 1; i <= industryModel.getIndustrys().size(); i++) {
                            list.add(industryModel);
                        }
                        setData(industryModel.getIndustrys().get(0).getId());
                        classAdapter = new ClassAdapter(activity, list);
                        list_classify.setAdapter(classAdapter);
                        if (swipe_layout.isRefreshing()) {
                            swipe_layout.setRefreshing(false);
                        }
                        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (Httputil.isNetworkAvailable(activity)) {
                                            if (list.isEmpty()) {
                                                intoData();
                                            }
                                        } else {
                                            Toast.makeText(activity, R.string.networkexception, Toast.LENGTH_LONG).show();
                                        }

                                        swipe_layout.setRefreshing(false);
                                    }
                                }, 3000);
                            }
                        });
                        list_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                        Toast.makeText(activity, industryModel.getIndustrys().get(position).getName(), Toast.LENGTH_LONG).show();

                                setData(industryModel.getIndustrys().get(position).getId());
                                classAdapter.setSelectItem(position);
                                classAdapter.notifyDataSetChanged();

                            }
                        });
                    }

                } else {
                    Toast.makeText(activity, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Classify_fragment", "请求失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);

    }

    private void setData(final int id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.INDUSTRYCIASAIYQUERY, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("分类商品", "请求成功" + s);
                if (!s.isEmpty()) {
                    smartIndustryModel = ExampleApplication.getInstance().getGson().fromJson(s, SmartIndustryModel.class);


                    smartIndustryModelList = new ArrayList<>();
                    for (int i = 1; i <= smartIndustryModel.getSmartIndustrys().size(); i++) {
                        smartIndustryModelList.add(smartIndustryModel);
                    }
                    commodityAdapater = new ClassCommodityAdapater(activity, smartIndustryModelList);
                    gv_classitem.setAdapter(commodityAdapater);

                    //判断集合大小是否为空，是否显示提示
                    if (smartIndustryModel.getSmartIndustrys().isEmpty()) {
                        txt_load.setVisibility(VISIBLE);
                    } else {
                        txt_load.setVisibility(View.GONE);
                    }
                    gv_classitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(activity, ProductdetailsActivity.class);
                            intent.putExtra("commodityid", smartIndustryModelList.get(position).getSmartIndustrys().get(position).getId() + "");
                            Log.e("商品id", smartIndustryModelList.get(position).getSmartIndustrys().get(position).getId() + "");
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(activity, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("分类商品", "请求失败" + volleyError);
                Toast.makeText(activity, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("industryId", String.valueOf(id));
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }


}
