package com.huashi.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.huashi.app.R;
import com.huashi.app.api.HuashiApi;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.ToPayOrders;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class ToPayOrdersAdapter extends BaseAdapter {
    private List<ToPayOrders.OrderModelsBean> list;
    private Context context;
    private SimpleDateFormat format;
    private DecimalFormat to = new DecimalFormat("0.00");
    //设置接口
    private View.OnClickListener btnone;
    private View.OnClickListener btntwo;
    private View.OnClickListener btnthree;
   public ToPayOrdersAdapter(Context context,List<ToPayOrders.OrderModelsBean> list){
        this.context=context;
        this.list=list;
       format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
   public void setBtnone(View.OnClickListener btnone){
       this.btnone=btnone;
   }
    public void setBtntwo(View.OnClickListener btntwo){
        this.btntwo=btntwo;
    }
    public void setBtnthree(View.OnClickListener btnthree){
        this.btnthree=btnthree;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder  viewHolder=null;
        if (viewHolder==null) {
            viewHolder=new viewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.orderstatus_item,parent,false);
            viewHolder.imgCommodity = (ImageView) convertView.findViewById(R.id.img_commodity);
            viewHolder.txtCommodityname = (TextView) convertView.findViewById(R.id.txt_commodityname);
            viewHolder.txtMondid = (TextView) convertView.findViewById(R.id.txt_mondid);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.txt_price);
            viewHolder.txtAllpic = (TextView) convertView.findViewById(R.id.txt_allpic);
            viewHolder.btnEstimate = (Button) convertView.findViewById(R.id.btn_estimate);
            viewHolder.btnEstimate.setOnClickListener(btnone);
            viewHolder.btnLogistics = (Button) convertView.findViewById(R.id.btn_logistics);
            viewHolder.btnLogistics.setOnClickListener(btntwo);
            viewHolder.btnDeleteorder = (Button) convertView.findViewById(R.id.btn_deleteorder);
            viewHolder.btnDeleteorder.setOnClickListener(btnthree);
            viewHolder.txtOrdertime = (TextView) convertView.findViewById(R.id.txt_ordertime);
            viewHolder.txtState = (TextView) convertView.findViewById(R.id.txt_state);
            viewHolder.txt_num= (TextView) convertView.findViewById(R.id.txt_num);
            viewHolder.txt_commoditycoun= (TextView) convertView.findViewById(R.id.txt_commoditycoun);
            convertView.setTag(viewHolder);

        }else {
          viewHolder= (ToPayOrdersAdapter.viewHolder) convertView.getTag();

        }
        ToPayOrders.OrderModelsBean model = list.get(position);

        Log.e("集合商品",list.size()+""+model.getCommodityModels().get(0).getNameCN());
        viewHolder.txtCommodityname.setText(model.getCommodityModels().get(0).getNameCN());
        viewHolder.txtMondid.setText(String.valueOf(model.getCommodityModels().get(0).getModelName()));
        viewHolder.txtPrice.setText(String.valueOf(model.getCommodityModels().get(0).getTrue_sell()));
        viewHolder.txtAllpic.setText("合计：￥"+to.format(Double.valueOf(model.getTotalCount()+"")));
        viewHolder.txt_num.setText("共"+model.getCommodityModels().get(0).getCount()+"件商品");
        viewHolder.txtOrdertime.setText(format.format(model.getCreateTime()));
        viewHolder.txt_commoditycoun.setText("x"+model.getCommodityModels().get(0).getCount());
        viewHolder.btnEstimate.setTag(position);
        viewHolder.btnDeleteorder.setTag(position);
        viewHolder.btnLogistics.setTag(position);
        Log.e("订单状态",list.get(position).getStatus()+"");
        switch (list.get(position).getStatus()) {
            case -1:
                viewHolder.txtState.setText("订单失效");
                viewHolder.btnLogistics.setVisibility(View.GONE);
                viewHolder.btnEstimate.setVisibility(View.GONE);
                viewHolder.btnDeleteorder.setVisibility(View.GONE);
                break;
            case 0:
                viewHolder.txtState.setText("待付款");
                viewHolder.btnLogistics.setText("取消订单");
                viewHolder.btnEstimate.setText("去付款");
                viewHolder.btnDeleteorder.setVisibility(View.GONE);
                break;
            case 1:
                viewHolder.txtState.setText("待付款");
                viewHolder.btnLogistics.setText("取消订单");
                viewHolder.btnEstimate.setText("去付款");
                viewHolder.btnDeleteorder.setVisibility(View.GONE);
                break;
            case 2:
                viewHolder.txtState.setText("等待卖家发货");
                viewHolder.btnLogistics.setText("退款");
                viewHolder.btnEstimate.setVisibility(View.GONE);
                viewHolder.btnDeleteorder.setVisibility(View.GONE);
                break;
            case 3:
                viewHolder.txtState.setText("待收货");
                viewHolder.btnLogistics.setText("确认收货");
                viewHolder.btnEstimate.setText("查看物流");
                viewHolder.btnDeleteorder.setVisibility(View.GONE);
                break;
            case 5:
                viewHolder.txtState.setText("退款中");
                viewHolder.btnLogistics.setVisibility(View.GONE);
                viewHolder.btnEstimate.setVisibility(View.GONE);
                viewHolder.btnDeleteorder.setVisibility(View.GONE);
                break;
            case 6:
                viewHolder.txtState.setText("退款成功");
                viewHolder.btnLogistics.setText("删除订单");
                viewHolder.btnEstimate.setVisibility(View.GONE);
                viewHolder.btnDeleteorder.setVisibility(View.GONE);
                break;
            case 7:
                viewHolder.txtState.setText("退款失败");
                viewHolder.btnLogistics.setText("联系客服");
                viewHolder.btnEstimate.setText("继续退款");
                viewHolder.btnDeleteorder.setVisibility(View.GONE);
                break;
            case 8:
                viewHolder.txtState.setText("交易成功");
                viewHolder.btnLogistics.setText("评价");
                viewHolder.btnEstimate.setText("再次购买");
                viewHolder.btnDeleteorder.setText("查看物流");
                break;
            case 9:
                viewHolder.txtState.setText("换货中");
                viewHolder.btnLogistics.setVisibility(View.GONE);
                viewHolder.btnEstimate.setVisibility(View.GONE);
                viewHolder.btnDeleteorder.setVisibility(View.GONE);
                break;
            case 10:
                viewHolder.txtState.setText("换货成功");
                viewHolder.btnLogistics.setVisibility(View.GONE);
                viewHolder.btnEstimate.setVisibility(View.GONE);
                viewHolder.btnDeleteorder.setVisibility(View.GONE);
                break;
            case 11:
                viewHolder.txtState.setText("交易成功");
                viewHolder.btnLogistics.setVisibility(View.GONE);
                viewHolder.btnEstimate.setVisibility(View.GONE);
                viewHolder.btnDeleteorder.setVisibility(View.GONE);
                break;


        }


        //        ImageLoader加载网络图片

        ImageLoader imageLoader = new ImageLoader( ExampleApplication.getInstance().getRequestQueue(), new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(viewHolder.imgCommodity,
                R.mipmap.load, R.mipmap.load);
        imageLoader.get(HuashiApi.PICTUREURL+list.get(0).getCommodityModels().get(0).getPictures()+"!m.jpg", listener);
        return convertView;
    }

    private class viewHolder{
        ImageView imgCommodity;
        TextView txtCommodityname;
        TextView txtMondid;
        TextView txtPrice;
        TextView txtAllpic;
        Button btnEstimate;
        Button btnLogistics;
        Button btnDeleteorder;
        TextView txtOrdertime;
        TextView txtState;
        TextView txt_num;
        TextView txt_commoditycoun;
    }




}
