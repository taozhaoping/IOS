package com.dahuatech.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName AppStart
 * @Description 应用程序启动类
 * @author 21291
 * @date 2014年4月18日 上午11:30:25
 */
public class AppStart extends Activity {
	
/*	private int mFirstCount;
	private SharedPreferences sp;*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		sp = getSharedPreferences(AppContext.GUIDEANDWELCOME_CONFIG_FILE,MODE_PRIVATE);
//	    //获取首次打开的判断值,默认为true
//		mFirstCount = sp.getInt(AppContext.IS_FIRST_COUNT_KEY,0);
//		if(mFirstCount < 2){  //小于2次  显示引导页
//		 	UIHelper.showGuide(AppStart.this);
//		}
//		else {//显示欢迎页
//		 	UIHelper.showWelcome(AppStart.this);
//		}
		UIHelper.showWelcome(AppStart.this);
		finish();
	}
}
