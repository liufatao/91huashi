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
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.IndexReDetailModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/9.
 */
public class RecommendAdapter extends BaseAdapter {
    Context context;
    ArrayList<IndexReDetailModel> list=null;
   public RecommendAdapter( Context context, ArrayList<IndexReDetailModel> list){
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
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.recomend_item,null);
            viewHolder.img_recomend= (ImageView) convertView.findViewById(R.id.img_recomend);
            viewHolder.txt_name= (TextView) convertView.findViewById(R.id.txt_recomendname);
            viewHolder.txt_pic= (TextView) convertView.findViewById(R.id.txt_pic);
            convertView.setTag(viewHolder);
        }else {
          viewHolder= (ViewHolder) convertView.getTag();

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
        ImageLoader.ImageListener listener = ImageLoader.getImageListener( viewHolder.img_recomend,
                R.mipmap.load, R.mipmap.load);
        imageLoader.get(list.get(position).getPicture(), listener);
        viewHolder.txt_name.setText(list.get(position).getName());
        viewHolder.txt_pic.setText(list.get(position).getPrice());

        return convertView;
    }

    public  class  ViewHolder{
        public ImageView img_recomend;
        public TextView txt_name;
        public  TextView txt_pic;
    }
}
