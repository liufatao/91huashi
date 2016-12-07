package com.huashi.app.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.fragment.Classify_Fragment;
import com.huashi.app.fragment.Frament_Shopcar;
import com.huashi.app.fragment.Home_Fragment;
import com.huashi.app.fragment.Personage_Fragment;
import com.huashi.app.model.AndroidAppModel;
import com.huashi.app.util.UpdateChecker;
import com.huashi.app.util.Utils;
import com.huashi.app.view.DragPointView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity implements View.OnClickListener{
    private ImageView imgbtn_search,img_home,img_class,img_shopcart,img_me;
    private long firstTime = 0l;
    private FragmentManager fragmentManager;
    private Home_Fragment home_fragment;
    private Frament_Shopcar frament_shopcar;
    private Personage_Fragment personage_fragment;
    private Classify_Fragment classify_fragment;
    private DragPointView dp_shop;
    private int id=0;
    private Utils utils;
    private String userId;
    private UpdateChecker updateChecker;
    private AndroidAppModel androidAppModel;
    public static boolean isForeground = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         id=getIntent().getIntExtra("id",0);
        intoView();
        initFragment(id);
        querShopCartNum();
    }


    /***
     *初始化视图
     *
     */
    private  void  intoView() {
        utils=new Utils(this);
        userId=utils.getInfomation();
        //底部菜单栏注册单击监听事件
        img_home= (ImageView) findViewById(R.id.img_home);
        img_class= (ImageView) findViewById(R.id.img_class);
        img_shopcart= (ImageView) findViewById(R.id.img_shopcart);
        img_me= (ImageView) findViewById(R.id.img_me);
        dp_shop= (DragPointView) findViewById(R.id.dp_shop);
        img_home.setOnClickListener(this);
        img_class.setOnClickListener(this);
        img_shopcart.setOnClickListener(this);
        img_me.setOnClickListener(this);
        updateChecker=new UpdateChecker(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.CHECKNEWST, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                androidAppModel= ExampleApplication.getInstance().getGson().fromJson(s,AndroidAppModel.class);
                int versionCode = utils.getVersionCode();
                if (androidAppModel.getApp().getVersionCode()>versionCode){
                    //检查版本更新
                    updateChecker.setCheckUrl(RequestUrlsConfig.CHECKNEWST);
                    updateChecker.checkForUpdates();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);

    }

    private void querShopCartNum(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYSHOPINGCARTSNUM, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("查询购物车成功",s.toString());
                try {
                    JSONObject jsonObject= new JSONObject(s);
                    int status=jsonObject.getInt("status");
                    String shoppingCarCount=jsonObject.getString("shoppingCarCount");
                    if (status == Constant.ONE){
                        dp_shop.setText(shoppingCarCount);
//
                    }else {
                        dp_shop.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            Log.e("查询购物车数据失败"+userId,volleyError.toString());
                dp_shop.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("userId",userId);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    /**
     * 根据游标切换相应的界面
     * @param i
     */
    private void initFragment(int i){
       fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (i){
            case 0:
                if(home_fragment==null){
                    home_fragment=new Home_Fragment();
                    transaction.add(R.id.frmelay_maincontent,home_fragment);
                }else {
                    transaction.show(home_fragment);
                }
                break;
            case 1:
                if (classify_fragment==null){
                    classify_fragment=new Classify_Fragment();
                    transaction.add(R.id.frmelay_maincontent,classify_fragment);
                }else {
                    transaction.show(classify_fragment);
                }
                break;
            case 2:
                if (frament_shopcar==null){
                    frament_shopcar=new Frament_Shopcar();
                    transaction.add(R.id.frmelay_maincontent,frament_shopcar);
                }else {
                    transaction.show(frament_shopcar);
                }
                break;
            case 3:
                if (personage_fragment==null){
                    personage_fragment=new Personage_Fragment();
                    transaction.add(R.id.frmelay_maincontent,personage_fragment);

                }else {
                    transaction.show(personage_fragment);
                }
                break;
        }
        transaction.commit();
    }

    /***
     * 隐藏Fragment
     * @param transaction
     */
 private  void hideFragment(FragmentTransaction transaction){
     if(frament_shopcar!=null){
         transaction.hide(frament_shopcar);
     }
     if(home_fragment!=null){
         transaction.hide(home_fragment);
     }
     if (classify_fragment!=null){
         transaction.hide(classify_fragment);
     }
     if(personage_fragment!=null){
         transaction.hide(personage_fragment);
     }
 }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /**
             * 底部菜单栏点击事件
             * 切换页面
             * 改变图标
             */
            case R.id.img_home:
                initFragment(0);
                img_home.setImageResource(R.mipmap.ico_btn_home_focus);
                img_class.setImageResource(R.mipmap.ico_btn_class);
                img_shopcart.setImageResource(R.mipmap.ico_btn_shopcart);
                img_me.setImageResource(R.mipmap.ico_button_me);
                break;
            case R.id.img_class:
                initFragment(1);
                img_home.setImageResource(R.mipmap.ico_btn_hom);
                img_class.setImageResource(R.mipmap.ico_btn_class_focus);
                img_shopcart.setImageResource(R.mipmap.ico_btn_shopcart);
                img_me.setImageResource(R.mipmap.ico_button_me);
                break;
            case R.id.img_shopcart:
                 initFragment(2);
                img_home.setImageResource(R.mipmap.ico_btn_hom);
                img_class.setImageResource(R.mipmap.ico_btn_class);
                img_shopcart.setImageResource(R.mipmap.ico_btn_shopcar_focus);
                img_me.setImageResource(R.mipmap.ico_button_me);
                break;
            case R.id.img_me:
                initFragment(3);
                img_home.setImageResource(R.mipmap.ico_btn_hom);
                img_class.setImageResource(R.mipmap.ico_btn_class);
                img_shopcart.setImageResource(R.mipmap.ico_btn_shopcart);
                img_me.setImageResource(R.mipmap.ico_button_me_focus);
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1000l) {
            Toast.makeText(this,"再按一次退出花市",Toast.LENGTH_LONG).show();
                firstTime = secondTime;
                return true;
            } else {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                //与当前应用相关的应用、进程、服务等也会被关闭。
                //会发送 ACTION_PACKAGE_RESTARTED广播。
                finishActivity(0);
                //关闭应用
                System.exit(0);
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
