package com.dahuatech.app.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.actionbarsherlock.app.SherlockExpandableListActivity;
import com.dahuatech.app.AppManager;

/**
 * @ClassName BaseExapandableListActivity
 * @Description 只针对基于ExapandableList列表的基类
 * @author 21291
 * @date 2014年8月27日 下午4:00:15
 */
public class BaseExapandableListActivity extends SherlockExpandableListActivity  {
	
	private boolean allowFullScreen=true; //是否允许全屏
	private boolean allowDestroy=true; //是否允许销毁	
	@SuppressWarnings("unused")
	private View view;
	
	public boolean isAllowFullScreen() { //获取是否全屏
		return allowFullScreen;
	}

	public void setAllowFullScreen(boolean allowFullScreen) { //设置是否全屏
		this.allowFullScreen = allowFullScreen;
	}
	
	public boolean isAllowDestroy() { //获取是否销毁
		return allowDestroy;
	}

	public void setAllowDestroy(boolean allowDestroy) { //设置是否销毁
		this.allowDestroy = allowDestroy;
	}

	public void setAllowDestroy(boolean allowDestroy, View view) { //设置是否销毁(重载) 
		this.allowDestroy = allowDestroy;
		this.view = view;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		allowFullScreen = true;
		AppManager.getAppManager().addActivity(this); //添加Activity到堆栈
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		//发送广播,判断是否网络状态发生改变
		Intent intent = new Intent("com.dahuatech.app.action.APPWIFI_CHANGE");  
		sendBroadcast(intent);
		super.onRestart();		
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();	
		AppManager.getAppManager().finishActivity(this); // 结束Activity&从堆栈中移除
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {	
		if (keyCode == KeyEvent.KEYCODE_BACK) { //监控返回键，并且包含视图组件
			if (!allowDestroy) {
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
