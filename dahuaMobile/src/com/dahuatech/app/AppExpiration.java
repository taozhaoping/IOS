package com.dahuatech.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName AppExpiration
 * @Description 应用程序过期时间类
 * @author 21291
 * @date 2014年12月9日 下午1:30:18
 */
public class AppExpiration extends Activity {
	
	private boolean fNotice=false;    			//是否有通知
	private boolean fLoginShowNotice=false;		//如果是通知栏进来的，过期了则需要通知登陆页面
	
	private String fItemNumber,fItemName; 		//员工号,员工名称
	private SharedPreferences sp;	  	//配置文件	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fNotice=extras.getBoolean(AppContext.FNOTICE_ALARM_KEY, false);	 
		}	
		sp = getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		fItemNumber=sp.getString(AppContext.USER_NAME_KEY, "");
		fItemName=sp.getString(AppContext.FITEMNAME_KEY, "");
		boolean fFirstLogin=sp.getBoolean(AppContext.FLOGIN_IS_FIRST_KEY, true);  //判断应用程序是否首次登陆
		
		if(fFirstLogin){  //说明是应用程序首次登陆
			UIHelper.showLogin(AppExpiration.this,fLoginShowNotice,fFirstLogin);
		}
		else{
			if(fNotice){ //如果是从通知栏过来	
				fLoginShowNotice=true;
			}
			SharedPreferences setting=getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE,MODE_PRIVATE);
			boolean isGestures=setting.getBoolean(AppContext.IS_GESTURES_KEY, true);
			if(isGestures){  //说明设置手势密码
				UIHelper.showLock(AppExpiration.this,fItemNumber,fItemName,fFirstLogin);
			}
			else{  //说明没有设置手势密码
				UIHelper.showLogin(AppExpiration.this,fLoginShowNotice,fFirstLogin);
			}
		}
	}
}
