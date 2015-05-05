package com.dahuatech.app.ui.main;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppManager;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.ValidLogin;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.UserLoginBussiness;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.common.UpdateManager;
import com.dahuatech.app.widget.LockPatternView;
import com.dahuatech.app.widget.LockPatternView.OnCompleteListener;
import com.google.gson.Gson;

/**
 * @ClassName LoginLockActivity
 * @Description 九宫格密码锁样式Activity
 * @author 21291
 * @date 2014年12月5日 下午2:56:45
 */
public class LoginLockActivity extends BaseActivity {

	private LockPatternView lpView;     			//密码锁视图控件
	private String fItemNumber,fItemName; 			//员工号,员工名称
	private boolean fFirstLogin;		    		//判断应用程序是否首次登陆
	private TextView fManager,fJump;				//管理手势和跳过此项		
	private SharedPreferences sp;  					//配置文件
	private int errorCount=5;						//错误次数
	
	private UserLoginBussiness userLoginBussiness;	//业务逻辑类
	private JSONObject jsonObject;					//json对象
	private String serviceUrl;  					//服务地址
	private AppContext appContext;					//全局Context
	private Gson gson;								//gson实例
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_lock);
		
		appContext = (AppContext)getApplication(); 	//初始化全局变量	
		if(!appContext.isNetworkConnected()) //网络连接	
		{
			UIHelper.ToastMessage(LoginLockActivity.this, R.string.network_not_connected);
			return;
		}
		
		sp = getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE, MODE_PRIVATE);	
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.USER_NAME_KEY);
			fItemName=extras.getString(AppContext.FITEMNAME_KEY);
			fFirstLogin=extras.getBoolean(AppContext.FLOGIN_IS_FIRST_KEY, true);
		}	
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_VERIFYVALIDACTIVITY;
		loadView();	
		init();
		checkSoftUpdate(); //检测是否有更新程序
	}
	
	/** 
	* @Title: loadView 
	* @Description: 加载控件信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年2月5日 下午4:45:53
	*/
	private void loadView(){
		lpView=(LockPatternView)findViewById(R.id.login_lock_Pattern);
		fManager=(TextView)findViewById(R.id.login_lock_manager_gestures);
		fJump=(TextView)findViewById(R.id.login_lock_jump);
		
		lpView.setOnCompleteListener(new OnCompleteListener() {  //密码手势设置事件
			
			@Override
			public void onComplete(String password) {
				if(lpView.verifyPassword(password)){  //验证成功
					skipToMain();
				}
				else{//验证失败
					lpView.markError();
					errorCount--;
					if(errorCount > 0){
						UIHelper.ToastMessage(LoginLockActivity.this,"密码错误,还可以再输入"+String.valueOf(errorCount)+"次");
					}
					else{
						alertLogin();
					}
				}
			}
		});
		
		if(fFirstLogin || lpView.isPasswordEmpty()){  //首次登陆或密码为空
			lpView.resetPassWord();   //清空密码
			fManager.setText("");
			fJump.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					alertDialog("jump");
				}
			});
		}
		else{
			fJump.setText("");
			fManager.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					alertDialog("manager");
				}
			});
		}
	}
	
	/** 
	* @Title: init 
	* @Description: 初始化额外的信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年2月5日 下午4:49:59
	*/
	private void init(){
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(LoginLockActivity.this);
		userLoginBussiness=(UserLoginBussiness)factoryBusiness.getInstance("UserLoginBussiness",serviceUrl);
		gson=GsonHelper.getInstance();
	}
	
	/** 
	* @Title: skipToMain 
	* @Description: 跳转到首页
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年2月5日 下午5:04:01
	*/
	private void skipToMain(){
		new validAsyncTask().execute();
	}

	/**
	 * @ClassName validAsyncTask
	 * @Description 异步登陆验证
	 * @author 21291
	 * @date 2015年2月5日 下午4:57:30
	 */
	private class validAsyncTask extends AsyncTask<Void, Void, ResultMessage> {
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
		}
		
		
		@Override
		protected ResultMessage doInBackground(Void... params) {			
			return userLoginBussiness.verifyValid(fItemNumber);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			showResult(result);
		}
				
	}
	
	/** 
	* @Title: showUploadResult 
	* @Description: 更新上传后UI结果
	* @param @param resultMessage     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月19日 上午11:33:19
	*/
	private void showResult(ResultMessage result){
		if(result.isIsSuccess()){  //说明验证成功
			try {
				jsonObject = new JSONObject(result.getResult());
				ValidLogin valid=gson.fromJson(jsonObject.toString(),ValidLogin.class);
				if(valid!=null){
					fItemName=valid.getFItemName();
				}
				sendLogs(); //发送日志信息进行统计
				UIHelper.showHome(LoginLockActivity.this,fItemNumber,fItemName);		
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else{
			UIHelper.ToastMessage(LoginLockActivity.this, getResources().getString(R.string.lock_verify_faile));
		}
	}
	
	/** 
	* @Title: checkSoftUpdate 
	* @Description: 软件升级提示
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年2月2日 下午1:35:25
	*/
	private void checkSoftUpdate(){
		UpdateManager.getUpdateManager(LoginLockActivity.this,AppUrl.URL_API_HOST_ANDROID_SETTINGACTIVITY).checkAppUpdate(false);
	}
	
	/** 
	* @Title: alertDialog 
	* @Description: 跳出提醒框
	* @param @param type     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月10日 下午2:11:48
	*/
	@SuppressLint("InlinedApi")
	private void alertDialog(final String type){
		String positiveBtn=getResources().getString(R.string.jump);
		String msg=getResources().getString(R.string.gestures_setting_jump); 
		
		if("manager".equals(type)){
			 positiveBtn=getResources().getString(R.string.reload);
			 msg=getResources().getString(R.string.gestures_setting_forget); 
		}
	
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);
		AlertDialog.Builder builder = new AlertDialog.Builder(LoginLockActivity.this);
		builder.setIcon(typedValue.resourceId);
		builder.setMessage(msg);
		builder.setPositiveButton(positiveBtn,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if("manager".equals(type)){
							UIHelper.showLogin(LoginLockActivity.this,false,true);
						}
						else{
							sp.edit().putBoolean(AppContext.IS_GESTURES_KEY, false).commit();
							UIHelper.showHome(LoginLockActivity.this,fItemNumber,fItemName);
						}
						dialog.dismiss();
					}
				});
		builder.setNegativeButton(R.string.cancle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}
	
	/** 
	* @Title: alertLogin 
	* @Description: 跳转到登陆页面
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月10日 下午2:13:35
	*/
	@SuppressLint("InlinedApi")
	private void alertLogin(){
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);
		AlertDialog.Builder builder = new AlertDialog.Builder(LoginLockActivity.this);
		builder.setIcon(typedValue.resourceId);
		builder.setMessage(getResources().getString(R.string.gestures_pwd_error));
		builder.setPositiveButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						UIHelper.showLogin(LoginLockActivity.this,false,true);
						dialog.dismiss();
					}
				});
		builder.show();
	}

	@Override
	protected void onStart() {
		super.onStart();
		View noSetPwd = (View) this.findViewById(R.id.login_lock_NoSetPwd);
		TextView getstureTv = (TextView) findViewById(R.id.login_lock_Gestures);
		if (lpView.isPasswordEmpty()) { // 如果密码为空,则进入设置密码的界面
			lpView.setVisibility(View.GONE);
			noSetPwd.setVisibility(View.VISIBLE);
			getstureTv.setText(getResources().getString(R.string.login_lock_set_gestures));
			noSetPwd.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {		
					UIHelper.showLockSetPwd(LoginLockActivity.this,fItemNumber,fItemName,"login");
					finish();
				}
			});
		} else {
			getstureTv.setText(getResources().getString(R.string.login_lock_gestures));
			lpView.setVisibility(View.VISIBLE);
			noSetPwd.setVisibility(View.GONE);
		}
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 验证成功时，发送日志记录到服务器
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年2月5日 下午4:59:41
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_login_module));
		logInfo.setFActionName("login");
		logInfo.setFNote("note");
		UIHelper.sendLogs(LoginLockActivity.this,logInfo);
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
		if(keyCode == KeyEvent.KEYCODE_BACK) { //返回键
			AppManager.getAppManager().AppExit(LoginLockActivity.this); // 退出
    	}
    	return super.onKeyDown(keyCode, event);
    }
}
