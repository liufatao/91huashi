package com.huashi.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.huashi.app.R;
import com.huashi.app.api.HuashiApi;
import com.huashi.app.application.ExampleApplication;
import com.huashi.app.model.ShopCarModel;

import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public class ShopCarAdapter extends BaseAdapter {
    private List<ShopCarModel.ShoppingCartsBean> list;
    private Context context;
    private ListView listView;
    private ImageLoader imageLoader;
    // 判断是否是初始化
    private boolean isInit = false;
    private int start_index, end_index;//开始 结束位置
    //第一步，设置接口
    private View.OnClickListener onAddNum;  //加商品数量接口
    private View.OnClickListener onSubNum;  //减商品数量接口
    private View.OnClickListener onCheck;   //是否选择


    public ShopCarAdapter(Context context,List<ShopCarModel.ShoppingCartsBean> list, ListView listView){
        this.list=list;
        this.context=context;
        this.listView=listView;

        Log.e("购物车集合大小",list.size()+"s");
    }

    public void setOnAddNum(View.OnClickListener onAddNum){
        this.onAddNum = onAddNum;
    }

    public void setOnSubNum(View.OnClickListener onSubNum){
        this.onSubNum = onSubNum;
    }
    public void setOnCheck(View.OnClickListener onCheck){
        this.onCheck=onCheck;
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
        Log.e("数量",position+"");
        ViewHolder viewHolder=null;
        if(viewHolder==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.shopcar_item,null);
            viewHolder.img_shopcarcommtity= (ImageView) convertView.findViewById(R.id.img_shopcarcommtity);
            viewHolder.txt_shopcartitle= (TextView) convertView.findViewById(R.id.txt_shopcartitle);
            viewHolder.txt_shopcartmodel= (TextView) convertView.findViewById(R.id.txt_shopcartmodel);
            viewHolder.txt_shopcarpic= (TextView) convertView.findViewById(R.id.txt_shopcarpic);
            viewHolder.txt_shopcarsum= (TextView) convertView.findViewById(R.id.txt_num);
            viewHolder.cb_caritem= (CheckBox) convertView.findViewById(R.id.cb_caritem);
            viewHolder.cb_caritem.setOnClickListener(onCheck);
            viewHolder.txt_plus= (TextView) convertView.findViewById(R.id.txt_plus);
            viewHolder.txt_plus.setOnClickListener(onAddNum);
            viewHolder.txt_munus= (TextView) convertView.findViewById(R.id.txt_munus);
            viewHolder.txt_munus.setOnClickListener(onSubNum);
            viewHolder.img_shopcarcommtity.setTag(position);

            convertView.setTag(viewHolder);
        }else {
         viewHolder= (ViewHolder) convertView.getTag();
        }
        ShopCarModel.ShoppingCartsBean shopCarModel = list.get(position);
        Log.e("购物车商品名称",shopCarModel.getCommodityNameCN());
        viewHolder.txt_shopcartitle.setText(shopCarModel.getCommodityNameCN()+position);
        viewHolder.txt_shopcartmodel.setText(shopCarModel.getModelName());
        viewHolder.txt_shopcarpic.setText(Double.toString(shopCarModel.getPrice()));
        viewHolder.txt_shopcarsum.setText(String.valueOf(shopCarModel.getCount()));
        viewHolder.cb_caritem.setChecked(shopCarModel.isStatue());
        viewHolder.txt_munus.setTag(position);
        viewHolder.txt_plus.setTag(position);
        viewHolder.cb_caritem.setTag(position);
        //        ImageLoader加载网络图片



        imageLoader = new ImageLoader( ExampleApplication.getInstance().getRequestQueue(), new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        if (!isInit) {
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(viewHolder.img_shopcarcommtity,
                R.mipmap.load, R.mipmap.load);
        imageLoader.get(HuashiApi.PICTUREURL+shopCarModel.getCommodityPicture()+"!s.jpg",listener);
        }
        return convertView;
    }




        public  class  ViewHolder{
        public TextView txt_shopcartitle,txt_shopcartmodel,txt_shopcarpic,txt_shopcarsum,txt_plus,txt_munus;
        public ImageView img_shopcarcommtity;
        public CheckBox cb_caritem;

    }
}
