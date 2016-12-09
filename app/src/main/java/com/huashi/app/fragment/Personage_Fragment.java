package com.huashi.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.activity.AccountInformationActivity;
import com.huashi.app.activity.AftesalesActivity;
import com.huashi.app.activity.AllOrderActivity;
import com.huashi.app.activity.CommentActivity;
import com.huashi.app.activity.Login_Activity;
import com.huashi.app.activity.MyAdderActivity;
import com.huashi.app.activity.MyCollectionActivity;
import com.huashi.app.activity.SetActivity;
import com.huashi.app.activity.WaitCommentActivity;
import com.huashi.app.activity.WaitpaymentActivity;
import com.huashi.app.activity.WaitshipmentActivity;
import com.huashi.app.activity.WaittakeActivity;
import com.huashi.app.api.HuashiApi;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.library.meg7.widget.CustomShapeSquareImageView;
import com.huashi.app.model.UserModel;
import com.huashi.app.util.Httputil;
import com.huashi.app.util.Utils;
import com.huashi.app.view.DragPointView;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人中心
 */
public class Personage_Fragment extends Fragment implements View.OnClickListener {
    private View personage_view;
    private Activity activity;
    private RelativeLayout rly_oder, rly_adder, rly_collection, rly_comment, rly_set;
    private CustomShapeSquareImageView img_head;
    private TextView txt_login, txt_state;
    private ImageView img_waitpayment, img_waitdelivery, img_waittake, img_waitcomment, img_aftersale;
    private Utils utils;
    private UserModel userModel;
    private String UserId;
    private Bitmap bitmap;
    private DragPointView dpWaitpayment;
    private DragPointView dpWaitdelivery;
    private DragPointView dpWaittake;
    private DragPointView dpWaitcomment;
    private DragPointView dpAftersale;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        personage_view = inflater.inflate(R.layout.frment_personage,container,false);
        return personage_view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        utils = new Utils(activity);
        intoView();

    }

    private void intoView() {
        UserId = utils.getInfomation();
        rly_oder = (RelativeLayout) personage_view.findViewById(R.id.rly_order);
        rly_oder.setOnClickListener(this);
        rly_adder = (RelativeLayout) personage_view.findViewById(R.id.rly_adder);
        rly_adder.setOnClickListener(this);
        rly_comment = (RelativeLayout) personage_view.findViewById(R.id.rly_comment);
        rly_comment.setOnClickListener(this);
        rly_set = (RelativeLayout) personage_view.findViewById(R.id.rly_set);
        rly_set.setOnClickListener(this);
        rly_collection = (RelativeLayout) personage_view.findViewById(R.id.rly_collection);
        rly_collection.setOnClickListener(this);
        img_head = (CustomShapeSquareImageView) personage_view.findViewById(R.id.img_head);
        img_head.setOnClickListener(this);
        txt_login = (TextView) personage_view.findViewById(R.id.txt_login);
        txt_login.setOnClickListener(this);
        img_waitpayment = (ImageView) personage_view.findViewById(R.id.img_waitpayment);
        img_waitpayment.setOnClickListener(this);
        img_waitdelivery = (ImageView) personage_view.findViewById(R.id.img_waitdelivery);
        img_waitdelivery.setOnClickListener(this);
        img_waittake = (ImageView) personage_view.findViewById(R.id.img_waittake);
        img_waittake.setOnClickListener(this);
        img_waitcomment = (ImageView) personage_view.findViewById(R.id.img_waitcomment);
        img_waitcomment.setOnClickListener(this);
        img_aftersale = (ImageView) personage_view.findViewById(R.id.img_aftersale);
        img_aftersale.setOnClickListener(this);
        txt_state = (TextView) personage_view.findViewById(R.id.txt_state);


        dpWaitpayment = (DragPointView) personage_view.findViewById(R.id.dp_waitpayment);
        dpWaitdelivery = (DragPointView) personage_view.findViewById(R.id.dp_waitdelivery);
        dpWaittake = (DragPointView) personage_view.findViewById(R.id.dp_waittake);
        dpWaitcomment = (DragPointView) personage_view.findViewById(R.id.dp_waitcomment);
        dpAftersale = (DragPointView) personage_view.findViewById(R.id.dp_aftersale);


        if (UserId != null) {
            txt_login.setClickable(false);

        }
        if (Httputil.isNetworkAvailable(activity)) {
            mSubitUserId();
        } else {
            Toast.makeText(activity, "网络不通畅", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rly_order:
                Intent intentallorder = new Intent(activity, AllOrderActivity.class);
                startActivity(intentallorder);
                break;
            case R.id.rly_adder:
                Intent intentadder = new Intent(activity, MyAdderActivity.class);
                startActivity(intentadder);
                break;
            case R.id.rly_collection:
                Intent collectionintent = new Intent(activity, MyCollectionActivity.class);
                startActivity(collectionintent);
                break;
            case R.id.rly_comment:
                Intent intentcomment = new Intent(activity, CommentActivity.class);
                startActivity(intentcomment);
                break;
            case R.id.rly_set:
                Intent intentset = new Intent(activity, SetActivity.class);
                startActivity(intentset);
                break;
            case R.id.img_head:
                if (Httputil.isNetworkAvailable(activity)) {
                    if (!TextUtils.isEmpty(UserId)) {
                        //用户资料
                        Intent intentaccount = new Intent(activity, AccountInformationActivity.class);
                        intentaccount.putExtra("userId", UserId);
                        startActivity(intentaccount);
                    } else {
                        //跳到登录页面
                        Intent intentlogin = new Intent(activity, Login_Activity.class);
                        startActivity(intentlogin);
                    }
                } else {
                    Toast.makeText(activity, R.string.networkexception, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.txt_login:
                //登录
                Intent intentlogin = new Intent(activity, Login_Activity.class);
                startActivity(intentlogin);
                break;
            case R.id.img_waitcomment:
                //戴评论
                Intent intentwaitcomment = new Intent(activity, WaitCommentActivity.class);
                startActivity(intentwaitcomment);
                break;
            case R.id.img_waitdelivery:
                //待发货
                Intent intentwaitdelivery = new Intent(activity, WaitshipmentActivity.class);
                startActivity(intentwaitdelivery);
                break;
            case R.id.img_waittake:
                //待收货
                Intent intentwaittake = new Intent(activity, WaittakeActivity.class);
                startActivity(intentwaittake);
                break;
            case R.id.img_waitpayment:
                //待付款
                Intent intentwaitpayment = new Intent(activity, WaitpaymentActivity.class);
                startActivity(intentwaitpayment);
                break;
            case R.id.img_aftersale:
                //售后
                Intent intentafersale = new Intent(activity, AftesalesActivity.class);
                startActivity(intentafersale);
                break;

        }
    }

    private void mSubitUserId() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.PERSONAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)){
                    userModel = ExampleApplication.getInstance().getGson().fromJson(s, UserModel.class);

                    if (userModel.getStatus() == Constant.ONE) {
                        mValuation(userModel);
                    } else {
                        dpWaitpayment.setVisibility(View.GONE);
                        dpWaitdelivery.setVisibility(View.GONE);
                        dpWaittake.setVisibility(View.GONE);
                        dpWaitcomment.setVisibility(View.GONE);
                        dpAftersale.setVisibility(View.GONE);
                        Toast.makeText(activity,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(activity,R.string.strsystemexception,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dpWaitpayment.setVisibility(View.GONE);
                dpWaitdelivery.setVisibility(View.GONE);
                dpWaittake.setVisibility(View.GONE);
                dpWaitcomment.setVisibility(View.GONE);
                dpAftersale.setVisibility(View.GONE);
                Toast.makeText(activity,R.string.strsystemexception,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("userId", UserId);


                return stringMap;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);

    }

    /**
     * 赋值
     *
     */
    private void mValuation(UserModel userModel) {

        txt_login.setText(userModel.getUser().getNickname());
        ImageRequest imageRequest = new ImageRequest(HuashiApi.PICTUREURL + userModel.getUser().getAvatar() + ".jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                img_head.setImageBitmap(bitmap);

            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                img_head.setImageResource(R.mipmap.me);
            }
        });
        if (!userModel.getUser().getMotto().isEmpty()) {
            txt_state.setText(String.format(getResources().getString(R.string.signature),userModel.getUser().getMotto()));
        }
        ExampleApplication.getInstance().getRequestQueue().add(imageRequest);
        if (userModel.getToPayNum() > 0) {
            dpWaitpayment.setText(" " + String.valueOf(userModel.getToPayNum()) + " ");
        } else {
            dpWaitpayment.setVisibility(View.GONE);
        }
        if (userModel.getToDeliveryNum() > 0) {
            dpWaitdelivery.setText(" " + String.valueOf(userModel.getToDeliveryNum()) + " ");
        } else {
            dpWaitdelivery.setVisibility(View.GONE);
        }
        if (userModel.getToRefundNum() > 0) {
            dpWaittake.setText(" " + String.valueOf(userModel.getToRefundNum()) + " ");
        } else {
            dpWaittake.setVisibility(View.GONE);
        }
        if (userModel.getToEvaluationNum() > 0) {
            dpWaitcomment.setText(" " + String.valueOf(userModel.getToEvaluationNum()) + " ");
        } else {
            dpWaitcomment.setVisibility(View.GONE);
        }
        if (userModel.getToBarterNum() > 0) {
            dpAftersale.setText(" " + String.valueOf(userModel.getToBarterNum()) + " ");
        } else {
            dpAftersale.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserId != null) {
            txt_login.setClickable(false);
            mSubitUserId();
        }
    }


}

