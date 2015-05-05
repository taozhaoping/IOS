package com.dahuatech.app.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.dahuatech.app.AppManager;

/**
 * @ClassName BaseActivity
 * @Description 应用程序Activity的基类
 * @author 21291
 * @date 2014年4月16日 上午9:53:47
 */
public class BaseActivity extends SherlockFragmentActivity{
	
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
		super.onRestart();	
		//发送广播,判断是否网络状态发生改变
		Intent intent = new Intent("com.dahuatech.app.action.APPWIFI_CHANGE");  
		sendBroadcast(intent);
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
		AppManager.getAppManager().finishActivity(this); // 结束Activity&从堆栈中移除
		super.onDestroy();	
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
