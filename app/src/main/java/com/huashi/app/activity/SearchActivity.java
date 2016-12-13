package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.adapter.Search_Hop_Adapter;
import com.huashi.app.adapter.Search_Top_Adapter;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.HotSearch;
import com.huashi.app.model.TopItemModel;
import com.huashi.app.sql.SearchDBHelper;
import com.huashi.app.util.Httputil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/9.
 * 搜索页
 */
public class SearchActivity extends Activity implements View.OnClickListener {
    private SearchDBHelper dbHelper;
    private GridView hotgrdview, historygridview;
    private TopItemModel itemModel;
    private Search_Top_Adapter hotadapter;
    private EditText edi_search;
    private Button btn_clear;
    private TextView txt_search;
    private ArrayList<TopItemModel> searchhistories;
    private List<HotSearch.HotSearchsBean> hotSearches;
    private HotSearch hotSearch;
    private Search_Top_Adapter searchTopAdapter;
    private Search_Hop_Adapter search_hop_adapter;
    private static List<String> list = new ArrayList<>();
    private ImageView img_back;
    private MyHandler handler = new MyHandler(this) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 点击清除记录按钮，刷新界面
                    Toast.makeText(SearchActivity.this, "清空数据了", Toast.LENGTH_LONG).show();
                    searchTopAdapter.refresh(queryHistoryData());
                    break;


            }
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        intoview();
        intotopData();
        searchHistory();
        if (Httputil.isNetworkAvailable(this)) {
            intotopData();
        } else {
            Toast.makeText(this, "网络不通畅", Toast.LENGTH_LONG).show();
        }
    }
    static class MyHandler extends Handler {
        private final WeakReference<Activity> mActivity;

        public MyHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = mActivity.get();
            if (activity != null) {
                // ...
            }
        }
    }
    //初始化控件
    private void intoview() {
        dbHelper = new SearchDBHelper(this);
        hotgrdview = (GridView) findViewById(R.id.gridview_hot);
        historygridview = (GridView) findViewById(R.id.gridview_history);
        edi_search = (EditText) findViewById(R.id.edi_search);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        txt_search = (TextView) findViewById(R.id.txt_search);
        txt_search.setOnClickListener(this);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        //热门搜索
        hotgrdview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                historygSearcha(hotSearches.get(position).getName());
            }
        });
        //历史记录
        historygridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                historygSearcha(searchhistories.get(position).getName());
                getinfo();
            }
        });
    }


    //搜索关键字
    private void getinfo() {
        final String word = edi_search.getText().toString().trim();
        if (word.isEmpty()) {
            Toast.makeText(SearchActivity.this, "搜索内容为空", Toast.LENGTH_LONG).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.SEARCHCOMMODITYDATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("搜索成功", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    if (status == Constant.ONE) {
                        Intent intent = new Intent(SearchActivity.this, CommodityDispayActivity.class);
                        intent.putExtra("word", word);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("搜索失败", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("word", word);
                map.put("searchType", 100 + "");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    //搜索关键字
    private void historygSearcha(final String word) {
        if (word.isEmpty()) {
            Toast.makeText(SearchActivity.this, "搜索内容为空", Toast.LENGTH_LONG).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.SEARCHCOMMODITYDATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("搜索成功", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");
                    if (status == Constant.ONE) {
                        Intent intent = new Intent(SearchActivity.this, CommodityDispayActivity.class);
                        intent.putExtra("word", word);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("搜索失败", volleyError.toString());
                Toast.makeText(SearchActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("word", word);
                map.put("searchType", 100 + "");
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    /***
     * 初始化热门搜索数据
     */
    private void intotopData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.HOTSEARCH, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!s.isEmpty()) {
                    hotSearch = ExampleApplication.getInstance().getGson().fromJson(s, HotSearch.class);
                    hotSearches = hotSearch.getHotSearchs();
                    if (!hotSearch.getHotSearchs().isEmpty()) {
                        search_hop_adapter = new Search_Hop_Adapter(SearchActivity.this, hotSearch.getHotSearchs());
                        hotgrdview.setAdapter(search_hop_adapter);
                    }

                } else {
                    Toast.makeText(SearchActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SearchActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();
            }
        });
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);

    }

    /***
     * 初始化搜索历史记录
     *
     */
    private void searchHistory() {
        searchhistories = queryHistoryData();
        searchTopAdapter = new Search_Top_Adapter(this, searchhistories);
        historygridview.setAdapter(searchTopAdapter);
    }

    private ArrayList<TopItemModel> queryHistoryData() {
        dbHelper = new SearchDBHelper(getApplicationContext());
        ArrayList<TopItemModel> his_list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor his_c = db.rawQuery("select * from "
                + Constant.HUASHI_TABLE, null);

        his_c.moveToFirst();
        while (!his_c.isAfterLast()) {
            int h_id = his_c.getInt(his_c.getColumnIndex("h_id"));
            String h_name = his_c.getString(his_c.getColumnIndex("h_name"));

            // 用一个HistoryShowBean类来封装得到的数据
            final TopItemModel his_bean = new TopItemModel();
            his_bean.setName(h_name);
            his_bean.setH_id(h_id);
            his_list.add(his_bean);
            his_c.moveToNext();
        }
        if (his_list.size() == 0) {
            btn_clear.setVisibility(View.GONE);
        }
        db.close();
        return his_list;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                //退出当前
                finish();
                break;
            //清空历史记录
            case R.id.btn_clear:
                deleteHistory();
                break;
            case R.id.txt_search:
                //点击搜索添加搜索内容
                getinfo();
                searchOnck();
                break;
        }
    }

    /****
     * 点击搜索按钮
     */
    private void searchOnck() {
        searchhistories.remove(list);
        btn_clear.setVisibility(View.GONE);
        String search = edi_search.getText().toString().trim();
        // 插入数据库数据
        if (!search.isEmpty()) {
            insertHistory(search);
        }

    }

    /**
     * 插入历史记录，点击按钮，获得edittext的值，写到数据库
     */
    private void insertHistory(String search) {
        dbHelper = new SearchDBHelper(getApplicationContext());
        // 插入数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.i("create", "数据库表history创建成功");
        int count = 0;
        // 查询数据库，判断edittext的内容是否已经存在，如果存在了，就不写了，如果不存在，就插入数据库
        // 取回查询存放history表的h_name列的list集合
        List<String> list = queryHistorySql();
        for (int i = 0; i < list.size(); i++) {
            // 获取搜索框的输入内容，和数据已经存在的记录比对，如果有一样的，就count增加；
            if (list.get(i).equals(search)) {
                count++;
            }
        }
        // 如果count == 0，说明没有重复的数据，就可以插入数据库history表中
        if (count == 0) {
            db.execSQL("insert into " + Constant.HUASHI_TABLE
                    + " values(?,?)", new Object[]{null, search});
            Log.i("create", "数据库表history数据插入成功");
        }

        db.close();
    }


    /*
     * 查询数据库的h_name一列，然后放到list集合中，用于判断是否插入数据
	 */
    private List<String> queryHistorySql() {
        dbHelper = new SearchDBHelper(getApplicationContext());
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "
                + Constant.HUASHI_TABLE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            // 查询数据库，取出h_name这一列，然后全部放到list集合中，在前面调用此方法的时候，用来判断
            String name = cursor.getString(cursor.getColumnIndex("h_name"));
            list.add(name);
            cursor.moveToNext();
        }

        db.close();
        // 返回一个list集合
        return list;
    }

    /*
	 * 删除历史记录
	 */
    private void deleteHistory() {
        dbHelper = new SearchDBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 删除表。
        db.execSQL("delete from " + Constant.HUASHI_TABLE);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // 此处handler发送一个message，用来更新ui
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }).start();
        db.close();
    }

}
