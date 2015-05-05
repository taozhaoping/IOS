package com.dahuatech.app.ui.main;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppManager;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.UserInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.UserLoginBussiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.common.UpdateManager;
import com.dahuatech.app.ui.service.AlarmBroadCast;

/**
 * @ClassName LoginActivity
 * @Description 登陆Activity
 * @author 21291
 * @date 2014年4月21日 上午9:12:58
 */
public class LoginActivity extends BaseActivity {
	
	private Button btnLogin; 							//提交按钮
	private EditText fItemNumberEdit,fPasswordEdit; 	//员工号,密码
	private ProgressDialog dialog; 						//提示框
	private String fItemNumber,fPassword,fItemName;	
	private CheckBox chb_rememberMe;
	private boolean isRememberMe;
	private boolean fLoginShowNotice=false; 			//是否有通知
	private boolean isNetWork=true;  					//是否有网络
	private boolean fFirstLogin=true;					//应用程序是否首次登陆
	
	private String serviceUrl;  						//服务地址
	private AppContext appContext;						//全局Context
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		appContext = (AppContext)getApplication(); 	//初始化全局变量		
		sp = getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_LOGINACTIVITY;	 //获取服务地址

		//控件初始化
		fItemNumberEdit = (EditText) this.findViewById(R.id.login_editUserName);
		fPasswordEdit = (EditText) this.findViewById(R.id.login_editPassWord);
        chb_rememberMe=(CheckBox)findViewById(R.id.login_checkBox);
        chb_rememberMe.setChecked(true);
        
