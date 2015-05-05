package com.dahuatech.app.ui.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.util.Log;

import com.dahuatech.app.AppContext;

/**
 * @ClassName BroadCast
 * @Description 通知信息广播接收器 (主要是为了显示还有多少条待办任务信息未处理)
 * @author 21291
 * @date 2014年4月23日 下午8:04:35
 */
public class AlarmBroadCast extends BroadcastReceiver {
	private final static String TAG="AlarmBroadCast";
	private int waitTaskCount; //待办任务数量
	private PowerManager.WakeLock moWakeLock;
	private WifiManager.WifiLock moWifiLock;

	@Override
	public void onReceive(Context context, Intent intent) {
		acquireLocks(context);
		String ACTION_NAME = intent.getAction();
		if("com.dahuatech.app.action.APPWIDGET_UPDATE".equals(ACTION_NAME)){
			waitTaskCount = intent.getIntExtra(AppContext.BROADCAST_WAITTASKCOUNT_KEY, 0);
			new NotificationShow().showNotification(context, waitTaskCount); //通知栏显示
		}
		releaseLocks();
	} 
	
	/** 
	* @Title: setAlarm 
	* @Description: 设置闹钟定时通知 间隔重复调用  默认间隔时间为1个小时
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年6月3日 下午4:46:09
	*/
	@SuppressWarnings("static-access")
	public void setAlarm(Context context){
		try {
			Intent mIntent = new Intent(context, NoticeService.class);   
			PendingIntent sender=PendingIntent.getService(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
			Calendar calendar = Calendar.getInstance();  
			calendar.setTimeInMillis(System.currentTimeMillis());
			int mHour = calendar.get(Calendar.HOUR_OF_DAY);  //当前小时
			int mMinute = calendar.get(Calendar.MINUTE); //当前分钟
			int mSecond=calendar.get(Calendar.SECOND);	 //当前多少秒
			int mDelayed=60 * 60;                       
			
			// 这里时区需要设置一下，不然会有8个小时的时间差  
			calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
			calendar.set(Calendar.MINUTE, mMinute);
		 	calendar.set(Calendar.HOUR_OF_DAY, mHour);
		 	calendar.set(Calendar.SECOND, mSecond);
		 	calendar.set(Calendar.MILLISECOND, 0);
		 
			calendar.add(calendar.SECOND, mDelayed);  //过一个小时后开始执行
			long atTimeInMillis = calendar.getTimeInMillis();   //触发时间
			long intervalDay = 1000L * 60 * 60;  //相隔一个小时
			
			AlarmManager alarmMgr=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);		
			alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, atTimeInMillis, intervalDay, sender);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
			Log.i(TAG,df.format(calendar.getTime())); 
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "error:"+e.toString()); 
		}
	}
	
	/** 
	* @Title: cancelAlarm 
	* @Description: 取消闹钟
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年6月3日 下午2:15:51
	*/
	public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, NoticeService.class);
        PendingIntent sender = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
	
    /** 
    * @Title: acquireLocks 
    * @Description: 保持屏幕唤醒
    * @param @param context     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014年6月3日 下午4:38:58
    */
    private void acquireLocks(Context context)
    {
        try {
            // Acquire a wake lock to prevent the device from entering "deep sleep"
            PowerManager oPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            this.moWakeLock = oPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
            this.moWakeLock.acquire();
            // Acquire a WiFi lock to ensure WiFi is enabled
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            this.moWifiLock = wm.createWifiLock(WifiManager.WIFI_MODE_FULL, TAG);
            this.moWifiLock.acquire();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    /** 
    * @Title: releaseLocks 
    * @Description: 释放屏幕唤醒资源
    * @param      
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014年6月3日 下午4:39:38
    */
    private void releaseLocks()
    {
        try {
            this.moWakeLock.release();
            this.moWifiLock.release();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
