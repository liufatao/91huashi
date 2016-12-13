package com.huashi.app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.huashi.app.Config.Constant;
import com.huashi.app.R;
import com.huashi.app.alipay.H5PayDemoActivity;
import com.huashi.app.alipay.PayResult;
import com.huashi.app.alipay.SignUtils;
import com.huashi.app.api.RequestUrlsConfig;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.PayInfoModel;
import com.huashi.app.util.Utils;
import com.huashi.app.weixinpay.MD5;
import com.huashi.app.weixinpay.Util;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2016/5/31.
 * 支付界面
 */
public class PayActivity extends Activity implements View.OnClickListener {
    // 商户PID
    public static final String PARTNER = Constant.PARTNER;
    // 商户收款账号
    public static final String SELLER = Constant.SELLER;
    // 商户私钥，pkcs8格式
    public static String RSA_PRIVATE = null;
    private static final int SDK_PAY_FLAG = 1;
    private ImageView imgBack;
    private Button btnOk;
    private double totalcount;
    private TextView txtAllpic;
    private String orderId;
    private RadioGroup rgPay;
    private ImageView imgAlipay;
    private TextView txtAlipay;
    private RadioButton rbPay;
    private ImageView imgWeixin;
    private TextView txtWeixinpay;
    private RadioButton rbWeixinpay;
    private RadioButton rbUnionpay;
    private int isto = 0;
    private String userId;
    private Utils utils;
    private PayInfoModel payInfoModel;
    private String orderCode;
    private DecimalFormat to = new DecimalFormat("0.00");
    private final String serverMode = "01";
    private String tn;
    private PayReq req;
    private IWXAPI msgApi;
    private Map<String, String> resultunifiedorder;
    private StringBuffer sb;
    private String API_KEY = null;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayActivity.this, R.string.paysuccess, Toast.LENGTH_SHORT).show();
                        Intent intentpaysuccess = new Intent(PayActivity.this, PaySuccessActivity.class);
                        startActivity(intentpaysuccess);

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayActivity.this, R.string.payload, Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayActivity.this, R.string.payfailure, Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        totalcount = getIntent().getDoubleExtra("totalcount", -1);
        orderCode = getIntent().getStringExtra("orderCode");
        Log.e("订单code", orderCode);
        intoView();
        getInfo();
        getWeiXinInfo();
    }

    private void intoView() {
        utils = new Utils(this);
        msgApi = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        req = new PayReq();
        //注册appid
        msgApi.registerApp(Constant.APP_ID);
        sb = new StringBuffer();
        userId = utils.getInfomation();
        orderId = getIntent().getStringExtra("orderId");
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        txtAllpic = (TextView) findViewById(R.id.txt_allpic);
        if (totalcount != -1) {
            txtAllpic.setText(String.format(getResources().getString(R.string.currentprice),to.format(totalcount)));
        }
        rgPay = (RadioGroup) findViewById(R.id.rg_pay);
        imgAlipay = (ImageView) findViewById(R.id.img_alipay);
        txtAlipay = (TextView) findViewById(R.id.txt_alipay);
        rbPay = (RadioButton) findViewById(R.id.rb_pay);
        rbUnionpay = (RadioButton) findViewById(R.id.rb_unionpay);
        rbPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PayActivity.this, "支付宝选择状态", Toast.LENGTH_LONG).show();
                    isto = 0;
                }
            }
        });
        imgWeixin = (ImageView) findViewById(R.id.img_weixin);
        txtWeixinpay = (TextView) findViewById(R.id.txt_weixinpay);
        rbWeixinpay = (RadioButton) findViewById(R.id.rb_weixinpay);

        rbWeixinpay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isto = 1;
                }
            }
        });
        rbUnionpay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PayActivity.this, "银联支付选择状态", Toast.LENGTH_LONG).show();
                    isto = 2;
                }
            }
        });


    }

    /**
     * 获得支付宝私钥
     */
    private void getInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.GETPRIVATEKEY, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("支付查询订单信息", s);
                payInfoModel = ExampleApplication.getInstance().getGson().fromJson(s, PayInfoModel.class);
                if (payInfoModel != null) {
                    RSA_PRIVATE = payInfoModel.getPrivateKey();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("支付查询订单信息失败", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                Log.e("订单号", orderId + "用户" + userId);
                map.put("id", orderId);
                map.put("userId", userId);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    /**
     * 获得微信公钥
     */
    private void getWeiXinInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrlsConfig.WEIXINGETPRIVATEKEY, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        API_KEY = jsonObject.getString("privateKey");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", orderId);
                map.put("userId", userId);
                return map;
            }
        };
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_ok:
                switch (isto) {
                    case 0:
                        pay(btnOk);
                        break;
                    case 1:
                        weixinPay();
                        break;
                    case 2:
                        Unionpay();
                        break;
                }

                break;
        }
    }

    /***
     * 银联支付
     */
    private void Unionpay() {
        StringRequest StringRequest = new StringRequest(Request.Method.POST, "http://101.231.204.84:8091/sim/getacptn", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                tn = s;
                UPPayAssistEx.startPay(PayActivity.this, null, null, tn, serverMode);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        ExampleApplication.getInstance().getRequestQueue().add(StringRequest);
    }

    /***
     * 微信支付
     */
    private void weixinPay() {
        //生成prepay_id
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
    }

    /***
     * 生成参数签名
     */
    private void genPayReq() {

        req.appId = Constant.APP_ID;
        req.partnerId = Constant.MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "prepay_id=" + resultunifiedorder.get("prepay_id");
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n" + req.sign + "\n\n");


        Log.e("orion", "----" + signParams.toString());

    }

    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(PayActivity.this, getString(R.string.app_tip), getString(R.string.getting_prepayid));
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {

            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            Log.e("prepay_id", sb.toString());
            if (sb == null) {
                Toast.makeText(PayActivity.this, "支付异常", Toast.LENGTH_LONG).show();
            }

            resultunifiedorder = result;
            //生成签名参数
            genPayReq();


            msgApi.sendReq(req);
            if (dialog != null) {
                dialog.dismiss();
            }


        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {

            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();


            byte[] buf = Util.httpPost(url, entity);

            String content = new String(buf);
            Log.e("orion", "----" + content);
            Map<String, String> xml = decodeXml(content);

            return xml;
        }
    }

    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();

        try {
            String nonceStr = genNonceStr();

            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", Constant.APP_ID));
            packageParams.add(new BasicNameValuePair("body", "91花市微信支付"));
            packageParams.add(new BasicNameValuePair("mch_id", Constant.MCH_ID));//商户号
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", RequestUrlsConfig.WEIXINRECEIVENOTIFY));//服务器通知地址
            packageParams.add(new BasicNameValuePair("out_trade_no", orderCode));//订单号genOutTradNo()
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new BasicNameValuePair("total_fee", (int) (0.01 * 100) + ""));//totalcount
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
            Log.e("微信支付价格", to.format(totalcount * 100));

            String xmlstring = toXml(packageParams);

            return new String(xmlstring.toString().getBytes(), "ISO8859-1");


        } catch (Exception e) {
            Log.e("支付", "----genProductArgs fail, ex = " + e.getMessage());
            return null;
        }


    }


    /**
     * 生成签名
     */

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(API_KEY);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", "----" + packageSign);
        return packageSign;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        Log.e("orion", "----" + appSign);
        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("orion", "----" + sb.toString());
        return sb.toString();
    }

    private String genOutTradNo() {
        Random random = new Random();
//		return "COATBAE810"; //订单号写死的话只能支付一次，第二次不能生成订单
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", "----" + e.toString());
        }
        return null;

    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(View v) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this).setTitle(R.string.strwarn).setMessage(R.string.strconfiguration)
                    .setPositiveButton(R.string.confirmname, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }
        /**
         * 上传商品详情
         * 商品名称
         * 商品型号
         * 价格
         * 订单号
         */

        String orderInfo = getOrderInfo(payInfoModel.getCommodity().getNameCN(), userId, payInfoModel.getCommodity().getModelName(), "0.01", orderCode);
        Log.e("异步通知信息", orderInfo);

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(this, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
        /**
         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
         * 商户可以根据自己的需求来实现
         */
        String url = "http://m.taobao.com";
        // url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
        extras.putString("url", url);
        intent.putExtras(extras);
        startActivity(intent);
    }

    /**
     * create the order info. 创建订单信息 lft 2016-1-13
     */
    public String getOrderInfo(String subject, String user_id,
                               String body, String price, String sn) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + sn + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";
        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + RequestUrlsConfig.RECEIVENOTIFY + "\"";
        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";
        // 付款用户id
        orderInfo += "&user_id=" + "\"" + user_id + "\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";
        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"http://27.10.10.221:8080/taozhenli-api/alipay/receiveNotify.do\"";
        // orderInfo+="&out_context=";
        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
    /**
     * create the order info. 创建订单信息
     *
     */
