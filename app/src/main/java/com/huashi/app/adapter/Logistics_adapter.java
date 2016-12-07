package com.huashi.app.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huashi.app.R;
import com.huashi.app.model.LogisticsModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */

public class Logistics_adapter extends BaseAdapter {
    private Context context;
    private List<LogisticsModel.LogisticsOrderBean.LogisticsInfoBean> list;
    public Logistics_adapter(Context context,List<LogisticsModel.LogisticsOrderBean.LogisticsInfoBean> list){
        this.list=list;
        this.context=context;

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
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (viewHolder==null) {
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_logistics,null);
            viewHolder.imgLable = (ImageView) convertView.findViewById(R.id.img_lable);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if (position==0){
            viewHolder.txtName.setTextColor(ContextCompat.getColor(context,R.color.colormainren));
            viewHolder.txtTime.setTextColor(ContextCompat.getColor(context,R.color.colormainren));
        }
        viewHolder.txtName.setText(list.get(position).getAcceptstation());
        viewHolder.txtTime.setText(list.get(position).getAccepttime());
        return convertView;
    }

    private class ViewHolder{
        ImageView imgLable;
        TextView txtName;
        TextView txtTime;


    }


}
