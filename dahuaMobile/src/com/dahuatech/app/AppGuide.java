package com.dahuatech.app;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName AppGuide
 * @Description 引用页面Activity
 * @author 21291
 * @date 2014年9月28日 上午10:40:11
 */
public class AppGuide extends Activity implements OnClickListener {
	private ViewPager viewPager;						//页卡内容
	private ImageView[] dots;							//底部图片
	private List<View> views;							//Tab页面列表
	private int currIndex;								//当前页卡编号
	private View view1,view2;							//各个页卡
    private static final int picsLength=2;   			//图片总数
    private Button fStart;								//点击按钮
    
    private int mCount;									//显示次数
    private SharedPreferences sp;  						//配置文件
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_viewpager);
		sp = getSharedPreferences(AppContext.GUIDEANDWELCOME_CONFIG_FILE,MODE_PRIVATE);
		initViewPager();
	}
	
	/** 
	* @Title: initViewPager 
	* @Description: 初始化页卡内容
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月20日 下午4:37:50
	*/
	@SuppressLint("InflateParams")
	private void initViewPager(){
		currIndex=0;
		
		views=new ArrayList<View>();
		LayoutInflater inflater=getLayoutInflater();
		view1=inflater.inflate(R.layout.guide_viewpager_lay1, null);
		view2=inflater.inflate(R.layout.guide_viewpager_lay2, null);
		views.add(view1);
		views.add(view2);
		
		viewPager=(ViewPager)findViewById(R.id.guide_viewpager);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setCurrentItem(currIndex);  
		
		initImageView();
	}
	
	/** 
	* @Title: initImageView 
	* @Description: 初始化底部图片
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月20日 下午5:06:21
	*/
	private void initImageView() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.guide_viewpager_layout);  
		dots=new ImageView[picsLength];
		for (int i = 0; i < dots.length; i++) {
			dots[i]=(ImageView)layout.getChildAt(i);
			dots[i].setEnabled(false); 	//默认为灰色 
			dots[i].setOnClickListener(this);  
			dots[i].setTag(i);				//设置位置tag，方便取出与当前位置对应  
		}
		dots[currIndex].setEnabled(true);//设置为蓝色，即选中状态  
	}
	
	/**
	 * @ClassName MyViewPagerAdapter
	 * @Description 自定义适配器类
	 * @author 21291
	 * @date 2014年11月20日 下午4:53:06
	 */
	private class MyViewPagerAdapter  extends PagerAdapter{
		private List<View> mListViews;
		
		private MyViewPagerAdapter(List<View> listViews){
			this.mListViews=listViews;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) 	{	
			container.removeView(mListViews.get(position)); //删除页卡
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {			
			 container.addView(mListViews.get(position), 0); //添加页卡
			 
			 if(position==1){
				 fStart=(Button)findViewById(R.id.guide_viewpager_start);
				 fStart.setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {
							redirectTo();
						}
					});
			 }
			 return mListViews.get(position);	 
		}
		
		@Override
		public int getCount() {
			return mListViews.size(); //返回页卡的数量  
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}	
	}
	
	/**
	 * @ClassName MyOnPageChangeListener
	 * @Description 监听滑动的事件
	 * @author 21291
	 * @date 2014年11月20日 下午4:53:17
	 */
	private class MyOnPageChangeListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			setCurDot(arg0);  
		}
	}

    /** 
    * @Title: setCurView 
    * @Description: 设置当前的引导页  
    * @param @param position     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014年11月21日 上午9:38:17
    */
    private void setCurView(int position)  
    {  
        if (position < 0 || position >= dots.length) {  
            return;  
        }  
        viewPager.setCurrentItem(position);  
    } 
    
    /** 
    * @Title: setCurDot 
    * @Description: 这只当前引导小点的选中  
    * @param @param positon     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014年11月21日 上午9:41:34
    */
    private void setCurDot(int positon)  
    {  
        if (positon < 0 || positon > picsLength - 1 || currIndex == positon) {  
            return;  
        }  
        dots[positon].setEnabled(true);  
        dots[currIndex].setEnabled(false);  
        currIndex = positon;  
    }  
	
	@Override
	public void onClick(View v) {
		int position = (Integer)v.getTag();  
        setCurView(position);  
        setCurDot(position);  
	}
	
	/** 
    * @Title: redirectTo 
    * @Description: 跳转到欢迎界面
    * @param      
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014年9月28日 上午11:10:25
    */
    private void redirectTo(){  
    	UIHelper.showWelcome(this);
    	closeGuide();
    }
	    
    /** 
    * @Title: closeGuide 
    * @Description: 关闭引导页
    * @param      
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014年9月28日 上午11:27:26
    */
    private void closeGuide(){
    	mCount = sp.getInt(AppContext.IS_FIRST_COUNT_KEY,0);
    	mCount++;
    	sp.edit().putInt(AppContext.IS_FIRST_COUNT_KEY, mCount).commit();
    	finish();
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {	
		if (keyCode == KeyEvent.KEYCODE_BACK) { //监控返回键，并且包含视图组件
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();	
	}
}