		btnLogin = (Button) this.findViewById(R.id.login_btnLogin);	
        btnLogin.setOnClickListener(btnLoginClick);
        
        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.dialog_logining));
        dialog.setCancelable(false); 
		
        Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fLoginShowNotice=extras.getBoolean(AppContext.FNOTICE_LOGIN_IS_SHOW_KEY, false);	 
			fFirstLogin=extras.getBoolean(AppContext.FLOGIN_IS_FIRST_KEY, true);	  
		}
		
		if(!appContext.isNetworkConnected()) //网络连接	
		{
			isNetWork=false;
			UIHelper.ToastMessage(LoginActivity.this, R.string.network_not_connected);
			return;
		}
		
		if(isNetWork){  //有网络的情况下
			checkSoftUpdate(); //检测是否有更新程序
			if(fLoginShowNotice){ //如果是从通知栏过来	
				UIHelper.ToastMessage(LoginActivity.this, R.string.msg_login_error);
			}
			appContext.clearSharedPreferences(sp);
			//是否显示登录信息
	        UserInfo user=appContext.getLoginInfo();
	        if(user==null || !user.isIsRememberMe()) return;
	        if(!StringUtils.isEmpty(user.getFItemNumber())){
	        	fItemNumberEdit.setText(user.getFItemNumber());
	        	fItemNumberEdit.selectAll();
	        	chb_rememberMe.setChecked(user.isIsRememberMe());
	        }
		}
	}
	
	//测试按钮
	public void OnTestClick(View v)  
    {  
		if(isNetWork){
			fItemNumber = AppContext.TEMPTEST_FITEMNUMBER;
			fItemName=AppContext.TEMPTEST_FITEMNAME;
			fPassword = "123456";
			isRememberMe = true;
			
			UserInfo user=UserInfo.getUserInfo();
			user.setFItemNumber(fItemNumber);
			user.setFItemName(fItemName);
			user.setFPassword(fPassword);
			user.setIsRememberMe(isRememberMe);
			
			appContext.saveLoginInfo(user);//保存登录信息
			//数据持久化 保存登陆配置信息 私有属性，只有本项目才能存取第一个参数指定的XML文件
			if(updateUserInfo())
			{
				new AlarmBroadCast().setAlarm(LoginActivity.this); //登陆成功后，发出通知栏信息  
				//sendLogs();	//发送日志信息进行统计
				//欢迎界面延迟2秒进入主界面
		        new Handler().postDelayed(new Runnable() {
		            @Override
		            public void run() {
		            	if(fFirstLogin){
		            		//跳到手势页面
		            		UIHelper.showLock(LoginActivity.this,fItemNumber,fItemName,fFirstLogin);
		            	}
		            	else{
		            		// 跳转到主页面
			            	UIHelper.showHome(LoginActivity.this,fItemNumber,fItemName);
		            	}
		            }
		        }, 2000);
			}
		}
		else{
			UIHelper.ToastMessage(LoginActivity.this, R.string.network_not_connected);
		}
    }  

	//登陆事件处理方法
	OnClickListener btnLoginClick = new OnClickListener(){
		@Override
		public void onClick(View v) {
			InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
			//隐藏软键盘
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
			
			if(isNetWork){  //有网络的情况下进行登陆验证
				fItemNumber = fItemNumberEdit.getText().toString();
				fPassword = fPasswordEdit.getText().toString();
				isRememberMe = chb_rememberMe.isChecked();
				
				//验证账号有没有输入
				if(StringUtils.isEmpty(fItemNumber)){
					UIHelper.ToastMessage(v.getContext(), R.string.useinfo_login_tipusername);
					return ;
				}
				//验证密码有没有输入
				if(StringUtils.isEmpty(fPassword)){
					UIHelper.ToastMessage(v.getContext(), R.string.useinfo_login_tippassword);
					return ;
				}
				loginAsyncTask loginTask=new loginAsyncTask();
				loginTask.execute(serviceUrl,fItemNumber,fPassword);
			}
			else{
				UIHelper.ToastMessage(LoginActivity.this, R.string.network_not_connected);
			}	
		}
    };
	
	/**
	 * @ClassName loginAsyncTask
	 * @Description 异步登陆调用
	 * @author 21291
	 * @date 2014年3月31日 下午1:57:35
	 */
	private class loginAsyncTask extends AsyncTask<String, Void, UserInfo> {
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(UserInfo user) {
			super.onPostExecute(user);
			// 销毁等待框
			dialog.dismiss();
			if(user!=null){ //说明登陆成功
				//登陆信息显示
				UIHelper.ToastMessage(getApplicationContext(), R.string.useinfo_login_success);
				//保存登陆信息
				appContext = (AppContext)getApplication();
				appContext.saveLoginInfo(user);
	
				//数据持久化 保存登陆配置信息 私有属性，只有本项目才能存取第一个参数指定的XML文件
				if(updateUserInfo())
				{
					new AlarmBroadCast().setAlarm(LoginActivity.this); //登陆成功后，发出通知栏信息  
					sendLogs();	//发送日志信息进行统计
					
					//欢迎界面延迟2秒进入主界面
			        new Handler().postDelayed(new Runnable() {
			            @Override
			            public void run() {
			            	if(fFirstLogin){
			            		//跳到手势页面
			            		UIHelper.showLock(LoginActivity.this,fItemNumber,fItemName,fFirstLogin);
			            	}
			            	else{
			            		// 跳转到主页面
				            	UIHelper.showHome(LoginActivity.this,fItemNumber,fItemName);
			            	}
			            }
			        }, 2000);
				}
			}
			else {
				UIHelper.ToastMessage(getApplicationContext(), R.string.useinfo_login_faile);
			}
		}
		
		@Override
		protected UserInfo doInBackground(String... params) {			
			return loginVerify(params[0], params[1],params[2]);
		}
	}
	
	/** 
	* @Title: loginVerify 
	* @Description: 获取返回实体
	* @param @param serviceUrl
	* @param @param fItemNumber
	* @param @param fPassword
	* @param @return     
	* @return UserInfo    
	* @throws 
	* @author 21291
	* @date 2014年4月21日 下午12:06:12
	*/
	private UserInfo loginVerify(String serviceUrl,String fItemNumber,String fPassword){		
		UserInfo returnUser=null;
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(LoginActivity.this);
		UserLoginBussiness userLoginBussiness=(UserLoginBussiness)factoryBusiness.getInstance("UserLoginBussiness",serviceUrl);
		UserInfo user=UserInfo.getUserInfo(fItemNumber, fPassword, isRememberMe);
		fItemName=userLoginBussiness.loginVerify(user);
		if(!StringUtils.isEmpty(fItemName)){
			returnUser=UserInfo.getUserInfo();
			returnUser.setFItemNumber(fItemNumber);
			returnUser.setFPassword(fPassword);
			returnUser.setFItemName(fItemName);
			returnUser.setIsRememberMe(isRememberMe);
		}
		return returnUser;
	}
	
	/** 
	* @Title: updateUserInfo 
	* @Description: 更新用户信息
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年12月12日 下午3:08:59
	*/
	private boolean updateUserInfo() {
		Editor editor = sp.edit();
		editor.putString(AppContext.USER_NAME_KEY, fItemNumber);
		editor.putString(AppContext.FITEMNAME_KEY, fItemName);
		editor.putBoolean(AppContext.FLOGIN_IS_FIRST_KEY, false);  //设置登陆过一次
		return editor.commit();
	}
	
	/** 
	* @Title: checkSoftUpdate 
	* @Description: 
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月22日 下午12:36:58
	*/
	private void checkSoftUpdate(){
		UpdateManager.getUpdateManager(LoginActivity.this,AppUrl.URL_API_HOST_ANDROID_SETTINGACTIVITY).checkAppUpdate(false);
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 点击登录时，发送日志记录到服务器
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月31日 上午11:05:52
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_login_module));
		logInfo.setFActionName("login");
		logInfo.setFNote("note");
		UIHelper.sendLogs(LoginActivity.this,logInfo);
	}
	
	@Override
	protected void onRestart() {
		if(!appContext.isNetworkConnected()) //判断网络连接
		{
			isNetWork=false;
		}
		else{
			isNetWork=true;
		}
		super.onRestart();		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) { //返回键
			AppManager.getAppManager().AppExit(LoginActivity.this); // 退出
    	}
    	return super.onKeyDown(keyCode, event);
    }
}
