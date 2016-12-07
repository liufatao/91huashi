package com.huashi.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.huashi.app.R;
import com.huashi.app.api.HuashiApi;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.OrderStateModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */

public class OrderDetailAdapter extends BaseAdapter {
    private List<OrderStateModel.OrderBean.DetailsBean> list;
    private Context context;
    private View.OnClickListener onrefund;
    private int status;
    public OrderDetailAdapter(Context context, List<OrderStateModel.OrderBean.DetailsBean> list, int staus){
        this.context=context;
        this.list=list;
        this.status=staus;
    }
    public void setOnrefund(View.OnClickListener onrefund){
        this.onrefund=onrefund;
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
          viewHolder viewHolder=null;
        if (viewHolder==null){
            viewHolder=new viewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_orderdetails,parent,false);
            viewHolder.imgCommodity = (ImageView) convertView.findViewById(R.id.img_commodity);
            viewHolder.txtCommodityname = (TextView) convertView.findViewById(R.id.txt_commodityname);
            viewHolder.txtMondid = (TextView) convertView.findViewById(R.id.txt_mondid);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.txt_price);
            viewHolder.txtCount = (TextView) convertView.findViewById(R.id.txt_count);
            viewHolder.btnRefund= (Button) convertView.findViewById(R.id.btn_refund);
            viewHolder.btnRefund.setOnClickListener(onrefund);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (OrderDetailAdapter.viewHolder) convertView.getTag();
        }
        OrderStateModel.OrderBean.DetailsBean model = list.get(position);
        viewHolder.txtCommodityname.setText(model.getName());
        viewHolder.txtMondid.setText(model.getModel_name());
        viewHolder.txtPrice.setText(model.getPrice()+"");
        viewHolder.txtCount.setText("x"+model.getCount());
        viewHolder.btnRefund.setTag(position);
        switch (status){
            case 0:
                //待付款
              viewHolder.btnRefund.setVisibility(View.GONE);
                break;
            case 1:
                //待付款
                viewHolder.btnRefund.setVisibility(View.GONE);
                break;
            case 2:
                //待发货
                viewHolder.btnRefund.setVisibility(View.GONE);
                break;
            case 3:
                //待收货
                viewHolder.btnRefund.setVisibility(View.GONE);
                break;
            case 4:
                //拒绝发货
                viewHolder.btnRefund.setVisibility(View.GONE);
                break;
            case 5:
                //退款中
                viewHolder.btnRefund.setVisibility(View.GONE);
                break;
            case 6:
                //退款成功
                viewHolder.btnRefund.setVisibility(View.GONE);
                break;
            case 7:
                //退款失败
                viewHolder.btnRefund.setVisibility(View.GONE);
                break;
            case 8:
                //交易成功
                viewHolder.btnRefund.setVisibility(View.GONE);
                break;
            case 9:
                //换货中
                viewHolder.btnRefund.setText("申请售后");
                break;
            case 10:
              //完成评价
                viewHolder.btnRefund.setVisibility(View.GONE);
                break;
        }


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
        imageLoader.get(HuashiApi.PICTUREURL+model.getPic_url()+"!s.jpg",listener);


        return convertView;
    }

    private class viewHolder{
        ImageView imgCommodity;
        TextView txtCommodityname;
        TextView txtMondid;
        TextView txtPrice;
        TextView txtCount;
        Button btnRefund;
    }





}
