package com.huashi.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.huashi.app.R;
import com.huashi.app.api.HuashiApi;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.library.meg7.widget.CustomShapeSquareImageView;
import com.huashi.app.model.MyCommentModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 */
public class MyCommentAdapter extends BaseAdapter {
    private List<MyCommentModel.CommodityCommentsBean> list;
    private Context context;
    private SimpleDateFormat formatter;
    public MyCommentAdapter(Context context, List<MyCommentModel.CommodityCommentsBean> list){
        this.context=context;
        this.list=list;
        Log.e("评论集合",list.size()+"");
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
        if(viewHolder==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_comment,null);
            viewHolder.imgCommodity = (CustomShapeSquareImageView) convertView.findViewById(R.id.img_commodity);
            viewHolder.txtCommodityTitle = (TextView) convertView.findViewById(R.id.txt_commodity_title);
            viewHolder.txtComment = (TextView) convertView.findViewById(R.id.txt_comment);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txt_date);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MyCommentModel.CommodityCommentsBean commentModel = list.get(position);
        Log.e("评论数量",position+"");
        if (commentModel!=null) {
            viewHolder.txtComment.setText(commentModel.getContent());
            viewHolder.txtCommodityTitle.setText(commentModel.getName());
            viewHolder.txtDate.setText(formatter.format(commentModel.getCreateTime()));
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
            imageLoader.get(HuashiApi.PICTUREURL+commentModel.getPicUrl()+"!s.jpg",listener);
        }
        return convertView;
    }


    private  class  ViewHolder{
        CustomShapeSquareImageView imgCommodity;
        TextView txtCommodityTitle;
        TextView txtComment;
        TextView txtDate;
    }


}
