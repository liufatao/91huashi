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
import com.huashi.app.model.OrderStateModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */

public class CommentFocusAdapter extends BaseAdapter {
    private Context context;
    private List<OrderStateModel.OrderBean.DetailsBean> list;
    public CommentFocusAdapter(Context context,List<OrderStateModel.OrderBean.DetailsBean> list){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_commentfocus,parent,false);
            viewHolder.imgCommodity = (ImageView) convertView.findViewById(R.id.img_commodity);
            viewHolder.txtCommodityname = (TextView) convertView.findViewById(R.id.txt_commodityname);
            viewHolder.txt_comment= (TextView) convertView.findViewById(R.id.txt_comment);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.txtCommodityname.setText(list.get(position).getName());
        if (list.get(position).getHas_comment()==1){
        viewHolder.txt_comment.setText("已评论");
        }else {
            viewHolder.txt_comment.setText("去评论");
        }
        //   ImageLoader加载网络图片

        ImageLoader imageLoader = new ImageLoader( ExampleApplication.getInstance().getRequestQueue(), new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener( viewHolder.imgCommodity,
                R.mipmap.load, R.mipmap.load);
        imageLoader.get(HuashiApi.PICTUREURL+list.get(position).getPic_url()+"!m.jpg", listener);

        return convertView;
    }




    private class ViewHolder{
        ImageView imgCommodity;
        TextView txtCommodityname;
        TextView txt_comment;
    }
}
