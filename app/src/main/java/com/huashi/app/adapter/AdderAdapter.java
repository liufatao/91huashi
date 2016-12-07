package com.huashi.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.huashi.app.R;
import com.huashi.app.model.UserAddress;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 */
public class AdderAdapter extends BaseAdapter {
    private List<UserAddress.UserAddressesBean> list;
    private Context context;
    private View.OnClickListener onEdit;
    private View.OnClickListener onDelete;
    private View.OnClickListener onChenk;
    public TextView txtDelete;
    public TextView txtCompile;

    public AdderAdapter(Context context, List<UserAddress.UserAddressesBean> list) {
        this.list = list;
        this.context = context;
    }

    public void setOnEdit(View.OnClickListener onEdit) {
        this.onEdit = onEdit;
    }

    public void setOnDelete(View.OnClickListener onDelete) {
        this.onDelete = onDelete;
    }

    public void setOnChenk(View.OnClickListener onChenk) {
        this.onChenk = onChenk;
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
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_adder, null);
            viewHolder.txtUsername = (TextView) convertView.findViewById(R.id.txt_username);
            viewHolder.txtUserphone = (TextView) convertView.findViewById(R.id.txt_userphone);
            viewHolder.txtUseradd = (TextView) convertView.findViewById(R.id.txt_useradd);
            viewHolder.cbAdd = (CheckBox) convertView.findViewById(R.id.cb_add);
            viewHolder.cbAdd.setOnClickListener(onChenk);
            viewHolder.txtDelete = (TextView) convertView.findViewById(R.id.txt_delete);
            viewHolder.txtDelete.setOnClickListener(onDelete);
            viewHolder.txtCompile = (TextView) convertView.findViewById(R.id.txt_compile);
            viewHolder.txtCompile.setOnClickListener(onEdit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        UserAddress.UserAddressesBean adderModel = list.get(position);
        viewHolder.txtUsername.setText(adderModel.getName());
        viewHolder.txtUserphone.setText(adderModel.getPhone());
        viewHolder.txtUseradd.setText(adderModel.getProvince()+adderModel.getCity()+adderModel.getDistrict()+adderModel.getStreet());
        if(adderModel.getStatus()==100) {
            viewHolder.cbAdd.setChecked(true);
        }else {
            viewHolder.cbAdd.setChecked(false);
        }
        viewHolder.cbAdd.setTag(position);
        viewHolder.txtDelete.setTag(position);
        viewHolder.txtCompile.setTag(position);
        return convertView;
    }



    private class ViewHolder {
        TextView txtUsername;
        TextView txtUserphone;
        TextView txtUseradd;
        CheckBox cbAdd;
        TextView txtDelete;
        TextView txtCompile;

    }
}
