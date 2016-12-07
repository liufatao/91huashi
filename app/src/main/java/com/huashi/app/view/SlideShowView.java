package com.huashi.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.huashi.app.R;
import com.huashi.app.application.ExampleApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * ViewPager实现的轮播图广告自定义视图；
 *  既支持自动轮播页面也支持手势滑动切换页面,可以动态设置图片的张数
 * @author Chao Gong
 *
 */
@SuppressLint("HandlerLeak")
public class SlideShowView extends FrameLayout {

    private final static boolean isAutoPlay = true; 
    private List<String> imageUris;
    private List<ImageView> imageViewsList;
    private List<ImageView> dotViewsList;
    private LinearLayout mLinearLayout;
    private ImageView imageView;
    private ViewPager mViewPager;
    private int currentItem  = 0;
    private ScheduledExecutorService scheduledExecutorService;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
    	@Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            mViewPager.setCurrentItem(currentItem);
        }
        
    };
    public SlideShowView(Context context) {
        this(context,null);
    }
    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
       
        initUI(context);
        if(!(imageUris.size()<=0))
        {

        	 setImageUris(imageUris);
        }
        	
        if(isAutoPlay){
            startPlay();
        }
        
    }
    private void initUI(Context context){
    	  imageViewsList = new ArrayList<ImageView>();
          dotViewsList = new ArrayList<ImageView>();
          imageUris=new ArrayList<String>();
          LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
          mLinearLayout=(LinearLayout)findViewById(R.id.linearlayout);
          mViewPager = (ViewPager) findViewById(R.id.viewPager);
          
         
    }
    public void setImageUris(List<String> imageuris)
    {
    	
    	for(int i=0;i<imageuris.size();i++)
    	{
    		imageUris.add(imageuris.get(i));

    	}
    	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 0, 0, 0);
        for(int i=0;i<imageUris.size();i++){
        	 imageView=new ImageView(getContext());
        	imageView.setScaleType(ScaleType.FIT_XY);//铺满屏幕
            imageView.setBackgroundResource(R.mipmap.load);
//         ImageLoader加载网络图片
            ImageLoader imageLoader = new ImageLoader(ExampleApplication.getInstance().getRequestQueue(), new ImageLoader.ImageCache() {
                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                }

                @Override
                public Bitmap getBitmap(String url) {
                    return null;
                }
            });
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                    R.mipmap.load, R.mipmap.load);
            imageLoader.get(imageuris.get(i),listener);
            imageViewsList.add(imageView);


            ImageView viewDot =  new ImageView(getContext());

        	  if(i == 0){  
        		  viewDot.setBackgroundResource(R.mipmap.main_dot_white);
              }else{  
            	  viewDot.setBackgroundResource(R.mipmap.main_dot_light);
              }  

        	viewDot.setLayoutParams(lp);
        	dotViewsList.add(viewDot);
        	mLinearLayout.addView(viewDot);
        	
       
      }
        mViewPager.setFocusable(true);
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
    }
   

    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
    }
 
    @SuppressWarnings("unused")
	private void stopPlay(){
        scheduledExecutorService.shutdown();
    }
    /** 
     *设置选中的tip的背景
     * @param selectItems 
     */  
    private void setImageBackground(int selectItems){  
        for(int i=0; i<dotViewsList.size(); i++){  
            if(i == selectItems){  
            	dotViewsList.get(i).setBackgroundResource(R.mipmap.main_dot_white);
            }else{  
            	dotViewsList.get(i).setBackgroundResource(R.mipmap.main_dot_light);
            }  
        }  
    }  
    private class MyPagerAdapter  extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager)container).removeView(imageViewsList.get(position));
            
        }

        @Override
        public Object instantiateItem(View container, int position) {
            // TODO Auto-generated method stub
            ((ViewPager)container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub
            
        }
        
    }
  
    
    private class MyPageChangeListener implements OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
            case 1:
                isAutoPlay = false;
                break;
            case 2:
                isAutoPlay = true;
                break;
            case 0:
                if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                    mViewPager.setCurrentItem(0);
                }
                
                else if (mViewPager.getCurrentItem() == 0 && !isAutoPlay) {
                    mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1);
                }
                break;
        }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub
        	 setImageBackground(pos % imageUris.size());  

        }
        
    }
    
    
    
    private class SlideShowTask implements Runnable{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (mViewPager) {
                currentItem = (currentItem+1)%imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
        
    }
 
    
    @SuppressWarnings("unused")
	private void destoryBitmaps() {

        for (int i = 0; i < imageViewsList.size(); i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                
                drawable.setCallback(null);
            }
        }
    }
    
}



