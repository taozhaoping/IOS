package com.dahuatech.app.ui.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.NoticeBussiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName NoticeService
 * @Description 后台开启轮询通知
 * @author 21291
 * @date 2014年5月29日 下午3:19:47
 */
public class NoticeService extends IntentService {

	private String fItemNumber;	  //员工号
	private String serviceUrl;  //服务地址
	
	public NoticeService() {
		super("NoticeService");	
	}
	
	@Override
	public void onCreate() {
		super.onCreate();	
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_NOTICESERVICE;	
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if(isNetworkConnected(NoticeService.this)){ //只有在有网络的情况下进行消息通知栏通知
			isStartService(); 
		}
	}
	
	/** 
	* @Title: isNetworkConnected 
	* @Description: 检测网络是否可用
	* @param @param context
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年7月31日 上午9:12:21
	*/
	private boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm!=null){
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if(ni != null && ni.isConnected()){
				// 当前网络是连接的
				if (ni.getState() == NetworkInfo.State.CONNECTED) {
					// 当前所连接的网络可用
					return true;
				}
			}
		}
		return false;
	}
	
	/** 
	* @Title: isStartService 
	* @Description: 首先判断是否启用通知栏通知
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月30日 下午3:41:36
	*/
	private void isStartService(){
		SharedPreferences setting= getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE,MODE_PRIVATE);					
		//判断是否开启通知栏
		if(setting.getBoolean(AppContext.IS_NOTICE_KEY,true)){
			SharedPreferences logining=getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
			fItemNumber=logining.getString(AppContext.USER_NAME_KEY, "");
			if(!StringUtils.isEmpty(fItemNumber)){
				new onNoticeAsync().execute(serviceUrl);
			}	
		}
	}

	/**
	 * @ClassName onNoticeAsync
	 * @Description 异步获取待办任务数量
	 * @author 21291
	 * @date 2014年7月4日 下午3:00:21
	 */
	private class onNoticeAsync extends AsyncTask<String , Void, Integer > {
		
	   @Override
	   protected Integer doInBackground(String... params) {
		   FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(NoticeService.this);
		   NoticeBussiness noticeBussiness=(NoticeBussiness)factoryBusiness.getInstance("NoticeBussiness",params[0]);   
		   return Integer.valueOf(noticeBussiness.getTaskCount(fItemNumber));       
	   }

	   @Override
	   protected void onPostExecute(Integer result) {
		   if(result > 0){  //发送通知栏通知信息
				UIHelper.sendBroadCast(NoticeService.this,result);
		   }
	   }
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
