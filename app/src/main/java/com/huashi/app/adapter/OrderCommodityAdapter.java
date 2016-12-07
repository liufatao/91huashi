package com.huashi.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.huashi.app.R;
import com.huashi.app.api.HuashiApi;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.OrderInfoModel;

import java.util.List;

/**
 * Created by Administrator on 2016/8/12.
 */
public class OrderCommodityAdapter extends BaseAdapter {
    private List<OrderInfoModel.OrderModelBean.CommodityModelsBean> list;
    private Context context;
    public OrderCommodityAdapter(Context context, List<OrderInfoModel.OrderModelBean.CommodityModelsBean> list){
        this.context=context;
        this.list=list;

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
        ViewHolder viewHolder=null;
        if (viewHolder==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_ordercomdtity,parent,false);
            viewHolder.imgCommodity = (ImageView) convertView.findViewById(R.id.img_commodity);
            viewHolder.txtCommodityname = (TextView) convertView.findViewById(R.id.txt_commodityname);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.txt_price);
            viewHolder.txtCount = (TextView) convertView.findViewById(R.id.txt_count);
            viewHolder.txt_mondid= (TextView) convertView.findViewById(R.id.txt_mondid);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();

        }
        OrderInfoModel.OrderModelBean.CommodityModelsBean commodity=list.get(position);
        viewHolder.txtCommodityname.setText(commodity.getNameCN());
        viewHolder.txtPrice.setText(commodity.getTrue_sell());
        viewHolder.txtCount.setText("X"+commodity.getCount());
        viewHolder.txt_mondid.setText(commodity.getModelName());
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
        imageLoader.get(HuashiApi.PICTUREURL+commodity.getPictures()+"!s.jpg",listener);


        return convertView;
    }



    private class ViewHolder{
        ImageView imgCommodity;
        TextView txtCommodityname;
        TextView txtPrice;
        TextView txtCount;
        TextView txt_mondid;




    }

}
