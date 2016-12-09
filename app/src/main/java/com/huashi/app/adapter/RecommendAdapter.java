package com.huashi.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.huashi.app.R;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.IndexReDetailModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/9.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {
    Context context;
    ArrayList<IndexReDetailModel> list=null;
   public RecommendAdapter( Context context, ArrayList<IndexReDetailModel> list){
        this.context=context;
        this.list=list;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return 视图初始化
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recomend_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.img_recomend= (ImageView) view.findViewById(R.id.img_recomend);
        viewHolder.txt_name= (TextView) view.findViewById(R.id.txt_recomendname);
        viewHolder.txt_pic= (TextView) view.findViewById(R.id.txt_pic);
        return viewHolder;
    }

    /**
     * 数据绑定
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageLoader imageLoader = new ImageLoader( ExampleApplication.getInstance().getRequestQueue(), new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener( holder.img_recomend,
                R.mipmap.load, R.mipmap.load);
        imageLoader.get(list.get(position).getPicture(), listener);
        holder.txt_name.setText(list.get(position).getName());
        holder.txt_pic.setText(list.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img_recomend;
        public TextView txt_name;
        public  TextView txt_pic;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
