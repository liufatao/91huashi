package com.huashi.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.adapter.CommentAdapter;
import com.huashi.app.adapter.CommofiyNatureAdapater;
import com.huashi.app.adapter.PictureAdapter;
import com.huashi.app.api.HuashiApi;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.CommodityModel;
import com.huashi.app.model.CommodityNatureModel;
import com.huashi.app.util.Httputil;
import com.huashi.app.util.Utils;
import com.huashi.app.view.SlideShowView;
import com.huashi.app.view.city.McoyProductContentPage;
import com.huashi.app.view.city.McoyProductDetailInfoPage;
import com.huashi.app.view.city.McoySnapPageLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/27.
 * 商品详情
 */
public class ProductdetailsActivity extends Activity implements View.OnClickListener {
    private TextView txt_promotionprice, txt_commodityname, txt_volume, txt_commoditypic, txt_allpic, txt_munus, txt_num, txt_plus;
    private TextView txt_postage, txt_evaluate, txt_price, txt_originalprice, txt_sales, txt_comments;
    private Button btn_cancel, btn_ok, btn_joinshop, btn_buy;
    private ImageView img_commodity, imgCollect, img_back, img_phone, img_cart;
    private SlideShowView mSlideShowView;
    private RelativeLayout ray_commdpopwindow, ry_evaluate;
    private PopupWindow popupWindowcomodity;
    private GridView gv_comen;
    private List<CommodityNatureModel.CommodityTypeModelBean.CommodityTypeBean> mCommodityNatureModelList;
    private String m_commodityid;
    private boolean isCommodityType;
    private CommofiyNatureAdapater natureAdapater;
    private PictureAdapter pictureAdapter;
    private final int ADDORREDUCE = 1;
    private boolean isFocus = true;
    private Utils utils;
    private CommodityModel commodityModel;
    private CommodityNatureModel natureModel;
    private ListView lv_comments;
    private List<CommodityModel> commodityModelList;
    private CommentAdapter commentAdapter;
    private ListView list_imagetext;
    private McoySnapPageLayout mcoySnapPageLayout = null;
    private McoyProductContentPage bottomPage = null;
    private McoyProductDetailInfoPage topPage = null;
    private View detailpag, contentpag;
    private String userid = null;
    private boolean type = true;
    private int length = 5;
    // 每行的GirdView显示的个数
    private int maxLength = 4;
    private double price;
    private int modelId;
    private String phonenumber;
    private String orderDetails;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        intoView();
        if (Httputil.isNetworkAvailable(this)) {
            intoResource();
        } else {
            Toast.makeText(this, "网络不通畅", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_buy:
                //立即购买
                if (Httputil.isNetworkAvailable(this)) {
                    if (userid != null) {
                        joinShop();
                    } else {
                        Intent intentlogo = new Intent(this, Login_Activity.class);
                        startActivity(intentlogo);
                        Toast.makeText(this, "还没有登录，请先登录", Toast.LENGTH_LONG).show();
                    }
                    type = true;
                    showcommoditywindow(btn_buy);
                } else {
                    Toast.makeText(this, R.string.networkexception, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_joinshop:
                //加入购物车
                if (Httputil.isNetworkAvailable(this)) {
                    if (userid != null) {
                        joinShop();
                    } else {
                        Toast.makeText(this, "还没有登录，请先登录", Toast.LENGTH_LONG).show();
                        Intent intentlogo = new Intent(this, Login_Activity.class);
                        startActivity(intentlogo);
                    }

                    type = false;
                    showcommoditywindow(btn_joinshop);
                } else {
                    Toast.makeText(this, R.string.networkexception, Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.ray_commdpopwindow:
                popupWindowcomodity.dismiss();
                break;
            case R.id.btn_cancel:
                //加入购物车取消按钮
                popupWindowcomodity.dismiss();
                break;
            case R.id.btn_ok:
                //加入购物车确定按钮
                if (type) {
                    setBuy();
                } else {
                    mJoinShopCar();
                }

                break;
            case R.id.txt_munus:
                //数量减
                munus();
                break;
            case R.id.txt_plus:
                //数量加
                plus();
                break;
            case R.id.img_collect:
                isOnCollect();
                break;
            case R.id.txt_comments:
                //查看评价
                Intent intentcomment = new Intent(ProductdetailsActivity.this, CommodityCommentActivity.class);
                intentcomment.putExtra("commodityid", m_commodityid);
                startActivity(intentcomment);
                break;
            case R.id.ry_evaluate:
                //查看评价
                Intent intentcomments = new Intent(ProductdetailsActivity.this, CommodityCommentActivity.class);
                intentcomments.putExtra("commodityid", m_commodityid);
                startActivity(intentcomments);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_phone:
                if (phonenumber != null) {
                    //打电话
                    Intent intent = new Intent();
                    //系统默认的action，用来打开默认的电话界面
                    intent.setAction(Intent.ACTION_CALL);
                    //需要拨打的号码
                    intent.setData(Uri.parse("tel:" + phonenumber));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "客服电话为空", Toast.LENGTH_LONG).show();
                }


                break;
        }

    }

    /****
     * 初始化控件
     */
    private void intoView() {
        utils = new Utils(this);
        mcoySnapPageLayout = (McoySnapPageLayout) findViewById(R.id.flipLayout);
        detailpag = getLayoutInflater().inflate(R.layout.mcoy_produt_detail_layout, null,false);
        contentpag = getLayoutInflater().inflate(R.layout.mcoy_product_content_page, null,false);

        topPage = new McoyProductDetailInfoPage(ProductdetailsActivity.this, detailpag);
        bottomPage = new McoyProductContentPage(ProductdetailsActivity.this, contentpag);
        mcoySnapPageLayout.setSnapPages(topPage, bottomPage);

        txt_promotionprice = (TextView) detailpag.findViewById(R.id.txt_promotionprice);
        //添加删除线
        txt_promotionprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        txt_postage = (TextView) detailpag.findViewById(R.id.txt_postage);
        txt_evaluate = (TextView) detailpag.findViewById(R.id.txt_evaluate);
        txt_price = (TextView) detailpag.findViewById(R.id.txt_price);
        txt_originalprice = (TextView) detailpag.findViewById(R.id.txt_originalprice);
        //折扣活动显示
        txt_sales = (TextView) detailpag.findViewById(R.id.txt_sales);
        btn_joinshop = (Button) findViewById(R.id.btn_joinshop);
        btn_joinshop.setOnClickListener(this);
        btn_buy = (Button) findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(this);
        imgCollect = (ImageView) findViewById(R.id.img_collect);
        imgCollect.setOnClickListener(this);
        txt_commodityname = (TextView) detailpag.findViewById(R.id.txt_commodity_title);
        mSlideShowView = (SlideShowView) detailpag.findViewById(R.id.sidview);
        txt_comments = (TextView) detailpag.findViewById(R.id.txt_comments);
        txt_comments.setOnClickListener(this);
        ry_evaluate = (RelativeLayout) detailpag.findViewById(R.id.ry_evaluate);
        ry_evaluate.setOnClickListener(this);
        lv_comments = (ListView) detailpag.findViewById(R.id.lv_comments);
        list_imagetext = (ListView) contentpag.findViewById(R.id.list_imagetext);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        img_phone = (ImageView) findViewById(R.id.img_phone);
        img_phone.setOnClickListener(this);

        userid = utils.getInfomation();
        animation = AnimationUtils.loadAnimation(ProductdetailsActivity.this, R.anim.cart_anim);

    }

    /****
     * 是否收藏
     */
    private void isOnCollect() {
        if (isFocus) {
            cancelCollect();
        } else {
            joinCollect();
        }
    }

    //取消收藏
    private void cancelCollect() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.CANCELCOLLECTCOMMODITY, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)){
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int state = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");
                        if (state == Constant.ONE) {
                            isFocus = false;
                            imgCollect.setImageResource(R.mipmap.ico_collect);
                        }
                        Toast.makeText(ProductdetailsActivity.this, message, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               Toast.makeText(ProductdetailsActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userid);
                map.put("commodityId", m_commodityid);

                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    //加入收藏
    private void joinCollect() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.INSERTCOLLECTCOMMODITY, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)){
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int state = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");
                        if (state == Constant.ONE) {
                            isFocus = true;
                            imgCollect.setImageResource(R.mipmap.ico_collect_select);
                        }
                        Toast.makeText(ProductdetailsActivity.this, message, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ProductdetailsActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("userId", userid);
                map.put("commodityId", m_commodityid);

                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    /**
     * 接收其它页面传过来的商品id
     */
    private void intoResource() {
        Intent intentcommodity = getIntent();
        m_commodityid = intentcommodity.getStringExtra("commodityid");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.COMMODITY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)){
                    List<java.lang.String> list = new ArrayList();
                    commodityModelList = new ArrayList<>();
                    commodityModel = ExampleApplication.getInstance().getGson().fromJson(s, CommodityModel.class);
                    if (commodityModel.getCommodity().getStatus()==Constant.ONE) {
                        txt_comments.setVisibility(View.VISIBLE);
                        mValuation(commodityModel);
                        List<CommodityModel.CommodityBean.PictureBean> picture = commodityModel.getCommodity().getPicture();

                        for (int i = 0; i < picture.size(); i++) {
                            list.add(HuashiApi.PICTUREURL + commodityModel.getCommodity().getPicture().get(i).getName() + ".jpg");
                        }
                        commodityModelList.add(commodityModel);
                        mSlideShowView.setImageUris(list);
                        List<CommodityModel.CommodityBean.IntroductionImagesBean> imagesBeen = commodityModel.getCommodity().getIntroductionImages();
                        pictureAdapter = new PictureAdapter(ProductdetailsActivity.this, imagesBeen);
                        list_imagetext.setAdapter(pictureAdapter);
                        int collectstatus = commodityModel.getCommodity().getCollectStatus();
                        if (collectstatus == Constant.ONE) {
                            imgCollect.setImageResource(R.mipmap.ico_collect_select);
                            isFocus = true;

                        } else {
                            imgCollect.setImageResource(R.mipmap.ico_collect);
                            isFocus = false;
                        }
                        commentAdapter = new CommentAdapter(ProductdetailsActivity.this, commodityModelList);
                        lv_comments.setAdapter(commentAdapter);
                    }else {
                        Toast.makeText(ProductdetailsActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ProductdetailsActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("commodityId", m_commodityid);
                if (userid != null) {
                    map.put("userId", userid);
                }

                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }


    //加入购物车
    private void showcommoditywindow(View v) {
        View widow;
        LayoutInflater inflater = LayoutInflater.from(this);
        widow = inflater.inflate(R.layout.popwindow_commodity, null);
        popupWindowcomodity = new PopupWindow(widow, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindowcomodity.setFocusable(true);
        popupWindowcomodity.setOutsideTouchable(false);
        popupWindowcomodity.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindowcomodity.setBackgroundDrawable(new ColorDrawable());
        popupWindowcomodity.showAtLocation(widow, Gravity.CENTER, 0, 0);
        //商品标题
        txt_commodityname = (TextView) widow.findViewById(R.id.txt_commodityname);
//        txt_volume = (TextView) widow.findViewById(R.id.txt_volume);//销量
        txt_commoditypic = (TextView) widow.findViewById(R.id.txt_commoditypic);//商品价格
        txt_allpic = (TextView) widow.findViewById(R.id.txt_allpic);//商品总价格
        img_commodity = (ImageView) widow.findViewById(R.id.img_commodity);//商品图标
        btn_cancel = (Button) widow.findViewById(R.id.btn_cancel);//取消
        btn_cancel.setOnClickListener(this);
        btn_ok = (Button) widow.findViewById(R.id.btn_ok);//确定
        btn_ok.setOnClickListener(this);
        gv_comen = (GridView) widow.findViewById(R.id.gv_comen);//型号
        img_cart = (ImageView) widow.findViewById(R.id.img_cart);

        ray_commdpopwindow = (RelativeLayout) widow.findViewById(R.id.ray_commdpopwindow);
        ray_commdpopwindow.setOnClickListener(this);
        txt_munus = (TextView) widow.findViewById(R.id.txt_munus);//数量减
        txt_munus.setOnClickListener(this);
        txt_plus = (TextView) widow.findViewById(R.id.txt_plus);//数量加
        txt_plus.setOnClickListener(this);
        txt_num = (TextView) widow.findViewById(R.id.txt_num);
//        gv_comen.setNumColumns(maxLength);
        gv_comen.setSelection(Color.TRANSPARENT);
        if (maxLength < 4) {
            length = 5;
        } else if (maxLength < 5) {
            length = 4;
        } else if (maxLength < 6) {
            length = 3;
        } else {
            length = 2;
        }


    }


    //加入购物车数据
    private void joinShop() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYCOMMODITYTYPES, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)) {
                    natureModel = ExampleApplication.getInstance().getGson().fromJson(s, CommodityNatureModel.class);
                    if (natureModel.getCommodityTypeModel()!=null){
                        mCommodityNatureModelList = new ArrayList<>();
                        if (!natureModel.getCommodityTypeModel().getCommodityType().isEmpty()){
                            mCommodityNatureModelList = natureModel.getCommodityTypeModel().getCommodityType();
                            natureAdapater = new CommofiyNatureAdapater(ProductdetailsActivity.this, mCommodityNatureModelList);
                            gv_comen.setAdapter(natureAdapater);
                            mshopCar(natureModel);
                            gv_comen.setOnItemClickListener(new ItemClickListener());
                        }else {
                            Toast.makeText(ProductdetailsActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                        }


                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ProductdetailsActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("commodityId", m_commodityid);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }


    // 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
    class ItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            CommodityNatureModel.CommodityTypeModelBean.CommodityTypeBean item = (CommodityNatureModel.CommodityTypeModelBean.CommodityTypeBean) arg0
                    .getItemAtPosition(arg2);
            item.setNameIsSelect(!item.getNameIsSelect());
            if (item.getNameIsSelect()) {
                isCommodityType = true;
            } else {
                isCommodityType = false;
            }

            for (int i = 0; i < mCommodityNatureModelList.size(); i++) {
                if (i != arg2) {
                    CommodityNatureModel.CommodityTypeModelBean.CommodityTypeBean natureModel = mCommodityNatureModelList
                            .get(i);
                    natureModel.setNameIsSelect(false);
                } else {
                    //计算价格
                    String num_add = Integer.valueOf(txt_num.getText().toString()) + ADDORREDUCE + "";
                    String allpic=(Double.valueOf(natureModel.getCommodityTypeModel().getCommodityType().get(arg2).getTrue_sell()) * (Double.valueOf(num_add) - 1) + "");
                    txt_allpic.setText(allpic);
                    //总价格
                    price = Double.valueOf(natureModel.getCommodityTypeModel().getCommodityType().get(arg2).getTrue_sell());
                    modelId = natureModel.getCommodityTypeModel().getCommodityType().get(arg2).getModelId();//商品型号
                }

            }
            natureAdapater.notifyDataSetChanged();
        }
    }


    //数量减
    private void munus() {
        if (!txt_num.getText().toString().equals("1")) {
            String num_reduce = Integer.valueOf(txt_num.getText().toString()) - ADDORREDUCE + "";
            txt_num.setText(num_reduce);
            String allpic=(price * Double.valueOf(num_reduce) + "");
            txt_allpic.setText(allpic);
        } else {
            Toast.makeText(this, R.string.strminsum, Toast.LENGTH_SHORT).show();
        }
    }

    //数量加
    private void plus() {
        if (!txt_num.getText().toString().equals("750")) {

            String num_add = Integer.valueOf(txt_num.getText().toString()) + ADDORREDUCE + "";
            txt_num.setText(num_add);
            String allpic=(price * Double.valueOf(num_add) + "");
            txt_allpic.setText(allpic);

        } else {
            Toast.makeText(this, R.string.strmaxsum, Toast.LENGTH_SHORT).show();
        }
    }

    /***
     * 赋值
     */
    private void mValuation(CommodityModel commodityModel) {
        if (commodityModel.getCommodity() != null) {
            txt_commodityname.setText(commodityModel.getCommodity().getNameCN()); //商品名称
            txt_price.setText(String.format(getResources().getString(R.string.currentprice),commodityModel.getCommodity().getTrue_sell()));//商品价格
            txt_originalprice.setText(String.format(getString(R.string.originalprice),commodityModel.getCommodity().getPrice()));//原价
            txt_promotionprice.setText("促销价");
//            txt_postage.setText("￥" + commodityModel.getCommodity());//邮费
            txt_evaluate.setText("评价(" + commodityModel.getCommodity().getCommentCount() + ")");//评论条数
            phonenumber = commodityModel.getCommodity().getPhone();

//            if (commodityModel.getCommodity().getTrue_sell().equals(commodityModel.getCommodity().getPrice())) {
//                txt_originalprice.setVisibility(View.GONE);
//                txt_sales.setVisibility(View.GONE);
//                txt_price.setVisibility(View.GONE);
//            } else {
//
//            }
        }
    }

    //购物车弹出框赋值
    private void mshopCar(CommodityNatureModel commodityNatureModel) {
        CommodityNatureModel.CommodityTypeModelBean shopcar = commodityNatureModel.getCommodityTypeModel();
        txt_commodityname.setText(shopcar.getName());
        txt_commoditypic.setText(String.format(getResources().getString(R.string.currentprice),shopcar.getCommodityType().get(0).getTrue_sell()));//商品价格
        txt_allpic.setText(String.format(getResources().getString(R.string.currentprice),shopcar.getCommodityType().get(0).getTrue_sell()));//商品总价格
        price = Double.valueOf(shopcar.getCommodityType().get(0).getTrue_sell());
        //        ImageLoader加载网络图片

        ImageLoader imageLoader = new ImageLoader(ExampleApplication.getInstance().getRequestQueue(), new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(img_commodity,
                R.mipmap.load, R.mipmap.load);
        imageLoader.get(HuashiApi.PICTUREURL + shopcar.getPicture() + ".jpg", listener);

    }

    //购物车提交数据
    private void mJoinShopCar() {
        final String num_add = Integer.valueOf(txt_num.getText().toString()).toString();
        final String totalcount = txt_allpic.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.ADDCOMMODITYTOCART, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)){
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");
                        if (status == Constant.ONE) {
                            img_cart.setVisibility(View.VISIBLE);
                            img_cart.startAnimation(animation);

                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    img_cart.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        } else {
                            Toast.makeText(ProductdetailsActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ProductdetailsActivity.this, R.string.strsystemexception, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userid);
                map.put("commodityId", m_commodityid);
                map.put("modelId", String.valueOf(modelId));
                map.put("count", num_add);
                map.put("totalcount", totalcount);
                return map;


            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }


    //立即购买提交数据
    private void setBuy() {
        final String num_add = Integer.valueOf(txt_num.getText().toString()).toString();
        final String totalcount = txt_allpic.getText().toString().trim();
        //封装json数据

        try {
            JSONObject object = new JSONObject();
            object.put("order_id", 0);
            object.put("commodity_id", m_commodityid);
            object.put("model_id", modelId);
            object.put("price", price + "");
            object.put("count", num_add);
            object.put("shoppingcar_id", 0);

            orderDetails = object.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("json数据", orderDetails);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.INSERTORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)){
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");
                        String orderId = jsonObject.getString("orderId");
                        if (status == Constant.ONE) {
                            popupWindowcomodity.dismiss();
                            Intent intentorder = new Intent(ProductdetailsActivity.this, ConfirmOrderActivity.class);
                            intentorder.putExtra("orderId", orderId);
                            intentorder.putExtra("modelId", modelId);
                            intentorder.putExtra("totalcount", totalcount);
                            intentorder.putExtra("commodityid", m_commodityid);
                            startActivity(intentorder);
                            Log.e("modelId", modelId + "");
                        } else {
                            Toast.makeText(ProductdetailsActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("立即购买请求失败", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("userId", userid);
                map.put("orderDetails", orderDetails);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

}