//    private String getOrderInfo(String subject, String body, String price, String trade_no) {
//
//        // 签约合作者身份ID
//        String orderInfo = "partner=" + "\"" + PARTNER + "\"";
//
//        // 签约卖家支付宝账号
//        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
//
//        // 商户网站唯一订单号
//        orderInfo += "&out_trade_no=" + "\"" + trade_no + "\"";//getOutTradeNo()
//
//        // 商品名称
//        orderInfo += "&subject=" + "\"" + subject + "\"";
//
//        // 商品详情
//        orderInfo += "&body=" + "\"" + body + "\"";
//
//        // 商品金额
//        orderInfo += "&total_fee=" + "\"" + price + "\"";
//        // 服务器异步通知页面路径
//        orderInfo += "&notify_url=" + "\"" + RequestUrlsConfig.RECEIVENOTIFY
//                + "\"";
//        // 服务接口名称， 固定值
//        orderInfo += "&service=\"mobile.securitypay.pay\"";
//
//        // 支付类型， 固定值
//        orderInfo += "&payment_type=\"1\"";
//
//        // 参数编码， 固定值
//        orderInfo += "&_input_charset=\"utf-8\"";
//        // 付款用户id
//        orderInfo += "&user_id=" + "\"" + userId + "\"";
//        // 设置未付款交易的超时时间
//        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
//        // 取值范围：1m～15d。
//        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
//        // 该参数数值不接受小数点，如1.5h，可转换为90m。
//        orderInfo += "&it_b_pay=\"30m\"";
//
//        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
//        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
//
//        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//        orderInfo += "&return_url=\"http://114.215.78.107:8080/flowerfair-api/alipay/receiveNotify.do\"";
//
//        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
////         orderInfo += "&paymethod=\"expressGateway\"";
//
//        return orderInfo;
//    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 验签证书同后台验签证书
                    // 此处的verify，商户需送去商户后台做验签
                    boolean ret = verify(dataOrg, sign, serverMode);
                    if (ret) {
                        // 验证通过后，显示支付结果
                        msg = "支付成功！";
                    } else {
                        // 验证不通过后的处理
                        // 建议通过商户后台查询支付结果
                        msg = "支付失败！";
                    }
                } catch (JSONException e) {
                }
            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                msg = "支付成功！";
            }
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;

    }
}
