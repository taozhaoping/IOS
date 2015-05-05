package com.dahuatech.app.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;

/**
 * @ClassName MenuExapandableListActivity
 * @Description 我的流水特殊菜单父类
 * @author 21291
 * @date 2014年10月16日 下午5:17:35
 */
public class MenuExapandableListActivity extends BaseExapandableListActivity {

	private SharedPreferences sp;         //配置文件
	private SlidingMenu slidingMenu;	  //侧边栏类
	protected CommonMenu commonMenu;      //共同的菜单栏
	
	private Menu absMenu;				  //菜单项
	private String arrows="left";		  //箭头图标
	
	private int rightCount=0;			  //右边显示次数
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		sp = getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
			
		int width = getPixelsWidth(); 
		final int leftDimen=(int)(width * 2 / 3);		//左边显示宽度
		final int rightDimen=(int)(width * 1 / 3);		//右边显示宽度
		
		//配置侧边菜单类
		slidingMenu=new SlidingMenu(this);	
		slidingMenu.setOnClosedListener(new OnClosedListener() {
			
			@Override
			public void onClosed() {
				if(rightCount != 0){
					setMenuIcon("left");	
				}
			}
		});
		
		slidingMenu.setOnOpenListener(new OnOpenListener(){
			@Override
			public void onOpen() {
				slidingMenu.setBehindWidth(leftDimen);  //左边侧边栏宽度
			}
		});
		
		slidingMenu.setSecondaryOnOpenListner(new OnOpenListener(){
			@Override
			public void onOpen() {
				slidingMenu.setBehindWidth(rightDimen);  //右边侧边栏宽度
				if (!slidingMenu.isSecondaryMenuShowing() && rightCount==0) {
					setMenuIcon("right");
				}
				rightCount++;
			}
		});
				
		commonMenu=CommonMenu.getCommonMenu(MenuExapandableListActivity.this,sp,slidingMenu,"other",leftDimen);
		commonMenu.initSlidingMenu();
		commonMenu.initLeftButton();
		commonMenu.initRightButton();
	}
	
	@Override
	public void onBackPressed() {
		if (slidingMenu.isMenuShowing()) {
			slidingMenu.showContent();
			setMenuIcon("left");
		} else {
			super.onBackPressed();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.absMenu=menu;
		getSupportMenuInflater().inflate(R.menu.main, menu);
		if("left".equals(arrows)){
			rightCount=0;
			menu.findItem(R.id.menu_arrows).setIcon(R.drawable.menu_left_arrows);
		}
		else{
			menu.findItem(R.id.menu_arrows).setIcon(R.drawable.menu_right_arrows);
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
		switch (item.getItemId()) {
			case R.id.menu_arrows:   //箭头
				if (!slidingMenu.isSecondaryMenuShowing()) {
					slidingMenu.showSecondaryMenu();
					setMenuIcon("right");
				}
				return true;
	
			case android.R.id.home:  //返回上一级
				commonMenu.toggle();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/** 
	* @Title: getPixelsWidth 
	* @Description: 获取屏幕像素宽度
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年12月2日 上午11:21:35
	*/
	private int getPixelsWidth(){
		DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
	    return displaymetrics.widthPixels; 
	}
	
	/** 
	* @Title: setMenuIcon 
	* @Description: 设置图标方向
	* @param @param direction     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月19日 下午3:34:19
	*/
	private void setMenuIcon(String direction){
		arrows=direction;
		absMenu.clear();
		onCreateOptionsMenu(absMenu);
	}
}
