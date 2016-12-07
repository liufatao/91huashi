package com.huashi.app.activity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import com.huashi.app.api.HuashiApi;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.library.meg7.widget.CustomShapeSquareImageView;
import com.huashi.app.model.UserModel;
import com.huashi.app.util.Httputil;
import com.huashi.app.util.UploadUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/5/23.
 */
public class AccountInformationActivity extends Activity implements View.OnClickListener ,UploadUtil.OnUploadProcessListener{
    private RelativeLayout ray_hand, ray_nickname, ray_personalized, ray_changepassword,ray_popwindow;
    private PopupWindow popupWindow;
    private Button btn_photograph,btn_photo,btn_cancel;
    private TextView txt_nickname;
    private CustomShapeSquareImageView img_hand;
    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    private String userId;
    private ImageView img_back;
    private UserModel userModel;
    private String motto;
    private  ProgressDialog pd;
    private File filepath;
    /**
     * 去上传文件
     */
    protected static final int TO_UPLOAD_FILE = 1;
    /**
     * 选择文件
     */
    public static final int TO_SELECT_PHOTO = 3;
    /**
     * 上传初始化
     */
    private static final int UPLOAD_INIT_PROCESS = 4;
    /**
     * 上传中
     */
    /**
     * 上传文件响应
     */
    protected static final int UPLOAD_FILE_DONE = 2;
    private static final int UPLOAD_IN_PROCESS = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountinformation);
        userId=getIntent().getStringExtra("userId");
        intoView();
        if (Httputil.isNetworkAvailable(this)) {
            intoData();
        }else {
            Toast.makeText(this,"网络不通畅",Toast.LENGTH_LONG).show();
        }

    }

    private void intoView() {
        ray_hand = (RelativeLayout) findViewById(R.id.ray_hand);
        ray_hand.setOnClickListener(this);
        ray_nickname = (RelativeLayout) findViewById(R.id.ray_nickname);
        ray_nickname.setOnClickListener(this);
        ray_personalized = (RelativeLayout) findViewById(R.id.ray_personalized);
        ray_personalized.setOnClickListener(this);
        ray_changepassword = (RelativeLayout) findViewById(R.id.ray_changepassword);
        ray_changepassword.setOnClickListener(this);
        img_hand= (CustomShapeSquareImageView) findViewById(R.id.img_head);
        img_back= (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txt_nickname= (TextView) findViewById(R.id.txt_nickname);


    }

    /**
     * 初始化数据
     */
   private void intoData(){
       StringRequest stringRequest=new StringRequest(Request.Method.POST, RequestUrlsConfig.QUERYUSERBYID, new Response.Listener<String>() {
           @Override
           public void onResponse(String s) {
               userModel=ExampleApplication.getInstance().getGson().fromJson(s,UserModel.class);
               if (!s.isEmpty() && userModel.getStatus()==Constant.ONE){
                   mValuation(userModel);
               }else {
                   Toast.makeText(AccountInformationActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG);
               }





           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError volleyError) {
               Log.e("请求失败",volleyError.toString());
               Toast.makeText(AccountInformationActivity.this,R.string.strsystemexception,Toast.LENGTH_LONG);
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
     * 赋值
     * @param userModel
     */
    private void mValuation(UserModel userModel){

        txt_nickname.setText(userModel.getUser().getNickname());
        ImageRequest imageRequest=new ImageRequest(HuashiApi.PICTUREURL+userModel.getUser().getAvatar()+".jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                img_hand.setImageBitmap(bitmap);

            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                img_hand.setImageResource(R.mipmap.me);
            }
        });
        if (userModel.getUser().getMotto().toString()!=null) {
            motto=userModel.getUser().getMotto().toString();
        }
        ExampleApplication.getInstance().getRequestQueue().add(imageRequest);

    }
    /****
     * 头像切换
     * @param view
     */
    private  void showPopWindon(View view){
        View windon;
        LayoutInflater inflater = LayoutInflater.from(this);
        windon=inflater.inflate(R.layout.popwindow_headportrait,null);
        popupWindow = new PopupWindow(windon, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        ray_popwindow= (RelativeLayout) windon.findViewById(R.id.ray_popwindow);
        btn_photograph= (Button) windon.findViewById(R.id.btn_photograph);
        btn_cancel= (Button) windon.findViewById(R.id.btn_cancel);
        btn_photo= (Button) windon.findViewById(R.id.btn_photo);
        ray_popwindow.setOnClickListener(this);
        btn_photograph.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_photo.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ray_hand:
                //头像
                showPopWindon(ray_hand);
                break;
            case R.id.ray_nickname:
                //昵称
                Intent intentnickname=new Intent(this,NicknameActivity.class);
                startActivity(intentnickname);
                break;
            case R.id.ray_personalized:
                //个性签名
                Intent intentpersonalized=new Intent(this,PersonalizedActivity.class);
                intentpersonalized.putExtra("motto",motto);
                intentpersonalized.putExtra("userId",userId);
                startActivity(intentpersonalized);
                break;
            case R.id.ray_changepassword:
                //修改密码
                Intent intentchangepassword=new Intent(this,ChangePasswordActivity.class);
                intentchangepassword.putExtra("userId",userId);
                startActivity(intentchangepassword);
                break;
            case R.id.btn_photo:
                //从本地相册上传
                getPicFromPhoto();
                break;
            case R.id.btn_photograph:
                //从相机
                getPicFromCamera();

                break;
            case R.id.btn_cancel:
                //取消
                popupWindow.dismiss();
                break;
            case R.id.ray_popwindow:
                //隔层
                popupWindow.dismiss();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }





    /***
     * 手机拍照
     */
    private void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), "hand.jpg")));
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    /***
     * 相册选择
     */
    private void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_REQUEST);
    }

    /****
     * 调用系统自带切图工具
     * @param uri
     */
    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CLIP);
    }




    private void toUploadFile()
    {
        pd=ProgressDialog.show(this,"","正在上传文件...");
        pd.show();
        String fileKey = "avatarFile";
        UploadUtil uploadUtil = UploadUtil.getInstance();
        uploadUtil.setOnUploadProcessListener(AccountInformationActivity.this); //设置监听器监听上传状态

        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        uploadUtil.uploadFile( filepath,fileKey, RequestUrlsConfig.UPDATEAVATAR,params);
        Toast.makeText(this,"上传成功",Toast.LENGTH_LONG).show();
    }

    /**
     * 上传服务器响应回调
     */
    @Override
    public void onUploadDone(int responseCode, String message) {
        pd.dismiss();
        Message msg = Message.obtain();
        msg.what = UPLOAD_FILE_DONE;
        msg.arg1 = responseCode;
        msg.obj = message;
    }

    @Override
    public void onUploadProcess(int uploadSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_IN_PROCESS;
        msg.arg1 = uploadSize;
    }

    @Override
    public void initUpload(int fileSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_INIT_PROCESS;
        msg.arg1 = fileSize;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File file = new File(Environment.getExternalStorageDirectory()
                                + "/hand.jpg");
                        if (file.exists()) {
                          photoClip(Uri.fromFile(file));
                    }
                        }
                        break;

            case PHOTO_REQUEST://从相册取
                if (data != null) {
                    Uri uri=data.getData();
                    photoClip(data.getData());

                }
                break;
            case PHOTO_CLIP:
                if (data != null) {

                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        try {
                            filepath=UploadUtil.saveFile(photo,Environment.getExternalStorageDirectory().toString(),"hand.jpg");
                            toUploadFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        img_hand.setImageBitmap(photo);
                        popupWindow.dismiss();
                    }
                }
                break;
        }
    }





}
