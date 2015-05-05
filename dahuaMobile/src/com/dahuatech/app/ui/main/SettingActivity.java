package com.dahuatech.app.ui.main;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.common.UpdateManager;
import com.dahuatech.app.ui.service.AlarmBroadCast;
import com.dahuatech.app.ui.service.NotificationShow;

/**
 * @ClassName SettingActivity
 * @Description 系统设置Activity类
 * @author 21291
 * @date 2014年4月21日 下午8:07:09
 */
public class SettingActivity extends MenuActivity {
	
	private ImageView checkUpdate,versionImage,gesturesImage;
	private ToggleButton tgNotice,tgGestures;

	private String serviceUrl;  //服务地址
	
	private AppContext appContext; //全局Context
	private SharedPreferences sp;  
	
	private static SettingActivity mInstance;
	public static SettingActivity getInstance() {
		return mInstance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		setContentView(R.layout.setting);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); 
		
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_SETTINGACTIVITY;	
		//初始化全局变量
		appContext=(AppContext)getApplication();
		sp = getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE, MODE_PRIVATE);	
		
		//检查更新
		checkUpdate = (ImageView) findViewById(R.id.setting_checkUpdate_image);
		checkUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!appContext.isNetworkConnected()) {
					UIHelper.ToastMessage(SettingActivity.this, R.string.network_not_connected);
					return;
				}
				UpdateManager update=UpdateManager.getUpdateManager(SettingActivity.this,serviceUrl);
				update.checkAppUpdate(true);
			}
		});
		
		//版本信息
		versionImage=(ImageView) findViewById(R.id.setting_version_image);
		versionImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StringBuilder ua = new StringBuilder("");
				ua.append("APP版本编号："+appContext.getPackageInfo().versionCode+"\r\n");//App版本编号
				ua.append("APP版本名称："+appContext.getPackageInfo().versionName+"\r\n");//App版本名称
				ua.append("手机系统版本："+"Android "+android.os.Build.VERSION.RELEASE);//手机系统版本
				UIHelper.ToastMessage(SettingActivity.this, ua.toString());			
			}
		});
		
		boolean isNotice=sp.getBoolean(AppContext.IS_NOTICE_KEY, true);
		boolean isGestures=sp.getBoolean(AppContext.IS_GESTURES_KEY, true);   //默认为开启
		
		//是否启用通知栏
		tgNotice = (ToggleButton) findViewById(R.id.setting_notice_toggleButton);
		tgNotice.setChecked(isNotice);
		
		//是否启用手势密码
		tgGestures=(ToggleButton) findViewById(R.id.setting_gestures_toggleButton);
		tgGestures.setChecked(isGestures);
		
		//手势密码设置
		gesturesImage=(ImageView) findViewById(R.id.setting_gestures_image);
		gesturesImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences loginSp = getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
				UIHelper.showLockSetPwd(SettingActivity.this,loginSp.getString(AppContext.USER_NAME_KEY, ""),loginSp.getString(AppContext.FITEMNAME_KEY, ""),"setting");	
			}
		});
	}
	
	@Override
	protected void onStop() {
		super.onStop();		
	}
	
	@Override
	protected void onPause() {
		super.onPause();	
		Editor editor=sp.edit();
		editor.putBoolean(AppContext.IS_NOTICE_KEY, tgNotice.isChecked());
		editor.putBoolean(AppContext.IS_GESTURES_KEY, tgGestures.isChecked());
		editor.commit();
		if(!tgNotice.isChecked())//说明关闭通知栏
			this.cancleNotice();	
	}

	@Override
	protected void onDestroy() {
		if(mInstance!=null){
			mInstance=null;
		}
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	
	/** 
	* @Title: cancleNotice 
	* @Description: 清除通知栏信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月5日 下午4:42:49
	*/
	private void cancleNotice(){
		new NotificationShow().removeNotification(SettingActivity.this);
		new AlarmBroadCast().cancelAlarm(SettingActivity.this);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
}
