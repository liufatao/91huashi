package com.huashi.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huashi.app.R;
import com.huashi.app.model.TopItemModel;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/5/4.
 * 搜索页热门搜索适配器
 */
public class Search_Top_Adapter extends BaseAdapter{
     Context context;
    ArrayList<TopItemModel> mlist;
    public Search_Top_Adapter(Context context, ArrayList<TopItemModel> mlist){
        this.context=context;
        this.mlist=mlist;

    }

    // 刷新适配器,更新数据
    public void refresh(ArrayList<TopItemModel> data) {
        this.mlist = data;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;

        if(convertView == null){
            viewHolder=new ViewHolder();
            //得到视图
            convertView= LayoutInflater.from(context).inflate(R.layout.top_item,null);
            viewHolder.txt_name= (TextView) convertView.findViewById(R.id.txt_name);
            convertView.setTag(viewHolder);
        }else {

            viewHolder= (ViewHolder) convertView.getTag();
        }
        //绑定数据

     viewHolder.txt_name.setText(mlist.get(position).getName());
        return convertView;
    }


    public  class  ViewHolder{
        public TextView txt_name;
    }
}
