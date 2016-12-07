package com.huashi.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.huashi.app.R;
import com.huashi.app.api.HuashiApi;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.CommodityModel;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
public class PictureAdapter extends BaseAdapter {
    private List<CommodityModel.CommodityBean.IntroductionImagesBean> list;
    private Context context;
    public PictureAdapter(Context context, List<CommodityModel.CommodityBean.IntroductionImagesBean> list) {
        this.list=list;
        this.context=context;
    }

    @Override
    public int getCount() {
        Log.e("list.size()",list.size()+"");
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
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_imagetext, parent, false);
            viewHolder.imgDetail = (ImageView) convertView.findViewById(R.id.img_detail);
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
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(viewHolder.imgDetail,
                R.mipmap.load, R.mipmap.load);
        imageLoader.get(HuashiApi.PICTUREURL+list.get(position).getName()+".jpg", listener);

        return convertView;
    }

    private class ViewHolder {
        ImageView imgDetail;
    }


}
