/**
 * 
 */
package com.huashi.app.adapter;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huashi.app.R;
import com.huashi.app.model.CommodityNatureModel;

import java.util.List;

/**
 * 商品型号适配器
 * 
 * @author lft 2015-12-27
 */
public class CommofiyNatureAdapater extends BaseAdapter {
	List<CommodityNatureModel.CommodityTypeModelBean.CommodityTypeBean> list;
	Context context;

	/**
	 * 构造器传参
	 */
	public CommofiyNatureAdapater(Context context,List<CommodityNatureModel.CommodityTypeModelBean.CommodityTypeBean> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.commodity_gridview_item, null);
			viewHolder.layout = (LinearLayout) convertView
					.findViewById(R.id.layout);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.ItemText);
			convertView.setTag(viewHolder);// 绑定ViewHolder对象
		} else {

			viewHolder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
		}
		viewHolder.title.setText(list.get(position).getTypeName());
		// 点击改变选中listItem的背景色
		if (list.get(position).getNameIsSelect()) {
			viewHolder.layout
					.setBackgroundResource(R.drawable.commodity_naturetwo_shope);
			viewHolder.title.setTextColor(ContextCompat.getColor(context,R.color.colorwite));
		} else {
			viewHolder.layout
					.setBackgroundResource(R.drawable.commodity_nature_shope);
			viewHolder.title.setTextColor(ContextCompat.getColor(context,R.color.colorblack));
		}
		return convertView;
	}

	public final class ViewHolder {
		public TextView title;
		public LinearLayout layout;
	}

}
