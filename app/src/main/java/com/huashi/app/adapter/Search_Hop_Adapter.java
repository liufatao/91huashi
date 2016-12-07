package com.huashi.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huashi.app.R;
import com.huashi.app.model.HotSearch;

import java.util.List;


/**
 * Created by Administrator on 2016/5/4.
 * 搜索页热门搜索适配器
 */
public class Search_Hop_Adapter extends BaseAdapter{
     Context context;
    List<HotSearch.HotSearchsBean> mlist;
    public Search_Hop_Adapter(Context context, List<HotSearch.HotSearchsBean> mlist){
        this.context=context;
        this.mlist=mlist;

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
        viewHolder.txt_name.setText(mlist.get(position).getName().toString());
        //绑定数据
        Log.e("热门搜索名称",mlist.get(position).getName());
         return convertView;
    }


    public  class  ViewHolder{
        public TextView txt_name;
    }
}
