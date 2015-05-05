package com.dahuatech.app.ui.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppExpiration;
import com.dahuatech.app.R;

/**
 * @ClassName NotificationShow
 * @Description 通知栏显示服务类
 * @author 21291
 * @date 2014年6月3日 上午10:22:34
 */
public class NotificationShow {

	private final static int NOTIFICATION_ID = 1;  //整数ID
	
	/** 
	* @Name: NotificationShowService 
	* @Description:  默认构造函数
	*/
	public NotificationShow(){}

	/** 
	* @Title: showNotification 
	* @Description: 显示通知栏信息
	* @param @param context
	* @param @param noticeCount     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年6月3日 上午10:16:31
	*/
	public void showNotification(Context context,int noticeCount){
		//创建通知 Notification
		Notification notification = null;
		//创建 NotificationManager
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		//是否发出通知
		if(noticeCount==0){
			notificationManager.cancelAll();
			return;
		}	
		String contentTitle = context.getString(R.string.app_name);
		String contentText = "您尚有 " + noticeCount + " 条待办任务";
		//设置点击通知跳转
		Intent intent = new Intent(context, AppExpiration.class);
		intent.putExtra(AppContext.FNOTICE_ALARM_KEY, true);	
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		//将该通知显示为默认View  
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);	
				
		NotificationCompat.Builder builder= new NotificationCompat.Builder(context);			
		builder.setContentTitle(contentTitle);
		builder.setContentText(contentText);
		builder.setWhen(System.currentTimeMillis());
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setAutoCancel(true);  //允许清除通知
		builder.setContentIntent(contentIntent);
		builder.setOngoing(true);  
		notification=builder.build();
		
		//设置点击清除通知
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.icon=R.drawable.ic_launcher;
		
		//设置为默认铃声
		notification.defaults |=Notification.DEFAULT_SOUND;			
		//设置为默认闪光
		notification.defaults |=Notification.DEFAULT_LIGHTS;				
		//设置为震动   <需要加上用户权限android.permission.VIBRATE>
		notification.vibrate = new long[]{100, 250, 100, 500,100,750,100,1000};
	
		//发出通知
		notificationManager.notify(NOTIFICATION_ID, notification);	
	}
	 
	/** 
	* @Title: removeNotification 
	* @Description: 删除通知栏信息
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年6月3日 上午10:16:44
	*/
	public void removeNotification(Context context) {  
	    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);  
	    manager.cancel(NOTIFICATION_ID);  
	}  
}
