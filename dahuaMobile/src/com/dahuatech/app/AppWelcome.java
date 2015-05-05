package com.dahuatech.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName AppWelcome
 * @Description 显示欢迎界面并跳转到登陆界面
 * @author 21291
 * @date 2014年9月28日 上午11:02:53
 */
public class AppWelcome extends Activity {
	
	private TextView tVersionName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//View静态方法，获取布局文件实例对象
		final View view=View.inflate(this, R.layout.welcome, null); 
		setContentView(view);
		
		tVersionName=(TextView) view.findViewById(R.id.welcome_VersionName);
		tVersionName.setText(((AppContext)getApplication()).getPackageInfo().versionName);
		
		//渐变展示启动屏
		AlphaAnimation alphaAnimation  = new AlphaAnimation(0.3f,1.0f);
		alphaAnimation.setDuration(3000);
		view.startAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(new AnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}
			
		});
	}
	
    /** 
    * @Title: redirectTo 
    * @Description: 跳转到导航界面
    * @param      
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014年4月18日 上午11:37:36
    */
    private void redirectTo(){  
    	UIHelper.showExpiration(AppWelcome.this);
    	finish();
    }
}
