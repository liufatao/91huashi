package com.huashi.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huashi.app.R;
import com.huashi.app.model.IndustryModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/17.
 * 分类模块适配器
 */
public class ClassAdapter extends BaseAdapter {
    private ArrayList<IndustryModel> list;
    private Context context;
    public ClassAdapter(Context context,ArrayList<IndustryModel> list){
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
        ViewHolder holder=null;
        if(holder==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.class_lvitem,parent,false);
            holder.txt_title= (TextView) convertView.findViewById(R.id.txt_classtitle);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        IndustryModel industryModel = list.get(position);
        holder.txt_title.setText(industryModel.getIndustrys().get(position).getName());
        if (position == selectItem) {
            convertView.setBackgroundColor(Color.RED);
            holder.txt_title.setTextColor(Color.WHITE);
        }
        else {
            convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorgray));
            holder.txt_title.setTextColor(ContextCompat.getColor(context,R.color.colorblack));
        }
        return convertView;
    }
    public  void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }
    private int  selectItem=0;
    private  class  ViewHolder{
        public TextView txt_title;

    }
}
