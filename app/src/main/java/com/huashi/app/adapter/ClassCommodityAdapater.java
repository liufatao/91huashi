package com.huashi.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.huashi.app.R;
import com.huashi.app.api.HuashiApi;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.SmartIndustryModel;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29.
 */
public class ClassCommodityAdapater extends BaseAdapter {
    private List<SmartIndustryModel> list;
    private Context context;
    public ClassCommodityAdapater(Context context,List<SmartIndustryModel> list){
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
        viewHolder viewHolder=null;
        if (viewHolder==null){
            viewHolder=new viewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.class_lv_gvitem,parent,false);
            viewHolder.imgClassitem = (ImageView) convertView.findViewById(R.id.img_classitem);
            viewHolder.txtClassitemname = (TextView) convertView.findViewById(R.id.txt_classitemname);
            convertView.setTag(viewHolder);
        }else {
          viewHolder= (ClassCommodityAdapater.viewHolder) convertView.getTag();
        }

        viewHolder.txtClassitemname.setText(list.get(position).getSmartIndustrys().get(position).getNameCN());

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
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(viewHolder.imgClassitem,
                R.mipmap.load, R.mipmap.load);
        imageLoader.get(HuashiApi.PICTUREURL+list.get(position).getSmartIndustrys().get(position).getPicUrl()+"!s.jpg", listener);
        Log.e("图片路径",list.get(position).getSmartIndustrys().get(position).getPicUrl());
        return convertView;
    }
    private class viewHolder{
        ImageView imgClassitem;
        TextView txtClassitemname;



    }




}
