package com.dahuatech.app.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.R;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.widget.LockPatternView;
import com.dahuatech.app.widget.LockPatternView.OnCompleteListener;

/**
 * @ClassName LoginLockSetPwdActivity
 * @Description 九宫格密码锁设置
 * @author 21291
 * @date 2014年12月5日 下午3:16:58
 */
public class LoginLockSetPwdActivity extends BaseActivity {

	private LockPatternView lpView;     			//密码锁视图控件
	private Button btnSave,btnReset;				//按钮控件
	private TextView fGesturesTv;					//手势标题		

	private String password;						//设置的密码
	private boolean needVerify = true;   			//是否修改进来
	private String fItemNumber,fItemName,fType; 	//员工号,员工名称,类型
	private SharedPreferences sp;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_lock_setpwd);
		sp = getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE, MODE_PRIVATE);	
		
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.USER_NAME_KEY);
			fItemName=extras.getString(AppContext.FITEMNAME_KEY);
			fType=extras.getString(AppContext.GESTURES_PASSWORD_SOURCE_KEY);
		}	
		
		lpView=(LockPatternView)findViewById(R.id.login_lock_setpwd_Pattern);
		if (lpView.isPasswordEmpty()) { //如果密码为空,直接输入密码,不做验证
			this.needVerify = false;
		}
		lpView.setOnCompleteListener(new OnCompleteListener() {
			
			@Override
			public void onComplete(String mPassword) {
				password = mPassword;
				if (needVerify) {
					if (lpView.verifyPassword(mPassword)) {
						UIHelper.ToastMessage(LoginLockSetPwdActivity.this, getResources().getString(R.string.login_lock_old_gestures_success));
						fGesturesTv.setText(getResources().getString(R.string.login_lock_set_gestures));
						lpView.clearPassword();
						needVerify = false;
					} else {
						lpView.markError();
						UIHelper.ToastMessage(LoginLockSetPwdActivity.this, getResources().getString(R.string.login_lock_old_gestures_failure));
						password = "";
					}
				}
			}
		});
		
		fGesturesTv=(TextView)findViewById(R.id.login_lock_setpwd_Gestures);
		if("setting".equals(fType) && needVerify){  //说明进行修改手势密码
			fGesturesTv.setText(getResources().getString(R.string.login_lock_old_gestures));
		}
		else{ //其他都是设置密码
			fGesturesTv.setText(getResources().getString(R.string.login_lock_set_gestures));
		}
		
		btnSave = (Button) this.findViewById(R.id.login_lock_setpwd_save);
		btnReset = (Button) this.findViewById(R.id.login_lock_setpwd_reset);	
		btnSave.setOnClickListener(new mOnClickListener());
		btnReset.setOnClickListener(new mOnClickListener());
	}

	/**
	 * @ClassName mOnClickListener
	 * @Description 按钮点击事件类
	 * @author 21291
	 * @date 2014年12月5日 下午3:34:26
	 */
	private class mOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.login_lock_setpwd_save:
					if (StringUtils.isNotEmpty(password)) {
						lpView.setPassWord(password);
						UIHelper.ToastMessage(LoginLockSetPwdActivity.this, getResources().getString(R.string.gestures_pwd_success));
						sp.edit().putBoolean(AppContext.IS_GESTURES_KEY, true).commit();
						
						new Handler().postDelayed(new Runnable() {
				            @Override
				            public void run() {
				            	if("login".equals(fType) && !needVerify){  //说明是第一次进行设置
				            		UIHelper.showHome(LoginLockSetPwdActivity.this,fItemNumber,fItemName);	
								}
								else{
									finish();
								}
				            }
				        }, 1000);	
					} else {
						lpView.clearPassword();
						UIHelper.ToastMessage(LoginLockSetPwdActivity.this, getResources().getString(R.string.gestures_pwd_not_empty));
					}
					break;
				case R.id.login_lock_setpwd_reset:
					lpView.clearPassword();
					password="";
				break;
			}
		}
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	return super.onKeyDown(keyCode, event);
    }
}
